plugins {
    java
    id("org.springframework.boot") version "3.2.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.diffplug.spotless") version "6.23.0"
}

group = "com.ficrowe"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry:2.0.4")

    implementation("se.michaelthelin.spotify:spotify-web-api-java:8.3.0")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Firebase
    implementation("com.google.firebase:firebase-admin:9.2.0")
//    implementation("com.google.firebase:firebase-firestore:24.8.1")
//    implementation("com.google.firebase:firebase-auth:22.1.2")
}

spotless {
    java {
        googleJavaFormat()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
