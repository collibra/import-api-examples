plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

val feignVersion: String by project
val feignFormVersion: String by project
val log4jApiVersion: String by project
val openCsvVersion: String by project
val lombokVersion: String by project
val junitVersion: String by project
val mockitoJunitVersion: String by project
val assertJVersion: String by project

dependencies {
    implementation(project(":import-api-java-client"))

    implementation("io.github.openfeign:feign-core:$feignVersion")
    implementation("io.github.openfeign:feign-jackson:$feignVersion")
    implementation("io.github.openfeign:feign-slf4j:$feignVersion")
    implementation("io.github.openfeign.form:feign-form:$feignFormVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jApiVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoJunitVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("commons-io:commons-io:+")
}

tasks.test {
    useJUnitPlatform()
}
