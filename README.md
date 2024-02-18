# Java Spring Boot Passport

[토스는 Gateway 이렇게 씁니다](https://toss.tech/article/slash23-server) 포스트를 보던 중 Passport 관련 내용이 있었습니다.

간단하게 설명하자면 사용자 기기 정보와 유저 정보를 담은 하나의 토큰으로써 서비스에서는 해당 토큰을 가지고 유저 정보 호출 없이 유저에 대한 정보를 사용할 수 있도록 만든 토큰입니다.
해당 내용에 관심이 생기게 되어서 나만의 Passport를 만들었습니다.

## 실습 환경

- Java 17
- Lombok
- Spring Boot 3.2.x
- Spring Data JPA
- Spring Web
- H2 DB

## Quick Starter

```shell
cd script && sh run.sh
```