plugins {
    org.jetbrains.kotlin.multiplatform
    `kotlinx-atomicfu`
}

kotlin {
    sourceSets.all {
        val suffixIndex = name.indexOfLast { it.isUpperCase() }
        val targetName = name.substring(0, suffixIndex)
        val (srcPath, resourcesPath) = when (val suffix = name.substring(suffixIndex).toLowerCase()) {
            "main" -> "src" to "resources"
            else   -> suffix to "${suffix}Resources"
        }
        kotlin.setSrcDirs(listOf("$targetName/$srcPath"))
        resources.setSrcDirs(listOf("$targetName/$resourcesPath"))

        languageSettings {
            progressiveMode = true

            optIn("kotlin.RequiresOptIn")
        }
    }
}
