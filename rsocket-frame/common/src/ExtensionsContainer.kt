package rsocket.frame

import rsocket.io.*
import rsocket.protocol.*

public interface Extension {
    public val type: ExtensionType
    public val flags: ExtensionFlags
    public val data: Buffer
}

public sealed interface ExtensionFlags {

}

public interface SetupExtensionFlags : ExtensionFlags {
    public val canBeIgnored: Boolean
    public val needAcknowledgement: Boolean
}

public interface SetupOkExtensionFlags : ExtensionFlags

public interface CustomExtensionFlags : ExtensionFlags

public interface StreamExtensionFlags : ExtensionFlags
