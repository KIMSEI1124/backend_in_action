package com.seikim.passort.exception;

import lombok.Getter;

@Getter
public enum PassportErrorCode implements ErrorCode {
    AUTHORIZATION_NOT_FOUNT(400, "PASSPORT_001", "Authorization이 존재하지 않습니다"),
    GRANT_TYPE_NOT_FOUND(400, "PASSPORT_002", "GRANT TYPE이 존재하지 않습니다."),
    ALREADY_USED_PASSPORT(400, "PASSPORT_003", "이미 사용된 PASSPORT입니다."),
    EXPIRED_PASSPORT(400, "PASSPORT_004", "만료된 PASSPORT입니다.");
    private final int statusCode;
    private final String errorCode;
    private final String message;

    PassportErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
