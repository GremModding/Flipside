import io.gremstudio.gauntlet.metadata.Person

plugins {
    id("java-library")
    // see https://fabricmc.net/develop/ for new versions
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT" apply false
    // see https://projects.neoforged.net/neoforged/moddevgradle for new versions
    id("net.neoforged.moddev") version "2.0.144" apply false
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
    id("io.gremstudio.gauntlet") version "1.+"
}


val repo = providers.gradleProperty("repo").get()
val branch = providers.gradleProperty("branch").get()
val minecraftVersionProp = providers.gradleProperty("minecraft_version").get()
val modVersionProp = providers.gradleProperty("mod_version").get()

version = "${modVersionProp}+${project.name}-${minecraftVersionProp}"

publishMods {
    github("githubParent") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))
        repository.set(repo)
        commitish.set(branch)

        allowEmptyFiles.set(true)
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName =
            (project.version.toString()).replace("+", " ").replace("-", " ").replace("fabric", "Fabric")
    }
}

tasks.register("uploadMod") {
    description = "Uploads the mod to various platforms."
    group = "mod"

    var theTasks: MutableList<Any> = mutableListOf()

    var fabric : Project = project(":fabric")
    var neoforge : Project = project(":neoforge")

    if (project.providers.environmentVariable("PUBLISH_GH").getOrElse("False") == "True") {
        theTasks.add(rootProject.tasks["publishGHParent"])
        theTasks.add(fabric.tasks["publishGHFabric"])
        theTasks.add(neoforge.tasks["publishGHNeoforge"])
    }

    if (project.providers.environmentVariable("PUBLISH_CF").getOrElse("False") == "True") {
        theTasks.add(fabric.tasks["publishCurseforgeFabric"])
        theTasks.add(neoforge.tasks["publishCurseforgeNeoforge"])
    }

    if (project.providers.environmentVariable("PUBLISH_MR").getOrElse("False") == "True") {
        theTasks.add(fabric.tasks["publishModrinthFabric"])
        theTasks.add(neoforge.tasks["publishModrinthNeoforge"])
    }

    finalizedBy(theTasks)
}

gauntlet {
    modDetails {
        loaders = listOf("neoforge", "fabric")
        minecraftVersion = minecraftVersionProp
        modID = providers.gradleProperty("mod_id").get()
        modVersion = modVersionProp
        metadata {
            modName = providers.gradleProperty("mod_name").get()
            description = "The library used for various mods."
            license = "MIT"
            icon = "icon.png"
            authors.add(Person("Grem Studio"))
            contributors.add(Person("Siuol").setRole("Project Lead"))
            contacts = mapOf(
                "source" to "https://github.com/GremModding/Gremlib",
                "issues" to "https://github.com/GremModding/Gremlib/issues"
            )
        }

        modDependencies {
            create("fabric-api") {
                version = ">=0.154.0"
                onLoader("fabric")
            }
        }
    }
    gremSettings {
        javaVersion = providers.gradleProperty("java_version")

        neoformVersion = providers.gradleProperty("neoform_version")
        mixinVersion = providers.gradleProperty("mixin_version")
        fabricMixinVersion = providers.gradleProperty("fabric_mixin_version")
        mixinExtrasVersion = providers.gradleProperty("mixin_extras_version")
    }
}
