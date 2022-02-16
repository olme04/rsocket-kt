plugins {
    rsocket.multiplatform
}

library {
    description("RSocket extension API")
    uses(
        projects.io,
        projects.protocol, //for frame type
    )
}
