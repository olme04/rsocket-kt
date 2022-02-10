package rsocket.frame

import rsocket.protocol.*

public sealed interface ConnectionFrame : Frame {
    override val streamId: Int get() = 0
    override val type: ConnectionFrameType

    public sealed interface V1 : ConnectionFrame {
        override val type: ConnectionFrameType.V1
    }

    public sealed interface V2 : ConnectionFrame {
        override val type: ConnectionFrameType.V2
    }
}
