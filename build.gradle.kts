plugins {
    id("java")
}

group = "io.github.dovehometeam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:77af815afe")
}

tasks.test {
    useJUnitPlatform()
}