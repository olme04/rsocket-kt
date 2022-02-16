package rsocket.frame

import rsocket.protocol.*

public sealed interface Frame {
    public val streamId: Int
    public val type: FrameType
    public val flags: Flags
}

public sealed interface ConnectionFrame : Frame {
    override val streamId: Int get() = 0
    override val type: ConnectionFrameType
}

public sealed interface Flags {
    public object Empty : Flags

    public sealed interface HasExtensions : Flags {
        public val hasExtensions: Boolean
    }

    public sealed interface HasMetadata : Flags {
        public val hasMetadata: Boolean
    }

    public sealed interface Follows : Flags {
        public val follows: Boolean
    }

    public sealed interface Complete : Flags {
        public val complete: Boolean
    }

    public sealed interface Next : Flags {
        public val next: Boolean
    }

}
