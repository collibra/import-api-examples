plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

val feignVersion: String by project
val assertJVersion: String by project
val log4jApiVersion: String by project

dependencies {
    implementation(project(":import-api-java-client"))
    implementation("io.github.openfeign:feign-core:$feignVersion")
    implementation("io.github.openfeign:feign-jackson:$feignVersion")
    implementation("io.github.openfeign:feign-slf4j:$feignVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jApiVersion")

    implementation("org.assertj:assertj-core:$assertJVersion")
    implementation("commons-io:commons-io:+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
