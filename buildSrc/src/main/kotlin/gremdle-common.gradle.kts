plugins {
    id("java-library")
    id("maven-publish")
}

val java_version: String by project
val minecraft_version: String by project
val mod_id: String by project
val mod_version: String by project
val mod_name: String by project
val mod_author: String by project

base {
    archivesName = "${mod_id}-${project.name}"
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(java_version)
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()

    // https://docs.gradle.org/current/userguide/declaring_repositories.html#declaring_content_exclusively_found_in_one_repository

    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }

    exclusiveContent {
        forRepositories(
            maven {
                name = "ParchmentMC"
                url = uri("https://maven.parchmentmc.org/")
            },
            maven {
                name = "NeoForge"
                url = uri("https://maven.neoforged.net/releases")
            }
        )
        filter { includeGroup("org.parchmentmc.data") }
    }

    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com")
    }
}

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
arrayOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.get(variant).outgoing {
        capability("$group:${project.name}:$version")
        capability("$group:${base.archivesName.get()}:$version")
        capability("$group:$mod_id-${project.name}-${minecraft_version}:$version")
        capability("$group:$mod_id:$version")
    }

    publishing.publications.configureEach {
        if (this is MavenPublication)
            suppressPomMetadataWarningsFor(variant)
    }
}

tasks {
    getByName<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }
    }


    getByName<Jar>("jar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }

        val archiveVersion = this.archiveVersion
        manifest {
            attributes += mapOf(
                "Specification-Title" to mod_name,
                "Specification-Vendor" to mod_author,
                "Specification-Version" to archiveVersion,
                "Implementation-Title" to "flipside",
                "Implementation-Version" to archiveVersion,
                "Implementation-Vendor" to mod_author,
                "Built-On-Minecraft" to minecraft_version
            )
        }
    }


   getByName<ProcessResources>("processResources") {
        var expandProps = mutableMapOf(
            "mod_version" to mod_version,
            //"group" to project.group, //Else we target the task's group.
            "minecraft_version" to minecraft_version
        )

        var jsonExpandProps = mutableMapOf<String, Object>();

        expandProps.forEach {
                entry -> jsonExpandProps += mapOf(entry.key to
                    (if (entry.value is String) (entry.value as String).replace("\n", "\\\\n") else entry.value) as Object
                )
        }

        filesMatching(listOf("META-INF/neoforge.mods.toml")) {
            expand(expandProps)
        }

        files(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
            expand(jsonExpandProps)
        }

        inputs.properties(expandProps)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components.getByName("java"))
        }
    }

    /*repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }*/
}
