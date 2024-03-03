package com.seikim.kotlinjwt.auth.data.request

data class SignInRequest(
    val email: String,
    val password: String
)
