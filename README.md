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

## Import Local Repo to GitHub

```shell
git init -b main
git add .
git commit -m "initial commit" -a
gh repo create --homepage "https://github.com/rubensgomes" --private gradle-catalog
git remote add origin https://github.com/rubensgomes/gradle-catalog
git push -u origin main
```

Then, go to the [repo](https://github.com/rubensgomes/gradle-catalog) and create
a `release` branch. Click on the `drop-down` to `View all branches` and click
on the `New branch` button.

## Display Java Tools Installed

```shell
./gradlew -q javaToolchains
```

## Clean, Build, Publish, Release

```shell
./gradlew --info clean
```

```shell
git commit -m "updated gradle-catalog" -a
git push
```

```shell
./gradlew --info build
```


```shell
./gradlew --info publish
```

```shell
./gradlew --info release
```

## Usage: Gradle Kotlin DSL

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
