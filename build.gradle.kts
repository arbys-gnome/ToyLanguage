plugins {
    id("java")
    id("groovy")
    kotlin("jvm") version "2.2.21"
}

group = "io.github.BogdanR6"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

val kotestVersion = "5.9.1"
val junitPlatformVersion = "1.10.2"
val mockkVersion = "1.13.10"
val log4jVersion = "2.25.2"

dependencies {
    // Kotest framework
    testImplementation("io.kotest:kotest-framework-datatest:${kotestVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-property:${kotestVersion}")
    testImplementation("io.mockk:mockk:${mockkVersion}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:${log4jVersion}")}

tasks.test {
    systemProperty("kotest.framework.classpath.scanning.config.disable", "false")
    useJUnitPlatform()
}