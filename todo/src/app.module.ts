import { Module } from '@nestjs/common';

import { ActuatorController } from './actuator.controller';
import { MemberModule } from './member/member.module';
import { DatabaseModule } from './global/config/database.module';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [ConfigModule.forRoot({
      isGlobal: true,
    },
  ),
    DatabaseModule, MemberModule],
  controllers: [ActuatorController],
})
export class AppModule {
}
