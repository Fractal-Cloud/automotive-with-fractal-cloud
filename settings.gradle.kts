plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "automotive-with-fractal-cloud"
include(":app-writer")
include(":app-reader")
include(":architecture")
include(":environment")
include(":environment-app")

project(":app-writer").projectDir = file("application/writer/app")
project(":app-reader").projectDir = file("application/reader/app")
project(":architecture").projectDir = file("architecture/app")
project(":environment").projectDir = file("environment/lib")
project(":environment-app").projectDir = file("environment/app")
