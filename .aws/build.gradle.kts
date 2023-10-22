plugins {
    java
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
    implementation("software.amazon.awssdk:aws-cdk-lib:2.91.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
