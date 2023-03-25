import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.liquibase.gradle") version "2.2.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "net.nuclearprometheus.tpm"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val springCloudVersion: String by extra {
    "2022.0.1"
}

val liquibaseVersion: String by extra {
    "4.20.0"
}

val kotlinCsvVersion: String by extra {
    "1.8.0"
}

val springdocVersion: String by extra {
    "2.0.4"
}

val logstashLogbackEncoderVersion: String by extra {
    "7.3"
}

val elasticApmAgentVersion: String by extra {
    "1.36.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.liquibase.ext:liquibase-hibernate6:$liquibaseVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:$kotlinCsvVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
    implementation("co.elastic.apm:apm-agent-attach:$elasticApmAgentVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.postgresql:postgresql")
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:$liquibaseVersion")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("org.springframework.boot:spring-boot-starter-data-jpa")
    liquibaseRuntime(sourceSets.getByName("main").output)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

liquibase {
    activities {
        create("main") {
            arguments = mapOf(
                "url" to "jdbc:postgresql://localhost:5432/tpm",
                "username" to "application",
                "password" to "1qaz@WSX",
                "changeLogFile" to "./src/main/resources/db/changelog/db.changelog-master.yml",
                "referenceUrl" to "hibernate:spring:net.nuclearpromentheus.tpm.applicationserver?dialect=org.hibernate.dialect.PostgreSQLDialect",
            )
        }
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

tasks.named("diff") {
    dependsOn("compileKotlin")
}

tasks.named("diffChangelog") {
    dependsOn("compileKotlin")
}

tasks.named("generateChangelog") {
    dependsOn("compileKotlin")
}
