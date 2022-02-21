package rsocket.broker

import rsocket.protocol.extension.*

public enum class RoutingType {
    Unicast, Multicast, Shard //TODO is shard needed?
}

public class RouteAddress(
    public val routingType: RoutingType,
    public val originRouteId: RouteId,
    public val tags: Map<BrokerKey, String>,
) : RequestInitExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Broker
}
