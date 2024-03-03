package com.seikim.kotlinbasic.member.utils

import com.seikim.kotlinbasic.member.domain.Member
import com.seikim.kotlinbasic.member.domain.MemberDataBase

class MemberUtils {
    companion object {
        fun findById(dataBase: MemberDataBase, id: Int): Member {
            return dataBase.findById(id) ?: throw NoSuchElementException("해당 아이디를 가진 사용자는 존재하지 않습니다.")
        }
    }
}