package com.example.demo.repository;

import java.util.List;

import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;

import static com.example.demo.exception.ErrorCode.*;

@Repository
public class ArticleRepositoryJpa implements ArticleRepository {

    private final EntityManager entityManager;
    private final EntityTransaction entityTransaction;

    public ArticleRepositoryJpa(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

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
            entityTransaction.begin();
            entityManager.persist(article);
            return entityManager.find(Article.class, article.getId());
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public Article update(Article article) {
        try {
            entityTransaction.begin();
            entityManager.merge(article);
            entityTransaction.commit();
            return entityManager.find(Article.class, article.getId());
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public void deleteById(Long id) {
        entityTransaction.begin();
        entityManager.remove(findById(id));
        entityTransaction.commit();
    }
}
