plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:5.0.0-alpha.14")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
/*
application {
    // This syntax has been updated to use the correct way to specify the main class in the latest versions of Gradle.
    mainClass.set("MainKt")
}
*/