package com.seikim.passort.member.application;

import com.seikim.passort.jwt.TokenProvider;
import com.seikim.passort.member.data.request.MemberSaveRequest;
import com.seikim.passort.member.data.request.MemberTokenRequest;
import com.seikim.passort.member.data.response.MemberSaveResponse;
import com.seikim.passort.member.data.response.MemberTokenResponse;
import com.seikim.passort.member.domain.Member;
import com.seikim.passort.member.domain.MemberRepository;
import com.seikim.passort.member.utils.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final MemberMapper mapper;

    @Transactional
    public MemberSaveResponse save(MemberSaveRequest request) {
        Member member = mapper.toEntity(request);
        Member savedMember = memberRepository.save(member);
        return new MemberSaveResponse(savedMember.getId());
    }

    public MemberTokenResponse token(MemberTokenRequest request) {
        Member findMember = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        String token = tokenProvider.createToken(findMember);
        return new MemberTokenResponse(token);
    }
}
