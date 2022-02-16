package rsocket.frame

import rsocket.io.*
import rsocket.protocol.*

public sealed interface ErrorFrame : Frame {
    override val type: FrameType.Error
    override val flags: Flags.Empty get() = Flags.Empty

    public val code: ErrorCode
    public val data: Buffer //TODO: make it buffer??? or payload?
}

public interface ConnectionErrorFrame : ErrorFrame, ConnectionFrame {
    override val type: ConnectionFrameType.Error get() = ConnectionFrameType.Error
}

public interface StreamErrorFrame : ErrorFrame, StreamFrame {
    override val type: StreamFrameType.Error get() = StreamFrameType.Error
}
