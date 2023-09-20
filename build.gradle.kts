plugins {
    java
    id("org.springframework.boot") version "3.2.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.2"
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
    implementation("software.amazon.awssdk:dynamodb:2.20.125")
    compileOnly("org.projectlombok:lombok")
    compileOnly("software.amazon.awssdk:aws-sdk-java:2.20.125")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
