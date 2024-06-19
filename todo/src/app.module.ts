import {Module} from '@nestjs/common';

import {ActuatorController} from './actuator.controller';
import {MemberModule} from './member/member.module';
import {ConfigModule} from '@nestjs/config';
import {GlobalModule} from "./global/global.module";

@Module({
    imports: [ConfigModule.forRoot({
            isGlobal: true,
        },
    ),
        GlobalModule, MemberModule],
    controllers: [ActuatorController],
})
export class AppModule {
}
