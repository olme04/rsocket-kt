plugins {
    rsocket.multiplatform
}

library {
    description("RSocket keepalive API")
    uses(
        projects.rsocketProtocol,
    )
}
