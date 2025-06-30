// src/main/java/com/example/linebot/presentation/replier/JankenReply.java

package com.example.linebot.presentation.replier;

import com.example.linebot.service.JankenResult;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

/**
 * じゃんけんの結果を整形して返信する
 */
public class JankenReply implements Replier {

    public static final String MESSAGE_FORMAT = "あなた: %s, 相手: %s\n結果: %s";

    private final JankenResult jankenResult;

    public JankenReply(JankenResult jankenResult) {
        this.jankenResult = jankenResult;
    }

    @Override
    public Message reply() {
        // JankenResultから、APIの応答であるJankenResponseを取り出す
        var response = jankenResult.response();
        // JankenResponseから、それぞれの値を取り出す
        String userHand = response.aite(); // 相手の手（ユーザー）
        String botHand = response.jibun();  // 自分の手（ボット）
        String result = response.kekka();   // 結果

        // フォーマットに従ってメッセージ文字列を生成する
        String message = String.format(MESSAGE_FORMAT, userHand, botHand, result);

        return new TextMessage(message);
    }
}