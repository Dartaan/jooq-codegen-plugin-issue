group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.5.20"
    id("nu.studer.jooq") version "6.0" apply false
}

allprojects {
    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
        // TODO set repository with JOOQ PRO
        maven {
            url = uri("")
            credentials {
                username = ""
                password = ""
            }
        }
    }
}

