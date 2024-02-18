package com.seikim.passort.exception;

public interface ErrorCode {
    int getStatusCode();

    String getErrorCode();

    String getMessage();
}
