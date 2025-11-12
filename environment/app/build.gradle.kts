plugins {
    application
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":environment"))
    implementation("com.yanchware:fractal.sdk:13.2.4")
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "cloud.fractal.samples.automotive.environment.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "9.1.0"
}

tasks.register("prepareKotlinBuildScriptModel") {}
