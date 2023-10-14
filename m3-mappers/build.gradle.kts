plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":m3-api"))
    implementation(project(":m3-common"))

    testImplementation(kotlin("test-junit"))
}