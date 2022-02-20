package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.errors.*
import rsocket.protocol.extensions.*

public sealed interface ConnectionLifecycleFrame : ConnectionFrame

public class ConnectionExtensionFrame(
    override val extensions: List<CustomConnectionExtension>,
) : ConnectionLifecycleFrame, Frame.Extension

public class ConnectionLifecycleErrorFrame(
    override val code: ConnectionLifecycleErrorCode,
    override val data: Buffer,
) : ConnectionLifecycleFrame, Frame.Error
