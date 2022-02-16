plugins {
    rsocket.multiplatform
}

library {
    description("RSocket transport API")
    uses(
        projects.io
    )
    dependencies(
        libs.kotlinx.coroutines.core
    )
}
