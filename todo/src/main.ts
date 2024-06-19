import {NestFactory} from '@nestjs/core';
import {AppModule} from './app.module';
import {CustomLogger} from "./global/log/logger.service";

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