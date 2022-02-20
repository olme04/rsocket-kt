package rsocket.broker

import rsocket.io.*
import rsocket.protocol.extension.*

/*
    after setup, send route added to other brokers
    ability to send broker info
    auto send route added/removed on new route setup
    on any announcement update routing table
    on receiving address, forward all frames to routable destination based on routing type and tags
 */

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
