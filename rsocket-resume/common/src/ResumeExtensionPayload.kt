package rsocket.resume

import rsocket.io.*
import rsocket.protocol.extension.*

//just marker, that resume should be enabled
public object ResumeEnabled : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Resume
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

public class ResumeStreamsPosition(
    public val streamIds: IntArray,
    public val streamImpliedPositions: LongArray,
) : ExtensionPayload,
    RestoreExtensionPayload,
    AckExtensionPayload,
    ConnectionExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Resume
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

//just marker, that requester needs resume for this stream
public object ResumeNeeded : RequestInitExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Resume
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}

//just marker, that responder allows resuming of this stream
public object ResumeAllowed : ExtensionPayload,
    PayloadExtensionPayload,
    RequestNExtensionPayload,
    CancelExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Resume
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
