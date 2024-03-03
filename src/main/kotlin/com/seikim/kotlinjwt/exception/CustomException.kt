package com.seikim.kotlinjwt.exception

open class CustomException(
    val statusCode: Int,
    val errorCode: String,
    override val message: String
) : RuntimeException() {
    constructor(code: ErrorCode) : this(
        statusCode = code.getStatusCode(),
        errorCode = code.getErrorCode(),
        message = code.getMessage()
    )
}
