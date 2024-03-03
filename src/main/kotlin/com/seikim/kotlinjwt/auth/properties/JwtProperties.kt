package com.seikim.kotlinjwt.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessTokenExpireTime: Long,
    val refreshTokenExpireTime: Long,
)