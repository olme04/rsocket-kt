package rsocket.frame

import rsocket.io.*
import rsocket.protocol.*

public sealed interface ResumeFrame : ConnectionFrame {
    override val type: ConnectionFrameType.Resume get() = ConnectionFrameType.Resume
    override val flags: Flags.Empty get() = Flags.Empty

    public val version: RSocketVersion
    public val token: Buffer

    public interface V1 : ResumeFrame, ConnectionFrame.V1 {
        override val type: ConnectionFrameType.Resume get() = ConnectionFrameType.Resume

        public val lastReceivedServerPosition: Long
        public val firstAvailableClientPosition: Long
    }

    public interface V2 : ResumeFrame, ConnectionFrame.V2 {
        override val type: ConnectionFrameType.Resume get() = ConnectionFrameType.Resume

        public val position: ResumableStreamsPosition
    }
}
