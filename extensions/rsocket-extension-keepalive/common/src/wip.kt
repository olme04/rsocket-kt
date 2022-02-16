package rsocket.extension.keepalive

import rsocket.io.*
import kotlin.time.*

public class SetupExtensionPayload(
    public val keepAliveInterval: Duration,
    public val keepAliveMaxLifetime: Duration
)

public class CustomExtensionPayload(
    public val respond: Boolean,
    public val data: Buffer
)
