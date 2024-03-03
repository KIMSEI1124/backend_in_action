package com.seikim.kotlinjwt.member.domain

import jakarta.persistence.*
import lombok.Builder

@Builder
@Entity
class Member(
    @Column(name = "member_email", unique = true, nullable = false)
    val email: String,
    @Column(name = "member_password", nullable = false)
    val password: String,
    @Column(name = "member_name", nullable = false)
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Int = 0
}