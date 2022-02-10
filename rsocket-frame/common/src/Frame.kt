package rsocket.frame

import rsocket.protocol.*

public sealed interface Frame {
    public val streamId: Int
    public val type: FrameType
    public val flags: Flags

    public sealed interface V1 : Frame {
        override val type: FrameType.V1
    }

    public sealed interface V2 : Frame {
        override val type: FrameType.V2
    }
}

public sealed interface Flags {
    public object Empty : Flags

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

    public sealed interface Resume : Flags {
        public val resume: Boolean
    }
}
