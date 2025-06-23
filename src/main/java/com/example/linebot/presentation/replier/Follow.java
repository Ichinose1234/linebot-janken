package com.example.linebot.presentation.replier;

import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.webhook.model.FollowEvent;

public class Follow implements Replier {

    private FollowEvent event;

    public Follow(FollowEvent event) {
        this.event = event;
    }

    @Override
    public Message reply() {
        String userId = this.event.source().userId();
        String text = String.format("あなたのユーザーIDは %s", userId);
        return new TextMessage(text);
    }

}
