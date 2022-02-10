package rsocket.frame

import rsocket.payload.*
import rsocket.protocol.*

public sealed interface StreamFrame : Frame {
    override val type: StreamFrameType

    public sealed interface WithPayload : StreamFrame {
        override val type: StreamFrameType.WithPayload
        override val flags: StreamWithPayloadFlags

        public val payload: Payload
    }
}

public sealed interface StreamWithPayloadFlags : Flags, Flags.HasMetadata, Flags.Follows
