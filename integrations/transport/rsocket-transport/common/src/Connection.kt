package rsocket.transport

import kotlinx.coroutines.*
import rsocket.io.*

public sealed interface Connection : CoroutineScope

//TCP like
public interface SequentialConnection : Connection {
    public suspend fun sendFrame(frame: Buffer)

    public fun start(handler: FrameHandler)
}

//QUIC like
public interface StreamingConnection : Connection {
    // TODO: what to do with prioritization of zero stream frame?
    //  for sequential transport, it will be automatically prioritized all time
    public suspend fun createStream(handler: FrameHandler, prioritized: Boolean): StreamOutbound

    public fun start(acceptor: StreamAcceptor)
}

//RSocket like
public interface MultiplexedConnection : Connection {
    public suspend fun sendConnectionFrame(frame: Buffer)
    public suspend fun createRequestStream(handler: FrameHandler): StreamOutbound

    public fun start(handler: MultiplexedHandler)
}

public interface StreamOutbound : Closeable {
    public suspend fun sendFrame(frame: Buffer)
}

public interface FrameHandler {
    public fun handleFrame(frame: Buffer)
}

public interface StreamAcceptor {
    public fun acceptStream(outbound: StreamOutbound): FrameHandler
}

public interface MultiplexedHandler {
    public fun handleConnectionFrame(frame: Buffer)
    public fun acceptRequestStream(outbound: StreamOutbound): FrameHandler
}
