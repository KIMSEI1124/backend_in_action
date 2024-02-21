package com.seikim.githubaction.github;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GitHubService {
    private final GitHubRepository gitHubRepository;

    @Transactional
    public GitHub save(String repositoryName) {
        GitHub gitHub = GitHub.builder()
                .repository(repositoryName)
                .build();
        return gitHubRepository.save(gitHub);
    }

    public GitHub findById(Long id) {
        return gitHubRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않습니다."));
    }
}
