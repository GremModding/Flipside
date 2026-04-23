plugins {
    id("gremdle-loader")
    id("fabric-loom")
}

val minecraft_version : String by project

val mod_id: String by project
val mod_version: String by project
val mod_name: String by project

val parchment_minecraft_version : String by project
val parchment_version : String by project

val fabric_loader_version : String by project
val fabric_api_version : String by project

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings (
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${parchment_minecraft_version}:${parchment_version}@zip")
        }
    )
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}+${minecraft_version}")
}

loom {
    var aw = project(":common").file("src/main/resources/${mod_id}.accesswidener")

    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }

    mixin {
        defaultRefmapName = ("${mod_id}.refmap.json")
    }

    runs {
        this.getByName("client") {
            client()
            setConfigName("Fabric Client")
            ideConfigGenerated(true)
            runDir("run/client")
        }

        this.getByName( "server") {
            server()
            setConfigName("Fabric Server")
            ideConfigGenerated(true)
            runDir("run/server")
        }
    }
}

fabricApi {
    configureDataGeneration() {
        client = true
    }
}
