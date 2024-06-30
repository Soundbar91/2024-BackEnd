package com.example.demo.service;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.domain.Board;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.BoardRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::from)
                .toList();
    }

    public BoardResponse getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(BoardResponse::from)
                .orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        try {
            Board saved = boardRepository.save(request.toEntity());
            return BoardResponse.from(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(BOARD_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void deleteBoard(Long id) {
        try {
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));

            boardRepository.delete(board);
            boardRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(BOARD_REFERENCE_EXISTS);
        }
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        return boardRepository.findById(id).map(board -> {
            board.update(request.name());
            try {
                boardRepository.flush();
            } catch (DataIntegrityViolationException e) {
                throw new ApplicationException(BOARD_ALREADY_EXISTS);
            }
            return BoardResponse.from(board);
        }).orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));
    }
}
