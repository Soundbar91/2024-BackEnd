package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Article {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long authorId;

    @NotNull
    private Long boardId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    public Article() {
    }

    public Article(
        Long id,
        Long authorId,
        Long boardId,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Article(Long authorId, Long boardId, String title, String content) {
        this.id = null;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(Long boardId, String title, String description) {
        this.boardId = boardId;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }

}
