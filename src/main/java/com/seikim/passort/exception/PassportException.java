package com.seikim.passort.exception;

import lombok.Getter;

@Getter
public class PassportException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;
    private final String message;

    public PassportException(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}
