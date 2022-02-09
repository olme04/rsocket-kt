package rsocket.payload

import rsocket.io.*
import rsocket.metadata.*

public interface Payload {
    public val data: Buffer
    public val metadata: Metadata
}
