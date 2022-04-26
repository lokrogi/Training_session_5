package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import com.sda.currencyexchangeapi.model.CurrencyDto;
import com.sda.currencyexchangeapi.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private static CurrencyRepository currencyRepository;
    @Mock
    private static ExchangeRateApiConnection exchangeRateApi;
    @Mock
    private static CurrencyMapper currencyMapper;
    @Mock
    private static ExchangeNbpApiConnection exchangeNbpApi;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void should_return_rate_from_database() {
        Currency testCurrency = new Currency(0, "base", "target", 4.22, Date.valueOf("2022-04-25"));
        when(currencyRepository.findByBaseAndTargetAndDate("base", "target", Date.valueOf("2022-04-25"))).thenReturn(testCurrency);
        when(currencyMapper.map(testCurrency)).thenReturn(new CurrencyDto(4.22));

        CurrencyDto latestCurrencyRate = currencyService.getLatestCurrencyRate("base", "target", "2022-04-25");

        assertEquals(latestCurrencyRate.getRate(), testCurrency.getRate());
    }

    @Test
    public void should_return_rate_from_exchange_rate_api() {
        Currency testCurrency = new Currency(0, "notPln", "target", 4.22, Date.valueOf("2022-04-25"));
        when(exchangeRateApi.getCurrency("notPln", "target", "2022-04-25")).thenReturn(testCurrency);
        when(currencyRepository.save(testCurrency)).thenReturn(testCurrency);
        when(currencyMapper.map(testCurrency)).thenReturn(new CurrencyDto(testCurrency.getRate()));

        CurrencyDto latestCurrencyRate = currencyService.getLatestCurrencyRate("notPln", "target", "2022-04-25");

        verify(exchangeRateApi).getCurrency("notPln", "target", "2022-04-25");
        verifyNoInteractions(exchangeNbpApi);

        assertEquals(latestCurrencyRate.getRate(), testCurrency.getRate());
    }

    @Test
    public void should_return_rate_from_nbp_api() {
        Currency testCurrency = new Currency(0, "PLN", "target", 4.22, Date.valueOf("2022-04-25"));
        when(exchangeNbpApi.getPlnCurrency("target", "2022-04-25")).thenReturn(testCurrency);
        when(currencyRepository.save(testCurrency)).thenReturn(testCurrency);
        when(currencyMapper.map(testCurrency)).thenReturn(new CurrencyDto(testCurrency.getRate()));

        CurrencyDto latestCurrencyRate = currencyService.getLatestCurrencyRate("PLN", "target", "2022-04-25");

        verify(exchangeNbpApi).getPlnCurrency("target", "2022-04-25");
        verifyNoInteractions(exchangeRateApi);

        assertEquals(latestCurrencyRate.getRate(), testCurrency.getRate());
    }

    @Test
    public void should_throw_DateFormatException() {
        assertThrows(DateFormatException.class, () -> {
            currencyService.getLatestCurrencyRate("base", "target", "20222-04-25");
        }, "Invalid date format");
    }
}