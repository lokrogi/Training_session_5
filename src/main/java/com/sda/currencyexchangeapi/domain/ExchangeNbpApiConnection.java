package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Currency;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;

@Component
public class ExchangeNbpApiConnection {

    private static final String stringUrl = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public JSONObject getPlnCurrencyExchangeJson(String target, String date) throws URISyntaxException, IOException, InterruptedException {

        String strUrlWithParams = stringUrl + target + "/" + date + "/?format=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(strUrlWithParams))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }

    public Currency getPlnCurrency(String base, String target, String date) {
        try {
            JSONObject jsonObject = getPlnCurrencyExchangeJson(target, date);

            Currency currency = Currency.builder()
                    .base(base)
                    .target(target)
                    .rate(jsonObject.getJSONArray("rates").getJSONObject(0).getDouble("bid"))
                    .date(Date.valueOf(jsonObject.getJSONArray("rates").getJSONObject(0).getString("effectiveDate")))
                    .build();

            double temp = currency.getRate();
            currency.setRate(1/temp);

            return currency;
        } catch (URISyntaxException | InterruptedException | IOException |JSONException e) {
            throw new CurrencyException("There is no such currency");
        }
    }
}
