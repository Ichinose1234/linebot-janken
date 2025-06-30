// src/main/java/com/example/linebot/presentation/replier/ImageSizeReply.java

package com.example.linebot.presentation.replier;

import com.example.linebot.service.JankenResult;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

/**
 * 画像サイズを返信する
 */
public class ImageSizeReply implements Replier {

    public static final String MESSAGE_FORMAT = "画像サイズ: %d";
    private final JankenResult jankenResult;

    public ImageSizeReply(JankenResult jankenResult) {
        this.jankenResult = jankenResult;
    }

    @Override
    public Message reply() {
        String message = String.format(MESSAGE_FORMAT, jankenResult.imageSize());
        return new TextMessage(message);
    }
}