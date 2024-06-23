package com.example.demo.repository;

import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Repository
public class MemberRepositoryJpa implements MemberRepository {

    private final EntityManager entityManager;
    private final EntityTransaction entityTransaction;

    public MemberRepositoryJpa(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("from Member", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member == null) throw new ApplicationException(MEMBER_NOT_FOUND);
        return member;
    }

    @Override
    public Member insert(Member member) {
        try {
            entityTransaction.begin();
            entityManager.persist(member);
            return entityManager.find(Member.class, member.getId());
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    @Override
    public Member update(Member member) {
        try {
            entityTransaction.begin();
            entityManager.merge(member);
            entityTransaction.commit();
            return entityManager.find(Member.class, member.getId());
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw new ApplicationException(FK_NOT_EXISTS);
        }
    }

    /*TODO
    *  400 예외 처리 추가*/
    @Override
    public void deleteById(Long id) {
        entityTransaction.begin();
        entityManager.remove(findById(id));
        entityTransaction.commit();
    }
}
