package rsocket.protocol

import kotlinx.coroutines.*
import rsocket.protocol.frame.*

public interface InitProtocol : CoroutineScope {
    public suspend fun onInit(frame: ClientInitFrame, protocol: ConnectionProtocol): ConnectionProtocol
    public suspend fun onInitWithAck(frame: ClientInitFrame, protocol: ConnectionProtocol): Pair<ServerInitFrame, ConnectionProtocol>
}

public interface ConnectionProtocol : CoroutineScope {
    public suspend fun onFrame(frame: ConnectionLifecycleFrame)
    public suspend fun onRequestInit(frame: StreamInitFrame, protocol: RequestProtocol): RequestProtocol
}

public interface RequestProtocol : CoroutineScope {
    public suspend fun onFrame(frame: StreamLifecycleFrame)
}

//remote(transport) protocols | local(impl) protocols
