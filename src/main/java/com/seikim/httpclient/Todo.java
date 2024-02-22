package com.seikim.httpclient;

public record Todo(
        Integer userId,
        Integer id,
        String title,
        boolean completed
) {
}
