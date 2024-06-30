/*
package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.domain.Article;

public class ArticleRepositoryMemory implements ArticleRepository {

    private static final Map<Long, Article> articles = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    @Override
    public List<Article> findAll() {
        return articles.entrySet().stream()
            .map(it -> {
                Article article = it.getValue();
                article.setId(it.getKey());
                return article;
            }).toList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return articles.entrySet().stream()
            .filter(it -> it.getValue().getBoardId().equals(boardId))
            .map(it -> {
                Article article = it.getValue();
                article.setId(it.getKey());
                return article;
            }).toList();
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return articles.entrySet().stream()
            .filter(it -> it.getValue().getAuthorId().equals(memberId))
            .map(it -> {
                Article article = it.getValue();
                article.setId(it.getKey());
                return article;
            }).toList();
    }

    @Override
    public Article findById(Long id) {
        return articles.getOrDefault(id, null);
    }

    @Override
    public Article insert(Article article) {
        long id = autoincrement.getAndIncrement();
        articles.put(id, article);
        article.setId(id);
        return article;
    }

    @Override
    public Article update(Article article) {
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public void deleteById(Long id) {
        articles.remove(id);
    }
}
*/
