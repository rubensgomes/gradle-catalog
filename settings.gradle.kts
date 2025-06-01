rootProject.name = "catalog"

pluginManagement {
    val releasePluginVersion: String by settings
    plugins {
        id("net.researchgate.release") version releasePluginVersion
    }
}
