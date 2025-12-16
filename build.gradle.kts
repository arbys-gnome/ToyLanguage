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
val groovyVersion = "5.0.0"
val spockVersion = "2.4-M7-groovy-5.0"
val junitPlatformVersion = "1.10.2"
val mockkVersion = "1.13.10"

dependencies {
    // Kotest framework
    testImplementation("io.kotest:kotest-framework-datatest:${kotestVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-property:${kotestVersion}")
    testImplementation("io.mockk:mockk:${mockkVersion}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    systemProperty("kotest.framework.classpath.scanning.config.disable", "false")
    useJUnitPlatform()
}