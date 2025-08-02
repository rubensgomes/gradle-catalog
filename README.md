# gradle-catalog

This project implements a 
[Gradle version catalog](https://docs.gradle.org/current/userguide/platforms.html)
defining plugins and libraries to be consumed by JVM (Java and Kotlin) 
multi-repo Java/Kotlin Gradle software development projects. 

The version catalog is published to a Maven
[repository](https://repsy.io/mvn/rubensgomes/default/) for consumption by
Maven or Gradle build projects.

## CICD Automation

The project is planned to be built using an automated CircleCI build pipeline.

### CICD TODO

- Set up account in CircleCI
- Integrate GitHub with CircleCI builds

## Gradle CLI Commands

### Display Java Tools Installed

```shell
./gradlew -q javaToolchains
```

### Update the gradlew wrapper version

```shell
./gradlew wrapper --gradle-version=9.0.0 --distribution-type=bin
```

### Clean, Build, Publish, Release

```shell
git commit -m "updated gradle-catalog" -a
git push
```

```shell
./gradlew --info clean
```

```shell
./gradlew --info build
```

```shell
./gradlew --info release
```

```shell
git checkout release
./gradlew --info publish
git checkout main
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
            // https://repsy.io/mvn/rubensgomes/default/com/rubensgomes/catalog/
            from("com.rubensgomes:catalog:<release-version>")
        }
    }

    repositories {
        mavenCentral()

        maven {
            url = uri("https://repo.repsy.io/mvn/rubensgomes/default/")
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
