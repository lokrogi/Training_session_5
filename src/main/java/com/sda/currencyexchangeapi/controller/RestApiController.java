package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.domain.CurrencyService;

import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;


@RestController
public class RestApiController {

    private CurrencyService currencyService;

    @Autowired
    public RestApiController(CurrencyService currencyService) {

        this.currencyService = currencyService;
    }

    @GetMapping("api/latest")
    public CurrencyDto getLatestCurrencyRate(@RequestParam(name = "base") String base, @RequestParam(name = "target") String target) {

        String date = String.valueOf(LocalDate.now());
        return currencyService.getLatestCurrencyRate(base, target, date);
    }

    @GetMapping("api/historical")
    public CurrencyDto getHistoricalCurrencyRate(
            @RequestParam(name = "base") String base,
            @RequestParam(name = "target") String target,
            @RequestParam(name = "date") String date){

        return currencyService.getLatestCurrencyRate(base, target, date);
    }

}

