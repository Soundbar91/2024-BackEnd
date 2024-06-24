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
    @Column(name = "author_id")
    private Long authorId;

    @NotNull
    @Column(name = "board_id")
    private Long boardId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP")
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
