plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":escalop-api"))
    implementation(project(":escalop-common"))

    testImplementation(kotlin("test-junit"))
}