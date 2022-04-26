package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateApiConnectionTest {

    private final ExchangeRateApiConnection exchangeRateApiConnection = new ExchangeRateApiConnection();

    @Test
    void should_return_currency() {
        Currency currency = exchangeRateApiConnection.getCurrency("EUR", "PLN", "2022-04-20");

        assertNotNull(currency.getBase());
        assertNotNull(currency.getTarget());
        assertNotEquals(currency.getRate(),0);
        assertNotNull(currency.getDate());
    }

    @Test
    void should_throw_CurrencyException_when_base_is_invalid() {
        assertThrows(CurrencyException.class, () -> {
           exchangeRateApiConnection.getCurrency("EURR", "PLN", "2022-04-25");
        }, "There is no such currency");
    }

    @Test
    void should_throw_CurrencyException_when_target_is_invalid() {
        assertThrows(CurrencyException.class, () -> {
            exchangeRateApiConnection.getCurrency("EUR", "PLNN", "2022-04-25");
        }, "There is no such currency");
    }
}