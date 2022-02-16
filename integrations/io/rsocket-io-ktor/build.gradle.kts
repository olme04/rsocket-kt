plugins {
    rsocket.multiplatform
}

library {
    description("RSocket IO implementation via ktor-io")
    uses(projects.io)
    dependencies(libs.ktor.io)
}
