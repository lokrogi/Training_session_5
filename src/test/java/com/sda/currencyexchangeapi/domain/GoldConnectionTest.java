package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.Gold;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldConnectionTest {

    private final GoldConnection goldConnection = new GoldConnection();

    @Test
    void should_return_gold() {

        Gold gold = goldConnection.getGold("2022-04-21");

        assertNotNull(gold.getDate());
        assertNotNull(gold.getPrice());
    }

    @Test
    void should_return_invalid_date_exception() {
        assertThrows(DateFormatException.class, () -> {
            goldConnection.getGold("1882-04-21");
        }, "Given date is out of range");

    }

}