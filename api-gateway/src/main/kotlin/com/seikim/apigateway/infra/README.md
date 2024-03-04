# HttpClient

타임 아웃을 위한 설정을 합니다.
이 외에 SSL, DNS to IP 로 변환 같은 작업을 할 수 있지만 하지 않았습니다.

```kotlin
private val httpClient: HttpClient = HttpClient.create(ConnectionProvider.newConnection())
    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 5) // 연결이 5초 안에 설정되지 않으면 연결 시도에 실패합니다.
    .responseTimeout(Duration.ofSeconds(5)) // 응답이 5초 안에 오지 않으면 응답을 기다리는 작업이 중단됩니다.
    .doOnConnected { // 60초 내에 읽기나 쓰기가 완료되지 않으면 연결이 종료됩니다.
        it.addHandlerLast(ReadTimeoutHandler(60))
            .addHandlerLast(WriteTimeoutHandler(60))
    }
```

# Ref

1. [신규 전시 프로젝트에서 WebClient 사용하기 - 올리브영](https://oliveyoung.tech/blog/2022-11-10/oliveyoung-discovery-premium-webclient/)