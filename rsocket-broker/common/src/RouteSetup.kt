package rsocket.broker

import rsocket.protocol.extension.*

public class RouteSetup(
    public val routeId: RouteId,
    public val serviceName: String,
    public val tags: Map<BrokerKey, String>,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Broker
}
