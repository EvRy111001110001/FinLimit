package com.evry.FinLimit.component;

import com.evry.FinLimit.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


@Component
//@RequiredArgsConstructor
public class ExternalExchangeRateClient {

    private final RestTemplate restTemplate;

    public ExternalExchangeRateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${twelvedata.api.url}")
    private String apiUrl;
    @Value("${twelvedata.api.key}")
    private String apiKey;

    public BigDecimal fetchExchangeRate(String currencyPair) {
        String[] currencies = currencyPair.split("/");
        if (currencies.length != 2) {
            throw new IllegalArgumentException("Incorrect currency pair format");
        }
        String baseCurrency = currencies[0];
        String targetCurrency = currencies[1];

        String url = String.format("%s/exchange_rate?symbol=%s/%s&apikey=%s",
                apiUrl, baseCurrency, targetCurrency, apiKey);

        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        if (response != null && response.getRate() != null) {
            return response.getRate();
        }

        throw new RuntimeException("Unable to get exchange rate");
    }
}
