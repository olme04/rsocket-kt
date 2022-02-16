package rsocket.extension.resume

import rsocket.io.*

public sealed interface SetupExtensionPayload {
    public val token: Buffer

    public interface Init : SetupExtensionPayload
    public interface Resume : SetupExtensionPayload {
        public val position: StreamsPosition
    }
}

public class SetupOkExtensionPayload(
    public val position: StreamsPosition
)

public class CustomExtensionPayload(
    public val position: StreamsPosition
)

public class RequesterStreamExtensionPayload(
    public val needResume: Boolean
)

public class ResponderStreamExtensionPayload(
    public val willResume: Boolean
)

public class StreamsPosition(
    public val streamIds: IntArray,
    public val streamImpliedPositions: LongArray
)