package com.seikim.kotlinbasic.global

interface DataBase<T> {
    fun save(value: T): T
    fun findById(id: Int): T?
}