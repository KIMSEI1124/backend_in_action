package com.seikim.kotlinjwt.auth.exception

import com.seikim.kotlinjwt.exception.ErrorCode

enum class AuthErrorCode(
    private val statusCode: Int,
    private val errorCode: String,
    private val message: String
) : ErrorCode {
    INVALID_JWT(400, "AUTH_01", "잘못된 JWT 입니다."),
    EXPIRED_JWT(400, "AUTH_02", "만료된 JWT 입니다."),
    INVALID_BEARER_TYPE(400, "AUTH_03", "잘못된 Bearer 입니다.");

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