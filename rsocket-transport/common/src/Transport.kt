package rsocket.transport

import kotlinx.coroutines.*

public sealed interface Transport : CoroutineScope

public interface ClientTransport : Transport, ConnectionFactory

//TODO decide on reusing of server transport
public interface ServerTransport<SI : ServerInstance> : Transport {
    @TransportApi
    public suspend fun start(acceptor: ConnectionAcceptor): SI
}

public interface ServerInstance : CoroutineScope
