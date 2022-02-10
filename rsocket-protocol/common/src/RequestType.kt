package rsocket.protocol

public enum class RequestType {
    FireAndForget,
    RequestResponse,
    RequestStream,
    RequestChannel
}
