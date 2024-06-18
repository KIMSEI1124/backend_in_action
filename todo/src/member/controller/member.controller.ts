import { Body, Controller, Get, Post, Req } from '@nestjs/common';
import { MemberService } from '../service/member.service';
import { MemberSaveReq } from '../data/request/member.save.req';


@Controller('/members')
export class MemberController {

  /** Dependency Injection */
  constructor(private readonly memberService: MemberService) {
  }

  @Post('/v1/sign-in')
  async signIn(@Body() request: MemberSaveReq): Promise<string> {
    const id: number = await this.memberService.saveMember(request);
    return `Saved Member Id is "${id}"`;
  }
}