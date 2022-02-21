package rsocket.protocol.extension

import rsocket.io.*

public interface ExtensionPayload {
    public val type: ExtensionType

    public interface Encoder<T : ExtensionPayload> {
        public val type: ExtensionType
        public fun BufferFactory.encode(payload: T): Buffer
    }

    public interface Decoder<T : ExtensionPayload> {
        public val type: ExtensionType
        public fun BufferFactory.decode(buffer: Buffer): T
    }

    public interface Codec<T : ExtensionPayload> : Encoder<T>, Decoder<T>
}

//marker interfaces, to not pass extension payload into place, where it can not be used

//for connection
public interface SetupExtensionPayload : ExtensionPayload
public interface RestoreExtensionPayload : ExtensionPayload
public interface AckExtensionPayload : ExtensionPayload
public interface ConnectionExtensionPayload : ExtensionPayload

//for request
public interface RequestInitExtensionPayload : ExtensionPayload
public interface StreamRestoreExtensionPayload : ExtensionPayload
public interface PayloadExtensionPayload : ExtensionPayload
public interface RequestNExtensionPayload : ExtensionPayload
public interface CancelExtensionPayload : ExtensionPayload
public interface StreamExtensionPayload : ExtensionPayload
