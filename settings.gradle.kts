
rootProject.name = "kotlin-homework"
include("m1")
include("m2")
include("m3-api")
include("m3-common")
include("m3-mappers")
include("m4-snapshot-app-ktor")
include("m4-stubs")

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

