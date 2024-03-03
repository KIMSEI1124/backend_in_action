package com.seikim.kotlinjwt.auth.exception

import com.seikim.kotlinjwt.exception.CustomException
import com.seikim.kotlinjwt.exception.ErrorCode

class AuthException(code: ErrorCode) : CustomException(code)
