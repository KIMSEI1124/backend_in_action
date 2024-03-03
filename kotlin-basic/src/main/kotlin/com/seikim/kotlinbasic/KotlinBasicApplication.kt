package com.seikim.kotlinbasic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBasicApplication

fun main(args: Array<String>) {
    runApplication<KotlinBasicApplication>(*args)
}