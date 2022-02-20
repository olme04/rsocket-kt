plugins {
    rsocket.multiplatform
}

library {
    description("RSocket IMPL") //TODO
    uses(
        projects.transport,
        projects.protocol,
        projects.payload,
    )
}
