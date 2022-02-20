package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.errors.*
import rsocket.protocol.extensions.*
import rsocket.protocol.payload.*

public sealed interface StreamLifecycleFrame : StreamFrame

public class PayloadFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val complete: Boolean,
    override val next: Boolean,
    override val extensions: List<PayloadExtension>,
    override val payload: Payload,
) : StreamLifecycleFrame, StreamFrame.WithPayload, StreamFrame.WithComplete, StreamFrame.WithNext, Frame.WithExtensions

public class RequestNFrame(
    override val streamId: Int,
    override val request: Int,
    override val extensions: List<RequestNExtension>,
) : StreamLifecycleFrame, StreamFrame.WithRequest, Frame.WithExtensions

public class CancelFrame(
    override val streamId: Int,
    override val extensions: List<CancelExtension>,
) : StreamLifecycleFrame, Frame.WithExtensions

public class StreamExtensionFrame(
    override val streamId: Int,
    override val extensions: List<CustomRequestExtension>,
) : StreamLifecycleFrame, Frame.Extension

public class StreamLifecycleErrorFrame(
    override val streamId: Int,
    override val code: RequestErrorCode,
    override val data: Buffer,
) : StreamLifecycleFrame, Frame.Error
