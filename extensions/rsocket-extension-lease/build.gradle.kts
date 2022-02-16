plugins {
    rsocket.multiplatform
}

library {
    description("RSocket protocol Lease extension")
    uses(
        projects.protocol,
        projects.io,
        projects.metadata,
    )
}
