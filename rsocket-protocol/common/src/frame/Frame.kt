package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.errors.*
import rsocket.protocol.extension.*
import rsocket.protocol.payload.*

public sealed interface Frame {
    public val streamId: Int

    public sealed interface WithPayload : Frame {
        public val payload: Payload
    }

    public sealed interface WithExtensions : Frame {
        public val extensions: List<ExtensionPayload>
    }

    public sealed interface Extension : Frame, WithExtensions

    public sealed interface Error : Frame {
        public val code: ErrorCode
        public val data: Lazy<Buffer>
    }

}
