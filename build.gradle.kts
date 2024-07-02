import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    alias(libs.plugins.shadow)
}

group = "io.github.dovehometeam"
version = "1W1A"



repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:73b308673b")
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}
val java_version:String by rootProject
val targetJavaVersion = java_version.toInt()

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
    application {
        mainClass.set("io.github.nitianstudio.Main")
    }
    withType<ShadowJar>() {
        archiveFileName.set("nyc-test-${version}.jar")
    }
}
