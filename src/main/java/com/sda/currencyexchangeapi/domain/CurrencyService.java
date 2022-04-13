package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import com.sda.currencyexchangeapi.repository.CurrencyRepository;
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
            return currencyMapper.map(requestedCurrency);
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = exchangeRateApi.getCurrencyExchange(base, target, date);

            Currency currency = Currency.builder()
                    .base(base)
                    .target(target)
                    .rate(jsonObject.getJSONObject("rates").getDouble(target))
                    .date(Date.valueOf(jsonObject.getString("date")))
                    .build();

            return currencyMapper.map(currencyRepository.save(currency));
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return null;
        }
    }

}
