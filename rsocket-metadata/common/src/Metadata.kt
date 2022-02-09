package rsocket.metadata

import rsocket.io.*
import rsocket.protocol.*

public interface Metadata {
    public val buffer: Buffer
    public val mimeType: MimeType
}
