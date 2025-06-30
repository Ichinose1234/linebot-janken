// src/main/java/com/example/linebot/presentation/Callback.java

package com.example.linebot.presentation;

import com.example.linebot.presentation.replier.Follow;
import com.example.linebot.presentation.replier.ImageSizeReply;
import com.example.linebot.presentation.replier.JankenReply;
import com.example.linebot.presentation.replier.Parrot;
import com.example.linebot.presentation.replier.SenrekiReply;
import com.example.linebot.service.JankenResult;
import com.example.linebot.service.JankenService;
import com.example.linebot.service.Senreki;
import com.example.linebot.service.SenrekiService;
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
    private final SenrekiService senrekiService; // ← SenrekiServiceを注入するフィールドを追加

    // コンストラクタを修正し、SenrekiServiceも受け取るようにする
    public Callback(JankenService jankenService, SenrekiService senrekiService) {
        this.jankenService = jankenService;
        this.senrekiService = senrekiService;
    }

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        Follow follow = new Follow(event);
        return follow.reply();
    }

    // 文章や画像などのメッセージイベントに対応する
    @EventMapping
    public List<Message> handleMessage(MessageEvent event) throws Exception {
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
        return List.of(
                new ImageSizeReply(jankenResult).reply(),
                new JankenReply(jankenResult).reply()
        );
    }

    public List<Message> handleText(TextMessageContent tmc) {
        String text = tmc.text();
        switch (text) {

            case "戦歴":
                // SenrekiServiceを使って戦歴を計算する
                Senreki senreki = senrekiService.calcSenreki();
                // SenrekiReplyを使って返信メッセージを作成する
                return List.of(new SenrekiReply(senreki).reply());

            default:
                // おうむ返しのメッセージを作る
                Parrot parrot = new Parrot(text);
                return List.of(parrot.reply());
        }
    }
}