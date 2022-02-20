package rsocket.protocol.frame

public sealed interface ConnectionFrame : Frame {
    override val streamId: Int get() = 0
}
