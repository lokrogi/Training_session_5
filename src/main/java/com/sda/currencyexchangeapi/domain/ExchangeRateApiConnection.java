package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import org.springframework.web.util.UriBuilder;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;

@Slf4j
@Component
public class ExchangeRateApiConnection {

    private static final String stringUrl = "https://api.exchangerate.host/";

    private JSONObject getCurrencyExchangeJson(String base, String target, String date) throws URISyntaxException, IOException, InterruptedException {
        String strUrlWithParams = stringUrl + date + "?base=" + base + "&symbols=" + target;

        HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(strUrlWithParams))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }

    public Currency getCurrency(String base, String target, String date) {
        try {
            JSONObject jsonObject = getCurrencyExchangeJson(base, target, date);

            if(!base.equalsIgnoreCase(jsonObject.getString("base"))) {
                throw new CurrencyException("There is no such currency");
            }

            return Currency.builder()
                    .base(base)
                    .target(target)
                    .rate(jsonObject.getJSONObject("rates").getDouble(target))
                    .date(Date.valueOf(jsonObject.getString("date")))
                    .build();

        } catch (JSONException | URISyntaxException | InterruptedException | IOException e) {
            throw new CurrencyException("There is no such currency");
        }
    }
}
