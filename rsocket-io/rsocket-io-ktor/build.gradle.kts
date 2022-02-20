plugins {
    rsocket.multiplatform
}

library {
    description("RSocket IO implementation via ktor-io")
    uses(projects.rsocketIo)
    dependencies(libs.ktor.io)
}
