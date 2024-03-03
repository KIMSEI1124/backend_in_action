package com.seikim.kotlinjwt.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Int> {
    fun findByEmail(email: String): Member?
}