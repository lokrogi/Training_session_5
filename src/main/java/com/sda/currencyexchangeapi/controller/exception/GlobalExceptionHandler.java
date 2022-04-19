package com.sda.currencyexchangeapi.controller.exception;

import com.sda.currencyexchangeapi.domain.CurrencyException;
import com.sda.currencyexchangeapi.domain.ExchangeRateApiConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CurrencyException.class)
    public ErrorResponse handleCurrencyException(CurrencyException exception) {
        log.debug("Currency exception!");
        return new ErrorResponse(exception.getMessage());
    }
}
