import {Module} from '@nestjs/common';
import {MemberController} from './controller/member.controller';
import {MemberService} from './service/member.service';
import {MemberRepository} from './repository/member.repository';
import {TypeOrmModule} from '@nestjs/typeorm';
import {Member} from './domain/member.entity';

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