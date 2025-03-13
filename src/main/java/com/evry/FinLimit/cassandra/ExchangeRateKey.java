package com.evry.FinLimit.cassandra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**

 */
@PrimaryKeyClass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateKey implements Serializable {

    @PrimaryKeyColumn(name = "currency_pair", type = PrimaryKeyType.PARTITIONED)
    private String currencyPair;

    @PrimaryKeyColumn(name = "date", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant date;
}
