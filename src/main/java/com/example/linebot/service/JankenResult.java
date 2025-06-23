// src/main/java/com/example/linebot/service/JankenResult.java

package com.example.linebot.service;

import com.example.linebot.service.JankenResponse; // JankenResponseをインポート

public record JankenResult(
        long imageSize,
        JankenResponse response
) {


}
