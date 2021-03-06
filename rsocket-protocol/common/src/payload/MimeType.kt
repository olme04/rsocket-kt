package rsocket.protocol.payload

import rsocket.protocol.*

public sealed interface MimeType : CompactType {
    public companion object : CompactTypeFactory<WithId, WithName, WellKnown>(::WithIdImpl, ::WithNameImpl, enumValues())

    public sealed interface WithId : MimeType, CompactType.WithId
    public sealed interface WithName : MimeType, CompactType.WithName
    public enum class WellKnown(
        public override val text: String,
        public override val identifier: Byte,
    ) : MimeType, CompactType.WellKnown, WithId, WithName {
        ApplicationAvro("application/avro", 0x00),
        ApplicationCbor("application/cbor", 0x01),
        ApplicationGraphql("application/graphql", 0x02),
        ApplicationGzip("application/gzip", 0x03),
        ApplicationJavascript("application/javascript", 0x04),
        ApplicationJson("application/json", 0x05),
        ApplicationOctetStream("application/octet-stream", 0x06),
        ApplicationPdf("application/pdf", 0x07),
        ApplicationThrift("application/vnd.apache.thrift.binary", 0x08),
        ApplicationProtoBuf("application/vnd.google.protobuf", 0x09),
        ApplicationXml("application/xml", 0x0A),
        ApplicationZip("application/zip", 0x0B),
        AudioAac("audio/aac", 0x0C),
        AudioMp3("audio/mp3", 0x0D),
        AudioMp4("audio/mp4", 0x0E),
        AudioMpeg3("audio/mpeg3", 0x0F),
        AudioMpeg("audio/mpeg", 0x10),
        AudioOgg("audio/ogg", 0x11),
        AudioOpus("audio/opus", 0x12),
        AudioVorbis("audio/vorbis", 0x13),
        ImageBmp("image/bmp", 0x14),
        ImageGif("image/gif", 0x15),
        ImageHeicSequence("image/heic-sequence", 0x16),
        ImageHeic("image/heic", 0x17),
        ImageHeifSequence("image/heif-sequence", 0x18),
        ImageHeif("image/heif", 0x19),
        ImageJpeg("image/jpeg", 0x1A),
        ImagePng("image/png", 0x1B),
        ImageTiff("image/tiff", 0x1C),
        MultipartMixed("multipart/mixed", 0x1D),
        TextCss("text/css", 0x1E),
        TextCsv("text/csv", 0x1F),
        TextHtml("text/html", 0x20),
        TextPlain("text/plain", 0x21),
        TextXml("text/xml", 0x22),
        VideoH264("video/H264", 0x23),
        VideoH265("video/H265", 0x24),
        VideoVp8("video/VP8", 0x25),
        ApplicationHessian("application/x-hessian", 0x26),
        ApplicationJavaObject("application/x-java-object", 0x27),
        ApplicationCloudeventsJson("application/cloudevents+json", 0x28),
        ApplicationCapnProto("application/x-capnp", 0x29),
        ApplicationFlatBuffers("application/x-flatbuffers", 0x2A),

        MessageRSocketMimeType("message/x.rsocket.mime-type.v0", 0x7A),
        MessageRSocketAcceptMimeTypes("message/x.rsocket.accept-mime-types.v0", 0x7b),
        MessageRSocketAuthentication("message/x.rsocket.authentication.v0", 0x7C),
        MessageRSocketTracingZipkin("message/x.rsocket.tracing-zipkin.v0", 0x7D),
        MessageRSocketRouting("message/x.rsocket.routing.v0", 0x7E),
        MessageRSocketCompositeMetadata("message/x.rsocket.composite-metadata.v0", 0x7F);

        override fun toString(): String = toString("MimeType")
    }

    private class WithIdImpl(identifier: Byte) : WithId, AbstractCompactTypeWithId(identifier) {
        override fun toString(): String = toString("MimeType")
    }

    private class WithNameImpl(text: String) : WithName, AbstractCompactTypeWithName(text) {
        override fun toString(): String = toString("MimeType")
    }
}
