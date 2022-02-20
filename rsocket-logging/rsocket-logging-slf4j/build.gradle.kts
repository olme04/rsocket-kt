plugins {
    rsocket.multiplatform
}

library {
    description("RSocket logging implementation via slf4j")
    uses(projects.rsocketLogging)
//    dependencies(libs.slf4j)
}
