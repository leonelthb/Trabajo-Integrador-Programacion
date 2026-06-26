plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")

    implementation("com.h2database:h2:2.2.224")

    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

}

tasks.test {
    useJUnitPlatform()
}