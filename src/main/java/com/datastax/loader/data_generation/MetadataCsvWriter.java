package com.datastax.loader.data_generation;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.fluttercode.datafactory.impl.DataFactory;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class MetadataCsvWriter
        implements Callable<String>
{
    private String myName;
    private int count;
    private final long timeSleep;
    static final String FILEPATH_WRITE = "CosMetadata.csv";
    static final int num = 10000;
    public static final int MIN_CHAR_LEN = 10;
    public static final int MAX_CHAR_LEN = 25;
    CSVWriter csvWriter = null;
    DataFactory df = new DataFactory();

    MetadataCsvWriter(String name, int newcount, long newtimeSleep)
    {
        this.myName = name;
        this.count = newcount;
        this.timeSleep = newtimeSleep;
    }

    private static int getRandomNumberInRange(int min, int max)
    {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public String call()
            throws Exception
    {
        try
        {
            this.csvWriter = new CSVWriter(new FileWriter("CosMetadata.csv", true), ',', '"', '\000');

            List metadataList = new ArrayList();
            for (int i = 0; i < 10000; i++)
            {
                String id = this.df.getRandomText(10, 25);
                String doc = this.df.getRandomText(10, 25);
                Metadata metadata = new Metadata(id, doc);
                metadataList.add(metadata);
            }
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();

            mappingStrategy.setType(Metadata.class);

            String[] columns = { "id", "doc" };

            mappingStrategy.setColumnMapping(columns);

            BeanToCsv.write(mappingStrategy, this.csvWriter, metadataList);

            return "MetadataCsvWriter Task Done";
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                this.csvWriter.close();
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
        }
        return "MetadataCsvWriter Task Done";
    }
}
