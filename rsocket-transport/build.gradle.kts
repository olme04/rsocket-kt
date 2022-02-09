plugins {
    rsocket.multiplatform
}

library {
    description("RSocket transport API")
    uses(
        projects.frame
    )
    dependencies(
        libs.kotlinx.coroutines.core
    )
}
