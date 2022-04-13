package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    public CurrencyDto map(Currency currency) {
        return new CurrencyDto(currency.getRate());
    }
}
