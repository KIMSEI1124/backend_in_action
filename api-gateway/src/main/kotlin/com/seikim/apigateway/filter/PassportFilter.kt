package com.seikim.apigateway.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.seikim.apigateway.exception.ErrorCode
import com.seikim.apigateway.exception.GatewayException
import com.seikim.apigateway.header.CustomHeader
import com.seikim.apigateway.header.Passport
import com.seikim.apigateway.header.body.PassportBody
import com.seikim.apigateway.infra.AuthClient
import com.seikim.apigateway.utils.RequestHeaderUtils
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

@Component
class PassportFilter(
    val authClient: AuthClient,
    val objectMapper: ObjectMapper
) : AbstractGatewayFilterFactory<PassportFilter.Config>() {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request: ServerHttpRequest = exchange.request

            if (validateContainsAuthorizationHeader(request.headers)) {
                throw GatewayException(ErrorCode.NON_EXIST_AUTHORIZATION_HEADER)
            }

            val passport: Passport = authClient.issuePassport(
                PassportBody(
                    authorizationValue = request.headers[HttpHeaders.AUTHORIZATION].toString()
                )
            )
            val passportAsString = objectMapper.writeValueAsString(passport)

            RequestHeaderUtils.addValue(
                exchange,
                CustomHeader.PASSPORT,
                value = passportAsString
            )
            chain.filter(exchange)
        }
    }

    private fun validateContainsAuthorizationHeader(headers: HttpHeaders): Boolean {
        return headers.containsKey(HttpHeaders.AUTHORIZATION)
    }

    data class Config(
        val message: String,
    )
}

