val minecraft_version: String by rootProject
val neoform_version: String by rootProject

dynamicProject {
    neoform("${minecraft_version}-${neoform_version}")
}