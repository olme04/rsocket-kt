package rsocket.protocol.resume

import rsocket.io.*

public fun interface KeepAliveDataProducer : Closeable {
    public fun produce(): Buffer

    override fun close(): Unit = Unit
}
