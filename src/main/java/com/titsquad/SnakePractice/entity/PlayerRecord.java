package com.titsquad.SnakePractice.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "player_records")
public class PlayerRecord {

    @Id
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "record")
    private Integer record;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "background_color", length = 20)
    private String backgroundColor;

    @Column(name = "difficulty", length = 20)
    private String difficulty;

    // Конструкторы
    public PlayerRecord() {
    }

    public PlayerRecord(String nickname, Integer record, Float volume,
                        String color, String backgroundColor, String difficulty) {
        this.nickname = nickname;
        this.record = record;
        this.volume = volume;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.difficulty = difficulty;
    }

    // Геттеры и сеттеры
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRecord that = (PlayerRecord) o;
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    // toString
    @Override
    public String toString() {
        return "PlayerRecord{" +
                "nickname='" + nickname + '\'' +
                ", record=" + record +
                ", volume=" + volume +
                ", color='" + color + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}