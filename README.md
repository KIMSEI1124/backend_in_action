# 들어가며

---

Spring Boot로 테스트를 돌릴 때 `Redis`가 있을 때 번거로움이 있었습니다.

로컬 환경이라면 Redis가 동작하는지 확인해야하고 `Github Actions`이라면 Redis를 설치한 후 실행시키는 번거로운 작업, Jenkins라면 재대로 동작하지 않는 이유가 있습니다.

그리고 가장 큰 이류로는 프로젝트를 클론 받았을 때 바로 실행을 시킬 수 없다는 큰 단점이 있습니다.

위와 같은 이유로 Spring Boot내에서 동작하는 Redis를 구성해볼 예정입니다.

## 1. 라이브러리 선택

---

대표적으로 사용하고 있는 Embedded Redis 라이브러리입니다.

![](https://velog.velcdn.com/images/kimsei1124/post/b0ba572d-db8a-4d11-9d06-9c1227deedc0/image.png)

![](https://velog.velcdn.com/images/kimsei1124/post/bccee00d-0254-4c92-8cd4-854086896dbd/image.png)

처음으로 만들어진 프로젝트 인 [kstyrc/embedded-redis](https://github.com/kstyrc/embedded-redis) 에서 업데이트가 중단되어 forked [ozimov/embedded-redis](https://github.com/ozimov/embedded-redis) 만들어진 두 번째 프로젝트입니다.

하지만 두 번째 프로젝트도 4년째 업데이트가 되고 있지 않고 arm 아키텍처에서는 재대로 동작하지 않는다는 문제점이 있었습니다.

![](https://velog.velcdn.com/images/kimsei1124/post/31435ab3-4163-456a-9e39-ecf41d38ffcb/image.png)


그래서 찾은 라이브러리가 [ozimov/embedded-redis](https://github.com/ozimov/embedded-redis) 를 forked해서 만들어진 [codemonstur/embedded-redis](https://github.com/codemonstur/embedded-redis) 입니다.

24년 2월 26일 기준으로 마지막 커밋이 2주전이므로 가장 활발한 라이브러리입니다.
그래서 저는 해당 라이브러리를 선택하였습니다.

## 2. Embedded Redis 설정

---

다행히 사용법은 이전 라이브러리들과 동일합니다.

### 2.1. RedisEmbeddedConfig

---

먼저 `Embeddedd Redis` 로 사용하기 위한 설정을 해보도록 하겠습니다.

```java
@Slf4j
@Profile({"local", "embedded-test"})
@Configuration
public class RedisEmbeddedConfig {
    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    private void start() throws IOException {
        int port = isRedisRunning() ? findAvailablePort() : redisPort;
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    private void stop() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    public int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }
        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return !pidInfo.isEmpty();
    }
}
```

`local`, `embedded-test` 환경일 때 해당 설정이 주입되어서 사용할 수 있습니다.

포트가 충돌되어 실행할 수 없는 경우가 있을 수 있기 때문에 해결하기 위한 코드를 추가하였습니다.

> ⚠️ **Warning**
> 
> windows에서는 동작하지 않습니다.

### 2.2. RedisRepositoryConfig

---

```java
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<byte[], byte[]> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
```

- `RedisConnectionFactory`를 통해 Redis를 연결했습니다.
- `RedisTemplate`를 통해 `RedisConnection`에서 넘겨준 byte 값을 객체 직렬화 합니다.

이제 간단하게 설정이 끝났습니다.

## 3. 테스트 코드 작성

---

간단하게 RedisHash를 작성해보도록 하겠습니다.

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 60)
public class Token {
    @Id
    private String id;

    private String value;

    public Token(String value) {
        this.value = value;
    }
}

public interface TokenRepository extends CrudRepository<Token, String> {
}
```

해당 RedisHash를 저장하는 테스트를 진행하겠습니다.

```java
@ActiveProfiles("embedded-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenRepositoryEmbeddedTest {
    @Autowired
    private TokenRepository tokenRepository;

    @DisplayName("Token Save 테스트에 성공한다")
    @Test
    void saveTest() {
        /* Given */
        Token token = tokenRepository.save(new Token("token"));

        /* When */
        Token savedToken = tokenRepository.save(token);

        /* Then */
        assertThat(savedToken).isNotNull();
    }
}
```

테스트 결과는 정상적으로 되었습니다.

![](https://velog.velcdn.com/images/kimsei1124/post/9ebe0190-2e74-4786-b6fd-8a3d8051be64/image.png)

# 정리

---

새로운 라이브러리를 찾아 동작하는 것을 보고 드디어 해당 문제를 해결했다고 느꼈습니다.

TestContainer을 사용하고 싶지 않아서 더 많은 시간을 들여 찾아본 결과 개발해시는 개발자 분이 있으셔서 다행이라고 느꼈습니다.

추후에 프로젝트를 진행하거나 진행중인 프로젝트에 해당 라이브러리를 적극적으로 사용하여 프로젝트를 클론하고 다른 설정 없이 바로 실행되는 서버를 구성할 수 있어서 다행이라고 생각합니다.


# Ref

---

- [[Redis] SpringBoot Data Redis 로컬/통합 테스트 환경 구축하기 - 향로](https://jojoldu.tistory.com/297)
- [codemonstur/embedded-redis](https://github.com/codemonstur/embedded-redis)
