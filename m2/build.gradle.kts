group = "ru.otus.homework2"

plugins {
    distribution
    application
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"

    kotlin("plugin.lombok") version "1.8.20"
    id("io.freefair.lombok") version "5.3.0"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.register("hello"){
    doLast {
        println("Hello Gradle")
    }
}

task<Copy>("copy_dockerfile") {
    dependsOn("installDist")
    val from = "${rootProject.projectDir}\\m2\\Dockerfile"
    println(from)
    from(from)
    val to = "$buildDir\\install\\"
    println(to)
    into(to)
}
dependencies {

    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
