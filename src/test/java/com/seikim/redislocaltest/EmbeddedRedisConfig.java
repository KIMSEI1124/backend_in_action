package com.seikim.redislocaltest;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Profile({"local", "test"})
@Configuration
public class EmbeddedRedisConfig {
    private final RedisServer redisServer;

    protected EmbeddedRedisConfig(@Value("${spring.data.redis.port}") int port) {
        redisServer = new RedisServer(port);
    }

    @PostConstruct
    public void start() {
        redisServer.start();
    }

    @PreDestroy
    public void stop() {
        redisServer.stop();
    }
}
