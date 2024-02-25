package com.seikim.redislocaltest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("embedded-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenRepositoryEmbeddedTest {
    @Autowired
    private TokenRepository tokenRepository;

    @DisplayName("Token Save 테스트에 성공한다")
    @Test
    void saveTest() {
        /* Given */
        Token token = tokenRepository.save(new Token("token"));

        /* When */
        Token savedToken = tokenRepository.save(token);

        /* Then */
        assertThat(savedToken).isNotNull();
    }
}