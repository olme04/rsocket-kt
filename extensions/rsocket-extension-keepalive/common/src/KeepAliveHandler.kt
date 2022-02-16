package rsocket.protocol.resume

import rsocket.io.*

public fun interface KeepAliveHandler : Closeable {
    public suspend fun handle(respond: Boolean, data: Buffer)

    override fun close(): Unit = Unit
}
