import nu.studer.gradle.jooq.JooqEdition.PRO_JAVA_11
import org.gradle.jvm.tasks.Jar
import org.jooq.meta.jaxb.Logging.WARN
import org.jooq.meta.jaxb.SchemaMappingType

group = "org.example"
version = "1.0-SNAPSHOT"

val jooqVersion = "3.15.1"

val jooqPackageName = "test.db.entity"
val driverDependency = "com.oracle.database.jdbc:ojdbc10:19.8.0.0"
val jooqDriver = "oracle.jdbc.OracleDriver"

val jooqUrl = ""
val jooqUser = ""
val jooqPassword = ""
val jooqDatabaseName = "org.jooq.meta.oracle.OracleDatabase"
val jooqSchemas = listOf("")
val jooqIncludes = listOf("").joinToString("|")

plugins {
    id("nu.studer.jooq")
}

dependencies {
    implementation(driverDependency)
    jooqGenerator(driverDependency)
}

jooq {
    version.set(jooqVersion)
    edition.set(PRO_JAVA_11)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = WARN
                jdbc.apply {
                    driver = jooqDriver
                    url = jooqUrl
                    user = jooqUser
                    password = jooqPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        isDateAsTimestamp = true
                        name = jooqDatabaseName
                        includes = jooqIncludes
                        schemata.apply {
                            jooqSchemas.forEach {
                                add(SchemaMappingType().withInputSchema(it))
                            }
                        }
                    }
                    target.apply {
                        packageName = jooqPackageName
                        directory = "src/main/java"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

project.tasks.findByName("generateJooq")?.onlyIf { false }

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Main-Class"] = "test.db.SuccessAppKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}