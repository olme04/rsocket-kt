plugins {
    rsocket.multiplatform
}

library {
    description("RSocket protocol declarations")
    uses(
        projects.io
    )
    dependencies(
        libs.kotlinx.coroutines.core
    )
}
