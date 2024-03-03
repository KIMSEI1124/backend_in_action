package com.seikim.kotlinbasic.member.domain

import com.seikim.kotlinbasic.member.fixture.MemberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberDataBaseTest(
    @Autowired val memberDataBase: MemberDataBase
) {
    @ParameterizedTest
    @EnumSource(MemberFixture::class)
    fun `사용자 저장에 성공한다`(fixture: MemberFixture) {
        /* Given */
        val member = fixture.생성한다()

        /* When */
        val savedMember = memberDataBase.save(member)

        /* Then */
        assertAll(
            { assertThat(savedMember.name).isEqualTo(member.name) },
            { assertThat(savedMember.email).isEqualTo(member.email) }
        )
    }

    @ParameterizedTest
    @EnumSource(MemberFixture::class)
    fun `사용자 조회에 성공한다`(fixture: MemberFixture) {
        // given
        val savedMember = memberDataBase.save(fixture.생성한다())

        // when
        val findMember = memberDataBase.findById(savedMember.id)

        // then
        assertThat(findMember).isEqualTo(savedMember)
    }
}