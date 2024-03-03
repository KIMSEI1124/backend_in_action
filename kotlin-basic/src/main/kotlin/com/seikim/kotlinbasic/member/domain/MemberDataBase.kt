package com.seikim.kotlinbasic.member.domain

import com.seikim.kotlinbasic.global.DataBase
import org.springframework.stereotype.Repository
import java.util.concurrent.ThreadLocalRandom

@Repository
class MemberDataBase(
    val db: HashMap<Int, Member> = HashMap()
) : DataBase<Member> {
    var identityId = 1
    override fun save(value: Member): Member {
        Thread.sleep(ThreadLocalRandom.current().nextLong(100) + 300)
        value.id = identityId++
        db[value.id] = value
        return value
    }

    override fun findById(id: Int): Member? {
        Thread.sleep(ThreadLocalRandom.current().nextLong(100) + 100)
        return db[id]
    }
}