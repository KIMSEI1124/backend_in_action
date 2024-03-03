package com.seikim.kotlinjwt.member.exception

import com.seikim.kotlinjwt.exception.ErrorCode

enum class MemberErrorCode(
    private val statusCode: Int,
    private val errorCode: String,
    private val message: String
) : ErrorCode {
    ALREADY_EXIST_EMAIL(400, "MEMBER_01", "이미 사용중인 이메일 입니다."),
    NON_EXIST_EMAIL(400, "MEMBER_02", "존재하지 않는 이메일 입니다.");

    override fun getStatusCode(): Int {
        return statusCode
    }

    override fun getErrorCode(): String {
        return errorCode
    }

    override fun getMessage(): String {
        return message
    }
}