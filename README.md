# Jacoco

`Jacoco`는 자바 진영에서 프로젝트의 코드 커버리지를 분석하고, 보고서를 만들어주는 역활을 하는 코드 커버러지 프레임워크를 공부한 코드입니다.

## Quick Starter

```shell
./gradlew clean test
```

## 1. 코드 커버리지(Code Coverage)

### 1.1. 코드 커버리지(Code Coverage)란?

---


> **위키 백과**<sub>1)</sub>
>
> 코드 커버리지란? 소프트웨어의 테스트를 논할 때 얼마나 테스트가 충분한가를 나타내는 지표중 하나입니다. 소프트웨어 테스트를 진행했을 때 코드 자체가 얼마나 실행되었냐는 것 입니다.

보통 코드 커버리지는 80%를 목표로 하는 것을 일반적으로 받아 들이고 있습니다. 더 높은 커버리지에 도달하려고 하는 것은 비용이 많이 들 수 있으며, 그렇다고 해서 충분한 이점을 제공하는 것은 아닙니다.<sub>
4)</sub>

### 1.2. 코드 커버리지의 측정 기준

---


코드 커버리지는 소스 코드를 기반으로 수행하는 **화이트 박스 테스트**를 통해 측정합니다.

> ⭐ **Info**
>
> 블랙박스 테스트 : 객체 내부를 알 필요 없이 입력에 따라 원하는 결과 값이 나오는지 확인하는 테스트로, 사용자 관점의 테스트<sub>3)</sub>
> 화이트박스 테스트 : 객체 내부를 확인하고 검증하는 테스트로, 개발자 관점으로 진행되는 테스트<sub>3)</sub>

## 2. Jacoco란?

`Jacoco`는 자바 진영에서 프로젝트의 코드 커버리지를 분석하고, 보고서를 만들어주는 역활을 하는 코드 커버러지 프레임워크입니다.

### 2.1 Jacoco 적용하기

---


> 🚀 **실습환경**
>
> macOS
> Inteillj IDEA
> Java 17
> Spring Boot 3.x
> Junit 5

`Jacoco`의 버젼은 [Jacoco - Maven Repository](https://mvnrepository.com/search?q=jacoco)에서 확인할 수 있습니다.

> 2024.03.01 기준으로 최신 버젼은 0.8.11입니다.

### 2.2. Jacoco 추가하기

---

먼저 `build.gradle`에 플러그인을 가져오고 버젼을 설정하도록 작성합니다.

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.3'
    id 'io.spring.dependency-management' version '1.1.4'
    id 'jacoco' // Jacoco Plugin 추가
}

// ...

