package rsocket.frame

import rsocket.protocol.*

public sealed interface ResumeOkFrame : ConnectionFrame {
    override val type: ConnectionFrameType.ResumeOk get() = ConnectionFrameType.ResumeOk
    override val flags: Flags.Empty get() = Flags.Empty

    public interface V1 : ResumeOkFrame, ConnectionFrame.V1 {
        override val type: ConnectionFrameType.ResumeOk get() = ConnectionFrameType.ResumeOk

        public val lastReceivedClientPosition: Long
    }

    public interface V2 : ResumeOkFrame, ConnectionFrame.V2 {
        override val type: ConnectionFrameType.ResumeOk get() = ConnectionFrameType.ResumeOk

        public val position: ResumableStreamsPosition
    }
}
