# gradle-catalog

This project implements a
[Gradle version catalog](https://docs.gradle.org/current/userguide/platforms.html)
defining plugins and libraries to be consumed by Java and Kotlin JVM (Java
Virtual Machine) Gradle software development projects.

The catalog is published as a Maven artifact
(`com.rubensgomes:gradle-catalog`) to
[GitHub Packages](https://github.com/rubensgomes/jvm-libs/packages) for
consumption by Gradle build projects.

## Requirements

- **JDK 25** (Temurin, matches CI)
- **Gradle 9.6.1** (via the included wrapper — no local install needed)

## Branching Strategy

The project uses two branches:

1. **`main`** — Trunk-Based Development (TBD); tagged for new releases.
2. **`release`** — contains the most recently released code. Updated on
   every release by the `net.researchgate.release` plugin.

## CI/CD

CI/CD is defined in [`.github/workflows/release.yml`](.github/workflows/release.yml).
Every push to `main` triggers the release workflow, which runs
`./gradlew release` and publishes the resulting artifact to GitHub Packages:

- Browse published packages: https://github.com/rubensgomes/jvm-libs/packages
- Maven repository endpoint (for build scripts, not browsers):
  `https://maven.pkg.github.com/rubensgomes/jvm-libs`

## Consuming This Catalog

### 1. Look up the latest published version

Browse the published versions on the GitHub Packages page:

- https://github.com/rubensgomes/jvm-libs/packages/2811984

Or check the latest git tag on the [`release`](https://github.com/rubensgomes/gradle-catalog/tree/release)
branch.

### 2. Configure `settings.gradle.kts`

> **Important:** GitHub Packages requires authentication even for **reads**.
> Set `GITHUB_USER` and `GITHUB_TOKEN` (a PAT with the `read:packages` scope)
> in your environment before running Gradle.

```kotlin
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.rubensgomes:gradle-catalog:<release-version>")
        }
    }

    repositories {
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/rubensgomes/jvm-libs")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

If your project already has its own `gradle/libs.versions.toml`, delete it
before importing this catalog — a project cannot have both a local catalog
file and an imported catalog under the same name (`libs`).

### 3. Use the catalog in `build.gradle.kts`

```kotlin
plugins {
    // Examples of applying plugins defined in the version catalog.
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    // Single library
    implementation(libs.commons.configuration2)
    implementation(libs.jakarta.validation.api)

    // Bundle of related libraries
    implementation(libs.bundles.jakarta.bean.validator)

    // Test bundle
    testImplementation(libs.bundles.kotlin.junit5)

    // Single library on the test runtime classpath
    testRuntimeOnly(libs.junit.platform.launcher)
}
```

## Local Development

```bash
./gradlew -q javaToolchains                                     # List installed JDKs
./gradlew wrapper --gradle-version=9.6.1 --distribution-type=bin # Update wrapper
./gradlew clean                                                 # Clean build outputs
./gradlew clean build                                           # Full local build
```

## Releasing

The `net.researchgate.release` plugin automates the release lifecycle
(strip `-SNAPSHOT` → tag → publish → bump to next `-SNAPSHOT`). Releases
are normally driven by CI on every push to `main`:

```bash
git commit -am "updated gradle-catalog"
git push
```

To run the release manually from a machine with `GITHUB_USER` and
`GITHUB_TOKEN` (PAT with `write:packages`) set:

```bash
./gradlew --info release
```

---
Author: [Rubens Gomes](https://rubensgomes.com/)
