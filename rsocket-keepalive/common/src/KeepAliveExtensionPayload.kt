package rsocket.keepalive

import rsocket.io.*
import rsocket.protocol.extension.*
import kotlin.time.*

/*
    after setup, send KAD in interval
    respond if received KAD with respond = true
    close connection after not receiving KAD in maxLifetime
 */

public class KeepAliveSetup(
    public val keepAliveInterval: Duration,
    public val keepAliveMaxLifetime: Duration,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Keepalive
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class KeepAliveData(
    public val respond: Boolean,
    public val data: Lazy<Buffer>,
) : ConnectionExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Keepalive
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
