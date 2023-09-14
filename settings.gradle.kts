
rootProject.name = "kotlin-homework"
include("m1")
include("m2")
include("m3-api")


pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false


        id("org.openapi.generator") version openapiVersion apply false
    }
}