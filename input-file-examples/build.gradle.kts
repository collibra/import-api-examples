val feignVersion: String by project
val feignFormVersion: String by project
val openCsvVersion: String by project
val lombokVersion: String by project
val junitVersion: String by project
val mockitoJunitVersion: String by project
val assertJVersion: String by project

plugins {
    id("import-api-examples.java-conventions")
}

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation(project(":import-api-java-client"))

    implementation( "com.fasterxml.jackson.core:jackson-annotations:2.17.2")
    implementation( "com.fasterxml.jackson.core:jackson-databind:2.17.2")

    testImplementation("commons-io:commons-io:+")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoJunitVersion")
}

tasks.test {
    outputs.upToDateWhen { project.hasProperty("IGNORE_TEST_FAILURES") }
    ignoreFailures = project.hasProperty("IGNORE_TEST_FAILURES")
}
