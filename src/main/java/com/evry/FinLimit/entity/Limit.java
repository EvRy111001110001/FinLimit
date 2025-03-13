package com.evry.FinLimit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 */
@Entity
@Getter
@Setter
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_from")
    private Long accountFrom;

    @Column(name = "limit_sum")
    private BigDecimal limitSum;

    @Column(name = "limit_datetime")
    private LocalDateTime limitDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "limit_currency_shortname")
    private CurrencyShortname limitCurrencyShortname;


}
