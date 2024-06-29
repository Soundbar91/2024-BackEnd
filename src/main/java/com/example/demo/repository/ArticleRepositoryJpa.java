package com.example.demo.repository;

import java.util.List;

import com.example.demo.exception.ApplicationException;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;

import static com.example.demo.exception.ErrorCode.*;

@Repository
public class ArticleRepositoryJpa implements ArticleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("from Article", Article.class).getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return entityManager.createQuery("""
                        FROM Article a
                        WHERE a.boardId = :boardId
                        """, Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return entityManager.createQuery("""
                        FROM Article a
                        WHERE a.authorId = :memberId
                        """, Article.class)
                .setParameter("memberId", memberId).
                getResultList();
    }

    @Override
    public Article findById(Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article == null) throw new ApplicationException(ARTICLE_NOT_FOUND);
        return article;
    }

    @Override
    public Article insert(Article article) {
        try {
            entityManager.persist(article);
            return entityManager.find(Article.class, article.getId());
        } catch (PersistenceException e) {
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public Article update(Article article) {
        try {
            return entityManager.merge(article);
        } catch (PersistenceException e) {
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
