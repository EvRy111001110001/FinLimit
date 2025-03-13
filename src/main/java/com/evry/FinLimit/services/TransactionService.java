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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final ExchangeRateService exchangeRateService;
    private final TransactionMapper transactionMapper;


    @Transactional
    public void save(TransactionDTO transactionDTO) {
        Long accountId = transactionDTO.getAccountFrom();
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        if (!limitRepository.existsByAccountFrom(accountId)) {

            Limit newLimit = new Limit();
            newLimit.setAccountFrom(accountId);
            newLimit.setLimitCurrencyShortname(CurrencyShortname.USD);
            newLimit.setLimitSum(BigDecimal.valueOf(1000.00));
            newLimit.setLimitDatetime(LocalDateTime.now());
            limitRepository.save(newLimit);
        }

        transactionRepository.save(transaction);
    }

    public List<TransactionWithLimitDTO> getTransactionsExceedingLimits(Long accountId) {
        List<TransactionWithLimitDTO> transactions = transactionRepository.findTransactionsWithLimit(accountId);
        List<Limit> accountLimits = limitRepository.findByAccountIdOrderByDateAsc(accountId);

        Map<Limit, List<TransactionWithLimitDTO>> transactionsByLimit = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> findRelevantLimit(transaction, accountLimits)));//здесь действие проходит

        List<Long> result = new ArrayList<>();
        List<BigDecimal> debts = new ArrayList<>();
        processTransactionsByLimit(transactionsByLimit, result,debts);
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

    private void processTransactionsByLimit(Map<Limit, List<TransactionWithLimitDTO>> transactionsByLimit,
                                            List<Long> result,List<BigDecimal> debts){
        transactionsByLimit.forEach((limit, transactionsLim) -> {
            BigDecimal limitSum = limit.getLimitSum();
            if (!debts.isEmpty()) {
                limitSum = debtRepayment(limitSum,debts);
                log.info("limit " + limitSum.toString());
                calculateRemainingLimits(transactionsLim, limitSum, result,debts);
                log.info("result size =" + result.size());
                log.info("debts size =" + debts.size());
            } else {
                calculateRemainingLimits(transactionsLim, limitSum, result,debts);
            }
        });

    }

    private BigDecimal debtRepayment(BigDecimal limitSum, List<BigDecimal> debts) {
        Iterator<BigDecimal> iterator = debts.iterator();
        log.info("iterator =" + iterator.toString());

        while (iterator.hasNext()) {
            BigDecimal debt = iterator.next();
            limitSum = limitSum.subtract(debt);
            iterator.remove(); // Удаляем долг после вычитания

            if (limitSum.compareTo(BigDecimal.ZERO) < 0) {
                debts.add(limitSum.abs()); // Добавляем новый долг, если лимита не хватило
                return limitSum; // Возвращаем отрицательный остаток
            }
        }
        log.info("limitSum =" + limitSum.toString());
        return limitSum; // Возвращаем оставшийся лимит (может быть отрицательным)

    }

    private void calculateRemainingLimits(List<TransactionWithLimitDTO> transactionsLim,
                                          BigDecimal limit,List<Long> result,List<BigDecimal> debts){
        for(TransactionWithLimitDTO t : transactionsLim){
            BigDecimal transactionSum = convertCurrency(t.getSum(),t.getCurrencyName(),t.getTransactionDate());
            limit = limit.subtract(transactionSum);
            if (limit.compareTo(BigDecimal.ZERO) < 0){
                result.add(t.getTransactionId());
                log.info("Transaction Id =" + t.getTransactionId());
            }
        }
        if (limit.compareTo(BigDecimal.ZERO) < 0){
            debts.add(limit.abs());
            log.info("limit =" + limit.toString());
        }
    }

    private Limit findRelevantLimit(TransactionWithLimitDTO transaction, List<Limit> limits) { // сортировка лимитов
       return limits.stream()
        .filter(limit -> !limit.getLimitDatetime().isAfter(transaction.getTransactionDate()))
        .max(Comparator.comparing(Limit::getLimitDatetime))
        .orElse(null);
    }

    public BigDecimal convertCurrency(BigDecimal amount, CurrencyShortname currency,LocalDateTime dateTransactional) {
        String currencyExchangeRate = currency + "/" + CurrencyShortname.USD;
        Instant instant = dateTransactional.atZone(ZoneId.systemDefault()).toInstant();
        BigDecimal rate = exchangeRateService.getExchangeRate(currencyExchangeRate, instant);
        return amount.multiply(rate);
    }
}
