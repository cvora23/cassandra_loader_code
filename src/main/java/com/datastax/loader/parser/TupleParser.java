package com.datastax.loader.parser;

import com.datastax.driver.core.*;
import com.datastax.loader.MyBigIntCodec;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import javax.xml.crypto.Data;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

/**
 * Created by root on 5/17/17.
 */
public class TupleParser extends AbstractParser{

    private Parser value1Parser;
    private Parser value2Parser;
    private char collectionBegin;
    private char collectionEnd;
    private char collectionQuote = '\"';
    private char collectionEscape = '\\';
    private char tupleDelim;
    private TupleType tupleTypeElement;
    private TupleValue tupleValueElement;

    private CsvParser csvp = null;

    public TupleParser(Parser val1Parser,Parser val2Parser,
                       char inCollectionBegin,char inCollectionEnd,
                       char inTupleDelim){
        value1Parser = val1Parser;
        value2Parser = val2Parser;
        collectionBegin = inCollectionBegin;
        collectionEnd = inCollectionEnd;
        tupleDelim = inTupleDelim;
//        MyBigIntCodec myBigIntCodec = new MyBigIntCodec();
//        CodecRegistry codecRegistry = new CodecRegistry();
//        codecRegistry.register(myBigIntCodec);
//        tupleTypeElement = TupleType.of(ProtocolVersion.NEWEST_SUPPORTED, codecRegistry,DataType.cint(),DataType.bigint());

        tupleTypeElement = TupleType.of(ProtocolVersion.NEWEST_SUPPORTED, CodecRegistry.DEFAULT_INSTANCE,
                DataType.cint(),DataType.bigint());

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setDelimiter(tupleDelim);
        settings.getFormat().setQuote(collectionQuote);
        settings.getFormat().setQuoteEscape(collectionEscape);
        settings.getFormat().setCharToEscapeQuoteEscaping(collectionEscape);
        settings.setKeepQuotes(false);
        settings.setKeepEscapeSequences(false);

        csvp = new CsvParser(settings);

    }

    @Override
    public Object parseIt(String toparse) throws ParseException {
        if(null == toparse)
            return null;
        if(!toparse.startsWith(Character.toString(collectionBegin)))
            throw new ParseException("Must begin with "+ collectionBegin
            + "\n",0);
        if (!toparse.endsWith(Character.toString(collectionEnd)))
            throw new ParseException("Must end with " + collectionEnd
                    + "\n", 0);
        toparse = toparse.substring(1, toparse.length() - 1);
        StringReader sr = new StringReader(toparse);
        csvp.beginParsing(sr);
        try {
            String[] row;
            while ((row = csvp.parseNext()) != null) {
                Object value1 = value1Parser.parse(row[0]);
                Object value2 = value2Parser.parse(row[1]);
                tupleValueElement = tupleTypeElement.newValue();
                tupleValueElement.setInt(0, (Integer) value1);
                //BigInteger bigInteger = new BigInteger("5000");
                tupleValueElement.setLong(1,(Long) value2);
                //tupleValueElement.setVarint(1,(BigInteger) bigInteger);
            }
        }
        catch (Exception e) {
            System.err.println("Trouble parsing : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return tupleValueElement;
    }

    @Override
    public String format(Object o) {

        TupleValue tupleValue = (TupleValue) o;
        StringBuilder sb = new StringBuilder();

        sb.append(collectionBegin);
        int value1 = tupleValue.getInt(0);
        BigInteger value2 = tupleValue.getVarint(1);
        sb.append(value1Parser.format(tupleValue.getInt(0)));
        sb.append(tupleDelim);
        sb.append(value2Parser.format(tupleValue.getLong(1)));
        sb.append(collectionEnd);

        return quote(sb.toString());
    }
}
