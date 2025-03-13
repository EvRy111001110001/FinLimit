package com.evry.FinLimit.cassandra;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.math.BigDecimal;

/**
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("exchange_rates")
public class ExchangeRate {

    @PrimaryKey
    private ExchangeRateKey key;

    @Column("close_rate")
    private BigDecimal closeRate;

    @Column("previous_close")
    private BigDecimal previousClose;
}