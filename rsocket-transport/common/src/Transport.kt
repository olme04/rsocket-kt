package rsocket.transport

import kotlinx.coroutines.*

public sealed interface Transport : CoroutineScope

public interface ClientTransport : Transport {

}

public interface ServerTransport<SI : ServerInstance> : Transport {
    public suspend fun start(

    ): SI
}

public interface ServerInstance : CoroutineScope
