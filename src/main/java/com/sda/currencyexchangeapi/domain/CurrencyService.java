package com.sda.currencyexchangeapi.domain;


import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;

import com.sda.currencyexchangeapi.repository.CurrencyRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final ExchangeRateApiConnection exchangeRateApi;
    private final CurrencyMapper currencyMapper;
    private final ExchangeNbpApiConnection exchangeNbpApi;

    private final Logger logger = LogManager.getLogger(CurrencyService.class);

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, ExchangeRateApiConnection exchangeRateApi, CurrencyMapper currencyMapper, ExchangeNbpApiConnection exchangeNbpApi) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateApi = exchangeRateApi;
        this.currencyMapper = currencyMapper;
        this.exchangeNbpApi = exchangeNbpApi;
    }


    public CurrencyDto getLatestCurrencyRate(String base, String target, String date) {
        Currency requestedCurrency = currencyRepository
                .findByBaseAndTargetAndDate(base, target, Date.valueOf(date));

        if (requestedCurrency != null) {
            logger.info("Currency loaded from data base.");
            return currencyMapper.map(requestedCurrency);
        }

        Currency currencyFromExchangeApi;

        if(base.equalsIgnoreCase("PLN") && !target.equals(base)){
            currencyFromExchangeApi = exchangeNbpApi.getPlnCurrency(base, target, date);
        }else {
            currencyFromExchangeApi = exchangeRateApi.getCurrency(base, target, date);
        }

        if(currencyFromExchangeApi != null) {
            logger.info("Currency loaded from external api.");
            return currencyMapper.map(currencyRepository.save(currencyFromExchangeApi));
        }

        return null;
    }

}
