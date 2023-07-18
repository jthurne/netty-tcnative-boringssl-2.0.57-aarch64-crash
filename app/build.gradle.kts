import com.bmuschko.gradle.docker.tasks.container.*
import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    application
    id("com.bmuschko.docker-java-application") version "9.3.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.94.Final")
    runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.61.Final:linux-aarch_64")
//    implementation("io.netty:netty-all:4.1.87.Final")
//    runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.56.Final:linux-aarch_64")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.example.crash_reproducer.App")
}

docker {
    javaApplication {
        baseImage.set("bellsoft/liberica-openjdk-alpine-musl:17.0.7@sha256:195caee86f92aaa7433478320ffdc2265a5e47103785235924acd943e625abe4")
    }
}

tasks.named<Dockerfile>("dockerCreateDockerfile") {
    environmentVariable("JAVA_OPTS", "-XX:+CreateCoredumpOnCrash")
    instruction("RUN apk update && apk --no-cache add libc6-compat")
}
