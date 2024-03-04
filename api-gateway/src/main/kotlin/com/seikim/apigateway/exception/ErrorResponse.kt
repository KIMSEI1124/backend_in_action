package com.seikim.apigateway.exception

data class ErrorResponse(
    val statusCode: Int,
    val errorCode: String,
    val message: String,
) {
    companion object {
        fun from(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(
                statusCode = errorCode.statusCode,
                errorCode = errorCode.errorCode,
                message = errorCode.message
            )
        }
    }
}
