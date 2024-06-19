import {Injectable} from "@nestjs/common";
import {Repository} from "typeorm";
import {Log} from "./log.entity";

@Injectable()
export class LogRepository extends Repository<Log> {
}