plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
    implementation("org.flywaydb:flyway-core:11.1.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.1.1")
    implementation("com.azure.spring:spring-cloud-azure-starter-keyvault-secrets:5.19.0")
    compileOnly("org.projectlombok:lombok:1.18.36")
    runtimeOnly("org.postgresql:postgresql:42.7.4")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.yanchware.fractalcloud.samples.municipalities.application.writer.WriterApp"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "9.1.0"
}

tasks.register("prepareKotlinBuildScriptModel") {}
