package com.datastax.loader;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 5/23/17.
 */
public class CsvWriterExample {


    static List<Object[]> rows = Arrays.asList(
            new Object[][]{
                    {"\"dgdsfrg\"","efgh","TRUE","{\\\"sddsad\\\":\\\"(28,234000)\\\",\\\"bgsfd\\\":\\\"(23,234000)\\\"}",
                            "{0,1,2,3,4}",23},
//                    {"\"1997\"", "Ford", "E350", "ac, abs, moon", 3000.00},
//                    {"1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00"},
//                    {"1996", "Jeep", "Grand Cherokee", "MUST SELL!\nair, moon roof, loaded", "4799.00"},
//                    {},
//                    {"1999", "Chevy", "Venture \"Extended Edition, Very Large\"", null, "5000.00"},
//                    {null, "", "Venture \"Extended Edition\"", null, "4900.00"},
            });

    public static void main(String[] args) {

        // Writing to an in-memory byte array. This will be printed out to the standard output so you can easily see the result.
        ByteArrayOutputStream csvResult = new ByteArrayOutputStream();

        // CsvWriter (and all other file writers) work with an instance of java.io.Writer
        Writer outputWriter = null;
        try {
            outputWriter = new FileWriter("car.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //##CODE_START

        // All you need is to create an instance of CsvWriter with the default CsvWriterSettings.
        // By default, only values that contain a field separator are enclosed within quotes.
        // If quotes are part of the value, they are escaped automatically as well.
        // Empty rows are discarded automatically.
        CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
        //csvWriterSettings.setQuoteAllFields(true);
        csvWriterSettings.setQuoteEscapingEnabled(false);
        csvWriterSettings.setEscapeUnquotedValues(false);
        csvWriterSettings.setInputEscaped(true);
        CsvWriter writer = new CsvWriter(outputWriter, csvWriterSettings);

        // Write the record headers of this file
        //writer.writeHeaders("Year", "Make", "Model", "Description", "Price");

        // Here we just tell the writer to write everything and close the given output Writer instance.
        writer.writeRowsAndClose(rows);

        //##CODE_END

    }

}
