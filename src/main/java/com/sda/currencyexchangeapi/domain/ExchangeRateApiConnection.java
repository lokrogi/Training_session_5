package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import org.springframework.web.util.UriBuilder;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;

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

            Currency currency = Currency.builder()
                    .base(base)
                    .target(target)
                    .rate(jsonObject.getJSONObject("rates").getDouble(target))
                    .date(Date.valueOf(jsonObject.getString("date")))
                    .build();

            return currency;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return null;

        }
    }
}
