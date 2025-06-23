// src/main/java/com/example/linebot/service/JankenService.java

package com.example.linebot.service;

import com.example.linebot.data.Blob; // データ層のBlobをimport
import com.example.linebot.data.JankenAPI; // データ層のJankenAPIをimport
import com.linecorp.bot.webhook.model.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class JankenService {

    private final Blob blob;
    private final JankenAPI jankenAPI;

    public JankenService(Blob blob, JankenAPI jankenAPI) {
        this.blob = blob;
        this.jankenAPI = jankenAPI;
    }

    public JankenResult doJanken(ImageMessageContent imc) throws Exception {
        // 1. 画像データを取得する
        Resource imageResource = blob.getImageResource(imc);

        // 2. じゃんけんを実行する
        JankenResponse jankenResponse = jankenAPI.playGame(imageResource);

        // 3. 処理結果を返す (JankenResponseとimageSizeを使ってJankenResultを構築)
        JankenResult jankenResult = new JankenResult(imageResource.contentLength(), jankenResponse);

        return jankenResult;
    }
}