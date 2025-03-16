package com.evry.FinLimit.services;

import com.evry.FinLimit.cassandra.ExchangeRate;
import com.evry.FinLimit.cassandra.ExchangeRateKey;
import com.evry.FinLimit.cassandra.ExchangeRateRepository;
import com.evry.FinLimit.component.ExternalExchangeRateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
/**
 *Клиент для запроса курса валют из внешнего API
 */
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExternalExchangeRateClient externalClient;

    public BigDecimal getExchangeRate(String currencyPair, Instant date) {
        return exchangeRateRepository.findByCurrencyPairAndDate(currencyPair, date)
                .map(ExchangeRate::getCloseRate)
                .or(() -> exchangeRateRepository.findLatestByCurrencyPair(currencyPair)
                        .map(ExchangeRate::getCloseRate))
                .orElseGet(() -> {
                    BigDecimal newRate = externalClient.fetchExchangeRate(currencyPair);
                    BigDecimal prevRate = exchangeRateRepository.findLatestByCurrencyPair(currencyPair)
                            .map(ExchangeRate::getCloseRate)
                            .orElse(BigDecimal.ZERO);

                    ExchangeRate exchangeRate = new ExchangeRate(
                            new ExchangeRateKey(currencyPair, date), newRate, prevRate
                    );

                    exchangeRateRepository.save(exchangeRate);
                    return newRate;
                });
    }
}
