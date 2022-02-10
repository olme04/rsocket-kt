package rsocket.frame

import rsocket.protocol.*

public interface RequestNFrame : StreamFrame {
    override val flags: Flags.Empty get() = Flags.Empty
    override val type: StreamFrameType.RequestN get() = StreamFrameType.RequestN

    public val request: Int
}
