package com.seikim.kotlinjwt.member.service

import com.seikim.kotlinjwt.member.data.request.MemberSaveRequest
import com.seikim.kotlinjwt.member.data.response.MemberSaveResponse
import com.seikim.kotlinjwt.member.domain.Member
import com.seikim.kotlinjwt.member.domain.MemberRepository
import com.seikim.kotlinjwt.member.exception.MemberErrorCode
import com.seikim.kotlinjwt.member.exception.MemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberService(
    val memberRepository: MemberRepository
) : MemberModifyService {
    override fun save(request: MemberSaveRequest): MemberSaveResponse {
        validateMemberSaveRequest(request)
        val member = Member(
            email = request.email,
            password = request.password,
            name = request.name,
        )
        val savedMember = memberRepository.save(member)
        return MemberSaveResponse(memberId = savedMember.id)
    }

    fun validateMemberSaveRequest(request: MemberSaveRequest) {
        if (memberRepository.findByEmail(request.email) != null) {
            throw MemberException(MemberErrorCode.ALREADY_EXIST_EMAIL)
        }
    }
}