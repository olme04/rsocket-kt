package rsocket.protocol.frame

public sealed interface StreamFrame : Frame {
    //streamId != 0

    public sealed interface WithPayload : StreamFrame, Frame.WithPayload {
        public val follows: Boolean
    }

    public sealed interface WithComplete : StreamFrame {
        public val complete: Boolean
    }

    public sealed interface WithNext : StreamFrame {
        public val next: Boolean
    }

    public sealed interface WithRequest : StreamFrame {
        public val request: Int
    }

}
