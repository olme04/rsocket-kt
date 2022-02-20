package rsocket.transport

import kotlin.coroutines.*

public class MultiplexedConnection(
    override val coroutineContext: CoroutineContext,
) : Connection {
    override fun start(acceptor: StreamAcceptor): StreamFactory {
        TODO("Not yet implemented")
    }
}
