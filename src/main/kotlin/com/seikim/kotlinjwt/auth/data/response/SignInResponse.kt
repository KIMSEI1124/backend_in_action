package com.seikim.kotlinjwt.auth.data.response

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String
)
