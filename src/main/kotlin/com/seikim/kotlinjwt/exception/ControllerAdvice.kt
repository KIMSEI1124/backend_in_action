package com.seikim.kotlinjwt.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(CustomException::class)
    fun sendException(errorCode: ErrorCode): ResponseEntity<String> {
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorCode.getMessage())
    }
}