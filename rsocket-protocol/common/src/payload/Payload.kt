package rsocket.protocol.payload

import rsocket.io.*

public interface Payload {
    public val metadata: Metadata?
    public val data: Data

    public interface Data {
        public val mimeType: MimeType?
        public val buffer: Lazy<Buffer>
    }

    public interface Metadata {
        public val mimeType: MimeType?
        public val buffer: Lazy<Buffer>
    }
}

