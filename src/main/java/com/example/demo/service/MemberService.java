package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse getByMemberId(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::from)
                .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));
    }

    public List<MemberResponse> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        try {
            String passWord = passwordEncoder.encode(request.password());
            Member member = memberRepository.save(request.toEntity(passWord));
            return MemberResponse.from(member);
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void deleteMember(Long id) {
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
    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
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
