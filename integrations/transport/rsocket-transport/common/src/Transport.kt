package rsocket.transport

import kotlinx.coroutines.*

public sealed interface Transport : CoroutineScope

public interface ClientTransport : Transport {
    public suspend fun createConnection(): Connection
}

//TODO decide on reusing of server transport
public interface ServerTransport<SI : ServerInstance> : Transport {
    public suspend fun start(acceptor: ConnectionAcceptor): SI
}

public interface ServerInstance : CoroutineScope

public interface ConnectionAcceptor {
    public fun acceptConnection(connection: Connection)
}
