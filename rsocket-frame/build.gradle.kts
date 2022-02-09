plugins {
    rsocket.multiplatform
}

library {
    description("RSocket frame API")
    uses(
        projects.io,
        projects.protocol, //for frame type
        projects.payload //TODO payload  dependency?
    )
}
