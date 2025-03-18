package com.evry.FinLimit.services;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.evry.FinLimit.entity.Limit;
import com.evry.FinLimit.entity.Transaction;
import com.evry.FinLimit.mappers.TransactionMapper;
import com.evry.FinLimit.model.TransactionDTO;
import com.evry.FinLimit.model.TransactionWithLimitDTO;
import com.evry.FinLimit.repositories.LimitRepository;
import com.evry.FinLimit.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that handles business logic for working with transactions.
 * It includes operations for adding transactions to the database, adding a default limit to the client if it does not exist yet
 * getting a list of transactions that have exceeded the limit
 */

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final ExchangeRateService exchangeRateService;
    private final TransactionMapper transactionMapper;


    /**
     * Saves a transaction to the database.
     * Converts the provided {@link TransactionDTO} to an entity
     * and persists it using the repository.
     *
     * <p><b>Note:</b> The account limit check is performed separately
     * to avoid blocking transactions.</p>
     *
     * @param transactionDTO the transaction data transfer object to be saved
     */
    @Transactional
    public void save(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transactionRepository.save(transaction);

        ensureLimitExists(transactionDTO.getAccountFrom());
    }

    /**
     * Ensures that a limit exists for the given account.
     * <p>
     * If no limit is found for the specified account ID, a new limit
     * is created with default values and saved to the repository.
     * </p>
     *
     * <p><b>Thread-safety:</b> This method is synchronized to prevent
     * race conditions when checking and creating limits.</p>
     *
     * @param accountId the ID of the account for which the limit should exist
     */
    private synchronized void ensureLimitExists(Long accountId) {
        if (!limitRepository.existsByAccountFrom(accountId)) {
            Limit newLimit = new Limit();
            newLimit.setAccountFrom(accountId);
            newLimit.setLimitCurrencyShortname(CurrencyShortname.USD);
            newLimit.setLimitSum(BigDecimal.valueOf(1000.00));
            newLimit.setLimitDatetime(LocalDateTime.now());
            limitRepository.save(newLimit);
        }
    }

    /**
     * Retrieves transactions that exceed the account's limits.
     * <p>
     * This method fetches all transactions and limits associated with the given account,
     * then groups transactions by the most relevant limit. Transactions that exceed their
     * corresponding limits are identified and returned.
     * </p>
     *
     * @param accountId the ID of the account for which transactions should be checked
     * @return a list of transactions that exceed the account's limits
     */
    public List<TransactionWithLimitDTO> getTransactionsExceedingLimits(Long accountId) {
        List<TransactionWithLimitDTO> transactions = transactionRepository.findTransactionsWithLimit(accountId);
        List<Limit> accountLimits = limitRepository.findByAccountIdOrderByDateAsc(accountId);

        // Group transactions by the most relevant limit
        Map<Limit, List<TransactionWithLimitDTO>> transactionsByLimit = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> findRelevantLimit(transaction, accountLimits)));

        List<Long> result = new ArrayList<>();
        List<BigDecimal> debts = new ArrayList<>();
        processTransactionsByLimit(transactionsByLimit, result,debts);

        // Collect transactions that exceed the limits
        List<TransactionWithLimitDTO> transactionsResult = new ArrayList<>();
        for(Long r: result){
          for (TransactionWithLimitDTO t : transactions){
              if(r.equals(t.getTransactionId())){
                 transactionsResult.add(t);
              }
          }
        }

        return transactionsResult;
    }

    /**
     * Processes transactions based on their associated limits.
     * <p>
     * Iterates over the grouped transactions and their corresponding limits.
     * If there are existing debts, the available limit is first adjusted
     * by repaying the debt. Then, it calculates the remaining limits for
     * each transaction and updates the result list accordingly.
     * </p>
     *
     * @param transactionsByLimit a map containing limits as keys and their associated transactions as values
     * @param result a list to store transaction IDs that exceed their respective limits
     * @param debts a list of outstanding debts to be considered when processing limits
     */
    public void processTransactionsByLimit(Map<Limit, List<TransactionWithLimitDTO>> transactionsByLimit,
                                            List<Long> result,List<BigDecimal> debts){
        transactionsByLimit.forEach((limit, transactionsLim) -> {
            BigDecimal limitSum = limit.getLimitSum();
            if (!debts.isEmpty()) {
                limitSum = debtRepayment(limitSum,debts);
                calculateRemainingLimits(transactionsLim, limitSum, result,debts);
            } else {
                calculateRemainingLimits(transactionsLim, limitSum, result,debts);
            }
        });

    }

    /**
     * Reduces the available limit by repaying outstanding debts.
     * <p>
     * Iterates through the list of debts, subtracting each one from the available limit.
     * If the limit becomes negative, the remaining debt is stored back in the list,
     * and the negative limit is returned. If all debts are repaid within the available limit,
     * the remaining balance is returned.
     * </p>
     *
     * @param limitSum the available limit before debt repayment
     * @param debts a list of outstanding debts to be repaid
     * @return the remaining limit after debt repayment (maybe negative if the limit was exceeded)
     */
    public BigDecimal debtRepayment(BigDecimal limitSum, List<BigDecimal> debts) {
        Iterator<BigDecimal> iterator = debts.iterator();

        while (iterator.hasNext()) {
            BigDecimal debt = iterator.next();
            limitSum = limitSum.subtract(debt);
            iterator.remove();

            if (limitSum.compareTo(BigDecimal.ZERO) < 0) {
                debts.add(limitSum.abs());
                return limitSum;
            }
        }
        return limitSum; // Returns the remaining limit (maybe negative)

    }

    /**
     * Calculates the remaining limits after processing transactions.
     * <p>
     * Iterates through the list of transactions, converting their amounts to a common currency
     * and subtracting them from the available limit. If the limit becomes negative,
     * the transaction ID is added to the result list. Any remaining negative balance
     * (uncovered amount) is stored as debt.
     * </p>
     *
     * @param transactionsLim a list of transactions associated with the limit
     * @param limit the available limit before processing transactions
     * @param result a list to store transaction IDs that exceed the limit
     * @param debts a list to store any remaining negative balances as debts
     */
    public void calculateRemainingLimits(List<TransactionWithLimitDTO> transactionsLim,
                                          BigDecimal limit,List<Long> result,List<BigDecimal> debts){
        for(TransactionWithLimitDTO t : transactionsLim){
            BigDecimal transactionSum = convertCurrency(t.getSum(),t.getCurrencyName(),t.getTransactionDate());
            limit = limit.subtract(transactionSum);
            if (limit.compareTo(BigDecimal.ZERO) < 0){
                result.add(t.getTransactionId());
            }
        }
        if (limit.compareTo(BigDecimal.ZERO) < 0){
            debts.add(limit.abs());
        }
    }


    /**
     * Finds the most relevant limit for a given transaction.
     * <p>
     * Filters the list of limits to include only those with a date that is not after
     * the transaction date. Then, selects the most recent limit (the one with the latest date).
     * If no matching limit is found, returns {@code null}.
     * </p>
     *
     * @param transaction the transaction for which a relevant limit is being searched
     * @param limits a list of limits associated with the account
     * @return the most recent applicable limit for the transaction, or {@code null} if none is found
     */
    public Limit findRelevantLimit(TransactionWithLimitDTO transaction, List<Limit> limits) {
        return limits.stream()
                .filter(limit -> limit.getLimitDatetime().isAfter(transaction.getTransactionDate())
                        || limit.getLimitDatetime().isEqual(transaction.getTransactionDate())) // Учитываем и равные
                .max(Comparator.comparing(Limit::getLimitDatetime)) // Берём последний возможный лимит
                .orElse(null);
    }

    /**
     * Converts a given amount from one currency to USD using the exchange rate at a specific transaction date.
     * <p>
     * Constructs the currency exchange rate pair using the provided currency and USD.
     * Retrieves the exchange rate for the given date and multiplies the amount by this rate
     * to perform the conversion.
     * </p>
     *
     * @param amount the amount to be converted
     * @param currency the original currency of the amount
     * @param dateTransactional the date of the transaction for which the exchange rate is needed
     * @return the converted amount in USD
     */
    public BigDecimal convertCurrency(BigDecimal amount, CurrencyShortname currency,LocalDateTime dateTransactional) {
        String currencyExchangeRate = currency + "/" + CurrencyShortname.USD;
        Instant instant = dateTransactional.atZone(ZoneId.systemDefault()).toInstant();
        BigDecimal rate = exchangeRateService.getExchangeRate(currencyExchangeRate, instant);
        return amount.multiply(rate);
    }
}
