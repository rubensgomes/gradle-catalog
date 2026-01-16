# CLAUDE.md

## Project Overview

This is a **Gradle Version Catalog** project that provides centralized
dependency and plugin version management for Java/Kotlin libraries. It
contains no application source code—only Gradle build configuration files.

## Build Commands

```bash
./gradlew clean build          # Clean and build
./gradlew clean publish        # Publish to Maven repository
./gradlew release              # Execute release workflow (main branch only)
```

## Project Structure

- `build.gradle.kts` - Main build configuration (Kotlin DSL)
- `settings.gradle.kts` - Settings and plugin management
- `gradle.properties` - Project metadata and version
- `gradle/libs.versions.toml` - **The version catalog** (core of this project)
- `.circleci/config.yml` - CircleCI CI/CD pipeline configuration (no longer used)
- `.github/workflows/release.yml` - GitHub Workflow Actions CI/CD pipeline configuration

## Key Configuration

- **Group ID**: com.rubensgomes
- **Artifact ID**: gradle-catalog
- **Publishing**: GitHub Packages Maven repository
- **Credentials**: `GITHUB_USER` and `GITHUB_TOKEN` environment
  variables

## Version Catalog (libs.versions.toml)

The catalog defines:

- **[versions]** - Version numbers for dependencies
- **[libraries]** - Dependency coordinates using version references
- **[plugins]** - Gradle plugin definitions
- **[bundles]** - Groups of related libraries

## Release Process

The release plugin automates:

1. Removes "-SNAPSHOT" from version
2. Commits to release branch and tags
3. Publishes artifact to GitHub Packages
4. Increments version back to SNAPSHOT on main

## Consuming This Catalog

In dependent projects:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.rubensgomes:catalog:<version>")
        }
    }
}

// build.gradle.kts
dependencies {
    implementation(libs.commons.configuration2)
    testImplementation(libs.bundles.kotlin.junit5)
}
```
