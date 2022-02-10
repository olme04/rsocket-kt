package rsocket.logging

//TODO: extract comparator/Comparable from logging level

public sealed interface LoggingLevel : Comparable<LoggingLevel> {
    public val value: Int //TODO: set good values

    override fun compareTo(other: LoggingLevel): Int = value.compareTo(other.value)

    public object TRACE : LoggingLevel {
        override val value: Int get() = 1
    }

    public object DEBUG : LoggingLevel {
        override val value: Int get() = 2
    }

    public object INFO : LoggingLevel {
        override val value: Int get() = 3
    }

    public object WARN : LoggingLevel {
        override val value: Int get() = 4
    }

    public object ERROR : LoggingLevel {
        override val value: Int get() = 5
    }

    public interface Custom : LoggingLevel //should override toString()

    public companion object //for level extension
}

public fun interface LoggerFactory {
    public fun logger(tag: String): Logger
}

public sealed interface LogFilter {
    public fun isLoggable(level: LoggingLevel): Boolean

    public fun isLoggableTrace(): Boolean
    public fun isLoggableDebug(): Boolean
    public fun isLoggableInfo(): Boolean
    public fun isLoggableWarn(): Boolean
    public fun isLoggableError(): Boolean
    public fun isLoggableCustom(level: LoggingLevel.Custom): Boolean

    //TODO: naming
    public interface Simple : LogFilter {
        override fun isLoggableTrace(): Boolean = isLoggable(LoggingLevel.TRACE)
        override fun isLoggableDebug(): Boolean = isLoggable(LoggingLevel.DEBUG)
        override fun isLoggableInfo(): Boolean = isLoggable(LoggingLevel.INFO)
        override fun isLoggableWarn(): Boolean = isLoggable(LoggingLevel.WARN)
        override fun isLoggableError(): Boolean = isLoggable(LoggingLevel.ERROR)
        override fun isLoggableCustom(level: LoggingLevel.Custom): Boolean = isLoggable(level)
    }

    public interface PerLevel : LogFilter {
        override fun isLoggable(level: LoggingLevel): Boolean = when (level) {
            LoggingLevel.TRACE     -> isLoggableTrace()
            LoggingLevel.DEBUG     -> isLoggableDebug()
            LoggingLevel.INFO      -> isLoggableInfo()
            LoggingLevel.WARN      -> isLoggableWarn()
            LoggingLevel.ERROR     -> isLoggableError()
            is LoggingLevel.Custom -> isLoggableCustom(level)
        }
    }
}

public sealed interface LogAppender {
    public fun appendLog(level: LoggingLevel, throwable: Throwable?, message: Any?)

    public fun appendTrace(throwable: Throwable?, message: Any?)
    public fun appendDebug(throwable: Throwable?, message: Any?)
    public fun appendInfo(throwable: Throwable?, message: Any?)
    public fun appendWarn(throwable: Throwable?, message: Any?)
    public fun appendError(throwable: Throwable?, message: Any?)
    public fun appendCustom(level: LoggingLevel.Custom, throwable: Throwable?, message: Any?)

    //TODO
    public interface Simple : LogAppender
    public interface PerLevel : LogAppender
}


public interface Logger {
    public val tag: String

    public fun isLoggable(level: LoggingLevel): Boolean = when (level) {
        LoggingLevel.TRACE     -> isLoggableTrace()
        LoggingLevel.DEBUG     -> isLoggableDebug()
        LoggingLevel.INFO      -> isLoggableInfo()
        LoggingLevel.WARN      -> isLoggableWarn()
        LoggingLevel.ERROR     -> isLoggableError()
        is LoggingLevel.Custom -> isLoggableCustom(level)
    }

    public fun isLoggableTrace(): Boolean = isLoggable(LoggingLevel.TRACE)
    public fun isLoggableDebug(): Boolean = isLoggable(LoggingLevel.DEBUG)
    public fun isLoggableInfo(): Boolean = isLoggable(LoggingLevel.INFO)
    public fun isLoggableWarn(): Boolean = isLoggable(LoggingLevel.WARN)
    public fun isLoggableError(): Boolean = isLoggable(LoggingLevel.ERROR)
    public fun isLoggableCustom(level: LoggingLevel.Custom): Boolean = isLoggable(level)

    public fun appendLog(level: LoggingLevel, throwable: Throwable?, message: Any?) {
        when (level) {
            LoggingLevel.TRACE     -> appendTrace(throwable, message)
            LoggingLevel.DEBUG     -> appendDebug(throwable, message)
            LoggingLevel.INFO      -> appendInfo(throwable, message)
            LoggingLevel.WARN      -> appendWarn(throwable, message)
            LoggingLevel.ERROR     -> appendError(throwable, message)
            is LoggingLevel.Custom -> appendCustom(level, throwable, message)
        }
    }

    public fun appendTrace(throwable: Throwable?, message: Any?) {
        appendLog(LoggingLevel.TRACE, throwable, message)
    }

    public fun appendDebug(throwable: Throwable?, message: Any?) {
        appendLog(LoggingLevel.DEBUG, throwable, message)
    }

    public fun appendInfo(throwable: Throwable?, message: Any?) {
        appendLog(LoggingLevel.INFO, throwable, message)
    }

    public fun appendWarn(throwable: Throwable?, message: Any?) {
        appendLog(LoggingLevel.WARN, throwable, message)
    }

    public fun appendError(throwable: Throwable?, message: Any?) {
        appendLog(LoggingLevel.ERROR, throwable, message)
    }

    public fun appendCustom(level: LoggingLevel.Custom, throwable: Throwable?, message: Any?) {
        appendLog(level, throwable, message)
    }
}

public inline fun Logger.log(level: LoggingLevel, throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggable(level)) return
    appendLog(level, throwable, message())
}

public inline fun Logger.trace(throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableTrace()) return
    appendTrace(throwable, message())
}

public inline fun Logger.debug(throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableDebug()) return
    appendDebug(throwable, message())
}

public inline fun Logger.info(throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableInfo()) return
    appendInfo(throwable, message())
}

public inline fun Logger.warn(throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableWarn()) return
    appendWarn(throwable, message())
}

public inline fun Logger.error(throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableError()) return
    appendError(throwable, message())
}

public inline fun Logger.custom(level: LoggingLevel.Custom, throwable: Throwable? = null, message: () -> Any?) {
    if (!isLoggableCustom(level)) return
    appendCustom(level, throwable, message())
}

public class PrintLogger(
    override val tag: String,
    private val minLevel: LoggingLevel = LoggingLevel.INFO,
) : Logger {
    override fun isLoggable(level: LoggingLevel): Boolean = level >= minLevel
    override fun appendLog(level: LoggingLevel, throwable: Throwable?, message: Any?) {
        val error = throwable?.stackTraceToString()?.let { "Error: $it" } ?: ""
        println("[$level] ($tag) $message $error")
    }

    public companion object : LoggerFactory {
        override fun logger(tag: String): Logger = PrintLogger(tag)

        public fun withLevel(minLevel: LoggingLevel): LoggerFactory = LoggerFactory { PrintLogger(it, minLevel) }
    }
}


public object NoopLogger : Logger, LoggerFactory {
    override val tag: String get() = "noop"
    override fun isLoggable(level: LoggingLevel): Boolean = false
    override fun appendLog(level: LoggingLevel, throwable: Throwable?, message: Any?): Unit = Unit
    override fun logger(tag: String): Logger = this
}
