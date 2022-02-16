package rsocket.extension.resume

import rsocket.frame.*
import rsocket.io.*

//TODO: decide on suspend

public sealed interface ResumableFrameStorage : Closeable

//TODO: for version 1. TODO naming
public interface SequentialResumableFrameStorage : ResumableFrameStorage {
    public suspend fun store(frame: Frame)     // on send to connection
    public suspend fun stored(): List<Frame> // on reconnect
    public suspend fun invalidate(position: Long) // on keep alive / resume
}

public sealed interface StreamingResumableFrameStorage : ResumableFrameStorage

public interface PerStreamResumableFrameStorage : StreamingResumableFrameStorage {
    public suspend fun create(streamId: Int): Stream
    public suspend fun remove(streamId: Int)

    public sealed interface Stream {
        public suspend fun store(frame: Frame)
        public suspend fun stored(): List<Frame>
        public suspend fun invalidate(position: Long)
    }
}

public interface PerConnectionResumableFrameStorage : StreamingResumableFrameStorage {
    public suspend fun store(streamId: Int, frame: Frame)
    public suspend fun remove(streamId: Int)
    public suspend fun stored(streamId: Int): List<Frame>
    public suspend fun invalidate(streamId: Int, position: Long)
}
