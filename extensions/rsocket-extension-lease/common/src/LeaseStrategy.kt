package rsocket.extension.lease

import rsocket.protocol.*

public sealed interface LeaseStrategy : CompactType {
    public companion object :
        CompactTypeFactory<WithId, WithName, WellKnown>(::WithIdImpl, ::WithNameImpl, enumValues())

    public sealed interface WithId : LeaseStrategy, CompactType.WithId
    public sealed interface WithName : LeaseStrategy, CompactType.WithName
    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte,
    ) : LeaseStrategy, CompactType.WellKnown, WithId, WithName {
        RequestsCount("Requests Count", 0x00),
        ConcurrencyLimit("Concurrency Limit", 0x01),
        FramesCount("Frames Count", 0x02);

        override fun toString(): String = toString("LeaseStrategy")
    }

    private class WithNameImpl(text: String) : WithName, AbstractCompactTypeWithName(text) {
        override fun toString(): String = toString("LeaseStrategy")
    }

    private class WithIdImpl(identifier: Byte) : WithId, AbstractCompactTypeWithId(identifier) {
        override fun toString(): String = toString("LeaseStrategy")
    }
}
