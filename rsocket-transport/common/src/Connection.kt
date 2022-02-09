package rsocket.transport

import kotlinx.coroutines.*

//TODO: sequential/multiplexed naming
@TransportApi
public sealed interface Connection : CoroutineScope

public interface ConnectionFactory {
    @TransportApi
    public suspend fun createConnection(): Connection //will return connection, when server reached
}

@TransportApi
public interface ConnectionAcceptor {
    public fun acceptConnection(connection: Connection)
}

@TransportApi
public interface SequentialConnection : Connection {
    public suspend fun start(handler: FrameHandler): FrameOutbound //will return outbound, after it can be used
}

@TransportApi
public interface MultiplexedConnection : Connection {
    public suspend fun start(acceptor: StreamAcceptor): StreamFactory //will return outbound, after it can be used
}

@TransportApi
public interface RSocketConnection : Connection {
    public suspend fun start(input: RSocketConnectionInput): RSocketConnectionOutput
}
