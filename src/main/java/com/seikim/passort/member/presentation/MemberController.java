package com.seikim.passort.member.presentation;

import com.seikim.passort.member.application.MemberService;
import com.seikim.passort.member.data.request.MemberSaveRequest;
import com.seikim.passort.member.data.request.MemberTokenRequest;
import com.seikim.passort.member.data.response.MemberSaveResponse;
import com.seikim.passort.member.data.response.MemberTokenResponse;
import com.seikim.passort.passport.Authenticated;
import com.seikim.passort.passport.Passport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberSaveResponse> save(@RequestBody MemberSaveRequest request) {
        MemberSaveResponse response = memberService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/token")
    public ResponseEntity<MemberTokenResponse> token(
            @RequestBody MemberTokenRequest request
    ) {
        MemberTokenResponse response = memberService.token(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/passport")
    public ResponseEntity<Passport> encryptedPassport(@Authenticated Passport passport) {
        return ResponseEntity.ok(passport);
    }

    @GetMapping("/doing")
    public ResponseEntity<Void> doing(@Authenticated Passport passport) {
        memberService.doing(passport);
        return ResponseEntity.ok().build();
    }
}
