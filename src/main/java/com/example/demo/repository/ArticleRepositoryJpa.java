package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.exception.ErrorCode.ARTICLE_NOT_FOUND;

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
        return Optional.ofNullable(entityManager.find(Article.class, id))
                .orElseThrow(() -> new ApplicationException(ARTICLE_NOT_FOUND));
    }

    @Override
    public Article insert(Article article) {
        entityManager.persist(article);
        return entityManager.find(Article.class, article.getId());
    }

    @Override
    public Article update(Article article) {
        return entityManager.merge(article);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
