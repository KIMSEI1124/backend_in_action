package com.seikim.kotlinjwt.auth.utils

import com.seikim.kotlinjwt.auth.exception.AuthErrorCode
import com.seikim.kotlinjwt.auth.exception.AuthException
import com.seikim.kotlinjwt.member.domain.Member
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class TokenProvider(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.access-token-expire-time}") private val accessTokenExpireTime: Long,
    @Value("\${jwt.refresh-token-expire-time}") private val refreshTokenExpireTime: Long
) {
    private val key: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
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

        val accessToken: String = Jwts.builder()
            .subject(member.email)
            .claim("memberId", member.id)
            .signWith(key)
            .expiration(Date(now + (1000L * 60 * accessTokenExpireTime)))
            .compact()

        return "$BEARER_TYPE $accessToken"
    }

    fun generateRefreshToken(member: Member): String {
        val now: Long = Date().time

        val refreshToken: String = Jwts.builder()
            .subject(member.email)
            .claim("memberId", member.id)
            .signWith(key)
            .expiration(Date(now + (1000L * 60 * refreshTokenExpireTime)))
            .compact()

        return "$BEARER_TYPE $refreshToken"
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

    fun validateBearer(token: String) {
        if (token.contains(BEARER_TYPE)) {
            return
        }
        throw AuthException(AuthErrorCode.INVALID_BEARER_TYPE)
    }
}
