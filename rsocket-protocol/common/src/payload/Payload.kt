package rsocket.protocol.payload

import rsocket.io.*

public interface Payload {
    public val metadata: List<Metadata>
    public val data: Data

    public interface Data {
        public val mimeType: MimeType

        public interface Encoder<T : Data> {
            public val mimeType: MimeType //is it needed?
            public fun BufferFactory.encode(data: T): Buffer
        }

        public interface Decoder<T : Data> {
            public val mimeType: MimeType //is it needed?
            public fun BufferFactory.decode(buffer: Buffer): T
        }

        public interface Codec<T : Data> : Encoder<T>, Decoder<T>
    }

    public interface Metadata {
        public val mimeType: MimeType

        public interface Encoder<T : Metadata> {
            public val mimeType: MimeType //is it needed?
            public fun BufferFactory.encode(metadata: T): Buffer
        }

        public interface Decoder<T : Metadata> {
            public val mimeType: MimeType //is it needed?
            public fun BufferFactory.decode(buffer: Buffer): T
        }

        public interface Codec<T : Metadata> : Encoder<T>, Decoder<T>
    }
}
