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

catalog {
    versionCatalog {
        from(files("gradle/libs.versions.toml"))
    }
}

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
        val repsyUrl: String by project
        val repsyUsername: String by project
        val repsyPassword: String by project

        maven {
            project.version = version
            url = uri(repsyUrl)
            credentials {
                username = repsyUsername
                password = repsyPassword
            }
        }
    }
}

// "net.researchgate.release" configuration
release {
    with(git) {
        pushReleaseVersionBranch.set("release")
        requireBranch.set("main")
    }
}

// "net.researchgate.release" configuration
tasks.afterReleaseBuild {
    dependsOn("publish")
}

