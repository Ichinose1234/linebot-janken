// src/main/java/com/example/linebot.service/JankenResponse.java

package com.example.linebot.service; // JankenResponseが所属するパッケージ

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // JSONデシリアライズのためのアノテーション

/**
 * じゃんけんAPIからの応答を保持するレコード。
 * JSON形式で {"jibun": ..., "aite": ..., "kekka": ...} のデータに対応。
 * @param jibun 自分の手（Botが出した手）
 * @param aite 相手の手（ユーザーが出した手）
 * @param kekka 結果（勝ち、負け、あいこ）
 */
@JsonIgnoreProperties(ignoreUnknown = true) // JSONの未知のプロパティを無視する設定
public record JankenResponse(
        String jibun,
        String aite,
        String kekka
) {
    // レコードはコンストラクタやゲッターが自動生成されるため、通常はこれだけで十分です。
    // 必要に応じて、追加のメソッドなどをここに記述することもできます。
}