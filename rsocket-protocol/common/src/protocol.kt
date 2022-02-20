package rsocket.protocol

import kotlinx.coroutines.*
import rsocket.io.*
import rsocket.protocol.frame.*

public interface ConnectionInitProtocol : CoroutineScope {
    public suspend fun onInit(protocol: AcceptProtocol): ConnectProtocol
}

public interface ConnectProtocol {
    //should be used one time
    public suspend fun onConnect(frame: ConnectFrame, protocol: ConnectionProtocol): ConnectionProtocol
}

public sealed interface AcceptProtocol {
    //should be used one time
    public interface WithAck : AcceptProtocol {
        public suspend fun onAccept(frame: AcceptFrame)
    }

    public interface WithoutAck : AcceptProtocol {
        public suspend fun onAccept(frame: AcceptErrorFrame) //should be called, only when error happens
    }
}

public interface ConnectionProtocol : CoroutineScope {
    public suspend fun onFrame(frame: ConnectionLifecycleFrame)
    public suspend fun onStreamInit(frame: StreamInitFrame, protocol: StreamProtocol): StreamProtocol
}

public interface StreamProtocol : Closeable {
    public suspend fun onFrame(frame: StreamLifecycleFrame)
}

//remote(transport) protocols | local(impl) protocols

/*init sequence:

  client no ack:
    send frame
    connected | fail later with setup error

  server no ack:
    receive frame
    connected | send error


  client with ack:
    send frame
    await (ack | error)
    connected | failed

  server with ack
    receive frame
    send (ack | error)
    connected | failed

 */
