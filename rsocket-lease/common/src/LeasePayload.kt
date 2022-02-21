package rsocket.lease

import rsocket.io.*

public interface LeasePayload {
    public val strategy: LeaseStrategy

    public interface Encoder<T : LeasePayload> {
        public val strategy: LeaseStrategy
        public fun BufferFactory.encode(payload: T): Buffer
    }

    public interface Decoder<T : LeasePayload> {
        public val strategy: LeaseStrategy
        public fun BufferFactory.decode(buffer: Buffer): T
    }

    public interface Codec<T : LeasePayload> : Encoder<T>, Decoder<T>
}

public class RequestsCountLeasePayload(
    public val timeToLive: Int,
    public val numberOfRequests: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.RequestsCount
}

public class ConcurrencyLimitLeasePayload(
    public val timeToLive: Int,
    public val concurrencyLimit: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.ConcurrencyLimit
}

public class FramesCountLeasePayload(
    public val timeToLive: Int,
    public val numberOfFrames: Int,
) : LeasePayload {
    override val strategy: LeaseStrategy get() = LeaseStrategy.WellKnown.FramesCount
}
