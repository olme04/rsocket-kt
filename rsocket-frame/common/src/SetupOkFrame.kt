package rsocket.frame

import rsocket.protocol.*

public interface SetupOkFrame : ConnectionFrame {
    override val type: ConnectionFrameType.SetupOk get() = ConnectionFrameType.SetupOk

    public val extensions: ExtensionsContainer
}

public interface SetupOkFlags : Flags, Flags.HasMetadata, Flags.HasExtensions
