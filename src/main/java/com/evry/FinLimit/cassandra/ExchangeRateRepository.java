package com.evry.FinLimit.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 */
@Repository
public interface ExchangeRateRepository extends CassandraRepository<ExchangeRate, ExchangeRateKey> {

    @Query("SELECT * FROM exchange_rates WHERE currency_pair = ?0 AND date = ?1 LIMIT 1")
    Optional<ExchangeRate> findByCurrencyPairAndDate(String currencyPair, Instant date);

    @Query("SELECT * FROM exchange_rates WHERE currency_pair = ?0 LIMIT 1")
    Optional<ExchangeRate> findLatestByCurrencyPair(String currencyPair);
}
