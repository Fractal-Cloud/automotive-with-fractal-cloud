plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka_2.12:2.3.1")
    implementation("com.microsoft.azure:azure-client-authentication:1.7.14")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.22")
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "cloud.fractal.samples.automotive.application.reader.ReaderApp"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "9.1.0"
}

tasks.register("prepareKotlinBuildScriptModel") {}
