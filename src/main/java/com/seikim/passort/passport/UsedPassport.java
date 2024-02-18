package com.seikim.passort.passport;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "used_passport", timeToLive = 5)
public class UsedPassport {
    @Id
    private String id;

    public UsedPassport(String id) {
        this.id = id;
    }
}
