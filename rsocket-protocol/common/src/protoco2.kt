package rsocket.protocol

import kotlinx.coroutines.*
import rsocket.io.*
import rsocket.protocol.frame.*

public interface Protocol {
    public interface ConnectionInit {
        public interface Output : CoroutineScope {
            public suspend fun onInit(input: Accept.Input): Accept.Output
        }

        public interface Input : CoroutineScope {
            public suspend fun onInit(output: Accept.Output): Accept.Input
        }
    }

    public interface Connect {
        public interface Output {
            public suspend fun onConnect(frame: ConnectFrame, input: Connection.Input): Connection.Output
        }

        public interface Input {
            public suspend fun onConnect(frame: ConnectFrame, output: Connection.Output): Connection.Input
        }
    }

    public interface Accept {
        public sealed interface Output {
            public interface WithAck : Output {
                public suspend fun onAccept(frame: AcceptFrame)
            }

            public interface WithoutAck : Output {
                public suspend fun onAccept(frame: AcceptErrorFrame)
            }
        }

        public sealed interface Input {
            public interface WithAck : Input {
                public suspend fun onAccept(frame: AcceptFrame)
            }

            public interface WithoutAck : Input {
                public suspend fun onAccept(frame: AcceptErrorFrame)
            }
        }
    }

    public interface Connection {

        public interface Output : CoroutineScope {
            public suspend fun onFrame(frame: ConnectionLifecycleFrame)
            public suspend fun onStreamInit(frame: StreamInitFrame, output: Stream.Output): Stream.Input
        }

        public interface Input {
            public suspend fun onFrame(frame: ConnectionLifecycleFrame)
            public suspend fun onStreamInit(frame: StreamInitFrame, input: Stream.Input): Stream.Output
        }

        public interface Stream {
            public interface Output : Closeable {
                public suspend fun onFrame(frame: StreamLifecycleFrame)
            }

            public interface Input {
                public suspend fun onFrame(frame: StreamLifecycleFrame)
            }
        }

    }
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
