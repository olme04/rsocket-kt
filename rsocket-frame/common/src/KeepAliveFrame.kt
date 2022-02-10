package rsocket.frame

import rsocket.io.*
import rsocket.protocol.*

public sealed interface KeepAliveFrame : ConnectionFrame {
    override val type: ConnectionFrameType.KeepAlive get() = ConnectionFrameType.KeepAlive
    override val flags: KeepAliveFlags

    public val data: Buffer

    public interface V1 : KeepAliveFrame, ConnectionFrame.V1 {
        override val type: ConnectionFrameType.KeepAlive get() = ConnectionFrameType.KeepAlive

        public val lastReceivedPosition: Long
    }

    public interface V2 : KeepAliveFrame, ConnectionFrame.V2 {
        override val type: ConnectionFrameType.KeepAlive get() = ConnectionFrameType.KeepAlive

        public val position: ResumableStreamsPosition
    }
}

public interface KeepAliveFlags : Flags {
    public val respond: Boolean
}

