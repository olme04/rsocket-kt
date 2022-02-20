package rsocket.io.ktor

import io.ktor.utils.io.core.*
import rsocket.io.Buffer

public sealed interface BufferWithByteReadPacket : Buffer {
    public val packet: ByteReadPacket
}
