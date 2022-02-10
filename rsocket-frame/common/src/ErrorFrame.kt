package rsocket.frame

import rsocket.protocol.*

public sealed interface ErrorFrame : Frame, Frame.V1, Frame.V2 {
    override val type: FrameType.Error
    override val flags: Flags.Empty get() = Flags.Empty

    public val code: ErrorCode
    public val message: String //TODO: make it buffer???
}

public interface ConnectionErrorFrame : ErrorFrame, ConnectionFrame {
    override val type: ConnectionFrameType.Error get() = ConnectionFrameType.Error
}

public interface StreamErrorFrame : ErrorFrame, StreamFrame {
    override val type: StreamFrameType.Error get() = StreamFrameType.Error
}
