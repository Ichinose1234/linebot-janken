package com.example.linebot.data;

import com.example.linebot.service.JankenResponse;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Repository;

@Repository // Spring Frameworkの指定（ステレオタイプアノテーション）
public class JankenAPI {
    public JankenResponse playGame(Resource imageResource) {
        JankenResponse response = new JankenResponse("未設定", "未設定", "未設定"); // JankenResponseはまだ未定義
        return response;
    }
}