package rsocket.protocol.frame

import rsocket.protocol.errors.*
import rsocket.protocol.extension.*

public sealed interface ConnectionLifecycleFrame : ConnectionFrame

public class ConnectionExtensionFrame(
    override val extensions: List<ConnectionExtensionPayload>,
) : ConnectionLifecycleFrame, Frame.Extension

public class ConnectionErrorFrame(
    override val payload: ErrorPayload,
) : ConnectionLifecycleFrame, Frame.Error
