plugins {
    id("gremdle-loader")
    id("net.fabricmc.fabric-loom")
}

val minecraft_version : String by project

val mod_id: String by project
val version: String by project
val mod_name: String by project

val fabric_loader_version : String by project
val fabric_api_version : String by project
val gremlib_version : String by project

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    implementation ("net.fabricmc:fabric-loader:${fabric_loader_version}")
    implementation ("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}+${minecraft_version}")

    implementation("io.siuolplex:gremlib:${gremlib_version}+fabric")
}

loom {
    var aw = project(":common").file("src/main/resources/${mod_id}.accesswidener")

    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }

    runs {
        this.getByName("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run/client")
        }

        this.getByName("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run/server")
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}
