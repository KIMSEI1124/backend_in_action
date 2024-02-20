# Custom Properties

<img width="800" alt="redis-properties" src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/4f1bf8d0-0f1c-468a-a2b7-c5ca46397093" />

[RedisLocalTest]()를 진행하던중 아래 사진과 같은 클래스를 확인하였습니다.

해당 어노테이션이 궁금하여 `chat-gpt`에게 물어봤더니 다음과 같은 답변을 주었습니다.

<img width="800" alt="chat-gpt" src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/544c65db-51e6-4373-a799-5430d876ea0b" />

이제 실제로 동작하는지 확인하기 위해서 `@ConfigurationProperties` 를 사용하여 자동으로 외부 설정을 바인딩하는 기능을 만들어봅니다.

## 1. SpringBootApplication 설정

가장 먼저 `@ConfigurationPropertiesScan` 설정이 필요합니다. 경로 아래의 모든 `ConfigurationProperties`를 탐색하게 됩니다.

```java
@ConfigurationPropertiesScan("com.seikim.customproperties")  // 반드시 필요
@SpringBootApplication
public class CustomPropertiesApplication {
  // ...
}
```

Configuration 을 모아둔 패키지가 있다면 좀 더 상세하게 적어주면 좋을 것 같습니다. 

## 2. ConfigurationProperties

```java
@Setter
@Getter
@ConfigurationProperties(prefix = "custom.data")
public class CustomProperties {
    private int number = 100;
    private String string;
}
```

SpringBoot가 자동으로 값을 주입시켜주기 위해서는 `Setter`가 필요하고 값을 꺼내서 쓰기 위해서는 `Getter`도 필요합니다.
`Bean`으로도 등록되기 때문에 손 쉽게 꺼내서 사용할 수 있습니다.

```java
@RequiredArgsConstructor
@Service
public class FooService {
    private final CustomProperties customProperties;
}
```

## 3. yaml

<img width="400" alt="auto-complate" src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/6a5e376c-d8b1-4b98-b047-8eeb1c240d91" />

`Inteillj IDEA`에서 자동완성 기능을 사용하여 더 쉽게 작성할 수 있습니다.

## 4. Test

실제로 테스트 코드도 작성을 해보았습니다.
단순하게 Getter 로 기본 값과 바인딩된 값을 조회한 결과 아래 사진과 같이 정상적으로 테스트를 성공하였습니다.

<img width="400" alt="test-success" src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/576805c9-c10d-4851-9882-8cc9de1a134f" />

## 5. 정리

이러한 기능이 있는 것을 오늘 처음 알게 되어서 급하게 찾아보고 작성을 해보았습니다.

해당 기능을 사용하면 yaml에서 실수를 줄일 수 있고 빠르게 개발을 진행 할 수 있다고 판단하여 개발을 진행할 때 적극적으로 사용할 것 입니다.
