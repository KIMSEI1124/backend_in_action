package com.seikim.kotlinjwt.auth.service

import com.seikim.kotlinjwt.auth.data.request.SignInRequest
import com.seikim.kotlinjwt.auth.data.response.SignInResponse
import com.seikim.kotlinjwt.auth.domain.AuthToken
import com.seikim.kotlinjwt.auth.domain.AuthTokenRepository
import com.seikim.kotlinjwt.auth.utils.TokenProvider
import com.seikim.kotlinjwt.member.domain.Member
import com.seikim.kotlinjwt.member.domain.MemberRepository
import com.seikim.kotlinjwt.member.utils.MemberServiceUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AuthService(
    val authTokenRepository: AuthTokenRepository,
    val memberRepository: MemberRepository,
    val tokenProvider: TokenProvider
) {
    fun signIn(request: SignInRequest): SignInResponse {
        val findMember: Member = MemberServiceUtils.findByEmail(memberRepository, request.email)

        val accessToken: String = tokenProvider.generateAccessToken(findMember)
        val refreshToken: String = tokenProvider.generateRefreshToken(findMember)
        authTokenRepository.save(
            AuthToken(
                memberId = findMember.id,
                refreshToken = refreshToken
            )
        )

        return SignInResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun logout(authorization: String) {
        val memberId = tokenProvider.parseToken(authorization)
        authTokenRepository.deleteById(memberId)
    }
}