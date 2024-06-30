package com.example.demo.service;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            MemberRepository memberRepository,
            BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        return articleRepository.findById(id)
                .map(article -> ArticleResponse.of(article, article.getMember(), article.getBoard()))
                .orElseThrow(() -> new ApplicationException(ARTICLE_NOT_FOUND));
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
                .map(article -> ArticleResponse.of(article, article.getMember(), article.getBoard()))
                .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Member member = memberRepository.findById(request.authorId())
                .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));

        Article article = articleRepository.save(request.toEntity(board, member));
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ARTICLE_NOT_FOUND));
        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new ApplicationException(BOARD_NOT_FOUND));

        article.update(board, request.title(), request.description());
        articleRepository.flush();

        return ArticleResponse.of(article, article.getMember(), board);
    }

    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ARTICLE_NOT_FOUND));
        articleRepository.delete(article);
    }
}