jacoco {
    toolVersion = "0.8.11"
}
```

### 2.3. 보고서 생성하기

---


보고서 파일을 생성하기 위해 `build.gradle`에 추가적으로 내용을 작성합니다.

```groovy
jacocoTestReport {
    reports {
        xml {
            enabled true
        }
        csv {
            enabled true
        }
        html {
            enabled true
        }

        xml.destination file(project.layout.buildDirectory.dir("jacoco/index.xml")) as File
        csv.destination file(project.layout.buildDirectory.dir("jacoco/index.csv")) as File
        html.destination file(project.layout.buildDirectory.dir("jacoco/index.html")) as File
    }
}
```

기본적으로 Jacoco는 `exec`파일을 생성하여 사람이 읽을 수 없습니다. 그래서 XML, CSV, HTML 파일도 생성하는 기능을 제공하여 사람이 읽을 수 있도록 합니다.

Jacoco Plugin에서 `jacocoTestReport` 태스크는 바이너리 보고서 즉 `exec`파일을 사람이 읽기 좋은 형태로 출력해주는 역활을 합니다.

테스트 코드를 동작한 후 위의 태스크를 실행하면 사람이 읽을 수 있는 파일을 생성합니다.

![](https://velog.velcdn.com/images/kimsei1124/post/88e051f1-2ebc-454e-b01a-92020725bccb/image.png)

XML과 CSV 파일은 소나큐브등과 연동할 때 사용하며 HTML은 사람이 직접 커버리지를 확인할 때 사용됩니다. HTML파일을 직접 열어보면 아래 사진과 같이 보고서를 확인할 수 있습니다.

![](https://velog.velcdn.com/images/kimsei1124/post/b9d6090b-f00e-4f07-b1af-06d864e5ca91/image.png)

### 2.4. 보고서 자동으로 갱신하기

---


현재의 문제점은 테스트 코드를 동작한 후 `jacocoTestReport` 태스크를 동작하야 보고서가 생성됩니다. 해당 문제를 해결하기 위해 테스트가 끝나면 보고서를 생성하는 태스크가 바로 실행하도록
설정하도록 `build.gradle`에 아래의 내용을 추가합니다.

```groovy
test {
    useJUnitPlatform()
    finalizedBy 'jacocoTestReport'
}
```

`finalizedBy`를 사용하면 해당 테스크가 끝나고 성공 여부와 관계없이 명시한 태스크를 이어 실행하도록 설정할 수 있습니다.

## 3. 커버리지 기준 설정

Jacoco 플러그인에서는 `jacocoTestCoverageVerification` 태스크를 사용하여 Jacoco 규칙을 설정하여 프로젝트의 코드 커버리지가 규칙을 통과하지 않으면 빌드가 실패하도록 만들 수
있습니다.

```groovy
jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = 0.8
            }

            excludes = [
                    '*.*Application'
            ]
        }

        // ...

        rule {
            // 여러개의 규칙이 가능합니다.
        }
    }
}
```

### 3.1. enabled

---


규칙의 활성화 여부를 나타냅니다. 기본 값은 `true`입니다.

### 3.2. element

---


커버리지를 체크할 단위를 설정합니다.

- Bundle : 패키지 번들 ( 전체 프로젝트 )
- Class : 클래스
- Group : 논리적 번들 그룹
- Method : 메소드
- Package : 패키지
- Sourcefile : 소스파일

기본 값은 "Bundle" 입니다.

### 3.3. counter

---


코드 커버리지를 측정할 때 사용되는 지표입니다. 아래와 같은 옵션이 있습니다.

- Line : 빈 줄을 제외한 실제 코드의 라인 수
- Branch : 조건문등의 분기 수
- Class : 클래스 수
- Method : 메소드 수
- Instruction : 자바 바이트코드 명령 수
- Complexity : 복잡도

기본값은 "Instruction" 입니다.

### 3.4. 커버리지 만족 검사

---


새로운 태스크를 생성하였으므로 해당 내용도 추가하도록 합니다.

`jacocoTestReport` 태스크의 마지막에 `finalizedBy(jacocoTestCoverageVerification)`를 추가하여 보고서를 생성한 후 커버리지 만족 검사를 테스트하게 되고 커버리지가
기준 미달이면 빌드가 실패되도록 합니다.

## 4. 코드 커버리지 분석 대상 제외

예외 클래스, DTO 클래스 등 테스트를 하지 않아도 되는 클래스들이 있습니다. 이런 클래스까지 포함하여 코드 커버리지를 계산하면 코드 커버리지가 낮게 나옵니다. 이런 클래스들을 분석대상에서 제외할 수 있습니다.

### 4.1. jacocoTestReport

---

`jacocoTestReport` 태스크에서 보고서에 표시되는 클래스 중 일부를 제외할 수 있습니다.

```groovy
afterEvaluate {
    classDirectories.setFrom(
            files(classDirectories.files.collect {
                fileTree(dir: it, excludes: [
                        "**/*Application*",
                ])
            })
    )
}
```

제외 대상 파일들을 `Ant` 스타일로 작성합니다.

### 4.2. jacocoTestCoverageVerification

---

`jacocoTestCoverageVerification` 태스크에서는 코드 커버리지를 만족하는지 확인할 대상 중 일부 패키지를 제외할 수 있습니다.

`jacocoTestReport` 에서 작성한 것과 다르게 파일의 경로가 아닌 패키지 + 클래스명을 적어줘야 합니다. 와일드 카드로 `*` 여러 글자와 `?` 한 글자를 사용할 수 있습니다.

```groovy
jacocoTestCoverageVerification {
    violationRules {
        rule {
            // ...

            excludes = [
                    '*.*Application',
                    // ...
            ]
        }
    }
}
```

# Ref

1. [코드 커버리지 - 위키백과](https://ko.wikipedia.org/wiki/코드_커버리지)
2. [코드 커버리지(Code Coverage)가 뭔가요? - Tecoble](https://tecoble.techcourse.co.kr/post/2020-10-24-code-coverage/)
3. [[Android] Jacoco를 사용하여 코드 커버리지 확인하기 - Heeg's](https://heegs.tistory.com/131)
4. [코드 커버리지란 무엇입니까? - Atlassian](https://www.atlassian.com/ko/continuous-delivery/software-testing/code-coverage)