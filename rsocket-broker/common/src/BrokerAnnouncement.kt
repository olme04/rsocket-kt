package rsocket.broker

import rsocket.io.*
import rsocket.protocol.extension.*

//sent between brokers
public sealed interface BrokerAnnouncement : ConnectionExtensionPayload {
    public val brokerId: BrokerId
    public val timestamp: Long

    override val type: ExtensionType get() = ExtensionType.WellKnown.Broker
}

public class BrokerInfo(
    override val brokerId: BrokerId,
    override val timestamp: Long,
    public val tags: Map<BrokerKey, String>, //it's metadata
) : BrokerAnnouncement {
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class RouteAdded(
    override val brokerId: BrokerId,
    override val timestamp: Long,
    public val routeId: RouteId,
    public val serviceName: String,
    public val tags: Map<BrokerKey, String>,
) : BrokerAnnouncement {
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class RouteRemoved(
    override val brokerId: BrokerId,
    override val timestamp: Long,
    public val routeId: RouteId,
) : BrokerAnnouncement {
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
