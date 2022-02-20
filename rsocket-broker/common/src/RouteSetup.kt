package rsocket.broker

import rsocket.io.*
import rsocket.protocol.extension.*

public class RouteSetup(
    public val version: BrokerVersion,
    public val routeId: RouteId,
    public val serviceName: String,
    public val tags: Map<BrokerKey, String>,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Broker
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
