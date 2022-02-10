package rsocket.frame

import rsocket.protocol.*

public interface LeaseFrame : ConnectionFrame.V1 {
    override val type: ConnectionFrameType.Lease get() = ConnectionFrameType.Lease
    override val flags: Flags.HasMetadata

    public val timeToLive: Int
    public val numberOfRequests: Int
}
