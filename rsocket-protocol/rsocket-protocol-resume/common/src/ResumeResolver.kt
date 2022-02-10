package rsocket.protocol.resume

import rsocket.io.*
import rsocket.metadata.*
import rsocket.protocol.*

public fun interface ResumeResolver : Closeable {
    public suspend fun shouldResume(type: RequestType, metadata: Metadata?): Boolean

    public suspend fun shouldResumeRequestResponse(metadata: Metadata?): Boolean =
        shouldResume(RequestType.RequestResponse, metadata)

    public suspend fun shouldResumeRequestStream(metadata: Metadata?): Boolean =
        shouldResume(RequestType.RequestStream, metadata)

    public suspend fun shouldResumeRequestChannel(metadata: Metadata?): Boolean =
        shouldResume(RequestType.RequestChannel, metadata)

    override fun close(): Unit = Unit
}
