import io.gremstudio.gauntlet.util.Loaders

plugins {
    id("java-library")
    //id("gauntlet-common")
    id("net.neoforged.moddev")
    id("io.gremstudio.gauntlet")
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val neoformVersion = providers.gradleProperty("neoform_version").get()
val mixinVersion = providers.gradleProperty("mixin_version").get()
val fabricMixinVersion = providers.gradleProperty("fabric_mixin_version").get()
val mixinExtrasVersion = providers.gradleProperty("mixin_extras_version").get()
val gremlibVersion = providers.gradleProperty("gremlib_version").get()

dependencies {
    implementation("io.gremstudio:gremlib:${gremlibVersion}+common-${minecraftVersion}-SNAPSHOT")
}

gauntlet {
    loader {
        loader = Loaders.COMMON
        loaderVersion = neoformVersion // Temporary workaround until I can make this more proper.
        setMixin("gremlib.mixins.json")
        setClassTweaker("gremlib.classtweaker")
    }
}
