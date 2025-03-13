package com.evry.FinLimit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**

 */

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_from")
    private Long accountFrom;

    @Column(name = "account_to")
    private Long accountTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_shortname")
    private CurrencyShortname currencyName;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "expense_category")
    private  String expenseCategory;

    @Column(name = "datetime")
    private LocalDateTime datetime;
}
