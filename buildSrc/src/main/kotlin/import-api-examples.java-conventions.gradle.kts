import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.3")
}

tasks.test {
    useJUnitPlatform()
}
