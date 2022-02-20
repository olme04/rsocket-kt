package rsocket.lease

import rsocket.io.*

public interface LeasePayload { //todo right interface
    public val strategy: LeaseStrategy
    public val buffer: Lazy<Buffer>
}

public class RequestsCountLeasePayload(
    public val timeToLive: Int,
    public val numberOfRequests: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.RequestsCount
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class ConcurrencyLimitLeasePayload(
    public val timeToLive: Int,
    public val concurrencyLimit: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.ConcurrencyLimit
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class FramesCountLeasePayload(
    public val timeToLive: Int,
    public val numberOfFrames: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.FramesCount
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
