import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"
val exposed_version: String by extra("0.31.1")
val koin_version: String by extra("3.0.1")
val kodein_version: String by extra("7.5.0")
val kotest_version: String by extra("4.5.0")
val mockk_version: String by extra("1.11.0")
val h2_version: String by extra("1.4.200")

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.23")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.kodein.di:kodein-di:$kodein_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-core-ext:$koin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.kotest:kotest-property:$kotest_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("com.h2database:h2:$h2_version")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>() {
    useJUnitPlatform()
}