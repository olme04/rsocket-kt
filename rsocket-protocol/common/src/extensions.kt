package rsocket.protocol

public sealed interface ProtocolExtensionType {

    public sealed interface WithId : ProtocolExtensionType {
        public val identifier: Byte
    }

    public sealed interface WithName : ProtocolExtensionType {
        public val text: String
    }

    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte
    ) : WithName, WithId {
        Lease("lease", 0x00),
        Broker("broker", 0x01),
        Prioritization("prioritization", 0x02)
    }
}