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
- Redis

## Quick Starter

> ⚠️ Warning
>
> Quick Starter을 실행하기 위해서는 `docker-cli` 가 필요합니다.

```shell
cd script && sh run.sh
```

## 2. AuthenticatedResolver

`HandlerMethodArgumentResolver`를 활용하여 인증이 필요한 사용자의 요청에 대해서 어노테이션을 생성하여 인증을 편하도록 해보았습니다.

주요 코드를 보면 다음과 같습니다.

```java

@Override
public Passport resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    String token = getToken(Objects.requireNonNull(request)); // 1)
    Long memberId = tokenProvider.getPayload(token);
    return Passport.builder()
            .memberId(memberId)
            .path(request.getServletPath()) // 2)
            .userAgent(request.getHeader(HEADERS_USER_AGENT)) // 3)
            .expirationTime(LocalDateTime.now().plusSeconds(EXPIRATION_TIME)) // 4)
            .build();
}
```

1. 사용자의 정보
2. 사용자의 목적지
3. 사용자의 OS 정보
4. 해당 Passport의 만료 시간

다음과 같은 정보들을 파싱하여 `Passport`의 객체를 생성합니다.

## 3. Passport

위에서 Passport를 어떻게 생성하는지 파악했으니 Passport 객체를 확인해보도록 하겠습니다.

```java

@Getter
public class Passport {
    @JsonProperty("passport_id")
    private String id;
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("user_agent")
    private String userAgent;
    @JsonProperty("destination")
    private String path;
    @JsonProperty("expiration_time")
    private LocalDateTime expirationTime;

    @Builder
    public Passport(Long memberId, String userAgent, String path, LocalDateTime expirationTime) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.userAgent = userAgent;
        this.path = path;
        this.expirationTime = expirationTime;
    }
}
```

추가적으로 `passport_id`를 추가하였습니다.

### 3.1. Passport 검증

해당 Passport는 두 가지 방법으로 검증할 수 있습니다.

> 추후 더 생각이 날 수 있으나 현재 제 생각으로는 두 가지 방법을 생각하였습니다.

1. `passport_id`가 이미 사용되었는지 확인한다.
2. `expiration_time`이 넘었는지 확인한다.

각각의 코드를 한번 확인해보도록 하겠습니다.

```java

@RequiredArgsConstructor
@Component
@Aspect
public class PassportValidationAspect {
    private final UsedPassportRepository repository;

    // ...

    private void validateExpirationTime(LocalDateTime expirationTime) {
        if (LocalDateTime.now().isAfter(expirationTime)) {
            throw new PassportException(PassportErrorCode.EXPIRED_PASSPORT);
        }
    }

    private void validateAlreadyUsed(String id) {
        if (repository.existsById(id)) {
            throw new PassportException(PassportErrorCode.ALREADY_USED_PASSPORT);
        }
    }
}
```

Passport의 만료시간이 지나게 된다면 예외를 던지도록 작성하였고, Redis에서 해당 Id가 존재하면 이미 사용된 Passport로 판단하여 예외를 던지도록 작성하였습니다.

> [PassportValidationAspect.java](https://github.com/KIMSEI1124/backend_in_action/blob/java/springboot/passport/src/main/java/com/seikim/passort/passport/PassportValidationAspect.java)
> 에서 전체 코드를 확인할 수 있습니다.

테스트 코드로도 확인을 해보면 정상적으로 동작하는 것을 확인할 수 있습니다.

<img width="400" alt="Passport Test Success" src="https://velog.velcdn.com/images/kimsei1124/post/de7c866d-9d50-401d-99ce-4b88a2237266/image.png" />

## 4. Passport 장단점

Passport 인증객체를 도입함으로써 얻을 수 있는 장점과 단점을 정리해보도록 하겠습니다.

### 4.1. 장점

---

- **편의성 제공**
  Passport를 통해 사용자의 인증 및 인가를 한 번에 처리할 수 있습니다.
  만약 여러개의 서버를 운영한다면 하나의 인증서버에서 처리할 수 있습니다.

- **유연성**
  Passport 객체를 통해 사용자의 정보를 표준화하여 관리함으로써 다양한 서비스 및 기능에 적용할 수 있습니다. 또한 Passport를 확장하거나 변경하여 필요에 맞게 커스터마이징할 수 있습니다.

### 4.2. 단점

---

- **복잡성 증가**
  Passport 기능을 추가함으로써 시스템의 복잡성이 증가할 수 있습니다. 특히 Passport 관련된 로직이 복잡해질 경우 유지보수가 어려워질 수 있습니다.

- **성능 저하**
  Passport 검증을 위해 추가적인 로직이 필요하며, 이로 인해 서비스의 성능이 저하될 수 있습니다. 특히 Passport 검증이 빈번하게 발생할 경우 성능 문제가 심각해질 수 있습니다.

## 5. 정리

단일 서버에서 위와 같이 인증 객체를 만드는 것 보다 여러개의 서버를 운영하고 있을 때 만들면 좋겠다고 판단을 하였습니다.

주어진 숙제로는 해당 인증 객체가 여러 서버로 돌아다니게 된다면 암호화를 하여 보안에 신경을 더 써야할 것 같습니다.

# Ref

1. [토스는 Gateway 이렇게 씁니다](https://toss.tech/article/slash23-server)
