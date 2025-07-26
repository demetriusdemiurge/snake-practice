package com.titsquad.SnakePractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "player_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}