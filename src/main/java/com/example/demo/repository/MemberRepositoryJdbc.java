package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;

import com.example.demo.exception.ApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.demo.domain.Member;

import static com.example.demo.exception.ErrorCode.*;

public class MemberRepositoryJdbc implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getString("email"),
        rs.getString("password")
    );

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("""
                SELECT id, name, email, password
                FROM member
                """, memberRowMapper);
    }

    @Override
    public Member findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("""
                    SELECT id, name, email, password
                    FROM member
                    WHERE id = ?
                    """, memberRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationException(MEMBER_NOT_FOUND);
        }
    }

    @Override
    public Member insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                    INSERT INTO member (name, email, password) VALUES (?, ?, ?)
                    """, new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public Member update(Member member) {
        try {
            jdbcTemplate.update("""
                    UPDATE member
                    SET name = ?, email = ?
                    WHERE id = ?
                    """, member.getName(), member.getEmail(), member.getId());
            return findById(member.getId());
        } catch (DuplicateKeyException e) {
            throw new ApplicationException(EMAIL_EXISTS);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            jdbcTemplate.update("""
                    DELETE FROM member
                    WHERE id = ?
                    """, id);
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(MEMBER_REFERENCE);
        }
    }
}
