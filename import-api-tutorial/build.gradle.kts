val feignVersion: String by project
val assertJVersion: String by project
val junitVersion: String by project

plugins {
    id("import-api-examples.java-conventions")
}

dependencies {
    implementation(project(":import-api-java-client"))

    implementation("commons-io:commons-io:+")
    implementation("org.slf4j:slf4j-api:+")
}
