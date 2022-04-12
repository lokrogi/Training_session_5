package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    private CurrencyService currencyService;

    @Autowired
    public RestApiController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


}

