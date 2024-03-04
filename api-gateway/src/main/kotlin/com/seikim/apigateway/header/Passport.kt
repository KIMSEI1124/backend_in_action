package com.seikim.apigateway.header

data class Passport(
    val passportId: String, // UUID
    val memberId: Int,
    val expiredTime: Long,
)
