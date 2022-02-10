package rsocket.transport

import rsocket.frame.*
import rsocket.io.*

public interface FrameOutbound {
    public suspend fun sendFrame(frame: Frame)
}

public interface FrameHandler {
    public fun handleFrame(frame: Frame)
}

public interface StreamOutbound : FrameOutbound, Closeable

// TODO: what to do with prioritization of zero stream frame?
//  for sequential transport, it will be automatically prioritized all time
public interface StreamFactory {
    public suspend fun createStream(handler: FrameHandler, prioritized: Boolean): StreamOutbound
}

public interface StreamAcceptor {
    public fun acceptStream(outbound: StreamOutbound): FrameHandler
}

public interface RSocketConnectionOutput {
    public suspend fun sendFrame(frame: ConnectionFrame)
    public suspend fun createRequest(streamId: Int, input: RSocketRequestInput): RSocketRequestOutput
}

public interface RSocketConnectionInput {
    public fun receiveFrame(frame: ConnectionFrame)

    //TODO: should RequestFrame be here???
    public fun acceptRequest(frame: RequestFrame, output: RSocketRequestOutput): RSocketRequestInput
}

public interface RSocketRequestOutput : Closeable {
    public suspend fun sendFrame(frame: StreamFrame)
}

public interface RSocketRequestInput {
    public fun receiveFrame(frame: StreamFrame)
}
