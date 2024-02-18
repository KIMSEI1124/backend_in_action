package com.seikim.passort.member.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberTokenRequest(
        @JsonProperty("member_id")
        Long memberId
) {
}
