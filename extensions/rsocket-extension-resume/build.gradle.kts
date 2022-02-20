plugins {
    rsocket.multiplatform
}

library {
    description("RSocket resume API")
    uses(
        projects.protocol,
        projects.io,
        projects.metadata,
//        projects.frame,
    )
}
