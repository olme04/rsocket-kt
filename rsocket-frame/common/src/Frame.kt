package rsocket.frame

import rsocket.protocol.*

public sealed interface Frame {
    public val streamId: Int
    public val type: FrameType
}

public sealed interface ConnectionFrame : Frame {
    override val streamId: Int get() = 0
    override val type: ConnectionFrameType
}

public sealed interface StreamFrame : Frame {
    override val type: StreamFrameType
}

public sealed interface RequestFrame : StreamFrame {
    override val type: RequestFrameType
}
