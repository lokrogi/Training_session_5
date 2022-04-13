package com.sda.currencyexchangeapi.domain;


import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;

import com.sda.currencyexchangeapi.domain.ExchangeRateApiConnection;
import com.sda.currencyexchangeapi.model.Currency;

import com.sda.currencyexchangeapi.repository.CurrencyRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final ExchangeRateApiConnection exchangeRateApi;
    private final CurrencyMapper currencyMapper;

    private final Logger logger = LogManager.getLogger(CurrencyService.class);

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, ExchangeRateApiConnection exchangeRateApi, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateApi = exchangeRateApi;
        this.currencyMapper = currencyMapper;
    }


    public CurrencyDto getLatestCurrencyRate(String base, String target, String date) {
        Currency requestedCurrency = currencyRepository
                .findByBaseAndTargetAndDate(base, target, Date.valueOf(date));

        if (requestedCurrency != null) {
            logger.info("Currency loaded from data base.");
            return currencyMapper.map(requestedCurrency);
        }

        Currency currencyFromExchangeApi = exchangeRateApi.getCurrency(base, target, date);

        if(currencyFromExchangeApi != null) {
            logger.info("Currency loaded from external api.");
            return currencyMapper.map(currencyRepository.save(currencyFromExchangeApi));
        }

        return null;




    }

}
