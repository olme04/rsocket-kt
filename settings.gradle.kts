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

fun module(name: String) {
    include(name)
    project(":$name").projectDir = file(name.replace(":", "/"))
}

fun modules(prefix: String, vararg names: String) {
    names.forEach { module("$prefix-$it") }
}

module("rsocket-io")
modules("rsocket-io:rsocket-io", "ktor", "okio", "netty")

module("rsocket-logging")
modules("rsocket-logging:rsocket-logging", "kermit", "slf4j", "microutils")

module("rsocket-serialization")
modules("rsocket-serialization:rsocket-serialization", "kotlinx", "moshi", "microutils")

module("rsocket-transport")
modules("rsocket-transport:rsocket-transport", "memory", "okhttp", "ktor", "netty")
modules("rsocket-transport:rsocket-transport-ktor:rsocket-transport-ktor", "tcp", "websocket", "websocket-client", "websocket-server")
modules("rsocket-transport:rsocket-transport-netty:rsocket-transport-netty", "tcp", "quic", "websocket")

module("rsocket-protocol")
module("rsocket-metadata")

//extensions
module("rsocket-keepalive")
module("rsocket-resume")
module("rsocket-lease")
module("rsocket-broker")

//module("rsocket-loadbalance")

//
//modules(
//    "coroutines",
//    "internal",
//    "rsocket-configuration",
//    "rsocket-connection",
//)


/* SAGA extension

 */

/* Quorum extension

 */

/* Clock sync extension

 */

/* Security/Auth as extension?

 */
