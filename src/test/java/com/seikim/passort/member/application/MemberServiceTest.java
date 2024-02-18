package com.seikim.passort.member.application;

import com.seikim.passort.exception.PassportErrorCode;
import com.seikim.passort.exception.PassportException;
import com.seikim.passort.passport.Passport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private Passport passport;

    @BeforeEach
    void setup() {
        passport = Passport.builder()
                .memberId(1L)
                .userAgent("USER_AGENT")
                .path("PATH")
                .expirationTime(LocalDateTime.now().plusSeconds(5))
                .build();
    }

    @DisplayName("Passport 검증에 성공한다.")
    @Test
    void validatePassportSuccessTest() {
        assertThatCode(() -> memberService.doing(passport))
                .doesNotThrowAnyException();
    }

    @DisplayName("이미 사용된 Passport 검증에 성공한다.")
    @Test
    void validatePassportAlreadyUsedFailed() {
        memberService.doing(passport);
        assertThatThrownBy(() -> memberService.doing(passport))
                .isInstanceOf(PassportException.class)
                .hasMessage(PassportErrorCode.ALREADY_USED_PASSPORT.getMessage());
    }

    @DisplayName("만료된 Passport 검증에 성공한다.")
    @Test
    void validatePassportExpireTimeFailed() {
        Passport expiredPassport = Passport.builder()
                .memberId(1L)
                .userAgent("USER_AGENT")
                .path("PATH")
                .expirationTime(LocalDateTime.now().minusSeconds(10L))
                .build();

        assertThatThrownBy(() -> memberService.doing(expiredPassport))
                .isInstanceOf(PassportException.class)
                .hasMessage(PassportErrorCode.EXPIRED_PASSPORT.getMessage());
    }
}