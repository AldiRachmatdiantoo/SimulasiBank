plugins {
    kotlin("jvm") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}