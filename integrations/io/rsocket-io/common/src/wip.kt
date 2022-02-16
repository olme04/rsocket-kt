package rsocket.io

//TODO decide on which operations will be here
public interface BufferOperations { //implemented externally to support different underlying Buffer storages
    //wil transfer from source SI to EI into destination from SI to EI
    // if
    //TODO: composite buffer handling
    public fun transfer(
        source: Buffer,
        destination: MutableBuffer,
        sourceStartIndex: Int = 0, sourceEndIndex: Int = source.size,
        destinationStartIndex: Int = 0, destinationEndIndex: Int = destination.size,
    ): Int //returns transfered amount
}

public interface Charset

public interface CompositeBuffer<B : Buffer> : Buffer {
    public val entriesSize: Int
    public fun getEntry(index: Int): B
}

public interface MutableCompositeBuffer<B : Buffer> : CompositeBuffer<B> {
    public fun addEntry(buffer: B) //adds to the end

    public fun addEntry(index: Int, buffer: B) //adds entry at postition `index`
    public fun setEntry(index: Int, buffer: B): B? //old value if exists
    public fun removeEntry(index: Int): B? //old value if exists
}


public interface Reader {
    public val readIndex: Int //TODO?
    public val readRemaining: Int

    public fun limit(remaining: Int): Int //TODO is it needed?

    public fun skip(n: Int)

    public fun readByte(): Byte

    public fun readChar(): Char
    public fun readShort(): Short
    public fun readInt(): Int
    public fun readLong(): Long
    public fun readFloat(): Float
    public fun readDouble(): Double

    public fun readByteArray(destination: ByteArray, offset: Int = 0, size: Int = destination.size)
}

public interface Writer {
    public val writeIndex: Int //TODO
    public val writeRemaining: Int

    public fun skip(n: Int)

    public fun writeByte(value: Byte)

    public fun writeChar(value: Char)
    public fun writeShort(value: Short)
    public fun writeInt(value: Int)
    public fun writeLong(value: Long)
    public fun writeFloat(value: Float)
    public fun writeDouble(value: Double)

    public fun writeByteArray(value: ByteArray, offset: Int = 0, size: Int = value.size)
}
