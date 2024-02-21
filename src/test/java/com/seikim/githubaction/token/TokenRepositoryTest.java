package com.seikim.githubaction.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;

    @DisplayName("Token 저장 테스트")
    @Test
    void tokenSaveTest() {
        /* Given */
        Token token = new Token();

        /* When */
        Token savedToken = tokenRepository.save(token);

        /* Then */
        assertThat(savedToken).isNotNull();
    }
}