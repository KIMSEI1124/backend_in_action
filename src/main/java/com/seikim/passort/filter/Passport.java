package com.seikim.passort.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Passport {
    @JsonProperty("passport_id")
    private String id;
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("user_agent")
    private String userAgent;
    @JsonProperty("destination")
    private String path;
    @JsonProperty("expiration_time")
    private LocalDateTime expirationTime;

    @Builder
    public Passport(Long memberId, String userAgent, String path, LocalDateTime expirationTime) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.userAgent = userAgent;
        this.path = path;
        this.expirationTime = expirationTime;
    }
}
