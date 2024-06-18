# NestJS 3 Layer Architecture 구성

<img src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/f86e98c1-d183-4972-92e7-cc360261bab3"  alt="NestJS Logo"/>

이번에는 `Controller` -> `Service` -> `Domain` 와 같은 3 Layer Architecture 를 구성해보고 `Database`까지 연결해보도록 하겠습니다.

## 1. Health Check Controller

<img src="https://docs.nestjs.com/assets/Controllers_1.png" width="800" alt="nestJS controller" />

먼저 외부 클라이언트와 통신하기 위한 `Controller`를 먼저 확인해보도록 하겠습니다.

간단하게 구성한 healthCheck API 입니다.

```ts
import { Controller, Get } from '@nestjs/common';

export class HealthCheckResponse {
  status: string;
  server_time: string;

  constructor(isOk: boolean) {
    this.status = isOk ? 'Server Is OK' : 'Server Is Down';

    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    this.server_time = `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분 ${seconds}초`;
  }

  public static isOk(status: boolean): HealthCheckResponse {
    return new HealthCheckResponse(status);
  }
}

@Controller('/actuator')
export class ActuatorController {

  @Get('/v1/health-check')
  healthCheck(): HealthCheckResponse {
    return HealthCheckResponse.isOk(true);
  }
}
```

`@Controller`를 사용하여 라우팅을 하였습니다. 이후 `@Get`을 사용하여 Get 요청에 대한 주소를 작성하였습니다. 물론 `@Post`, `@Delete` 등도 가능합니다.
해당 컨트롤러를 생성했으니 NestJS에 등록을 시켜주겠습니다.

## 2. Module

```ts
import { Module } from '@nestjs/common';

import { ActuatorController } from './actuator.controller';
import { MemberModule } from './member/member.module';

@Module({
  imports: [MemberModule],
  controllers: [ActuatorController],
})
export class AppModule {
}
```

`@Module`을 사용하여 등록할 수 있습니다. `controllers`에 생성한 컨트롤러를 등록하였습니다.

위에 보면 `import`에 다른 모듈이 있는 것을 확인할 수 있습니다.

<img src="https://docs.nestjs.com/assets/Modules_1.png" alt="modules" width="800">

위 사진 처럼 모듈들은 서로를 `import`를 할 수 있습니다.

## 3. Repository

이번에는 Database 를 먼저 설정해보도록 하겠습니다. 저는 `NestJS`진영에서 가장 많이 사용하고 있는 `TypeORM`을 사용하며 Database는 Postgresql을 사용하겠습니다.

### 3.1. TypeORM

먼저 `TypeORM`을 설치해보도록 하겠습니다.

```shell
pnpm install --save @nestjs/typeorm typeorm pg
```

> `pg` 는 `Postgresql` 을 뜻합니다.

해당 명령어를 실행하면 정상적으로 설치되는 것을 확인할 수 있습니다.

### 3.2. Entity

이번에는 `Entity`를 생성해보도록 하겠습니다.

먼저 간단하게 회원에 대한 정보를 입력해보도록 하겠습니다.

```ts
import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Member {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ unique: true })
  email: string;

  @Column()
  password: string;

  @Column()
  name: string;

  constructor(email: string, password: string, name: string) {
    this.email = email;
    this.password = password;
    this.name = name;
  }
}
```

간단하게 생성한 Entity 입니다.
해당 클래스가 엔티티로 선언하기 위해서 `@Entity` 데코레이션을 사용하였고 PK값인 id에는 `@PrimaryGeneratedColumn`을 사용하였습니다.

> 기본적으로 `@PrimaryGeneratedColumn`는 AutoIncrement로 설정되어 있습니다.

이후 각 값들에 대해서 `@Column`설정을 해주었습니다.
이메일같은 경우에는 중복되면 안되기 때문에 `unique`제약 조건을 설정하였습니다.

### 3.3. Database 설정

이번에는 데이터베이스 설정을 해보도록 하겠습니다.

```ts
import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Member } from '../member/domain/member.entity';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'password',
      database: 'test',
      entities: [Member],
      synchronize: true,
    }),
  ],
})
export class DatabaseModule {
}
```

다음과 같이 `DatabaseModule`로 하나 생성하여 분리하였습니다. 해당 모듈을 `app.module.ts`에 추가합니다.
위에서 생성한 `Member`을 `entities`에 등록합니다.

```ts
import { Module } from '@nestjs/common';

import { ActuatorController } from './actuator.controller';
import { MemberModule } from './member/member.module';
import { DatabaseModule } from './global/database.module';

@Module({
  imports: [DatabaseModule, MemberModule], /** DatabaseModule 추가 */
  controllers: [ActuatorController],
})
export class AppModule {
}
```

이후 서버를 실행시키면 `Entity`에서 설정한 테이블과 컬럼들이 `database.module.ts`에서 설정한 데이터베이스에 자동으로 생성되는 것을 확인할 수 있습니다.

<img src="https://github.com/KIMSEI1124/backend_in_action/assets/74192619/50c59bea-5e96-49c8-8062-15a70e8c032f" alt="database_init">

### 3.3.1. env

위에서 보면 포트와 비밀번호가 다 노출되고 있습니다. 이렇게 SVN에 올라가게 된다면 보안에 매우 취약하게 됩니다.
해당 내용들을 `env`로 분리해야합니다!

```shell
pnpm i --save @nestjs/config
```

해당 스크립트를 사용하여 관련 라이브러리를 설치합니다.

이후 `app.module.ts`에 `ConfigModule.forRoot()`을 imports 에 추가합니다.

