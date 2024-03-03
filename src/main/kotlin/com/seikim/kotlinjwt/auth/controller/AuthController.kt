package com.seikim.kotlinjwt.auth.controller

import com.seikim.kotlinjwt.auth.data.request.SignInRequest
import com.seikim.kotlinjwt.auth.data.response.SignInResponse
import com.seikim.kotlinjwt.auth.service.AuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/v1/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<SignInResponse> {
        val response = authService.signIn(request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/v1/logout")
    fun logout(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String
    ): ResponseEntity<Unit> {
        authService.logout(authorization)
        return ResponseEntity.ok().build()
    }
}