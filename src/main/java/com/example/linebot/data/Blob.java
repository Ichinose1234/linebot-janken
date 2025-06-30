package com.example.linebot.data;

import com.linecorp.bot.messaging.client.MessagingApiBlobClient;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import com.linecorp.bot.client.base.BlobContent;
import com.linecorp.bot.client.base.Result;
import java.io.InputStream;

@Repository
public class Blob {

    // LINEの画像保存場所にアクセスするためのクラス
    // （専用のブラウザのようなもの）
    private MessagingApiBlobClient blob;

    // Springの機能で自動的にインスタンスを生成
    public Blob(MessagingApiBlobClient blob) {
        this.blob = blob;
    }

    public Resource getImageResource(ImageMessageContent imc) throws Exception {
        // 例外をスローするように変更するのを忘れずに

        // 送られたLINEのメッセージID（一つのメッセージにIDがついている）を取得する
        String msgId = imc.id();

        // Blob (Lineの画像保存場所) からメッセージIDと対応する画像の取得準備をする
        Result<BlobContent> blobContentResult =
                blob.getMessageContent(msgId).get();

        try (InputStream is = blobContentResult.body().byteStream()) {
            // 画像をバイトデータとして取得する
            // 画像が期限切れなどの場合には、例外が発生
            LINEImageResource resource = new LINEImageResource(is.readAllBytes());
            return resource;
        }
    }
}