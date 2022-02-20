plugins {
    rsocket.multiplatform
}

library {
    description("RSocket transport API")
    uses(
        projects.rsocketIo
    )
    dependencies(
        libs.kotlinx.coroutines.core
    )
}
