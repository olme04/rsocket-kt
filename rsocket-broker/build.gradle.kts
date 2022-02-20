plugins {
    rsocket.multiplatform
}

library {
    description("RSocket protocol Broker extension")
    uses(
        projects.rsocketProtocol,
    )
}
