import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val oasGeneratorName: String by project
val oasLibrary: String by project
val oasGenerateModelTests: String by project
val oasGenerateApiTests: String by project

plugins {
    id("import-api-examples.java-conventions")

    id("org.openapi.generator") version "4.3.1"
    id("org.jsonschema2dataclass") version "3.0.0"
}

dependencies {
    val openCsvVersion: String by project
    val lombokVersion: String by project
    val junitVersion: String by project
    val mockitoJunitVersion: String by project
    val assertJVersion: String by project

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("com.opencsv:opencsv:$openCsvVersion")

    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoJunitVersion")

    // Workaround for https://github.com/OpenAPITools/openapi-generator/issues/9602#issuecomment-859528250
    // Dependencies copied from openapi generated gradle build script
    implementation( "com.brsanthu:migbase64:2.2")
    implementation( "com.fasterxml.jackson.core:jackson-annotations:2.10.3")
    implementation( "com.fasterxml.jackson.core:jackson-core:2.10.3")
    implementation( "com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation( "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.3")
    implementation( "com.google.code.findbugs:jsr305:3.0.2")
    implementation( "io.github.openfeign:feign-core:11.0")
    implementation( "io.github.openfeign:feign-jackson:11.0")
    implementation( "io.github.openfeign:feign-slf4j:11.0")
    implementation( "io.github.openfeign.form:feign-form:3.8.0")
    implementation ("io.swagger:swagger-annotations:1.6.14")
    implementation( "org.apache.oltu.oauth2:org.apache.oltu.oauth2.client:1.0.1")
    implementation( "org.openapitools:jackson-databind-nullable:0.2.1")
}

java {
    sourceSets.main {
        java {
            srcDirs(
                    layout.buildDirectory.dir("generated-openapi/src/main/java"),
                    layout.buildDirectory.dir("generated-params/main")
            )
        }
    }
}

val additionalPropertiesMap = mapOf(
    "hideGenerationTimestamp" to "true",
    "useApacheHttpClient" to "true",
    "useFeign10" to "true",
    "java8" to "true"
)

val configOptionsMap = mapOf("dateLibrary" to "java8")

val buildCoreRestClient by tasks.registering(GenerateTask::class) {
    generatorName.set(oasGeneratorName)
    library.set(oasLibrary)
    generateModelTests.set(oasGenerateModelTests.toBoolean())
    generateApiTests.set(oasGenerateApiTests.toBoolean())

    inputSpec.set("$projectDir/schemas/dgc-rest.json")
    outputDir.set("$buildDir/generated-openapi")

    apiPackage.set("com.collibra.core.rest.client.api")
    invokerPackage.set("com.collibra.core.rest.client.invoker")
    modelPackage.set("com.collibra.core.rest.client.model")
    configOptions.set(configOptionsMap)
    additionalProperties.set(additionalPropertiesMap)
}

openApiGenerate {
    generatorName.set(oasGeneratorName)
    library.set(oasLibrary)
    generateModelTests.set(oasGenerateModelTests.toBoolean())
    generateApiTests.set(oasGenerateApiTests.toBoolean())

    inputSpec.set("$projectDir/schemas/dgc-importer-rest.json")
    outputDir.set("$buildDir/generated-openapi")

    apiPackage.set("com.collibra.importer.rest.client.api")
    invokerPackage.set("com.collibra.importer.rest.client.invoker")
    modelPackage.set("com.collibra.importer.rest.client.model")
    configOptions.set(configOptionsMap)
    additionalProperties.set(additionalPropertiesMap)
}

jsonSchema2Pojo {
    source.setFrom(fileTree("$projectDir/schemas"))
    targetDirectoryPrefix.set(file("$buildDir/generated-params"))
    targetPackage = "com.collibra.importer.parameters"
    generateBuilders = true
    includeDynamicBuilders = true
    initializeCollections = false
}


tasks.compileJava {
    dependsOn(buildCoreRestClient, tasks.openApiGenerate)
}

tasks.test {
    outputs.upToDateWhen { project.hasProperty("IGNORE_TEST_FAILURES") }
    ignoreFailures = project.hasProperty("IGNORE_TEST_FAILURES")
}
