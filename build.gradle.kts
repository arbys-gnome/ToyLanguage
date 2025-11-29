plugins {
    java
    "java-test-suite"
}

group = "me.rares"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val mockitoVersion = "5.20.0"
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}