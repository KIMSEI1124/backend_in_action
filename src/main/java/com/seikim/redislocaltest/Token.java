package com.seikim.redislocaltest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 60)
public class Token {
    @Id
    private String id;

    private String value;

    public Token(String value) {
        this.value = value;
    }
}
