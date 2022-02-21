package rsocket.lease

import rsocket.protocol.extension.*

/*
    after setup, track outgoing/incoming requests based on lease strategy
    reject incoming request, if there is no lease for it
    defer(fail) outgoing requests, if there is no lease for it
    send lease updates at some time
 */

public class LeaseSupported(
    public val strategies: List<LeaseStrategy>,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
}

public class LeaseInit(
    public val payload: LeasePayload,
) : AckExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
}

public class LeaseUpdate(
    public val payload: LeasePayload,
) : ConnectionExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Lease
}
