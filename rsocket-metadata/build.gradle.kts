plugins {
    rsocket.multiplatform
}

library {
    description("RSocket metadata API")
    uses(
        projects.rsocketProtocol
    )
}
