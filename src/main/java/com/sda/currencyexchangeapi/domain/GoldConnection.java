package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Gold;
import org.json.JSONArray;
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
public class GoldConnection {

    private static final String stringUrl = "http://api.nbp.pl/api/cenyzlota/";

    private JSONArray getGoldJson(String date) throws URISyntaxException, IOException, InterruptedException {

        String strUrlWithParams = stringUrl + date + "/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(strUrlWithParams))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONArray(response.body());
    }

    public Gold getGold(String date) {
        try {
            JSONArray jsonArray = getGoldJson(date);

            Gold gold = Gold.builder()
                    .date(Date.valueOf(date))
                   // .price(jsonObject.getDouble("cena"))
                    .price(jsonArray.getJSONObject(0).getDouble("cena"))
                    .build();

            return gold;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return null;
        }
    }

}
