rootProject.name = "kotlin-homework"

include("escalop-api")
include("escalop-common")
include("escalop-mappers")
include("escalop-ktor-app")
include("escalop-stubs")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val kotestVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

