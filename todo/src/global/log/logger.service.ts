import {ConsoleLogger, Injectable} from "@nestjs/common";
import {LogRepository} from "./log.repository";
import {Log} from "./log.entity";
import {InjectRepository} from "@nestjs/typeorm";

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