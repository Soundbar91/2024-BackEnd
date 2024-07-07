package com.example.demo.service;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email());
        if (member == null) return null;

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) return null;
        else return member;
    }
}
