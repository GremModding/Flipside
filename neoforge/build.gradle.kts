plugins {
    id("gremdle-loader")
    id("net.neoforged.moddev")
}

val minecraft_version : String by project

val mod_id: String by project
val version: String by project
val mod_name: String by project

val neoforge_version : String by project
val gremlib_version : String by project

neoForge {
    version = neoforge_version
    // Automatically enable neoforge AccessTransformers if the file exists
    var at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    val intInject = project(":common").file("interfaces.json")
    if (intInject.exists()) {
        interfaceInjectionData.from(intInject.absolutePath)
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
            ideName = "NeoForge ${this.name.capitalize()} (${project.path})" // Unify the run config names with fabric
        }
        register("client") {
            client()
            gameDirectory = project.file("run/client")
        }
        register("data") {
            clientData()
            gameDirectory = project.file("run/client")
            // DataGen can be run by - "./gradlew :neoforge:runData" in Terminal.
            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll( "--mod", mod_id, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
        }
        register("server") {
            server()
            gameDirectory = project.file("run/server")
        }
    }
    mods {
        this.register(mod_id) {
            sourceSet (sourceSets.main.get())
        }
    }
}

sourceSets.main.get().resources {
    srcDir ("src/generated/resources")
}

dependencies {
    implementation("io.siuolplex:gremlib:${gremlib_version}+neoforge")
}
