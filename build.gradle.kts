/*
 * Copyright 2025 Rubens Gomes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the__LICENSE] [1].
 */

plugins {
    id("version-catalog")
    id("maven-publish")
    id("net.researchgate.release")
}

val developerId: String by project
val developerName: String by project

val group: String by project
val artifact: String by project
val version: String by project
val title: String by project
val description: String by project

project.group = group
project.version = version
project.description = description

// ----------------------------------------------------------------------------
// --------------- >>> Gradle Version Catalog Plugin <<< ----------------------
// ----------------------------------------------------------------------------
// https://docs.gradle.org/current/userguide/version_catalogs.html

catalog {
    versionCatalog {
        from(files("gradle/libs.versions.toml"))
    }
}

// ----------------------------------------------------------------------------
// --------------- >>> Gradle Maven Publish Plugin <<< ------------------------
// ----------------------------------------------------------------------------
// https://docs.gradle.org/current/userguide/publishing_maven.html

publishing {

    publications {
        val developerEmail: String by project

        val scmConnection: String by project
        val scmUrl: String by project

        val license: String by project
        val licenseUrl: String by project

        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = artifact
            version = project.version.toString()

            from(components["versionCatalog"])

            pom {
                name = title
                description = project.description
                inceptionYear = "2024"

                licenses {
                    license {
                        name = license
                        url = licenseUrl
                    }
                }
                developers {
                    developer {
                        id = developerId
                        name = developerName
                        email = developerEmail
                    }
                }
                scm {
                    connection = scmConnection
                    developerConnection = scmConnection
                    url = scmUrl
                }
            }

        }
    }

    repositories {
        val jvmLibsRepoPackages: String by project

        maven {
            name = "GitHubPackages"
            project.version = version
            url = uri(jvmLibsRepoPackages)
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

// ----------------------------------------------------------------------------
// --------------- >>> net.researchgate.release Plugin <<< --------------------
// ----------------------------------------------------------------------------
// https://github.com/researchgate/gradle-release

release {
    with(git) {
        pushReleaseVersionBranch.set("release")
        requireBranch.set("main")
    }
}

tasks.afterReleaseBuild {
    dependsOn("publish")
}

