package com.seikim.passort.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberSaveResponse(
        @JsonProperty("member_id")
        Long id
) {
}
