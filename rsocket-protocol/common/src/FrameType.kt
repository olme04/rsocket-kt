package rsocket.protocol

public sealed interface FrameType {
    public sealed interface WithError : FrameType
    public sealed interface WithPayload : FrameType
}

public sealed interface ConnectionFrameType : FrameType {
    public object Setup : ConnectionFrameType
    public object Lease : ConnectionFrameType
    public object KeepAlive : ConnectionFrameType
    public object MetadataPush : ConnectionFrameType
    public object Resume : ConnectionFrameType
    public object ResumeOk : ConnectionFrameType
    public object Error : ConnectionFrameType, FrameType.WithError
}

public sealed interface StreamFrameType : FrameType {
    public object Payload : StreamFrameType, FrameType.WithPayload
    public object RequestN : StreamFrameType
    public object Cancel : StreamFrameType
    public object Error : StreamFrameType, FrameType.WithError
}

public sealed interface RequestFrameType : StreamFrameType, FrameType.WithPayload {
    public val requestType: RequestType

    public object FireAndForget : RequestFrameType {
        override val requestType: RequestType get() = RequestType.FireAndForget
    }

    public object RequestResponse : RequestFrameType {
        override val requestType: RequestType get() = RequestType.RequestResponse
    }

    public object RequestStream : RequestFrameType {
        override val requestType: RequestType get() = RequestType.RequestStream
    }

    public object RequestChannel : RequestFrameType {
        override val requestType: RequestType get() = RequestType.RequestChannel
    }
}
