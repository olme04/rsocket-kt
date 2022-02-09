package rsocket.frame

import rsocket.protocol.*

public sealed interface Frame {
    public val type: FrameType
}
