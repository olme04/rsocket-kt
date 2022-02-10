package rsocket.protocol.resume

import rsocket.io.*

public interface KeepAliveSender {
    public suspend fun send(data: Buffer)
}
