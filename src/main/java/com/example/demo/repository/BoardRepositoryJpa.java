package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Repository
public class BoardRepositoryJpa implements BoardRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
            entityManager.persist(board);
            return entityManager.find(Board.class, board.getId());
        } catch (PersistenceException e) {
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            entityManager.remove(findById(id));
        } catch (Exception e) {
            throw new ApplicationException(BOARD_REFERENCE);
        }
    }

    @Override
    public Board update(Board board) {
        try {
            entityManager.merge(board);
            return entityManager.find(Board.class, board.getId());
        } catch (PersistenceException e) {
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }
}
