package com.evry.FinLimit.model;

import com.evry.FinLimit.entity.CurrencyShortname;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**

 */
@Getter
@Setter
@Schema(description = "Transaction Response")
public class TransactionWithLimitDTO {

    private Long transactionId;
    private Long accountFrom;
    private Long accountTo;
    private CurrencyShortname currencyName;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime transactionDate;
    private LocalDateTime limitDate;
    private BigDecimal limitAmount;
    private CurrencyShortname limitCurrency;

    public TransactionWithLimitDTO(
            Long transactionId,
            Long accountFrom,
            Long accountTo,
            String currencyName,
            BigDecimal sum,
            String expenseCategory,
            Timestamp transactionDate,
            Timestamp limitDate,
            BigDecimal limitAmount,
            String limitCurrency
    ) {
        this.transactionId = transactionId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyName = CurrencyShortname.valueOf(currencyName); // строку в ENUM
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.transactionDate = transactionDate != null ? transactionDate.toLocalDateTime() : null; // Конвертируем Timestamp -> LocalDateTime
        this.limitDate = limitDate != null ? limitDate.toLocalDateTime() : null; // Конвертируем Timestamp -> LocalDateTime
        this.limitAmount = limitAmount;
        this.limitCurrency = CurrencyShortname.valueOf(limitCurrency); // строку в ENUM
    }
}

