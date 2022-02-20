package rsocket.protocol.extensions

import rsocket.io.*

public interface Extension {
    public val type: ExtensionType
    public val buffer: Buffer
}

//marker interfaces, to not pass extension payload into place, where it can not be used

//for connection
public interface SetupExtension : Extension
public interface RestoreExtension : Extension
public interface AckExtension : Extension
public interface CustomConnectionExtension : Extension

//for request
public interface RequestInitExtension : Extension
public interface PayloadExtension : Extension
public interface RequestNExtension : Extension
public interface CancelExtension : Extension
public interface CustomRequestExtension : Extension
