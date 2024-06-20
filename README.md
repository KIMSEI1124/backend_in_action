# Custom Logging

백엔드 서버를 운영 및 개발할 때 가장 중요하다고 생각하는 것은 모니터링입니다. 그 중 가장 기초적인 것이 바로 로깅을 하는 것이라고 생각합니다.
이번에는 NestJS에서 로그를 남기고 커스텀하는 방법을 알아보려고 합니다.

## 1. NestJS Logger

기본적으로 `@nestjs/common`에 관련된 코드들이 있기 때문에 따로 라이브러리를 다운받을 필요는 없습니다.

## 2. CustomLogger

기존에 있는 Logger가 아닌 커스텀한 로거를 만들어보겠습니다.

먼저, 구현하기 위한 요구사항은 다음과 같습니다.

1. 로그를 데이터베이스에 저장해서 관리해야한다.
2. `ConsoleLogger`를 `extends`해야한다.

### 2.1. Log Entity 및 Repository

데이터베이스에 로그를 저장하기 위해 엔티티를 먼저 만들어보겠습니다.

```ts
import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";
import { LogDomain, LogLevel, LogType }           from "./log.type";

@Entity()
export class Log {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    time: Date;

    @Column()
    message: string;

    @Column()
    level: LogLevel

    @Column()
    type: LogType

    @Column({ nullable: true })
    domain: LogDomain

    constructor(message: string, level: LogLevel, type: LogType, domain: LogDomain) {
        this.time = new Date();
        this.message = message;
        this.level = level;
        this.type = type;
        this.domain = domain;
    }
}
```

메시지와 로그가 발생한 시간을 저장하는 엔티티입니다.
그 외에 데이터베이스에서 값을 조회할 때 사용하기 위한 로그 레벨, 타입, 도메인과 같은 정보도 포함하였습니다.

```ts
import { Injectable } from "@nestjs/common";
import { Repository } from "typeorm";
import { Log }        from "./log.entity";

@Injectable()
export class LogRepository extends Repository<Log> {
}
```

위와 같이 `Repository`를 등록합니다.

> 2024.06.20 수정

```ts
@CreateDateColumn()
time
:
Date;
```

Date인 경우에는 위와 같이 `@CreateDateColumn()`를 사용하여 생성되는 시간을 설정할 수 있습니다. 동일하게 업데이트 되는 시간도 가능합니다.

### 2.2. Service

이번에는 `CustomLogger`을 만들어보도록 하겠습니다.

```ts
import { ConsoleLogger, Injectable } from "@nestjs/common";
import { LogRepository }             from "./log.repository";
import { Log }                       from "./log.entity";
import { InjectRepository }          from "@nestjs/typeorm";

@Injectable()
export class CustomLogger extends ConsoleLogger {

    @InjectRepository(Log) private readonly logRepository: LogRepository;

    log(message: string) {
        super.log(`💡 ${message}`);
    }

    warn(message: string) {
        super.warn(`⚠️ ${message}`);
    }

    error(message: string) {
        super.error(`🔥 ${message}`);
    }

    bootstrap(port: number) {
        const log = new Log(`Server Start on Port : ${port}`, 'INFO', 'SYSTEM', 'SYSTEM');
        this.save(log).then(() => {
            super.log(`🚀 ${log.message}`);
        })
    }

    async shutdown(port: number): Promise<void> {
        const log = new Log(`Server Shutting Down on Port : ${port}`, 'INFO', 'SYSTEM', 'SYSTEM');
        await this.save(log);
        super.log(`🗑️ ${log.message}`);
    }

    private save(log: Log) {
        return this.logRepository.save(log);
    }
}
```

먼저 `log`, `warn`, `error` 로그 레벨에 대해서는 이모지와 함께 메시지가 출력되도록 하였습니다.
그리고 서버가 켜지고 종료될 때 로그를 출력하며 데이터베이스에 저장하도록 하였습니다.

### 2.3. main.ts

이제 위에서 생성한 Logger를 사용해보도록 하겠습니다.

먼저 `main.ts`에서 서버가 실행되고 종료될 때 로그를 남기기 위해 `bootstrap()`안에서 로직을 작성합니다.

```ts
import { NestFactory }  from '@nestjs/core';
import { AppModule }    from './app.module';
import { CustomLogger } from "./global/log/logger.service";

const port: number = 3000;

async function bootstrap() {
    const app = await NestFactory.create(AppModule);
    const logger = app.get(CustomLogger);

    await app.listen(port);

    logger.bootstrap(port);

    const shutdown = async () => {
        await logger.shutdown(port).then(() => {
            process.exit(0);
        });
    };

    process.on('SIGINT', shutdown);     // Ctrl + C
    process.on('SIGTERM', shutdown);    // 프로세스 종료
}

bootstrap();
```

`app.get(CustomLogger)`을 한 이유는 `NestJS`에게 의존성을 가져와서 사용해야하기 때문입니다.
이후 서버 실행 및 프로세스 종료, 강제 종료할 때 로그를 발생시켰습니다.

### 2.4. 로그 사용하기

내가 원하는 곳에 커스텀 로거를 사용하여 로그를 남기고 싶으면 아래와 같이 사용하면 됩니다.

```ts
@Controller('/actuator')
export class ActuatorController {
    private readonly logger = new CustomLogger(ActuatorController.name);

    @Get('/v1/health-check')
    healthCheck(): HealthCheckResponse {
        this.logger.log("Request HealthCheck API");
        return HealthCheckResponse.isOk(true);
    }
}
```

> `CustomLogger`를 생성할 때 생성자 매개변수 값으로 클래스의 이름을 넣게되면 로그를 출력할 때 어떤 클래스에서 로그가 발생했는지 파악하기 쉽습니다.

## 3. 로그 확인

![1  log](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/4d6d02fb-707c-476c-89d2-25c3f73facef)

서버가 실행되고 종료될 때 정상적으로 로그가 찍히는 것을 확인할 수 있습니다.

<img alt="2  database log" src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/9e325542-95d6-4685-9533-7e546c9cdd89" width="800" />

데이터베이스에도 정상적으로 로그들이 저장된 것을 확인할 수 있습니다.

## Ref

- [NestJS - Logger](https://docs.nestjs.com/techniques/logger)