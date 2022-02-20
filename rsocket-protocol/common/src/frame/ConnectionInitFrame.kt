package rsocket.protocol.frame

import rsocket.io.*
import rsocket.protocol.*
import rsocket.protocol.errors.*
import rsocket.protocol.extension.*
import rsocket.protocol.payload.*

public sealed interface ConnectionInitFrame : ConnectionFrame

public sealed interface ConnectFrame : ConnectionInitFrame, Frame.WithPayload, Frame.WithExtensions {
    public val sessionToken: Buffer?
}

public class SetupFrame(
    public val version: RSocketVersion,
    public val defaultMetadataMimeType: MimeType,
    public val defaultDataMimeType: MimeType,
    override val sessionToken: Buffer?,
    override val extensions: List<SetupExtensionPayload>,
    override val payload: Payload,
) : ConnectFrame

public class RestoreFrame(
    override val sessionToken: Buffer?,
    override val extensions: List<RestoreExtensionPayload>,
    override val payload: Payload,
) : ConnectFrame

public sealed interface AcceptFrame : ConnectionInitFrame

public class AckFrame(
    override val extensions: List<AckExtensionPayload>,
    override val payload: Payload,
) : AcceptFrame, Frame.WithPayload, Frame.WithExtensions

public class AcceptErrorFrame(
    override val code: AcceptErrorCode,
    override val data: Lazy<Buffer>,
) : AcceptFrame, Frame.Error
