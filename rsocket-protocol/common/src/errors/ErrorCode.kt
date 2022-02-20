package rsocket.protocol.errors

public sealed interface ErrorCode {
// TODO set values
//    public val value: Int

    public sealed interface Custom : ErrorCode
}

public sealed interface AcceptErrorCode : ErrorCode {
    public object UnsupportedSetup : AcceptErrorCode
    public object InvalidSetup : AcceptErrorCode
    public object RejectedSetup : AcceptErrorCode

    public object UnsupportedRestore : AcceptErrorCode
    public object InvalidRestore : AcceptErrorCode
    public object RejectedRestore : AcceptErrorCode

    public interface Custom : AcceptErrorCode, ErrorCode.Custom
}

public sealed interface ConnectionErrorCode : ErrorCode {
    public object ConnectionError : ConnectionErrorCode
    public object ConnectionClose : ConnectionErrorCode

    public interface Custom : ConnectionErrorCode, ErrorCode.Custom
}

public sealed interface StreamErrorCode : ErrorCode {
    public object ApplicationError : StreamErrorCode
    public object Invalid : StreamErrorCode
    public object Rejected : StreamErrorCode
    public object Canceled : StreamErrorCode

    public interface Custom : StreamErrorCode, ErrorCode.Custom
}
