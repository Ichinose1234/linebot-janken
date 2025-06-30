// src/main/java/com/example/linebot/service/Senreki.java

package com.example.linebot.service;

/**
 * 戦歴の計算結果を格納するレコード
 * @param gameCount これまでのゲーム回数
 * @param jigunWinCount 自分が勝った回数
 * @param jibunWinRate 自分の勝率
 */
public record Senreki(int gameCount, int jigunWinCount, float jibunWinRate) {
}