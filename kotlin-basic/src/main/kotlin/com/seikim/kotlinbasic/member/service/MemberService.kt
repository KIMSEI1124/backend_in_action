package com.seikim.kotlinbasic.member.service

import com.seikim.kotlinbasic.member.utils.MemberUtils
import com.seikim.kotlinbasic.member.domain.Member
import com.seikim.kotlinbasic.member.domain.MemberDataBase
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberDataBase: MemberDataBase
) {
    fun save(name: String, email: String): Member {
        val member = Member(name, email)
        return memberDataBase.save(member)
    }

    fun findById(id: Int): Member {
        return MemberUtils.findById(memberDataBase, id)
    }
}