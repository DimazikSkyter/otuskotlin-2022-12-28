plugins {
    id("kotlin")
    kotlin("jvm")
    id("org.openapi.generator")
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

dependencies {

    val jacksonVersion: String by project
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.api.v1"
    generatorName.set("kotlin") // Это и есть активный генератор
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/specs/specs-snapshot-v1.yaml")

    /**
     * Здесь указываем, что нам нужны только модели, все остальное не нужно
     * https://openapi-generator.tech/docs/globals
     */
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    /**
     * Настройка дополнительных параметров из документации по генератору
     * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
     */
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}


tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
