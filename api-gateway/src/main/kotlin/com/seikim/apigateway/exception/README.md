# 정적 팩토리 패턴

`of`, `from`과 같은 정적 생성자를 사용하고 싶었습니다.
자바에서는 `static` 예약어를 통해 생성했지만 코틀린에서는 `companion object`를 사용합니다.

```kotlin
companion object {
    fun from(errorCode: ErrorCode): ErrorResponse {
        return ErrorResponse(
            statusCode = errorCode.statusCode,
            errorCode = errorCode.errorCode,
            message = errorCode.message
        )
    }
}
```

위 코드와 같이 작성하여 사용합니다.

# GatewayExceptionHandler

```kotlin
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class GatewayExceptionHandler(
    val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response: ServerHttpResponse = exchange.response
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorResponse: ErrorResponse = when (ex) {
            is GatewayException -> ErrorResponse.from(ex.errorCode)
            else -> ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR)
        }

        return response.writeWith(
            Jackson2JsonEncoder(objectMapper).encode(
                Mono.just(errorResponse),
                response.bufferFactory(),
                ResolvableType.forInstance(errorResponse),
                MediaType.APPLICATION_JSON,
                Hints.from(Hints.LOG_PREFIX_HINT, exchange.logPrefix)
            )
        )
    }
}
```

- `Order(Ordered.HIGHEST_PRECEDENCE)` : `Ordered.HIGHEST_PRECEDENCE`로 설정하여 스프링 기본 설정보다 높은 우선순위를 가집니다.