
rootProject.name = "kotlin-homework"
include("m1")


pluginManagement {
    val kotlinJvmVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinJvmVersion
    }
}