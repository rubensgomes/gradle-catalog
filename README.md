# gradle-catalog

This project implements a
[Gradle version catalog](https://docs.gradle.org/current/userguide/platforms.html)
defining plugins and libraries to be consumed by Java and Kotlin JVM (Java
Virtual Machine) Gradle software development projects.

The version catalog is published to a
Maven [repository](https://repsy.io/mvn/rubensgomes/default/) for consumption by
Maven or Gradle build projects.

## Branching Strategy

The project is using two branches:

1. **_main_**: which is used as the Trunk-Based Development (TBD) with tagging
   for new
   releases.
2. **_release_**: which contains the most recently released code. That is, every
   time
   a release is made, this branch is updated.

## CICD Automation

The project was initially planned to be built using an automated CircleCI build
pipeline. Recently, the CI/CD build pipline has been moved to use The GitHub
Workflow Actions.

The built artifact package is now being deployed to the following GitHub Pakcage:

- https://maven.pkg.github.com/rubensgomes/jvm-libs

## Gradle CLI Commands

### Display Java Tools Installed

```bash
./gradlew -q javaToolchains
```

### Update the gradlew wrapper version

```bash
./gradlew wrapper --gradle-version=9.2.1 --distribution-type=bin
```

### Clean, Build, Publish, Release

```bash
./gradlew --info clean
```

```bash
./gradlew --info clean build
```

```bash
# every push to the main branch should trigger a new release.
git commit -m "updated gradle-catalog" -a
git push
```

```bash
# creates a release and publishes the released artifacts
./gradlew --info release
```

### Usage: Gradle Kotlin DSL

First, make sure you delete local `gradle/libs.versions.toml`.

### gradle-catalog artifact version

Lookup the latest released version of the gradle `catalog` artifact in the Maven
[repository](https://repsy.io/mvn/rubensgomes/default/).

### gradle build configurations

- settings.gradle.kts

```kotlin
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // NOTE: you should use the latest released version from:
            // https://maven.pkg.github.com/rubensgomes/com/rubensgomes/catalog/
            from("com.rubensgomes:catalog:<release-version>")
        }
    }

    repositories {
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/rubensgomes/jvm-libs")
        }
    }
}
```

- build.gradle.kts

```kotlin
plugins {
    // Examples of applying plugins defined in the version catalog.
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    // Examples of using a single library in implementation 
    implementation(libs.commons.configuration2)
    implementation(libs.jakarta.validation.api)

    // Examples of using bundled libraries in implementation 
    implementation(libs.bundles.jakarta.bean.validator)

    // Examples of using bundled libraries in testImplementation 
    testImplementation(libs.bundles.kotlin.junit5)

    // Examples of using single library in testRuntimeOnly 
    testRuntimeOnly(libs.junit.platform.launcher)

}
```

---
Author:  [Rubens Gomes](https://rubensgomes.com/)
