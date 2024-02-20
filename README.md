# Custom Properties

![사진1]()

[RedisLocalTest]()를 진행하던중 아래 사진과 같은 클래스를 확인하였습니다.
해당 어노테이션이 궁금하여 `chat-gpt`에게 물어봤더니 다음과 같은 답변을 주었습니다.

![사진2]()

이제 실제로 동작하는지 확인하기 위해서 `@ConfigurationProperties` 를 사용하여 자동으로 외부 설정을 바인딩하는 기능을 만들어봅니다.