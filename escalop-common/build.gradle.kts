plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.jpa")
}


group = rootProject.group
version = rootProject.version


kotlin {

    jvm {}
    linuxX64 {}

    sourceSets {
        val datetimeVersion: String by project
        val kotlinxSerializationVersion: String by project

        val jvmMain by getting {
            dependencies {
                implementation("org.hibernate.orm:hibernate-core:6.6.1.Final")
                implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
                implementation("com.zaxxer:HikariCP:5.1.0")
                implementation( "com.fasterxml.jackson.core:jackson-databind:2.17.0")
                runtimeOnly("ch.qos.logback:logback-classic:1.5.7")
                runtimeOnly("org.postgresql:postgresql:42.7.4")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okhttp3:okhttp:4.12.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")

                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
