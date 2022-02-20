package rsocket.resume

import rsocket.io.*
import rsocket.protocol.extension.*

/*
    after setup, store incoming/outgoing frames based on Needed/Allowed configuration
    attach to KA info about current streams positions
    after restore, retransmit frames for streams where possible, and allowed
 */

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

//just marker, that requester/responder completed resume operation for stream
public object ResumeCompleted : StreamRestoreExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Resume
    override val buffer: Lazy<Buffer> = lazy { TODO() }
}
