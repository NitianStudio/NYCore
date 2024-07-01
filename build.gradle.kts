plugins {
    id("java")
    id("net.neoforged.gradleutils") version("3.0.0-alpha.10") apply(false)
}

apply(plugin = "net.neoforged.gradleutils")

group = "io.github.dovehometeam"
version = "1W1A"



repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:77af815afe")
}

val targetJavaVersion = 21

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks {
    compileJava {
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            options.release = targetJavaVersion
        }
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}