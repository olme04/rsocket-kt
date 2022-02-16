package rsocket.extension.lease

public class SetupExtensionPayload(
    public val supportedStrategies: List<LeaseStrategy>
)

public class SetupOkExtensionPayload(
    public val selectedStrategy: LeaseStrategy
)

public class CustomExtensionPayload(
    public val permit: LeasePermit
)

public interface LeasePermit

public class RequestsCountPermit(
    public val timeToLive: Int,
    public val numberOfRequests: Int
) : LeasePermit

public class ConcurrencyLimitPermit(
    public val timeToLive: Int,
    public val limit: Int
) : LeasePermit
