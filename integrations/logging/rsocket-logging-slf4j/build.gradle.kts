plugins {
    rsocket.multiplatform
}

library {
    description("RSocket logging implementation via slf4j")
    uses(projects.logging)
//    dependencies(libs.slf4j)
}
