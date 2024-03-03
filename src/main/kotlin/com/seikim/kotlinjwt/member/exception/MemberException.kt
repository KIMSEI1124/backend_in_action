package com.seikim.kotlinjwt.member.exception

import com.seikim.kotlinjwt.exception.CustomException
import com.seikim.kotlinjwt.exception.ErrorCode

class MemberException(code: ErrorCode) : CustomException(code)
