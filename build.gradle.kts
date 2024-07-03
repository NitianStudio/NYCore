import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.GitCommand


buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
    }
}

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
var gitMineston = file("build/jgit")

var call: Git;
if (!gitMineston.exists()) {
    val cc = Git.cloneRepository().setURI("https://github.com/Minestom/Minestom.git")
    call = cc.setDirectory(gitMineston).call()
} else {
    call = Git.open(gitMineston)
    call.pull()
}
var hash = call.repository.exactRef("refs/heads/master").objectId.abbreviate(10).name()



dependencies {
    implementation("net.minestom:minestom-snapshots:${hash}")
//    implementation(libs.minestom)
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation(libs.slf4j2.api)
    implementation(libs.log4j.slf4j2.impl)
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
        val r = file("run")
        if (!r.exists()) {
            r.mkdirs()
        }
        getByName<JavaExec>("run").workingDir(r)

    }
    withType<ShadowJar>() {
        archiveFileName.set("nyc-test-${version}.jar")
    }
}

