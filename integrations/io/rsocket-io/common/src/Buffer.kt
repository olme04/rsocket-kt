package rsocket.io

public interface Buffer : Closeable {
    public val size: Int

    public fun slice(index: Int, size: Int): Buffer

    public fun getByte(index: Int): Byte

    public fun getChar(index: Int): Char
    public fun getShort(index: Int): Short
    public fun getInt(index: Int): Int
    public fun getLong(index: Int): Long
    public fun getFloat(index: Int): Float
    public fun getDouble(index: Int): Double

    public fun getByteArray(index: Int, destination: ByteArray, offset: Int = 0, size: Int = destination.size)
    public fun getCharArray(index: Int, destination: CharArray, offset: Int = 0, size: Int = destination.size)
    public fun getShortArray(index: Int, destination: ShortArray, offset: Int = 0, size: Int = destination.size)
    public fun getIntArray(index: Int, destination: IntArray, offset: Int = 0, size: Int = destination.size)
    public fun getLongArray(index: Int, destination: LongArray, offset: Int = 0, size: Int = destination.size)
    public fun getFloatArray(index: Int, destination: FloatArray, offset: Int = 0, size: Int = destination.size)
    public fun getDoubleArray(index: Int, destination: DoubleArray, offset: Int = 0, size: Int = destination.size)
}

public sealed interface BufferFactory : Closeable {
    public fun createBuffer(size: Int): Buffer
}

public abstract class AbstractBufferFactory<MB : MutableBuffer, B : Buffer> : BufferFactory {
    public abstract override fun createBuffer(size: Int): MB

    protected abstract fun map(mutable: MB): B

    public fun buildBuffer(size: Int, block: MB.() -> Unit): B {
        val buffer = createBuffer(size)
        try {
            block(buffer)
            return map(buffer)
        } catch (cause: Throwable) {
            buffer.close()
            throw cause
        }
    }
}
