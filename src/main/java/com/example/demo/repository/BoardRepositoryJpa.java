package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.exception.ErrorCode.*;

public class BoardRepositoryJpa implements BoardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("from Board", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        return Optional.ofNullable(entityManager.find(Board.class, id))
                .orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));
    }

    @Override
    public Board insert(Board board) {
        try {
            entityManager.persist(board);
            return entityManager.find(Board.class, board.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) throw new ApplicationException(BOARD_EXISTS);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Board board = findById(id);
            entityManager.remove(board);
            entityManager.flush();
        } catch (Exception e) {
            if (e.getMessage().contains("foreign key constraint")) throw new ApplicationException(BOARD_REFERENCE);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Board update(Board board) {
        try {
            Board updateBoard = entityManager.merge(board);
            entityManager.flush();
            return updateBoard;
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) throw new ApplicationException(BOARD_EXISTS);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }
}
