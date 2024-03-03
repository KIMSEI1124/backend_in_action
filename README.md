# Kotlin Spring Boot Jwt Provider

코틀린으로 `JWT`를 발급합니다.

## 실습환경

- macOS
- Intellij IDEA
- Kotlin 1.9.22
- Spring Boot 3.2.2
- Spring Data JPA
- Spring Data Redis
- [Embedded Redis Server](https://github.com/codemonstur/embedded-redis)
- jjwt
    - [api](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api/0.12.5)
    - [impl](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl/0.12.5)
    - [jackson](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson/0.12.5)
- Lombok

## Quick Starter

```shell
./gradlew clean bootRun
```

**테스트 종료 후** 아래의 스크립트를 반드시 실행시켜 백그라운드로 동작하고 있는 레디스 서버를 종료합니다.

```shell
sh kill.sh
```