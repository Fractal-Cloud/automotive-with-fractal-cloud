plugins {
    `java-library`
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.yanchware:fractal.sdk:13.2.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "9.1.0"
}

tasks.register("prepareKotlinBuildScriptModel") {}
