package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Repository
public class BoardRepositoryJpa implements BoardRepository {

    private final EntityManager entityManager;
    private final EntityTransaction entityTransaction;

    public BoardRepositoryJpa(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("from Board", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        Board board = entityManager.find(Board.class, id);
        if (board == null) throw new ApplicationException(ARTICLE_NOT_FOUND);
        return board;
    }

    @Override
    public Board insert(Board board) {
        try {
            entityTransaction.begin();
            entityManager.persist(board);
            return entityManager.find(Board.class, board.getId());
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

    @Override
    public Board update(Board board) {
        try {
            entityTransaction.begin();
            entityManager.merge(board);
            entityTransaction.commit();
            return entityManager.find(Board.class, board.getId());
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }
}
