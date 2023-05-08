
rootProject.name = "kotlin-homework"
include("m1")
include("m2")
pluginManagement {

    val kotlinJvmVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinJvmVersion
    }
}
include("eureka")
include("untitled")
