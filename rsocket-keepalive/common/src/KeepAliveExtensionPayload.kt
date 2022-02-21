package rsocket.keepalive

import rsocket.io.*
import rsocket.protocol.*
import rsocket.protocol.extension.*
import rsocket.protocol.frame.*
import kotlin.time.*

/*
    after setup, send KAD in interval
    respond if received KAD with respond = true
    close connection after not receiving KAD in maxLifetime
 */

public class KeepAliveSetup(
    public val interval: Duration,
    public val maxLifetime: Duration,
) : SetupExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Keepalive
}

private class KAT(
    private val original: ConnectionProtocol,
) : ConnectionProtocol by original {
    override suspend fun onFrame(frame: ConnectionLifecycleFrame) {
        when (frame) {
            is ConnectionErrorFrame     -> Unit
            is ConnectionExtensionFrame -> when (val tick = frame.extensions.filterIsInstance<KeepAliveTick>().firstOrNull()) {
                null -> Unit
                else -> {
                    tick.payload
                    if (tick.respond) {

                    }
                }
            }
        }
        return original.onFrame(frame)
    }
}

private class KACP(
    private val original: ConnectProtocol,
) : ConnectProtocol {
    override suspend fun onConnect(frame: ConnectFrame, protocol: ConnectionProtocol): ConnectionProtocol {
        val remoteProtocol = original.onConnect(frame, KAT(protocol))
        when (frame) {
            is RestoreFrame -> TODO()
            is SetupFrame   -> when (val setup = frame.extensions.filterIsInstance<KeepAliveSetup>().firstOrNull()) {
                null -> Unit
                else -> {
                    setup.interval
                    setup.maxLifetime
                }
            }
        }
        return remoteProtocol
    }
}

public interface KeepAlivePayload {
    public interface Encoder<T : KeepAlivePayload> {
        public fun BufferFactory.encode(payload: T): Buffer
    }

    public interface Decoder<T : KeepAlivePayload> {
        public fun BufferFactory.decode(buffer: Buffer): T
    }

    public interface Codec<T : KeepAlivePayload> : Encoder<T>, Decoder<T>
}

public class KeepAliveTick(
    public val respond: Boolean,
    public val payload: KeepAlivePayload,
) : ConnectionExtensionPayload {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Keepalive
}

private class KATEncoder(
    private val payloadEncoder: KeepAlivePayload.Encoder<KeepAlivePayload>,
) : ExtensionPayload.Encoder<KeepAliveTick> {
    override val type: ExtensionType get() = ExtensionType.WellKnown.Keepalive

    override fun BufferFactory.encode(payload: KeepAliveTick): Buffer {
        val header = createBuffer(1).apply {
            setByte(0, 1)
        }
        val payloadBuffer = with(payloadEncoder) {
            encode(payload.payload)
        }
        return header + payloadBuffer
    }
}
