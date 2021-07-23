import org.gradle.jvm.tasks.Jar

group = "org.example"
version = "1.0-SNAPSHOT"

val jooqVersion = "3.15.1"

dependencies {
    implementation(project(":db"))

    // Without pro jooq dependency project sync successful, but we have java.lang.NoSuchFieldError in runtime
    implementation("org.jooq.pro-java-11:jooq:$jooqVersion")

    // Without oss jooq dependency project sync finish with
    // Could not find org.jooq:jooq:.
    // Required by:
    //    project :module > project :db
    implementation("org.jooq:jooq:$jooqVersion")

    // workaround
    dependencies {
        modules {
            module("org.jooq.jooq:jooq") {
                replacedBy("org.jooq.pro-java-11:jooq", "nu.studer.jooq bugfix")
            }
        }
    }

    // Also debug behavior depend on dependency order.
    // if oss jooq first we java.lang.NoSuchFieldError in runtime again
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Main-Class"] = "test.app.ProblemAppKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}