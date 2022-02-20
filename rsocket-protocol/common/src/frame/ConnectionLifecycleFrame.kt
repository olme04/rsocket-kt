package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.errors.*
import rsocket.protocol.extension.*

public sealed interface ConnectionLifecycleFrame : ConnectionFrame

public class ConnectionExtensionFrame(
    override val extensions: List<ConnectionExtensionPayload>,
) : ConnectionLifecycleFrame, Frame.Extension

public class ConnectionErrorFrame(
    override val code: ConnectionErrorCode,
    override val data: Lazy<Buffer>,
) : ConnectionLifecycleFrame, Frame.Error
