import me.modmuss50.mpp.PublishOptions
import io.gremstudio.gauntlet.util.Loaders

plugins {
    //id("gauntlet-loader")
    id("io.gremstudio.gauntlet")
    id("net.fabricmc.fabric-loom")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

val modId = providers.gradleProperty("mod_id").get()
val modName = providers.gradleProperty("mod_name").get()

val fabricLoaderVersion = providers.gradleProperty("fabric_loader_version").get()
val fabricApiVersion = providers.gradleProperty("fabric_api_version").get()

val curseforgeId = providers.gradleProperty("curseforge_id").get()
val modrinthId = providers.gradleProperty("modrinth_id").get()
val repo = providers.gradleProperty("repo").get()
val branch = providers.gradleProperty("branch").get()

val gremlibVersion = providers.gradleProperty("gremlib_version").get()

dependencies {
    implementation ("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}+${minecraftVersion}")
    implementation("io.gremstudio:gremlib:${gremlibVersion}+fabric-${minecraftVersion}-SNAPSHOT")
}

publishMods {
    plugins.apply("java-library")

    val publishes: PublishOptions = publishOptions {
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName = (project.version.toString()).replace("+", " ").replace("-", " ").replace("fabric", "Fabric")
        file = (project.tasks.named<Jar>("jar").get().archiveFile)

        modLoaders = listOf("fabric", "quilt")
    }.get()

    curseforge("curseforgeFabric") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("CURSEFORGE_TOKEN").orNull ?: project.findProperty("curseforgeToken")
                ?.toString()
        )
        projectId.set(curseforgeId)
        minecraftVersions.add(minecraftVersion)

        changelogType.set("markdown")

        javaVersions.add(JavaVersion.VERSION_25)

        client.set(true)
        server.set(true)

        requires("fabric-api")
    }

    modrinth("modrinthFabric") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("MODRINTH_PAT").orNull ?: project.findProperty("modrinthPAT")?.toString()
        )
        projectId.set(modrinthId)
        minecraftVersions.add(minecraftVersion)

        projectDescription.set(providers.fileContents(rootProject.layout.projectDirectory.file("readme.md")).asText)

        requires("fabric-api")
    }

    github("ghFabric") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

        file = (project.tasks.named<Jar>("jar").get().archiveFile)
        this.parent(project(":").tasks.named("publishGithubParent"))
    }

}

gauntlet {
    loader {
        loader = Loaders.FABRIC
        setMixin("gremlib.fabric.mixins.json")
        loaderVersion = fabricLoaderVersion
    }
}
