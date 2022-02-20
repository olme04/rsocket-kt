package rsocket.io

public interface MutableBuffer : Buffer {
    override fun slice(index: Int, size: Int): MutableBuffer

    public fun setByte(index: Int, value: Byte)

    public fun setChar(index: Int, value: Char)
    public fun setShort(index: Int, value: Short)
    public fun setInt(index: Int, value: Int)
    public fun setLong(index: Int, value: Long)
    public fun setFloat(index: Int, value: Float)
    public fun setDouble(index: Int, value: Double)

    public fun setByteArray(index: Int, value: ByteArray, offset: Int = 0, size: Int = value.size)
    public fun setCharArray(index: Int, value: CharArray, offset: Int = 0, size: Int = value.size)
    public fun setShortArray(index: Int, value: ShortArray, offset: Int = 0, size: Int = value.size)
    public fun setIntArray(index: Int, value: IntArray, offset: Int = 0, size: Int = value.size)
    public fun setLongArray(index: Int, value: LongArray, offset: Int = 0, size: Int = value.size)
    public fun setFloatArray(index: Int, value: FloatArray, offset: Int = 0, size: Int = value.size)
}
