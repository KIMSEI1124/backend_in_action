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