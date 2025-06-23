// src/main/java/com/example/linebot/presentation/Callback.java

package com.example.linebot.presentation;

import com.example.linebot.presentation.replier.Follow;
import com.example.linebot.presentation.replier.Parrot; // Parrotの正しいパッケージ
// import com.example.linebot.presentation.replier.Replier; // Replierインターフェースを直接使わない場合は削除可能

import com.example.linebot.service.JankenResult; // JankenResultをimport
import com.example.linebot.service.JankenService; // JankenServiceをimport
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.FollowEvent;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    private final JankenService jankenService;

    public Callback(JankenService jankenService) {
        this.jankenService = jankenService;
    }

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }

    // 文章や画像などのメッセージイベントに対応する
    @EventMapping
    public List<Message> handleMessage(MessageEvent event) throws Exception { // throws Exception は必須
        MessageContent mc = event.message();
        switch (mc) {
            case TextMessageContent tmc:
                return handleText(tmc);
            case ImageMessageContent imc:
                return handleJanken(imc);
            default:
                throw new RuntimeException("対応していないメッセージ");
        }
    }

    public List<Message> handleJanken(ImageMessageContent imc) throws Exception {
        JankenResult jankenResult = jankenService.doJanken(imc);

        // JankenResultの内容を文字列化して返信するよう修正
        return List.of(new TextMessage(jankenResult.toString()));
    }

    public List<Message> handleText(TextMessageContent tmc) {
        String text = tmc.text();
        switch (text) {
            case "戦歴":
                // 何もせずに default に進む
            default:
                // おうむ返しのメッセージを作る
                Parrot parrot = new Parrot(text);
                return List.of(parrot.reply());
        }
    }
}