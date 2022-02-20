package rsocket.frame

import rsocket.payload.*
import rsocket.protocol.*

public interface SetupFrame : ConnectionFrame {
    override val type: ConnectionFrameType.Setup get() = ConnectionFrameType.Setup
    override val flags: SetupFlags

    public val version: RSocketVersion
    public val payload: Payload
    public val extensions: List<Extension>
}

public interface SetupFlags : Flags, Flags.HasMetadata, Flags.HasExtensions {
    public val needAcknowledgement: Boolean
}

public sealed interface CustomFrame : ConnectionFrame {
    override val type: ConnectionFrameType.Extension get() = ConnectionFrameType.Extension

    public val extensions: List<Extension>
}
