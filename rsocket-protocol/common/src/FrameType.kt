package rsocket.protocol

public sealed interface FrameType {
    public sealed interface V1 : FrameType
    public sealed interface V2 : FrameType

    public sealed interface Error : FrameType, V1, V2
}

public sealed interface ConnectionFrameType : FrameType {
    public sealed interface V1 : ConnectionFrameType, FrameType.V1
    public sealed interface V2 : ConnectionFrameType, FrameType.V2

    public object Setup : V1, V2
    public object SetupOk : V2
    public object Lease : V1
    public object KeepAlive : V1, V2
    public object MetadataPush : V1, V2
    public object Resume : V1, V2
    public object ResumeOk : V1, V2
    public object Error : FrameType.Error, V1, V2
    public object Extension : V2
}

public sealed interface StreamFrameType : FrameType, FrameType.V1, FrameType.V2 {
    public object Payload : StreamFrameType, WithPayload
    public object RequestN : StreamFrameType
    public object Cancel : StreamFrameType
    public object Error : FrameType.Error, StreamFrameType

    public sealed interface WithPayload : StreamFrameType
}

public sealed interface RequestFrameType : StreamFrameType, StreamFrameType.WithPayload {
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
