package rsocket.frame

import rsocket.protocol.*

public interface PayloadFrame : StreamFrame.WithPayload {
    override val type: StreamFrameType.Payload get() = StreamFrameType.Payload
    override val flags: PayloadFlags
}

public interface PayloadFlags : StreamWithPayloadFlags, Flags.Complete, Flags.Next
