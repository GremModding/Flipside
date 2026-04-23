plugins {
    id("java-library")
    id("gremdle-common")
}

val java_version: Int by project
val minecraft_version: String by project
val mod_id: String by project
val mod_version: String by project
val mod_name: String by project
val mod_author: String by project

val commonJava by configurations.creating {
    isCanBeResolved = true
}

val commonResources by configurations.creating {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("$group:$mod_id")
        }
        val loaderAttribute: Attribute<String> = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
        attributes {
            attribute(loaderAttribute, "common")
        }
    }

    commonJava(project(":common", "commonJava"))
    commonResources(project(":common", "commonResources"))
}

tasks {
    getByName<ProcessResources>("processResources") {
        dependsOn(commonResources)
        from(commonResources)
    }

    getByName<JavaCompile>("compileJava") {
        dependsOn(commonJava)
        source(commonJava)
    }

    getByName<Javadoc>("javadoc") {
        dependsOn(commonJava)
        source(commonJava)
    }

    getByName<Jar>("sourcesJar") {
        dependsOn(commonJava)
        from(commonJava)
        dependsOn(commonResources)
        from(commonResources)
    }
}
