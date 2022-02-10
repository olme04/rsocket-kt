package rsocket.frame

import rsocket.io.*
import rsocket.protocol.*

public interface MetadataPushFrame : ConnectionFrame, ConnectionFrame.V1, ConnectionFrame.V2 {
    override val type: ConnectionFrameType.MetadataPush get() = ConnectionFrameType.MetadataPush
    override val flags: Flags.Empty get() = Flags.Empty //TODO: or use HasMetadata with always true

    public val metadata: Buffer
}
