// src/main/java/com/example/linebot/service/SenrekiService.java

package com.example.linebot.service;

import com.example.linebot.data.JankenLog;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SenrekiService {

    private final JankenLog jankenLog;

    public SenrekiService(JankenLog jankenLog) {
        this.jankenLog = jankenLog;
    }

    public Senreki calcSenreki() {
        // データベースから永続化されたJankenResponseのリストを取得する
        List<JankenResponse> jankenResponses = jankenLog.selectAll();

        // (1) ゲーム回数をJankenResponseから計算する
        int gameCount = jankenResponses.size();

        // (2) 自分が勝った回数をJankenResponseから計算する
        //    変数名を指示書に合わせて jigunWinCount に変更
        long jigunWinCountLong = jankenResponses.stream()
                .filter(response -> response.kekka().equals("勝ち"))
                .count();
        int jigunWinCount = (int) jigunWinCountLong;

        // (3) ゲーム回数・自分が勝った回数から自分の勝率(float)を計算する
        //     変数名を指示書に合わせて jibunWinRate に変更
        float jibunWinRate = 0.0f;
        if (gameCount > 0) {
            jibunWinRate = (float) jigunWinCount / gameCount;
        }

        // (4) 修正したSenrekiインスタンスを生成して戻り値にする
        return new Senreki(gameCount, jigunWinCount, jibunWinRate);
    }
}