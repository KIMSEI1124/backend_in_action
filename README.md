# Java Spring Boot Passport

[토스는 Gateway 이렇게 씁니다](https://toss.tech/article/slash23-server) 포스트를 보던 중 Passport 관련 내용이 있었습니다.

간단하게 설명하자면 사용자 기기 정보와 유저 정보를 담은 하나의 토큰으로써 서비스에서는 해당 토큰을 가지고 유저 정보 호출 없이 유저에 대한 정보를 사용할 수 있도록 만든 토큰입니다.
해당 내용에 관심이 생기게 되어서 나만의 Passport를 만들었습니다.

![java-springboot-passport drawio](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/20a205cc-32d3-44d8-aa37-59a9b45268f2)

다음과 같이 JWT Token을 사용하여 사용자가 API 요청을하게 되면 서버내에서 활용할 수 있는 인증 객체를 생성합니다.

## 실습 환경

- Java 17
- Lombok
- Spring Boot 3.2.x
- Spring Data JPA
- Spring Web
- H2 DB
- JWT 0.11.2

## Quick Starter

> ⚠️ Warning
> 
> Quick Starter을 실행하기 위해서는 `docker-cli` 가 필요합니다.

```shell
cd script && sh run.sh
```
