package com.example.linebot.presentation.replier;

import com.example.linebot.service.SentimentResponse;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

public class SentimentReply implements Replier {

    private final SentimentResponse response;

    public SentimentReply(SentimentResponse response) {
        this.response = response;
    }

    @Override
    public Message reply() {
        String labelText;
        // scoreを書き換え可能な変数として取り出す
        double score = response.score().get(0);

        // Flaskから返ってきた "label" の値によって表示を切り替える
        switch (response.label()) {
            case "1":
                labelText = "ポジティブ";
                // ★ポジティブの場合、スコアは「1 - ネガティブの確率」で計算し直す
                score = 1.0 - score;
                break;
            case "0":
                labelText = "ネガティブ";
                // ★ネガティブの場合、スコアは正しいので何もしない
                break;
            default:
                labelText = "不明";
                break;
        }

        // 表示するメッセージを組み立てる（変数のscoreを使うように変更）
        String messageText = String.format(
                "感情分析結果\nラベル: %s\nスコア: %.4f",
                labelText,
                score
        );

        return new TextMessage(messageText);
    }
}