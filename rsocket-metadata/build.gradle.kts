plugins {
    rsocket.multiplatform
}

library {
    description("RSocket metadata API")
    uses(
        projects.io,
        projects.protocol //for mime type
    )
}
