// src/main/java/com/example/linebot/data/JankenLog.java

package com.example.linebot.data;

import com.example.linebot.service.JankenResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JankenLog {

    // Spring Framework のデータベース接続ライブラリを利用する
    private final JdbcClient jdbcClient;

    // Spring Framework のデータベース接続ライブラリを初期化する
    public JankenLog(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * janken_log テーブルにJankenResponseのデータを記録する (永続化)
     * @param response じゃんけんAPIからの結果
     * @return データベースに記録できた行数
     */
    public int insert(JankenResponse response) {
        String statement = "insert into janken_log VALUES (?, ?, ?)";
        int n = jdbcClient.sql(statement)
                .params(response.jibun(), response.aite(), response.kekka())
                .update();
        return n;
    }

    /**
     * janken_log テーブルに記録された全てのデータを JankenResponse のリストとして取得する
     * @return JankenResponseのリスト
     */
    public List<JankenResponse> selectAll() {
        String statement = """
                select jibun, aite, kekka from janken_log
                where kekka != 'エラー'
                """;
        List<JankenResponse> selected = jdbcClient.sql(statement)
                .query(JankenResponse.class)
                .list();
        return selected;
    }
}