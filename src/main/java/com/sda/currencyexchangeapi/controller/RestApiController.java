package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.domain.CurrencyService;

import com.sda.currencyexchangeapi.domain.GoldService;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import com.sda.currencyexchangeapi.model.GoldDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
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
    public ResponseEntity<CurrencyDto> getLatestCurrencyRate(
            @RequestParam(name = "base") String base,
            @RequestParam(name = "target") String target) {

        String date = String.valueOf(LocalDate.now());

        return ResponseEntity.ok(currencyService.getLatestCurrencyRate(base, target, date));
    }

    @GetMapping("api/historical")
    public ResponseEntity<CurrencyDto> getHistoricalCurrencyRate(
            @RequestParam(name = "base") String base,
            @RequestParam(name = "target") String target,
            @RequestParam(name = "date") String date){

        return ResponseEntity.ok(currencyService.getLatestCurrencyRate(base, target, date));
    }

    @GetMapping("api/gold")
    public ResponseEntity<GoldDto> getGoldPrice(
            @RequestParam(name = "date", required = false) String date) {

        return ResponseEntity.ok(goldService.getGoldPrice(date));
    }

}

