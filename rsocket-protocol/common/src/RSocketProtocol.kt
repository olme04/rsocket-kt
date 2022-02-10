package rsocket.protocol

public enum class RSocketProtocol { V1, V2 }

public data class RSocketVersion(
    public val major: RSocketProtocol,
    public val minor: Int
)
