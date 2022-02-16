package rsocket.protocol

public class RSocketException(public val code: ErrorCode, override val message: String) : RuntimeException(message)

//TODO move value to errorCode?
public sealed interface ErrorCode {
    public sealed interface Custom : ErrorCode {
        public val value: Int
    }
}

public sealed interface ConnectionErrorCode : ErrorCode {
    public object InvalidSetup : ConnectionErrorCode
    public object UnsupportedSetup : ConnectionErrorCode
    public object RejectedSetup : ConnectionErrorCode
    public object RejectedResume : ConnectionErrorCode
    public object ConnectionError : ConnectionErrorCode
    public object ConnectionClose : ConnectionErrorCode

    public interface Custom : ConnectionErrorCode, ErrorCode.Custom
}

public sealed interface StreamErrorCode : ErrorCode {
    public object ApplicationError : StreamErrorCode
    public object Rejected : StreamErrorCode
    public object Canceled : StreamErrorCode
    public object Invalid : StreamErrorCode

    public interface Custom : StreamErrorCode, ErrorCode.Custom
}
