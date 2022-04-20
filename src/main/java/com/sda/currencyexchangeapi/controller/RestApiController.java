package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.domain.CurrencyService;

import com.sda.currencyexchangeapi.domain.GoldService;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import com.sda.currencyexchangeapi.model.GoldDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
public class RestApiController {

    private CurrencyService currencyService;
    private GoldService goldService;

    @Autowired
    public RestApiController(CurrencyService currencyService, GoldService goldService) {

        this.currencyService = currencyService;
        this.goldService = goldService;
    }

    @GetMapping("api/latest")
    public CurrencyDto getLatestCurrencyRate(
            @RequestParam(name = "base") String base,
            @RequestParam(name = "target") String target) {

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

    @GetMapping("api/gold")
    public GoldDto getGoldPrice(
            @RequestParam(name = "date") String date) {

        return goldService.getGoldPrice(date);
    }

}

