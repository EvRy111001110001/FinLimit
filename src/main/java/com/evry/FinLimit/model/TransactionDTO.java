package com.evry.FinLimit.model;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**

 */
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Transaction Request")
public class TransactionDTO {
    @JsonIgnore
    private Long id;
    private Long accountFrom;
    private Long accountTo;
    private CurrencyShortname currencyName;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
}
