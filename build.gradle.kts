
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.eclipse.jgit.api.Git
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors


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
    maven {
        name = "sponge"
        url =uri("https://repo.spongepowered.org/maven")
    }
}

fun call(dir: File, url: String): Git {
    if (!dir.exists()) {
        return Git.cloneRepository().setURI(url).setDirectory(dir).call()
    } else {


        return Git.open(dir).apply {
            this.pull()
        }
    }
}
val minestom = call(file("build/minestom"), "https://github.com/Minestom/Minestom.git")
Executors.newVirtualThreadPerTaskExecutor().use {
    val list = listOf<CompletableFuture<Void>>(
        CompletableFuture.runAsync( {
            val neo = call(file("build/neo"), "https://github.com/neoforged/NeoForge.git")
        }, it),
        CompletableFuture.runAsync({
            val fabric = call(file("build/fabric"), "https://github.com/FabricMC/fabric.git")
        }, it),
        CompletableFuture.runAsync({
            val forge = call(file("build/forge"), "https://github.com/MinecraftForge/MinecraftForge.git")
        }, it)

    )
    it.close()
}





var hash = minestom.repository.exactRef("refs/heads/master").objectId.abbreviate(10).name()





dependencies {
    implementation("net.minestom:minestom-snapshots:${hash}")
//    implementation(libs.minestom)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.jline)
    for (s in listOf("compileOnly", "annotationProcessor")) {
        add(s, libs.lombok)
    }
    implementation(libs.bundles.mixin)


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
//        applicationDefaultJvmArgs = listOf("-Djline.terminal=jline.UnsupportedTerminal")
    }

    withType<ShadowJar>() {
        archiveFileName.set("nyc-test-${version}.jar")
    }
}

