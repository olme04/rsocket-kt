plugins {
    rsocket.multiplatform
}

library {
    description("RSocket protocol declarations")
    uses(
        projects.rsocketIo
    )
    dependencies(
        libs.kotlinx.coroutines.core
    )
}
