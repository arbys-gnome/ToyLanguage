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
val mockitoVersion = "5.20.0"

// Create a configuration for the Mockito agent
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    // Kotest framework
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-property:${kotestVersion}")

    testImplementation("io.kotest:kotest-framework-datatest:${kotestVersion}")

    testImplementation("org.spockframework:spock-core:$spockVersion")
    testImplementation("org.apache.groovy:groovy:$groovyVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    mockitoAgent("org.mockito:mockito-core:$mockitoVersion") {
        isTransitive = false
    }
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    systemProperty("kotest.framework.classpath.scanning.config.disable", "false")
    useJUnitPlatform() // for spock

    // Attach Mockito as a javaagent
    doFirst {
        val agentJar = mockitoAgent.singleFile
        jvmArgs("-javaagent:$agentJar")
    }
}