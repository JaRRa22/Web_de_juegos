package com.springboot.webdejuegos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private User user;

    private long points;

    private LocalDate date;

    private LocalTime time;

    public Result (Long id, Game game, User user){
        this.id = id;
        this.game = game;
        this.user = user;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Result (){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }
}