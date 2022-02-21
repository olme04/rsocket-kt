package rsocket.broker

import rsocket.protocol.*

public sealed interface BrokerKey : CompactType {
    public companion object : CompactTypeFactory<WithId, WithName, WellKnown>(::WithIdImpl, ::WithNameImpl, enumValues())

    public sealed interface WithId : BrokerKey, CompactType.WithId
    public sealed interface WithName : BrokerKey, CompactType.WithName
    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte,
    ) : BrokerKey, CompactType.WellKnown, WithId, WithName {
        ServiceName("io.rsocket.broker.ServiceName", 0x01),
        RouteId("io.rsocket.broker.RouteId", 0x02),
        InstanceName("io.rsocket.routing.InstanceName", 0x03);
        //TODO add more

        override fun toString(): String = toString("BrokerKey")
    }

    private class WithNameImpl(text: String) : WithName, AbstractCompactTypeWithName(text) {
        override fun toString(): String = toString("BrokerKey")
    }

    private class WithIdImpl(identifier: Byte) : WithId, AbstractCompactTypeWithId(identifier) {
        override fun toString(): String = toString("BrokerKey")
    }
}
