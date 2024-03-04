package com.seikim.apigateway.exception

open class GatewayException(
    val errorCode: ErrorCode
) : RuntimeException() {
}