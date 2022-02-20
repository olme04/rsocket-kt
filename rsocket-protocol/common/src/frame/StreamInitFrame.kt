package rsocket.protocol.frame

import rsocket.protocol.extensions.*
import rsocket.protocol.payload.*

public sealed interface StreamInitFrame : StreamFrame, Frame.WithExtensions

public sealed interface RequestInitFrame : StreamInitFrame, StreamFrame.WithPayload

public class FireAndForgetFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val extensions: List<RequestInitExtension>,
    override val payload: Payload,
) : RequestInitFrame

public class RequestResponseFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val extensions: List<RequestInitExtension>,
    override val payload: Payload,
) : RequestInitFrame

public class RequestStreamFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val request: Int,
    override val extensions: List<RequestInitExtension>,
    override val payload: Payload,
) : RequestInitFrame, StreamFrame.WithRequest

public class RequestChannelFrame(
    override val streamId: Int,
    override val follows: Boolean,
    override val complete: Boolean,
    override val request: Int,
    override val extensions: List<RequestInitExtension>,
    override val payload: Payload,
) : RequestInitFrame, StreamFrame.WithRequest, StreamFrame.WithComplete

public class RequestRestoreFrame(
    override val streamId: Int,
    override val extensions: List<RequestInitExtension>,
) : StreamInitFrame
