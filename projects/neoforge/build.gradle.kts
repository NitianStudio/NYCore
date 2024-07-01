import net.neoforged.gradleutils.ChangelogGenerationExtension
import net.neoforged.gradleutils.GradleUtilsExtension
import net.neoforged.jarcompatibilitychecker.gradle.JCCPlugin
import org.gradle.toolchains.foojay.distributionOrderOfPreference

plugins {
    `java-library`
    `maven-publish`
    id("net.neoforged.jarcompatibilitychecker") version("0.1.9")
}

apply(plugin = "net.neoforged.gradleutils")

val minecraft_version: String by rootProject
val neoform_version: String by rootProject

val gradleutils:GradleUtilsExtension by project

gradleutils.setupSigning(project, true)

val changelog:ChangelogGenerationExtension by project

changelog.from("20.6")
changelog.disableAutomaticPublicationRegistration()

dynamicProject {
    runtime("${minecraft_version}-${neoform_version}",
        rootProject.layout.projectDirectory.dir("patches"),
        rootProject.layout.projectDirectory.dir("rejects"))
}

val checkVersion = JCCPlugin.providePreviousVersion(
    project.providers, project.providers.provider(listOf("https://maven.neoforged.net/releases")), project.providers.provider("net.neoforged:neoforge")

)
final createCompatJar = tasks.register('createCompatibilityCheckJar', ProvideNeoForgeJarTask) {
    // Use the same jar that the patches were generated against
    cleanJar.set(tasks.generateClientBinaryPatches.clean)
    maven.set('https://maven.neoforged.net/releases')
    artifact.set('net.neoforged:neoforge')
    version.set(checkVersion)
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(java_version)
    }
}