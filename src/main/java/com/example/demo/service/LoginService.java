package com.example.demo.service;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ApplicationException(MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) return null;
        else return MemberResponse.from(member);
    }
}
