// src/main/java/com/example/linebot/service/JankenService.java

package com.example.linebot.service;

import com.example.linebot.data.Blob;
import com.example.linebot.data.JankenAPI;
import com.example.linebot.data.JankenLog; // JankenLogDao から JankenLog に変更
import com.linecorp.bot.webhook.model.ImageMessageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class JankenService {

    // データ層：LINEのデータ格納領域にアクセスするクラス
    private final Blob blob;
    // データ層：みなさんのAWS EC2のじゃんけんプログラムにアクセスするクラス
    private final JankenAPI jankenAPI;
    // データ層：データベースの janken_log にアクセスするクラス
    private final JankenLog jankenLog;

    // コンストラクタを修正
    public JankenService(Blob blob, JankenAPI jankenAPI, JankenLog jankenLog) {
        this.blob = blob;
        this.jankenAPI = jankenAPI;
        this.jankenLog = jankenLog;
    }

    public JankenResult doJanken(ImageMessageContent imc)
            throws Exception {
        // 画像データを取得する
        Resource imageResource = blob.getImageResource(imc);

        // じゃんけんを実行する
        JankenResponse jankenResponse = jankenAPI.playGame(imageResource);

        // じゃんけんの結果を永続化する
        jankenLog.insert(jankenResponse);

        // 処理結果を返す
        JankenResult jankenResult =
                new JankenResult(imageResource.contentLength(), jankenResponse);
        return jankenResult;
    }
}