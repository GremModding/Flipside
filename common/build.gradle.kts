plugins {
    id("java-library")
    id("gremdle-common")
    id("net.neoforged.moddev")
}

val minecraft_version : String by project
val neoform_version : String by project
val mixin_version : String by project
val fabric_mixin_version : String by project
val mixin_extras_version : String by project

val gremlib_version : String by project

neoForge {
    neoFormVersion = neoform_version
    // Automatically enable AccessTransformers if the file exists
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    val interfaceInjection = file("src/main/resources")
}

dependencies {
    compileOnly("net.fabricmc:sponge-mixin:${fabric_mixin_version}+mixin.${mixin_version}")

    // fabric and neoforge both bundle mixinextras, so it is safe to use it in common
    compileOnly("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")
    annotationProcessor("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")

    implementation("io.siuolplex:gremlib:${gremlib_version}+common")
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
