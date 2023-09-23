group = "ru.otus.homework1"

plugins {
    apply { "kotlin" }
    kotlin("jvm")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
}