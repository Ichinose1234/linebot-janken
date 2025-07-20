// src/main/java/com/example/linebot/service/SentimentService.java
package com.example.linebot.service;

import com.example.linebot.data.SentimentAPI;
import org.springframework.stereotype.Service;

@Service
public class SentimentService {

    private final SentimentAPI sentimentAPI;

    public SentimentService(SentimentAPI sentimentAPI) {
        this.sentimentAPI = sentimentAPI;
    }

    public SentimentResponse doAnalyze(String text) {
        return sentimentAPI.analyze(text);
    }
}