import io.ktor.plugin.features.*
import org.jetbrains.kotlin.util.suffixIfNot

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

group = "ru.otus"

version = "1.0-SNAPSHOT"
repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    mavenCentral()
}

val ktorVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"


ktor {
    docker {
        localImageName.set(project.name + "-ktor")
        imageTag.set(project.version.toString())
        jreVersion.set(JreVersion.JRE_17)
    }
}


dependencies {
    val logback_version: String by project

    implementation(project(":escalop-api"))
    implementation(project(":escalop-common"))
    implementation(project(":escalop-mappers"))
    implementation(project(":escalop-biz"))

    implementation(ktor("config-yaml"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("caching-headers"))
    implementation(ktor("cors"))
    implementation(project(":escalop-stubs"))
    implementation(ktor("websockets"))
    implementation(ktor("content-negotiation"))
    implementation(ktor("core-jvm"))
    implementation(ktor("sessions-jvm"))
    implementation(ktor("default-headers-jvm"))
    implementation(ktor("cio-jvm"))
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

    testImplementation(ktor("tests-jvm"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.2.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}