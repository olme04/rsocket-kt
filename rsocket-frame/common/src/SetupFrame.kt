package rsocket.frame

import rsocket.io.*
import rsocket.payload.*
import rsocket.protocol.*
import kotlin.time.*

public sealed interface SetupFrame : ConnectionFrame {
    override val type: ConnectionFrameType.Setup get() = ConnectionFrameType.Setup
    override val flags: SetupFlags

    public val version: RSocketVersion
    public val keepAliveInterval: Duration
    public val keepAliveMaxLifetime: Duration
    public val resumeToken: Buffer?
    public val metadataMimeType: MimeType
    public val dataMimeType: MimeType
    public val payload: Payload

    public interface V1 : SetupFrame, ConnectionFrame.V1 {
        override val type: ConnectionFrameType.Setup get() = ConnectionFrameType.Setup
        override val flags: SetupFlags.V1
    }

    public interface V2 : SetupFrame, ConnectionFrame.V2 {
        override val type: ConnectionFrameType.Setup get() = ConnectionFrameType.Setup
        override val flags: SetupFlags.V2
        //TODO: add extensions payload here
    }
}

public sealed interface SetupFlags : Flags, Flags.HasMetadata {
    public val resumeEnabled: Boolean

    public interface V1 : SetupFlags {
        public val honorLease: Boolean
    }

    public interface V2 : SetupFlags {
        public val hasExtensions: Boolean
    }
}

public sealed interface SetupOkFrame : ConnectionFrame.V2 {
    override val type: ConnectionFrameType.SetupOk get() = ConnectionFrameType.SetupOk
}

public sealed interface ExtensionsFrame : ConnectionFrame.V2 {
    override val type: ConnectionFrameType.Extension get() = ConnectionFrameType.Extension
}
