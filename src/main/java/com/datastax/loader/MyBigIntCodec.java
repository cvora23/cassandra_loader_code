package com.datastax.loader;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.InvalidTypeException;
import com.datastax.driver.core.utils.Bytes;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Created by root on 5/18/17.
 */
//public class MyBigIntCodec extends TypeCodec<BigInteger> {
//
//    private static final MyBigIntCodec instance = new MyBigIntCodec();
//
//    public MyBigIntCodec(){
//        super(DataType.bigint(),BigInteger.class);
//    }
//
//    @Override
//    public ByteBuffer serialize(BigInteger value, ProtocolVersion protocolVersion) throws InvalidTypeException {
//        return value == null ? null : ByteBuffer.wrap(value.toByteArray());
//    }
//
//    @Override
//    public BigInteger deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
//        return bytes == null || bytes.remaining() == 0 ? null : new BigInteger(Bytes.getArray(bytes));
//    }
//
//    @Override
//    public BigInteger parse(String value) throws InvalidTypeException {
//        try {
//            return value == null || value.isEmpty() || value.equalsIgnoreCase("NULL") ? null : new BigInteger(value);
//        } catch (NumberFormatException e) {
//            throw new InvalidTypeException(String.format("Cannot parse bigint value from \"%s\"", value), e);
//        }
//    }
//
//    @Override
//    public String format(BigInteger value) throws InvalidTypeException {
//        if (value == null)
//            return "NULL";
//        return value.toString();
//    }
//}

public class MyBigIntCodec extends TypeCodec<BigInteger> {

    private static final MyBigIntCodec instance = new MyBigIntCodec();

    public MyBigIntCodec(){
        super(DataType.bigint(),BigInteger.class);
    }

    @Override
    public ByteBuffer serialize(BigInteger value, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return value == null ? null : ByteBuffer.wrap(value.toByteArray());
    }

    @Override
    public BigInteger deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return bytes == null || bytes.remaining() == 0 ? null : new BigInteger(Bytes.getArray(bytes));
    }

    @Override
    public BigInteger parse(String value) throws InvalidTypeException {
        try {
            return value == null || value.isEmpty() || value.equalsIgnoreCase("NULL") ? null : new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new InvalidTypeException(String.format("Cannot parse bigint value from \"%s\"", value), e);
        }
    }

    @Override
    public String format(BigInteger value) throws InvalidTypeException {
        if (value == null)
            return "NULL";
        return value.toString();
    }
}
