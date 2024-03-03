package com.seikim.kotlinjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan("com.seikim.kotlinjwt.*")
@SpringBootApplication
class KotlinJwtApplication

fun main(args: Array<String>) {
    runApplication<KotlinJwtApplication>(*args)
}
