import io.gremstudio.gauntlet.util.Loaders
import me.modmuss50.mpp.PublishOptions

plugins {
    //id("gauntlet-loader")
    id("io.gremstudio.gauntlet")
    id("net.neoforged.moddev")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

val modId = providers.gradleProperty("mod_id").get()
val modName = providers.gradleProperty("mod_name").get()

val neoforgeVersion = providers.gradleProperty("neoforge_version").get()

val curseforgeId = providers.gradleProperty("curseforge_id").get()
val modrinthId = providers.gradleProperty("modrinth_id").get()
val repo = providers.gradleProperty("repo").get()
val branch = providers.gradleProperty("branch").get()

val gremlibVersion = providers.gradleProperty("gremlib_version").get()

dependencies {
    implementation("io.gremstudio:gremlib:${gremlibVersion}+neoforge-${minecraftVersion}-SNAPSHOT")
    accessTransformers("io.gremstudio:gremlib:${gremlibVersion}+neoforge-${minecraftVersion}-SNAPSHOT")
    interfaceInjectionData("io.gremstudio:gremlib:${gremlibVersion}+neoforge-${minecraftVersion}-SNAPSHOT")
}

publishMods {
    plugins.apply("java-library")

    val publishes: PublishOptions = publishOptions {
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName = (project.version.toString()).replace("+", " ").replace("-", " ").replace("neoforge", "Neoforge")
        file = (project.tasks.named<Jar>("jar").get().archiveFile)

        modLoaders.add("neoforge")
    }.get()

    curseforge("curseforgeNeo") {
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

    }

    modrinth("modrinthNeo") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("MODRINTH_PAT").orNull ?: project.findProperty("modrinthPAT")?.toString()
        )
        projectId.set(modrinthId)
        minecraftVersions.add(minecraftVersion)
    }

    github("ghNeo") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

        file = (project.tasks.named<Jar>("jar").get().archiveFile)
        this.parent(project(":").tasks.named("publishGithubParent"))
    }
}

gauntlet {
    loader {
        loader = Loaders.NEOFORGE
        setMixin("gremlib.neoforge.mixins.json")
        loaderVersion = neoforgeVersion
    }
}
