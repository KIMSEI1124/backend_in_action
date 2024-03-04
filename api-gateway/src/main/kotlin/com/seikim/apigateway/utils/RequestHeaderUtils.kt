package com.seikim.apigateway.utils

import com.seikim.apigateway.header.CustomHeader
import org.springframework.http.HttpHeaders
import org.springframework.web.server.ServerWebExchange

class RequestHeaderUtils {
    companion object {
        fun addValue(
            exchange: ServerWebExchange,
            customHeader: CustomHeader,
            value: String
        ) {
            val httpHeaders: HttpHeaders = HttpHeaders.writableHttpHeaders(exchange.request.headers)
            httpHeaders.add(customHeader.value, value)
            exchange.request.mutate().headers { it.addAll(HttpHeaders()) }
        }
    }
}