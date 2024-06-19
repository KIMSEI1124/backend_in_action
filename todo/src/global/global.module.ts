import {Module} from "@nestjs/common";
import {CustomLogger} from "./log/logger.service";
import {DatabaseModule} from "./config/database.module";
import {LogRepository} from "./log/log.repository";
import {TypeOrmModule} from "@nestjs/typeorm";
import {Log} from "./log/log.entity";

@Module({
    imports: [DatabaseModule,
        TypeOrmModule.forFeature(
            [Log]
        )
    ],
    providers: [CustomLogger, LogRepository],
    exports: [CustomLogger]
})
export class GlobalModule {
}