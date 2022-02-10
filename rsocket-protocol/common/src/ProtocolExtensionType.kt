package rsocket.protocol

public sealed interface ProtocolExtensionType : CompactType {
    public companion object :
        CompactTypeFactory<WithId, WithName, WellKnown>(::WithIdImpl, ::WithNameImpl, enumValues())

    public sealed interface WithId : ProtocolExtensionType, CompactType.WithId
    public sealed interface WithName : ProtocolExtensionType, CompactType.WithName
    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte
    ) : ProtocolExtensionType, CompactType.WellKnown, WithId, WithName {
        Lease("Lease", 0x00),
        Broker("Broker", 0x01),
        Prioritization("Prioritization", 0x02);

        override fun toString(): String = toString("ProtocolExtensionType")
    }

    private class WithNameImpl(text: String) : WithName, AbstractCompactTypeWithName(text) {
        override fun toString(): String = toString("ProtocolExtensionType")
    }

    private class WithIdImpl(identifier: Byte) : WithId, AbstractCompactTypeWithId(identifier) {
        override fun toString(): String = toString("ProtocolExtensionType")
    }
}
