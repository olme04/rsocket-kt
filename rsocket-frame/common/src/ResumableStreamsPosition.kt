package rsocket.frame

public class ResumableStreamsPosition(
    public val streamIds: IntArray,
    public val streamImpliedPositions: LongArray
)
