package com.seikim.kotlinjwt.member.data.request

data class MemberSaveRequest(
    val email: String,
    val password: String,
    val name: String,
)
