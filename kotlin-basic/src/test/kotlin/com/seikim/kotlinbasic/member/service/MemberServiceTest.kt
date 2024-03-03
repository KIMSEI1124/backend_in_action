package com.seikim.kotlinbasic.member.service

import com.seikim.kotlinbasic.member.domain.MemberDataBase
import com.seikim.kotlinbasic.member.fixture.MemberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest(
    @Autowired val memberService: MemberService,
    @Autowired val memberDataBase: MemberDataBase
) {
    @ParameterizedTest
    @EnumSource(MemberFixture::class)
    fun `회원 저장에 성공한다`(fixture: MemberFixture) {
        // given
        // when
        val savedMember = memberService.save(fixture.memberName, fixture.email)

        // then
        assertAll(
            { assertThat(savedMember.id).isGreaterThan(0) },
            { assertThat(savedMember.name).isEqualTo(fixture.memberName) },
            { assertThat(savedMember.email).isEqualTo(fixture.email) }
        )
    }

    @ParameterizedTest
    @EnumSource(MemberFixture::class)
    fun `사용자 조회에 성공한다`(fixture: MemberFixture) {
        // given
        val savedMember = memberDataBase.save(fixture.생성한다())

        // when
        val findMember = memberService.findById(savedMember.id)

        // then
        assertThat(findMember).isEqualTo(savedMember)
    }
}