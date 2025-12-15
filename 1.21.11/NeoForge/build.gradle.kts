plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
}

dependencies {
    modCompileOnly(libs.puzzleslib.common)
    modApi(libs.puzzleslib.neoforge)
    modCompileOnly(libs.hotbarslotcycling.common)
    modApi(libs.hotbarslotcycling.neoforge)
    include(libs.hotbarslotcycling.neoforge)
}
