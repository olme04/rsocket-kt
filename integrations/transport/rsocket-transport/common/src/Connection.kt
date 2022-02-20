package rsocket.transport

import kotlinx.coroutines.*
import rsocket.io.*

public interface Connection : CoroutineScope, StreamFactory {
    public fun start(acceptor: StreamAcceptor)
}

//impl in transport impl
public interface StreamFactory {
    public suspend fun createStream(frame: Buffer, handler: FrameHandler): StreamOutbound
}

//impl inside rsocket
public interface StreamAcceptor {
    public fun onStream(frame: Buffer, outbound: StreamOutbound): FrameHandler
}

public interface StreamOutbound : FrameOutbound, Closeable {
    //TODO how it do better?
    // positive, 0 - no priority, 1 - max priority
    public var priority: Int
}

//impl in transport impl
public interface FrameOutbound {
    public suspend fun sendFrame(frame: Buffer)
}

//impl inside rsocket
public interface FrameHandler {
    public fun onFrame(frame: Buffer)
}
