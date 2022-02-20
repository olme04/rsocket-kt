plugins {
    rsocket.multiplatform
}

library {
    description("RSocket resume API")
    uses(
        projects.rsocketProtocol,
    )
}
