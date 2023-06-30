import com.bmuschko.gradle.docker.tasks.container.*
import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.bmuschko.docker-java-application") version "9.3.1"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.netty:netty-all:4.1.94.Final")
    runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.61.Final:linux-aarch_64")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.example.crash_reproducer.App")
}

docker {
    javaApplication {
        baseImage.set("bellsoft/liberica-openjdk-alpine-musl:17.0.7@sha256:195caee86f92aaa7433478320ffdc2265a5e47103785235924acd943e625abe4")
    }
}

tasks.named<Dockerfile>("dockerCreateDockerfile") {
    environmentVariable("JAVA_OPTS", "-XX:+CreateCoredumpOnCrash")
    instruction("RUN apk add libssl3=3.1.1-r1 libcrypto3=3.1.1-r1")
    instruction("RUN apk --no-cache add libgomp libstdc++")
}
