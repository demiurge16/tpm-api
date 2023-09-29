import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "net.nuclearprometheus.tpm"
version = "1.0.0-BETA"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val springCloudVersion: String by extra { "2022.0.4" }
val liquibaseVersion: String by extra { "4.20.0" }
val springdocVersion: String by extra { "2.1.0" }
val minioVersion: String by extra { "8.5.2" }
val keycloakVersion: String by extra { "22.0.1" }
val openCsvVersion: String by extra { "5.8" }
val logstashLogbackEncoderVersion: String by extra { "7.4" }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.keycloak:keycloak-core:$keycloakVersion")
    implementation("org.keycloak:keycloak-policy-enforcer:$keycloakVersion")
    implementation("org.keycloak:keycloak-admin-client:$keycloakVersion")
    implementation("org.keycloak:keycloak-authz-client:$keycloakVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.liquibase.ext:liquibase-hibernate6:$liquibaseVersion")
    implementation("com.opencsv:opencsv:$openCsvVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
    implementation("io.minio:minio:$minioVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}
