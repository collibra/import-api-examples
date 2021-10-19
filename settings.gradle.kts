pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "import-api-examples"

include(
    "import-api-java-client",
    "input-file-examples",
    "import-api-tutorial",
    "data-quality"
)
