package rsocket.protocol

//TODO naming
public interface CompactType {
    public interface WithId {
        public val identifier: Byte
    }

    public interface WithName {
        public val text: String
    }

    public interface WellKnown : WithId, WithName
}

@Suppress("UNCHECKED_CAST")
public abstract class CompactTypeFactory<WithId : CompactType.WithId, WithName : CompactType.WithName, WellKnown>(
    private val withIdConstructor: (identifier: Byte) -> WithId,
    private val withNameConstructor: (text: String) -> WithName,
    wellKnownValues: Array<WellKnown>,
) where WellKnown : CompactType.WellKnown, WellKnown : Enum<WellKnown> {
    private val wellKnownByIdentifier: Array<WellKnown?> = arrayOfNulls<CompactType?>(128) as Array<WellKnown?>
    private val wellKnownByName: MutableMap<String, WellKnown> = HashMap(128)

    init {
        wellKnownValues.forEach {
            wellKnownByIdentifier[it.identifier.toInt()] = it
            wellKnownByName[it.text] = it
        }
    }

    public fun WellKnown(text: String): WellKnown? = wellKnownByName[text]
    public fun WellKnown(identifier: Byte): WellKnown? = wellKnownByIdentifier[identifier.toInt()]
    public fun WellKnown(identifier: Int): WellKnown? = wellKnownByIdentifier[identifier]

    public fun WithName(text: String): WithName = (WellKnown(text) ?: withNameConstructor(text)) as WithName
    public fun WithId(identifier: Byte): WithId = (WellKnown(identifier) ?: withIdConstructor(identifier)) as WithId
    public fun WithId(identifier: Int): WithId = WithId(identifier.toByte())

    public operator fun invoke(text: String): WithName = WithName(text)
    public operator fun invoke(identifier: Byte): WithId = WithId(identifier)
    public operator fun invoke(identifier: Int): WithId = WithId(identifier)
}

//TODO internals
public fun CompactType.WellKnown.toString(typeName: String): String = "$typeName(id=$identifier, text=$text)"
public fun CompactType.WithId.toString(typeName: String): String = "$typeName(id=$identifier)"
public fun CompactType.WithName.toString(typeName: String): String = "$typeName(text=$text)"

public abstract class AbstractCompactTypeWithId(final override val identifier: Byte) : CompactType.WithId {
    init {
        requireLength128(identifier)
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AbstractCompactTypeWithId

        if (identifier != other.identifier) return false

        return true
    }

    final override fun hashCode(): Int {
        return identifier.hashCode()
    }

    abstract override fun toString(): String
}

public abstract class AbstractCompactTypeWithName(final override val text: String) : CompactType.WithName {
    init {
        requireAscii(text)
        requireLength128(text.length.toByte())
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AbstractCompactTypeWithName

        if (text != other.text) return false

        return true
    }

    final override fun hashCode(): Int {
        return text.hashCode()
    }

    abstract override fun toString(): String
}

private fun requireAscii(value: String) {
    require(value.all { it.code <= 0x7f }) { "String should be an ASCII encodded string" }
}

private fun requireLength128(value: Byte) {
    require(value in 1..128) { "Mime-type text length must be in range 1..128 but was '${value}'" }
}
