package com.seikim.githubaction.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Transactional
@SpringBootTest
class GitHubTest {

    @Autowired
    private GitHubService gitHubService;

    @DisplayName("GitHub 저장에 성공한다.")
    @Test
    void saveSuccessTest() {
        /* Given */
        String repository = "github";

        /* When */
        GitHub savedGitHub = gitHubService.save(repository);

        /* Then */
        assertThat(savedGitHub.getRepository()).isEqualTo(repository);
    }

    @DisplayName("FindById Test")
    @Nested
    class FindByIdTest {

        private GitHub savedGitHub;

        @BeforeEach
        void setup() {
            String repository = "github";
            savedGitHub = gitHubService.save(repository);
        }

        @DisplayName("조회에 성공한다.")
        @Test
        void findByIdSuccessTest() {
            /* Given */
            /* When */
            GitHub findGitHub = gitHubService.findById(savedGitHub.getId());

            /* Then */
            assertThat(findGitHub).isNotNull();
        }

        @DisplayName("조회에 실패한다.")
        @Test
        void findByIdFailTest() {
            /* Given */
            /* When */
            /* Then */
            assertThatCode(() -> gitHubService.findById(1_000_000L))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }
}