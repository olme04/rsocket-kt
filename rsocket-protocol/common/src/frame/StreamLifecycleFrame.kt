package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.errors.*
import rsocket.protocol.extension.*
import rsocket.protocol.payload.*

public sealed interface StreamLifecycleFrame : StreamFrame

public class PayloadFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val complete: Boolean,
    override val next: Boolean,
    override val extensions: List<PayloadExtensionPayload>,
    override val payload: Payload,
) : StreamLifecycleFrame, StreamFrame.WithPayload, StreamFrame.WithComplete, StreamFrame.WithNext, Frame.WithExtensions

public class RequestNFrame(
    override val streamId: Int,
    override val request: Int,
    override val extensions: List<RequestNExtensionPayload>,
) : StreamLifecycleFrame, StreamFrame.WithRequest, Frame.WithExtensions

public class CancelFrame(
    override val streamId: Int,
    override val extensions: List<CancelExtensionPayload>,
) : StreamLifecycleFrame, Frame.WithExtensions

public class StreamExtensionFrame(
    override val streamId: Int,
    override val extensions: List<StreamExtensionPayload>,
) : StreamLifecycleFrame, Frame.Extension

public class StreamErrorFrame(
    override val streamId: Int,
    override val code: StreamErrorCode,
    override val data: Lazy<Buffer>,
) : StreamLifecycleFrame, Frame.Error
