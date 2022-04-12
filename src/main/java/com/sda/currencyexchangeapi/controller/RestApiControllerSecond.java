package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.service.CurrencyServiceSecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class RestApiControllerSecond {

    private CurrencyServiceSecond currencyService;

    @Autowired
    public RestApiControllerSecond(CurrencyServiceSecond currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("api/latest")
    public String getLatestCurrency(@RequestParam(name = "base")String base, @RequestParam("target") String target)
            throws IOException, URISyntaxException, InterruptedException {

        return currencyService.getLatestCurrencyRate(base, target).toString();
    }
}
