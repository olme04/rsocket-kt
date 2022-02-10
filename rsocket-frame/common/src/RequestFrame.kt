package rsocket.frame

import rsocket.protocol.*

public sealed interface RequestFrame : StreamFrame {
    override val type: RequestFrameType

    public sealed interface WithInitialRequest : RequestFrame {
        public val initialRequest: Int
    }
}

public interface FireAndForgetFrame : RequestFrame, StreamFrame.WithPayload {
    override val type: RequestFrameType.FireAndForget get() = RequestFrameType.FireAndForget
    override val flags: RequestFlags
}

public interface RequestChannelFrame : RequestFrame, StreamFrame.WithPayload, RequestFrame.WithInitialRequest {
    override val type: RequestFrameType.RequestChannel get() = RequestFrameType.RequestChannel
    override val flags: RequestChannelFlags
}

public interface RequestResponseFrame : RequestFrame, StreamFrame.WithPayload {
    override val type: RequestFrameType.RequestResponse get() = RequestFrameType.RequestResponse
    override val flags: RequestFlags
}

public interface RequestStreamFrame : RequestFrame, StreamFrame.WithPayload, RequestFrame.WithInitialRequest {
    override val type: RequestFrameType.RequestStream get() = RequestFrameType.RequestStream
    override val flags: RequestFlags
}

public interface RequestFlags : StreamWithPayloadFlags
public interface RequestChannelFlags : RequestFlags, Flags.Complete
