package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse getById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::from)
                .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        try {
            Member member = memberRepository.save(request.toEntity());
            return MemberResponse.from(member);
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));

            memberRepository.delete(member);
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(MEMBER_REFERENCE_EXISTS);
        }
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        return memberRepository.findById(id).map(member -> {
            member.update(request.name(), request.email());
            try {
                memberRepository.flush();
            } catch (DataIntegrityViolationException e) {
                throw new ApplicationException(EMAIL_ALREADY_EXISTS);
            }
            return MemberResponse.from(member);
        }).orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));
    }
}
