package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.controller.RestApiController;
import com.sda.currencyexchangeapi.model.Gold;
import com.sda.currencyexchangeapi.model.GoldDto;
import com.sda.currencyexchangeapi.repository.GoldRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@WebMvcTest(RestApiController.class)
@ExtendWith(MockitoExtension.class)
class GoldServiceTest {

    @Mock
    private static GoldRepository goldRepository;
    @Mock
    private static GoldConnection goldConnection;
    @Mock
    private static GoldMapper goldMapper;

    @InjectMocks
    GoldService goldService;

    @Test
    public void should_return_gold_price_from_database() {
        Gold testGoldPrice = new Gold(0, Date.valueOf("2022-04-25"), 190.91);
        when(goldRepository.findByDate(Date.valueOf("2022-04-25"))).thenReturn(testGoldPrice);
        when(goldMapper.map(testGoldPrice)).thenReturn(new GoldDto(190.91));

        GoldDto checkGoldPrice = goldService.getGoldPrice("2022-04-25");

        assertEquals(checkGoldPrice.getPrice(), 190.91);
    }

    @Test
    public void should_return_gold_price_from_rate_api() {
        Gold testGoldPrice = new Gold(0, Date.valueOf("2022-04-25"), 190.91);
        when(goldConnection.getGold("2022-04-25")).thenReturn(testGoldPrice);
        when(goldRepository.save(testGoldPrice)).thenReturn(testGoldPrice);
        when(goldMapper.map(testGoldPrice)).thenReturn(new GoldDto(190.91));

        GoldDto checkGoldPrice = goldService.getGoldPrice("2022-04-25");

        verify(goldConnection).getGold("2022-04-25");

        assertEquals(checkGoldPrice.getPrice(), 190.91);
    }

    @Test
    public void should_throw_DateFormatException() {
        assertThrows(DateFormatException.class, () -> {
            goldService.getGoldPrice("2022-04-255");
        }, "Invalid date format");
    }



}