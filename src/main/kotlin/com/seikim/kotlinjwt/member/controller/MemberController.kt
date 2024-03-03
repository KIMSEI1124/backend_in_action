package com.seikim.kotlinjwt.member.controller

import com.seikim.kotlinjwt.member.data.request.MemberSaveRequest
import com.seikim.kotlinjwt.member.data.response.MemberSaveResponse
import com.seikim.kotlinjwt.member.service.MemberModifyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/member")
@RestController
class MemberController(
    val memberModifyService: MemberModifyService,
) {
    @PostMapping("/v1/signup")
    fun signup(
        @RequestBody request: MemberSaveRequest
    ): ResponseEntity<MemberSaveResponse> {
        val response: MemberSaveResponse = memberModifyService.save(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}