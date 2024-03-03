package com.seikim.kotlinbasic.member.fixture

import com.seikim.kotlinbasic.member.domain.Member

enum class MemberFixture(
    val memberName: String,
    val email: String,
) {
    김세이("김세이", "workju1124@gmail.com"),
    이자바("이자바", "java1999@gmail.com"),
    박틀린("박틀린", "kotlin2002@gmail.com");

    fun `생성한다`(): Member {
        return Member(memberName, email)
    }
}