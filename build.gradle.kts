buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.kotlin)
        classpath(libs.build.kotlinx.atomicfu)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.42.0"
}

//configureYarn()

//subprojects {
//    tasks.whenTaskAdded {
//        if (name.endsWith("test", ignoreCase = true)) onlyIf { !rootProject.hasProperty("skipTests") }
//        if (name.startsWith("link", ignoreCase = true)) onlyIf { !rootProject.hasProperty("skipLink") }
//    }
//}
