import { Repository } from 'typeorm';
import { Member } from '../domain/member.entity';
import { Injectable } from '@nestjs/common';

@Injectable()
export class MemberRepository extends Repository<Member> {
}