package com.seikim.kotlinjwt.member.service

import com.seikim.kotlinjwt.member.data.request.MemberSaveRequest
import com.seikim.kotlinjwt.member.data.response.MemberSaveResponse

fun interface MemberModifyService {
    fun save(request: MemberSaveRequest): MemberSaveResponse
}