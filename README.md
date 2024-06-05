# Typescript NestJS Starter

<img src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/f86e98c1-d183-4972-92e7-cc360261bab3"  alt="NestJS Logo"/>

`Spring Boot`로 개발을 하다보니 다른 프레임워크도 궁금하게 되었습니다.

그래서 선택한 것이 바로 `Nest.js`입니다. 리디, 인프랩 등 다양한 기업에서 사용하고 있으며 활발하게 진행하고 있는 프로젝트이기 때문에 궁금하여 시작하게 되었습니다!

## NestJS란?

먼저 [NestJS](https://nestjs.com/)에 대해서 간단하게 알아가보도록 하겠습니다.

`NestJS`는 `Node.js`를 기반으로 한 서버 애플리케이션을 개발하기 위한 프레임워크이며 내부적으로는 `Express` 프레임워크와 `Fastify` 프레임워크를 선택해서 사용할 수 있다고 합니다.

> 기본적으로는 `Express` 프레임워크를 사용합니다.

또한, Nest.js 는 상기 프레임워크의 API를 직접적으로 상요할 수 있도록 제공하고 있으며 서버 애플리케이션 개발에 있어서 보다 더 큰 자유도를 가질 수 있다고 합니다.

Controller, Service 등 개념이 유사하며, 다양한 Decorator(Spring의 Annotation과 같은 역할)가 사용된다는 점에서 기존 스프링 프레임워크를 다룬 개발자라면 사용해볼만 하다고
합니다!

또한 향로개발자님께서 스타트업에서 사용하면 가장 좋은 백엔드 스택이 바로 `NestJS`라고 한 만큼 좋은 프레임워크라고 생각합니다!

## NestJS Starter

[NestJS First Steps](https://docs.nestjs.com/first-steps)에 나온 명령어를 확인해보면 다음과 같습니다.

```shell
/bin/sh

npm i -g @nestjs/cli
nest new project-name
```

![Create NestJS Project](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/b599a24c-d5fd-4640-a999-74e5ce4b2771)

> 저는 `pnpm`을 사용하였습니다.

다음과 같이 프로젝트가 생성된 것을 확인할 수 있습니다.
이후 해당 프로젝트가 있는 폴더로 이동한 후 프로젝트를 실행하고 `postman`으로 API 테스트를 해보도록 하겠습니다.

> `NestJS`는 기본적으로 3000번 포트를 사용합니다.

![Postman Test](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/89ddd477-3fcd-4671-b9f1-2388c3a5e3cc)

정상적으로 API를 요청한 것을 확인할 수 있습니다!

먼저 간단하게 `NestJS`를 알아보고 설치한 후 API를 요청까지 해봤는데 현재까지는 매우 간단하게 프로젝트를 구성한 것 같습니다.
다음 글에서는 `Controller` -> `Service` -> `Domain` 와 같은 3 Layer Architecture 를 구성해보고 `Database`까지 연결해보도록 하겠습니다.

## Ref

- [NestJS First steps](https://docs.nestjs.com/first-steps)
- [One day dev / 하루개발 – 인프랩(Inflab) CTO 향로님 인터뷰 편](https://www.youtube.com/watch?v=SAzOaNESFb0)
- [[Nest.js] Nest.js 란 무엇일까? - 개요와 프로젝트 생성](https://velog.io/@dinb1242/Nest.js-%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%BC%EA%B9%8C-%EA%B0%9C%EC%9A%94%EC%99%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%83%9D%EC%84%B1)