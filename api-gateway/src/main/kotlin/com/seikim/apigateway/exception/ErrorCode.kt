package com.seikim.apigateway.exception

enum class ErrorCode(
    val statusCode: Int,
    val errorCode: String,
    val message: String,
) {
    NON_EXIST_AUTHORIZATION_HEADER(400, "HEADER_01", "AUTHORIZATION 헤더가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "SERVER_01", "알 수 없는 내부 서버 오류입니다."),
}