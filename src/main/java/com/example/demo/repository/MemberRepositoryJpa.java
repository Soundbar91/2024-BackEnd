package com.example.demo.repository;

import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.exception.ErrorCode.*;

public class MemberRepositoryJpa implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("from Member", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id))
                .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));
    }

    @Override
    public Member insert(Member member) {
        try {
            entityManager.persist(member);
            return entityManager.find(Member.class, member.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) throw new ApplicationException(EMAIL_EXISTS);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Member update(Member member) {
        try {
            Member updateMember = entityManager.merge(member);
            entityManager.flush();
            return updateMember;
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) throw new ApplicationException(EMAIL_EXISTS);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Member member = findById(id);
            entityManager.remove(member);
            entityManager.flush();
        } catch (Exception e) {
            if (e.getMessage().contains("foreign key constraint")) throw new ApplicationException(MEMBER_REFERENCE);
            else throw new ApplicationException(UNKNOWN_EXCEPTION);
        }
    }
}
