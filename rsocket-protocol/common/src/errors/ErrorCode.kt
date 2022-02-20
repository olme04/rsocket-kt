package rsocket.protocol.errors

public sealed interface ErrorCode {
// TODO set values
//    public val value: Int

    public sealed interface Custom : ErrorCode
}

public sealed interface ConnectionErrorCode : ErrorCode {
    public interface Custom : ConnectionErrorCode, ErrorCode.Custom
}

public sealed interface ConnectionLifecycleErrorCode : ConnectionErrorCode {
    public object ConnectionError : ConnectionLifecycleErrorCode
    public object ConnectionClose : ConnectionLifecycleErrorCode

    public interface Custom : ConnectionLifecycleErrorCode, ConnectionErrorCode.Custom
}

public sealed interface ConnectionInitErrorCode : ConnectionErrorCode {
    public object UnsupportedSetup : ConnectionInitErrorCode
    public object InvalidSetup : ConnectionInitErrorCode
    public object RejectedSetup : ConnectionInitErrorCode

    public object UnsupportedRestore : ConnectionInitErrorCode
    public object InvalidRestore : ConnectionInitErrorCode
    public object RejectedRestore : ConnectionInitErrorCode

    public interface Custom : ConnectionInitErrorCode, ConnectionErrorCode.Custom
}

public sealed interface RequestErrorCode : ErrorCode {
    public object ApplicationError : RequestErrorCode
    public object Invalid : RequestErrorCode
    public object Rejected : RequestErrorCode
    public object Canceled : RequestErrorCode

    public interface Custom : RequestErrorCode, ErrorCode.Custom
}
