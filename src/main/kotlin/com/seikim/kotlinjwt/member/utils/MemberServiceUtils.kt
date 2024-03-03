package com.seikim.kotlinjwt.member.utils

import com.seikim.kotlinjwt.member.domain.Member
import com.seikim.kotlinjwt.member.domain.MemberRepository
import com.seikim.kotlinjwt.member.exception.MemberErrorCode
import com.seikim.kotlinjwt.member.exception.MemberException

class MemberServiceUtils {
    companion object {
        fun findById(memberRepository: MemberRepository, id: Int): Member {
            return memberRepository.findById(id)
                .orElseThrow { MemberException(MemberErrorCode.ALREADY_EXIST_EMAIL) }
        }

        fun findByEmail(memberRepository: MemberRepository, email: String): Member {
            return memberRepository.findByEmail(email)
                ?: throw MemberException(MemberErrorCode.NON_EXIST_EMAIL)
        }
    }
}