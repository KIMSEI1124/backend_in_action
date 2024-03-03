package com.seikim.kotlinjwt.config

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import java.io.BufferedReader
import java.io.InputStreamReader

@Profile("local")
@Configuration
class RedisEmbeddedConfig(
    @Value("\${spring.data.redis.port}")
    private var redisPort: Int,
) {

    private lateinit var redis: RedisServer
    private val logger = KotlinLogging.logger {}

    @PostConstruct
    private fun start() {
        val port: Int = if (isReadingRunning()) findAvailablePort() else redisPort
        redis = RedisServer(port)
        redis.start()
        logger.info { "Start Embedded Redis Server, Running Port : [$port]" }
    }

    @PreDestroy
    private fun stop() {
        redis.start()
    }

    private fun isReadingRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(redisPort))
    }

    private fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process: Process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available Port : 10000 ~ 65535")
    }

    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN|grep $port"
        val shell: Array<String> = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        val pidInfo = StringBuilder()

        try {
            BufferedReader(InputStreamReader(process.inputStream))
                .use { input ->
                    var line: String? = input.readLine()
                    while (line != null) {
                        pidInfo.append(line)
                        line = input.readLine()
                    }
                }
        } catch (e: Exception) {
            logger.error(e) { "Embedded Redis Exception : ${e.message}" }
        }

        return pidInfo.isNotEmpty()
    }
}