package com.example.linebot.data;

import com.linecorp.bot.webhook.model.ImageMessageContent;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;

import org.springframework.stereotype.Repository;

@Repository // Spring Frameworkの指定（ステレオタイプアノテーション）
public class Blob {
    public Resource getImageResource(ImageMessageContent imc) throws Exception {
        // 画像データを取得する (現在はコメントアウトだが、今後実装していく)

        Resource resource = new ByteArrayResource(new byte[0]); // 仮のResourceを返す
        return resource;
    }
}