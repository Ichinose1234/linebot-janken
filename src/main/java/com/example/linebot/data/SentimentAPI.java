// src/main/java/com/example/linebot/data/SentimentAPI.java
package com.example.linebot.data;

import com.example.linebot.service.SentimentRequest;
import com.example.linebot.service.SentimentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class SentimentAPI {

    @Value("${sentiment.api.url}")
    private String API_URL;

    private final RestClient restClient;

    public SentimentAPI(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public SentimentResponse analyze(String text) {
        SentimentRequest requestBody = new SentimentRequest(text);

        return restClient.post()
                .uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(SentimentResponse.class);
    }
}