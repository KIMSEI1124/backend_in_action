package com.seikim.kotlinjwt.exception

interface ErrorCode {
    fun getStatusCode(): Int
    fun getErrorCode(): String
    fun getMessage(): String
}