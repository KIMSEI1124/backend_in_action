package com.seikim.githubaction.github;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubRepository extends JpaRepository<GitHub, Long> {
}
