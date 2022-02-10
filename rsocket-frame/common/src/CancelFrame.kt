package rsocket.frame

import rsocket.protocol.*

public interface CancelFrame : StreamFrame {
    override val flags: Flags.Empty get() = Flags.Empty
    override val type: StreamFrameType.Cancel get() = StreamFrameType.Cancel
}
