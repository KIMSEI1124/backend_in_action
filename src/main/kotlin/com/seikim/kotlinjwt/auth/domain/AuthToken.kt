package com.seikim.kotlinjwt.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "token", timeToLive = 60)
class AuthToken(
    @Id
    val memberId: Int,
    val refreshToken: String
) {
}