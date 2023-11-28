plugins {
    kotlin("multiplatform")
}

kotlin {

    jvm {}

    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        val commonMain by getting {
            dependencies {
                implementation(project(":escalop-stubs"))
                implementation(project(":escalop-common"))
            }
        }
        val commonTest by getting {

        }
    }
}
