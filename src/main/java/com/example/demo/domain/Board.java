package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Board {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Board() {
    }

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Board(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
