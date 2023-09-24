import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {


    repositories {
        mavenCentral()
    }



    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}



