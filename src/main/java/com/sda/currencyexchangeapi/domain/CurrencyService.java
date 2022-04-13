package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.domain.ExchangeRateApiConnection;
import com.sda.currencyexchangeapi.model.Currency;
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

    private CurrencyRepository currencyRepository;
    private ExchangeRateApiConnection exchangeRateApi;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, ExchangeRateApiConnection exchangeRateApi) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateApi = exchangeRateApi;
    }


    public JSONObject getLatestCurrencyRate(String base, String target) throws IOException, URISyntaxException, InterruptedException {
        Currency requestedCurrency = currencyRepository
                .findByBaseAndTargetAndDate(base, target, Date.valueOf(LocalDate.now()));

        if (requestedCurrency != null) {
            return new JSONObject(requestedCurrency);
        }

        JSONObject jsonObject = exchangeRateApi.getLatestCurrencyExchange(base, target);


        Currency currency = Currency.builder()
                .base(base)
                .target(target)
                .rate(jsonObject.getJSONObject("rates").getDouble(target))
                .date(Date.valueOf(jsonObject.getString("date")))
                .build();

        return new JSONObject(currencyRepository.save(currency));

    }

}
