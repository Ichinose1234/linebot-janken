// src/main/java/com/example/linebot/presentation/replier/SenrekiReply.java

package com.example.linebot.presentation.replier;

import com.example.linebot.service.Senreki;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

/**
 * 戦歴のメッセージを生成する
 */
public class SenrekiReply implements Replier {

    public static final String MESSAGE_FORMAT = "あなたは %d 戦中 %d 勝 (勝率 %d パーセント) です";

    private final Senreki senreki;

    public SenrekiReply(Senreki senreki) {
        this.senreki = senreki;
    }

    @Override
    public Message reply() {
        // recordの各値を取得する
        int gameCount = senreki.gameCount();
        int winCount = senreki.jigunWinCount();
        // 勝率をパーセント表記にする (例: 0.666... -> 66)
        int winRatePercent = (int) (senreki.jibunWinRate() * 100);

        // フォーマットに従ってメッセージ文字列を生成する
        String message = String.format(MESSAGE_FORMAT, gameCount, winCount, winRatePercent);

        return new TextMessage(message);
    }
}