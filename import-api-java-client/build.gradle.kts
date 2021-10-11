import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val oasGeneratorName: String by project
val oasLibrary: String by project
val oasGenerateModelTests: String by project
val oasGenerateApiTests: String by project

buildscript {
    repositories {
        mavenCentral()
    }

    val jsonschema2pojoVersion: String by project
    val openApiGeneratorVersion: String by project
    dependencies {
        classpath("org.jsonschema2pojo:jsonschema2pojo-gradle-plugin:$jsonschema2pojoVersion")
        classpath("org.openapitools:openapi-generator-gradle-plugin:$openApiGeneratorVersion")
    }
}

apply(plugin = "java")
apply(plugin = "org.openapi.generator")
apply(plugin = "jsonschema2pojo")


configure<JavaPluginExtension> {
    1.8
}

repositories {
    mavenCentral()
}

dependencies {
    val feignVersion: String by project
    val feignFormVersion: String by project
    val log4jApiVersion: String by project
    val openCsvVersion: String by project
    val lombokVersion: String by project
    val junitVersion: String by project
    val mockitoJunitVersion: String by project
    val assertJVersion: String by project
    val jsonschema2pojoVersion: String by project
    val openApiGeneratorVersion: String by project
    val openApiJacksonDatabindNullableVersion: String by project

    "implementation"("io.github.openfeign:feign-core:$feignVersion")
    "implementation"("io.github.openfeign:feign-jackson:$feignVersion")
    "implementation"("io.github.openfeign:feign-slf4j:$feignVersion")
    "implementation"("io.github.openfeign.form:feign-form:$feignFormVersion")
    "implementation"("org.apache.logging.log4j:log4j-api:$log4jApiVersion")
    "implementation"("org.jsonschema2pojo:jsonschema2pojo-gradle-plugin:$jsonschema2pojoVersion")
    "implementation"("org.openapitools:openapi-generator-gradle-plugin:$openApiGeneratorVersion")
    "implementation"("org.openapitools:jackson-databind-nullable:$openApiJacksonDatabindNullableVersion")
    "implementation"("com.opencsv:opencsv:$openCsvVersion")

    "compileOnly"("org.projectlombok:lombok:$lombokVersion")
    "annotationProcessor"("org.projectlombok:lombok:$lombokVersion")

    "testImplementation"("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    "testImplementation"("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    "testImplementation"("org.mockito:mockito-junit-jupiter:$mockitoJunitVersion")
    "testImplementation"("org.assertj:assertj-core:$assertJVersion")
}

configure<SourceSetContainer> {
    named("main") {
        java {
            srcDir("$buildDir/generated-openapi/src/main/java")
        }
     }
 }

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

val additionalPropertiesMap = mapOf(
        "hideGenerationTimestamp" to "true",
        "useApacheHttpClient"    to "true",
        "useFeign10"    to "true",
        "java8"         to "true"
)

val  configOptionsMap = mapOf("dateLibrary" to "java8")

 tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("buildCoreRestClient"){
     generatorName.value("$oasGeneratorName")
     library.value("$oasLibrary")
     generateModelTests.value("$oasGenerateModelTests".toBoolean())
     generateApiTests.value("$oasGenerateApiTests".toBoolean())

     inputSpec.value("$projectDir/schemas/dgc-rest.json")
     outputDir.value("$buildDir/generated-openapi")

     apiPackage.value("com.collibra.core.rest.client.api")
     invokerPackage.value("com.collibra.core.rest.client.invoker")
     modelPackage.value("com.collibra.core.rest.client.model")
     configOptions.value(configOptionsMap)
     additionalProperties.value(additionalPropertiesMap)
}

configure <org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension> {
    generatorName.value("$oasGeneratorName")
    library.value("$oasLibrary")
    generateModelTests.value("$oasGenerateModelTests".toBoolean())
    generateApiTests.value("$oasGenerateApiTests".toBoolean())

     inputSpec.value("$projectDir/schemas/dgc-importer-rest.json")
     outputDir.value("$buildDir/generated-openapi")

     apiPackage.value("com.collibra.importer.rest.client.api")
     invokerPackage.value("com.collibra.importer.rest.client.invoker")
     modelPackage.value("com.collibra.importer.rest.client.model")
     configOptions.value(configOptionsMap)
     additionalProperties.value(additionalPropertiesMap)
}

configure <org.jsonschema2pojo.gradle.JsonSchemaExtension> {
    sourceFiles = fileTree("$projectDir/schemas").files
    targetDirectory = file("$buildDir/generated-params")
    targetPackage = "com.collibra.importer.parameters"
    generateBuilders = true
    includeDynamicBuilders = true
    initializeCollections = false
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(project.tasks.getByName("buildCoreRestClient"), project.tasks.getByName("openApiGenerate"))
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(project.tasks.getByName("generateJsonSchema2Pojo"))
}

