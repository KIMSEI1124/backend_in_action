package com.seikim.kotlinjwt.auth.utils

import com.seikim.kotlinjwt.auth.exception.AuthErrorCode
import com.seikim.kotlinjwt.auth.exception.AuthException
import com.seikim.kotlinjwt.auth.properties.JwtProperties
import com.seikim.kotlinjwt.member.domain.Member
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class TokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val key: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        SecretKeySpec(
            keyBytes,
            Jwts.SIG.HS512.key().build().algorithm
        )
    }

    companion object {
        private const val BEARER_TYPE: String = "Bearer"
    }

    fun generateAccessToken(member: Member): String {
        val now: Long = Date().time

        val accessToken: String = generateToken(
            member,
            now,
            expireTime = jwtProperties.accessTokenExpireTime
        )

        return accessToken
    }

    fun generateRefreshToken(member: Member): String {
        val now: Long = Date().time

        val refreshToken: String = generateToken(
            member,
            now,
            expireTime = jwtProperties.refreshTokenExpireTime
        )

        return refreshToken
    }

    private fun generateToken(member: Member, now: Long, expireTime: Long): String {
        val token: String = Jwts.builder()
            .subject(member.email)
            .claim("memberId", member.id)
            .signWith(key)
            .expiration(Date(now + (1000L * 60 * expireTime)))
            .compact()
        return "$BEARER_TYPE $token"
    }

    fun parseToken(token: String): Int {
        validateBearer(token)
        val jws = token.replace(BEARER_TYPE, "")
            .trim()
        try {
            val claimsJws: Jws<Claims> = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jws)
            return claimsJws.payload["memberId"] as Int
        } catch (e: ExpiredJwtException) {
            throw AuthException(AuthErrorCode.EXPIRED_JWT)
        } catch (e: JwtException) {
            throw AuthException(AuthErrorCode.INVALID_JWT)
        }
    }

    private fun validateBearer(token: String) {
        if (token.contains(BEARER_TYPE)) {
            return
        }
        throw AuthException(AuthErrorCode.INVALID_BEARER_TYPE)
    }
}
