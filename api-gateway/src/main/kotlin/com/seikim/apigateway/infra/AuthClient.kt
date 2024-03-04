package com.seikim.apigateway.infra

import com.seikim.apigateway.header.Passport
import com.seikim.apigateway.header.body.PassportBody
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.InternalServerErrorException
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration


@Component
class AuthClient(
    private val httpClient: HttpClient = HttpClient.create(ConnectionProvider.newConnection())
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 5)
        .responseTimeout(Duration.ofSeconds(5))
        .doOnConnected {
            it.addHandlerLast(ReadTimeoutHandler(60))
                .addHandlerLast(WriteTimeoutHandler(60))
        },
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("http://127.0.0.1:8081/auth-service")
        .codecs { it.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .build()
) {
    fun issuePassport(body: PassportBody): Passport {
        val exchangeToMono: Mono<Passport> = webClient.post()
            .uri("v1/passport")
            .bodyValue(body)
            .exchangeToMono {
                val statusCode = it.statusCode().value()
                if (statusCode == 200) {
                    return@exchangeToMono it.bodyToMono(Passport::class.java)
                }
                if (statusCode == 400) {
                    throw BadRequestException("잘못된 사용자 요청입니다.")
                }
                throw InternalServerErrorException("내부 서버 오류입니다.")
            }
        return exchangeToMono.block() ?: throw InternalServerErrorException("내부 서버 오류입니다.")
    }
}