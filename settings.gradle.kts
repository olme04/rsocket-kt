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

fun modules(folder: File, vararg names: String) {
    names.forEach { name ->
        include(name)
        project(":$name").projectDir = folder.resolve("rsocket-$name")
    }
}

fun modules(vararg names: String) {
    modules(rootDir, *names)
}

fun group(folder: String, groupName: String, vararg names: String) {
    modules(file(folder), groupName, *names.map { "$groupName-$it" }.toTypedArray())
}

group("integrations/io", "io", "ktor", "okio", "netty")
group("integrations/logging", "logging", "kermit", "slf4j", "microutils")
group("integrations/serialization", "serialization", "kotlinx", "moshi")

group(
    "integrations/transport",
    "transport",
    "memory",
    "okhttp",
    "ktor",
    "ktor-tcp",
    "ktor-websocket",
    "ktor-websocket-client",
    "ktor-websocket-server",
    "netty",
    "netty-tcp",
    "netty-quic",
    "netty-websocket",
)

group(
    "extensions",
    "extension",
    "keepalive",
    "resume",
    "lease",
    "broker",
)

modules(
    "protocol",
    "metadata",
    "payload",
    "frame",
)

//
//include(
//    "rsocket-coroutines",
//    "rsocket-internal",
//    "rsocket-annotations", //TODO: is it needed?
//    "rsocket-configuration",
//    "rsocket-machinery",
//    "rsocket-connection",
//    "rsocket-loadbalance",
//)
