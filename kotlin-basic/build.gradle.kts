import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "Kotlin Basic"

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {

}