```ts
import { Module } from '@nestjs/common';

import { ActuatorController } from './actuator.controller';
import { MemberModule } from './member/member.module';
import { DatabaseModule } from './global/config/database.module';
import { ConfigModule } from '@nestjs/config';

@Module({
  /** ConfigModule 추가 및 전역에서 사용하도록 isGlobal 는 true 로 설정 */
  imports: [ConfigModule.forRoot({
      isGlobal: true,
    },
  ), DatabaseModule, MemberModule],
  controllers: [ActuatorController],
})
export class AppModule {
}
```

이후 `database.module.ts`를 수정합니다.

```ts
@Module({
  imports: [
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => ({
        type: 'postgres',
        host: configService.get<string>('DATABASE_HOST'),
        port: configService.get<number>('DATABASE_PORT'),
        username: configService.get<string>('DATABASE_USERNAME'),
        password: configService.get<string>('DATABASE_PASSWORD'),
        database: configService.get<string>('DATABASE_NAME'),
        autoLoadEntities: true,
        synchronize: true,
      }),
    }),
  ],
})
export class DatabaseModule {
}
```

기존에 노출된 값들이 환경변수로 숨겨진 것을 확인할 수 있습니다.

## 4. Service

```ts
import { Injectable } from '@nestjs/common';
import { MemberRepository } from '../repository/member.repository';
import { InjectRepository } from '@nestjs/typeorm';
import { Member } from '../domain/member.entity';
import { MemberSaveReq } from '../data/request/member.save.req';

@Injectable()
export class MemberService {
  constructor(@InjectRepository(Member) private readonly memberRepository: MemberRepository) {
  }

  async saveMember(request: MemberSaveReq): Promise<number> {
    /** Validate */
    await this.validateDuplicateEmail(request.email);

    const member = new Member(request.email, request.password, request.name);
    return await this.memberRepository.save(member).then(savedMember => {
      return savedMember.id;
    }).catch((err) => {
      throw new Error(err.toString());
    });
  }

  private async validateDuplicateEmail(email: string) {
    if (!!await this.memberRepository.findOneBy({ email })) {
      throw new Error(`Duplicate Email ${email}`);
    }
  }
}
```

해당 서비스를 주입할 수 있도록 `@Injectable` 데코레이션을 사용합니다. 그리고 생성한 `Repository`를 사용하기 위해서 `@InjectRepository`를 사용하여 가져옵니다.

## 5. Controller

클라이언트와 통신할 수 있는 컨트롤러를 만들어 보도록 하겠습니다.

```ts
import { Body, Controller, Get, Post, Req } from '@nestjs/common';
import { MemberService } from '../service/member.service';
import { MemberSaveReq } from '../data/request/member.save.req';


@Controller('/members')
export class MemberController {

  /** Dependency Injection */
  constructor(private readonly memberService: MemberService) {
  }

  @Post('/v1/sign-in')
  async signIn(@Body() request: MemberSaveReq): Promise<string> {
    const id: number = await this.memberService.saveMember(request);
    return `Saved Member Id is "${id}"`;
  }
}
```

먼저 `Json`으로 요청을 응답 받기 위해서 `@Body` 데코레이션을 사용하여 요청 데이터를 받습니다. 이후 결과값을 반환합니다.

> 반환 값도 Response Dto 로 만들어주면 더 좋을 것 같습니다.

## 6. Module에 등록하기

위에서 만든 모든 클래스들을 `module.ts`에 등록합니다. 저는 각 도메인별로 모듈을 분리하여 사용하려고 하기 때문에 `member.module.ts`를 생성한 뒤 작성하였습니다.

```ts
import { Module } from '@nestjs/common';
import { MemberController } from './controller/member.controller';
import { MemberService } from './service/member.service';
import { MemberRepository } from './repository/member.repository';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Member } from './domain/member.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature(
      [Member],
    ),
  ],
  controllers: [MemberController],
  providers: [MemberService, MemberRepository],
})
export class MemberModule {
}
```

위와 같이 `Entity` - `Repository` - `Service` - `Controller` 를 모두 등록합니다. 이후 해당 모듈을 최상단 모듈인 `app.module.ts`에 등록합니다.

```ts
@Module({
  imports: [ConfigModule.forRoot({
      isGlobal: true,
    },
  ),
    DatabaseModule, MemberModule], /** Member 모듈 등록 */
  controllers: [ActuatorController],
})
export class AppModule {
}
```

## 7. 사용자 저장하기

이제 실제로 동작하는지 테스트를 진행해보도록 하겠습니다.

먼저 서버를 실행시킨 후 `Postman`을 통해 API를 요청해보겠습니다.

![sign-in post request](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/11f569d1-54a4-439e-8540-1f4c95b5a207)
다음과 같이 사용자를 저장하는 API를 요청하기위해서 `JSON`으로 요청을 해보았습니다.

![save](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/8f0b4a63-7e18-48e6-a1c6-a97560f6d603)
그 결과 정상적으로 저장되었다고 응답이 왔습니다.

![view database](https://github.com/KIMSEI1124/backend_in_action/assets/74192619/e9557514-77e6-4047-abed-1b56bb60bd01)
실제로 데이터베이스에도 저장이 된 데이터를 확인할 수 있습니다.

## 8. 정리

이렇게 3 layer 로 API를 작성해보면서 필요한 데코레이션들을 확인해 보았습니다.
Spring과 매우 유사한 부분이 많다고 느꼈고 더욱 흥미로워져 깊이 공부할 것 같습니다!

## Ref

- [NestJS - Controllers](https://docs.nestjs.com/controllers)
- [NestJS - Modules](https://docs.nestjs.com/modules)
- [NestJS - Database](https://docs.nestjs.com/techniques/database)
- [NestJS - Configuration](https://docs.nestjs.com/techniques/configuration)