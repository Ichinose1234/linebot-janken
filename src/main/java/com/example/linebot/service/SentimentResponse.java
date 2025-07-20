// src/main/java/com/example/linebot/service/SentimentResponse.java
package com.example.linebot.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SentimentResponse(
        String label,
        List<Double> score
) {
}