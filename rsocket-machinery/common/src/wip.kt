package rsocket.wip

import rsocket.io.*
import rsocket.protocol.*
import rsocket.protocol.errors.*
import rsocket.protocol.extensions.*
import rsocket.protocol.payload.*

private class AckInfo<T>(
    val extensions: List<ExtensionPayload>,
    val payload: Payload,
    val data: T,
)

private interface ServerSetup {
    fun handleSetup(
        version: RSocketVersion,
        defaultMetadataMimeType: MimeType,
        defaultDataMimeType: MimeType,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: ConnectionOutput,
    ): ConnectionInput

    fun handleSetupWithAck(
        version: RSocketVersion,
        defaultMetadataMimeType: MimeType,
        defaultDataMimeType: MimeType,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: ConnectionOutput,
    ): AckInfo<ConnectionInput>
}

private interface ClientSetup {
    suspend fun setup(
        version: RSocketVersion,
        defaultMetadataMimeType: MimeType,
        defaultDataMimeType: MimeType,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: ConnectionInput,
    ): ConnectionOutput

    suspend fun setupWithAck(
        version: RSocketVersion,
        defaultMetadataMimeType: MimeType,
        defaultDataMimeType: MimeType,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: ConnectionInput,
    ): AckInfo<ConnectionOutput>
}

private interface ConnectionOutput {

    suspend fun fireAndForgot(
        follows: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: RequestInput,
    ): RequestOutput

    suspend fun requestResponse(
        follows: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: RequestInput,
    ): RequestOutput

    suspend fun requestStream(
        follows: Boolean,
        initialRequest: Int,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: RequestInput,
    ): RequestOutput

    suspend fun requestChannel(
        follows: Boolean,
        complete: Boolean,
        initialRequest: Int,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        input: RequestInput,
    ): RequestOutput

    suspend fun custom(
        extensions: List<ExtensionPayload>,
    )

    suspend fun error(
        code: ErrorCode,
        data: Buffer,
    )

}

private interface ConnectionInput {

    fun handleFireAndForgot(
        follows: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: RequestOutput,
    ): RequestInput

    fun handleRequestResponse(
        follows: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: RequestOutput,
    ): RequestInput

    fun handleRequestStream(
        follows: Boolean,
        initialRequest: Int,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: RequestOutput,
    ): RequestInput

    fun handleRequestChannel(
        follows: Boolean,
        complete: Boolean,
        initialRequest: Int,
        extensions: List<ExtensionPayload>,
        payload: Payload,
        output: RequestOutput,
    ): RequestInput

    fun handleCustom(
        extensions: List<ExtensionPayload>,
    )

    fun handleError(
        code: ErrorCode,
        data: Buffer,
    )

}

private interface RequestOutput {
    suspend fun payload(
        follows: Boolean,
        complete: Boolean,
        next: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
    )

    suspend fun requestN(
        request: Int,
        extensions: List<ExtensionPayload>,
    )

    suspend fun cancel(
        extensions: List<ExtensionPayload>,
    )

    suspend fun custom(
        extensions: List<ExtensionPayload>,
    )

    suspend fun error(
        code: ErrorCode,
        data: Buffer,
    )
}

private interface RequestInput {
    fun handlePayload(
        follows: Boolean,
        complete: Boolean,
        next: Boolean,
        extensions: List<ExtensionPayload>,
        payload: Payload,
    )

    fun handleRequestN(
        request: Int,
        extensions: List<ExtensionPayload>,
    )

    fun handleCancel(
        extensions: List<ExtensionPayload>,
    )

    fun handleCustom(
        extensions: List<ExtensionPayload>,
    )

    fun handleError(
        code: ErrorCode,
        data: Buffer,
    )
}
