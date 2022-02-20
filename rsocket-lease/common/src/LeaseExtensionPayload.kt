package rsocket.lease

import rsocket.io.*
import rsocket.protocol.extension.*

public class LeaseSupported(
    public val strategies: List<LeaseStrategy>,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class LeaseInit(
    public val payload: LeasePayload,
) : AckExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class LeaseUpdate(
    public val payload: LeasePayload,
) : ConnectionExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
