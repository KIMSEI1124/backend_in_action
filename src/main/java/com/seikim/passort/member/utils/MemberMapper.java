package com.seikim.passort.member.utils;

import com.seikim.passort.member.data.request.MemberSaveRequest;
import com.seikim.passort.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(MemberSaveRequest request) {
        return Member.builder()
                .name(request.name())
                .build();
    }
}
