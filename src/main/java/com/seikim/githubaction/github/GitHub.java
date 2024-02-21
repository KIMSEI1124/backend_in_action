package com.seikim.githubaction.github;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GitHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "github_id")
    private Long id;

    private String repository;

    @Builder
    public GitHub(Long id, String repository) {
        this.id = id;
        this.repository = repository;
    }
}
