package com.example.linebot.presentation.replier;

import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

// おうむ返しをする
public class Parrot implements Replier {

    private String text;

    public Parrot(String text) {
        this.text = text;
    }

    public Message reply() {
        return new TextMessage(text);
    }
}