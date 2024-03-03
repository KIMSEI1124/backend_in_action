package com.seikim.kotlinjwt.auth.domain

import org.springframework.data.repository.CrudRepository

interface AuthTokenRepository : CrudRepository<AuthToken, Int> {
}