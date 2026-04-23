plugins {
    id("java-library")
    id("gremdle-common")
    id("net.neoforged.moddev")
}

val minecraft_version : String by project
val neoform_version : String by project
val parchment_minecraft_version : String by project
val parchment_version : String by project
val is_parchment_ready : String by project

neoForge {
    neoFormVersion = neoform_version
    // Automatically enable AccessTransformers if the file exists
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    if (is_parchment_ready.equals("true")) {
        parchment {
            minecraftVersion = parchment_minecraft_version
            mappingsVersion = parchment_version
        }
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.7")

    // fabric and neoforge both bundle mixinextras, so it is safe to use it in common
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
}


val commonJava by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

val commonResources by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

artifacts {
    add(commonJava.name, (sourceSets.main.get().java.sourceDirectories.singleFile))
    add(commonResources.name, (sourceSets.main.get().resources.sourceDirectories.singleFile))
}

