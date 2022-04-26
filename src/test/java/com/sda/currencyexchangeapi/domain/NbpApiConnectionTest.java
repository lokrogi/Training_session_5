package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NbpApiConnectionTest {

    private final NbpApiConnection nbpApiConnection = new NbpApiConnection();

    @Test
    void should_return_currency() {
        Currency currency = nbpApiConnection.getPlnCurrency("EUR", "2022-04-25");

        assertEquals(currency.getBase(), "PLN");
        assertNotNull(currency.getTarget());
        assertNotEquals(currency.getRate(),0);
        assertNotNull(currency.getDate());
    }

    @Test
    void should_throw_CurrencyException() {
        assertThrows(CurrencyException.class, () -> {
            nbpApiConnection.getPlnCurrency( "EURR", "2022-04-25");
        }, "There is no such currency");
    }



}