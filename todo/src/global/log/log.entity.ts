import {Column, Entity, PrimaryGeneratedColumn} from "typeorm";
import {LogDomain, LogLevel, LogType} from "./log.type";

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

    @Column({nullable: true})
    domain: LogDomain

    constructor(message: string, level: LogLevel, type: LogType, domain: LogDomain) {
        this.time = new Date();
        this.message = message;
        this.level = level;
        this.type = type;
        this.domain = domain;
    }
}