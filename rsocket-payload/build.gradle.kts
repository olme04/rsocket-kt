plugins {
    rsocket.multiplatform
}

library {
    description("RSocket payload API")
    uses(
        projects.io,
        projects.metadata
    )
}
