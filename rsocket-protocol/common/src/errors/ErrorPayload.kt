package rsocket.protocol.errors

import rsocket.io.*

public interface ErrorPayload {
    public val code: ErrorCode

    public interface Encoder<T : ErrorPayload> {
        public val code: ErrorCode
        public fun BufferFactory.encode(payload: T): Buffer
    }

    public interface Decoder<T : ErrorPayload> {
        public val code: ErrorCode
        public fun BufferFactory.decode(buffer: Buffer): T
    }

    public interface Codec<T : ErrorPayload> : Encoder<T>, Decoder<T>
}
