// src/main/java/com/example/linebot/data/JankenAPI.java

package com.example.linebot.data;

import com.example.linebot.service.JankenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Repository
public class JankenAPI {

    // あなたのEC2の画像の送付先 application.properties ファイルから読み込まれる
    @Value("${janken.api.url}")
    private String API_URL;

    // Springの機能で自動的にインスタンスを生成する
    public JankenResponse playGame(Resource imageResource) {

        if (!imageResource.exists()) {
            throw new IllegalArgumentException("ファイルが存在しません: " + imageResource.getFilename());
        }

        // MultiValueMapで送信データを定義する
        // (HTMLの <input ... /> に対応する)
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", imageResource);

        // じゃんけんAPIに、画像を送信して、JankenResponseにデシリアライズする
        // (HTMLの <form ... /> からの要求送信、および返答の画面反映に対応する)
        JankenResponse response = RestClient.builder()
                .requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build()
                .post()
                .uri(API_URL)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(map)
                .retrieve()
                .body(JankenResponse.class);

        // HTTPの返答メッセージから、返答データを取得する
        // (HTMLで、ブラウザがの画面が表示されることに対応する)
        System.out.println(response);
        return response;
    }

}