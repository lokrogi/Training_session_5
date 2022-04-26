package com.sda.currencyexchangeapi.domain;


import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;

import com.sda.currencyexchangeapi.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Slf4j
@Service
public class CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final ExchangeRateApiConnection exchangeRateApi;
    private final CurrencyMapper currencyMapper;
    private final NbpApiConnection nbpApi;


    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, ExchangeRateApiConnection exchangeRateApi, CurrencyMapper currencyMapper, NbpApiConnection nbpApi) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateApi = exchangeRateApi;
        this.currencyMapper = currencyMapper;
        this.nbpApi = nbpApi;
    }


    public CurrencyDto getLatestCurrencyRate(String base, String target, String date) {
        Date validDate;
        try{
            validDate = Date.valueOf(date);
        }catch (IllegalArgumentException e) {
            throw new DateFormatException("Invalid date format");
        }

        Currency requestedCurrency = currencyRepository
                .findByBaseAndTargetAndDate(base, target, validDate);

        if (requestedCurrency != null) {
            log.info("Currency loaded from data base.");
            return currencyMapper.map(requestedCurrency);
        }

        Currency currencyFromExchangeApi;

        if(base.equalsIgnoreCase("PLN") && !target.equals(base)){
            currencyFromExchangeApi = nbpApi.getPlnCurrency(target, date);
            log.info("Currency loaded from NBP api.");
        }else {
            currencyFromExchangeApi = exchangeRateApi.getCurrency(base, target, date);
            log.info("Currency loaded from exchange rate api.");
        }

        return currencyMapper.map(currencyRepository.save(currencyFromExchangeApi));

    }

}
