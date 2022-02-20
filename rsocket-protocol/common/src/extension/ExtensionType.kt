package rsocket.protocol.extension

import rsocket.protocol.*

public sealed interface ExtensionType : CompactType {
    public companion object : CompactTypeFactory<WithId, WithName, WellKnown>(::WithIdImpl, ::WithNameImpl, enumValues())

    public sealed interface WithId : ExtensionType, CompactType.WithId
    public sealed interface WithName : ExtensionType, CompactType.WithName
    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte,
    ) : ExtensionType, CompactType.WellKnown, WithId, WithName {
        Keepalive("keepalive", 0x01),
        Resume("resume", 0x02),
        Lease("lease", 0x03),
        Broker("broker", 0x04),
        Prioritization("prioritization", 0x05);

        override fun toString(): String = toString("ExtensionType")
    }

    private class WithIdImpl(identifier: Byte) : WithId, AbstractCompactTypeWithId(identifier) {
        override fun toString(): String = toString("ExtensionType")
    }

    private class WithNameImpl(text: String) : WithName, AbstractCompactTypeWithName(text) {
        override fun toString(): String = toString("ExtensionType")
    }
}
