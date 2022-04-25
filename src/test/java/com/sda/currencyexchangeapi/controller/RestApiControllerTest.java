package com.sda.currencyexchangeapi.controller;

import com.sda.currencyexchangeapi.domain.CurrencyService;
import com.sda.currencyexchangeapi.domain.GoldService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_ok_response_for_latest_with_rate() throws Exception {
        mockMvc.perform(get("/api/latest?base=EUR&target=PLN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").exists());
    }

    @Test
    void should_return_ok_response_for_latest_with_with_invalid_currency_message() throws Exception {
        mockMvc.perform(get("/api/latest?base=EURR&target=PLN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("There is no such currency")));
    }

    @Test
    void should_return_ok_response_for_historical_with_rate() throws Exception {
        mockMvc.perform(get("/api/historical?base=EUR&target=PLN&date=2019-03-14")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").exists());
    }

    @Test
    void should_return_ok_response_for_historical_with_invalid_currency_message() throws Exception {
        mockMvc.perform(get("/api/historical?base=EURR&target=PLN&date=2019-03-14")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("There is no such currency")));
    }

    @Test
    void should_return_ok_response_for_historical_with_invalid_date_format_message() throws Exception {
        mockMvc.perform(get("/api/historical?base=EUR&target=PLN&date=20192-03-14")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Invalid date format")));
    }


}