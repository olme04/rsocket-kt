enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "rsocket-kt"

fun module(name: String, vararg submodules: String) {
    include(name)
    project(":$name").projectDir = file("rsocket-$name")

    submodules.forEach { submodule ->
        include("$name:$submodule")
        project(":$name:$submodule").projectDir = file("rsocket-$name/rsocket-$submodule")
    }
}

class Module(val parent: Module?, val names: List<String>) {

    val nested = mutableListOf<Module>()
    operator fun String.invoke(block: Module.() -> Unit = {}) {
        nested += Module(this@Module, names + this).apply(block)
    }

    val name: String = names.joinToString("-")
    fun projectPath(): String = (parent?.projectPath()?.let { "$it:" } ?: "") + name
    fun projectDir(): String = (parent?.projectDir()?.let { "$it/rsocket-" } ?: "") + name
}

fun modules(block: Module.() -> Unit) {
    fun List<Module>.create(): Unit = forEach { module ->
//        println(
//            module.name + " | " +
//                    module.projectPath().drop(1) + " | " +
//                    module.projectPath() + " | " +
//                    module.projectDir().drop(1)
//        )
        include(module.projectPath().drop(1))
        project(module.projectPath()).projectDir = file(module.projectDir().drop(1))
        module.nested.create()
    }

    Module(null, emptyList()).apply(block).nested.create()
}

modules {
    "io" {
        "ktor"()
        "okio"()
        "netty"()
    }
    "logging" {
        "kermit"()
        "slf4j"()
        "microutils"()
    }

    "annotations"() //???
    "coroutines"() //jobs, channels, flows extensions
    "internal"() //int map

    "protocol" {
        "resume"()
        "keepalive"()

        "extension" {
            "lease"()
            "broker"()
            "prioritization"() //TODO: experiment
        }

        "configuration"() //protocol configuration

        "machinery"() //protocol impl go here?

    }

    "metadata"() //metadata api and default impls
    "payload" {  //payload api and minimal impls
        "serialization" {
            "kotlinx"()
            "moshi"()
        }
    }
    "frame"() //frame api declarations

    "transport" {
        "memory"()//TODO: `memory` naming
        "ktor" {
            "tcp"()
            "websocket" {
                "client"()
                "server"()
            }
        }
        "netty" {
            "tcp"()
            "quic"()
            "websocket"()
        }
        "okhttp"()
    }

    "connection"() //links configuration and transport

    "loadbalance"()
}
