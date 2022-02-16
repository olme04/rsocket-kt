package rsocket.protocol

public sealed interface FrameType {
    public sealed interface Error : FrameType
    public sealed interface WithPayload : FrameType
}

public sealed interface ConnectionFrameType : FrameType {
    public object Setup : ConnectionFrameType, FrameType.WithPayload //|X|M|A|
    public object SetupOk : ConnectionFrameType, FrameType.WithPayload//|M|
    public object Error : ConnectionFrameType, FrameType.Error //no flags
    public object Custom : ConnectionFrameType //no flags
}

public sealed interface StreamFrameType : FrameType {
    public object RequestN : StreamFrameType //|X|
    public object Cancel : StreamFrameType //|X|
    public object Error : StreamFrameType, FrameType.Error //no flags
    public object Payload : StreamFrameType, FrameType.WithPayload //|X|M|

    public sealed interface Request : StreamFrameType, FrameType.WithPayload {
        public val requestType: RequestType
    }

    public object FireAndForget : Request {
        override val requestType: RequestType get() = RequestType.FireAndForget
    }

    public object RequestResponse : Request {
        override val requestType: RequestType get() = RequestType.RequestResponse
    }

    public object RequestStream : Request {
        override val requestType: RequestType get() = RequestType.RequestStream
    }

    public object RequestChannel : Request {
        override val requestType: RequestType get() = RequestType.RequestChannel
    }
}
