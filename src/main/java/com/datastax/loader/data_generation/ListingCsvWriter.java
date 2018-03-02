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

public class ListingCsvWriter
        implements Callable<String>
{
    private String myName;
    private int count;
    private final long timeSleep;
    static final String FILEPATH_WRITE = "CosListing.csv";
    static final int num = 10000;
    public static final int MIN_CHAR_LEN = 10;
    public static final int MAX_CHAR_LEN = 25;
    CSVWriter csvWriter = null;
    DataFactory df = new DataFactory();

    public ListingCsvWriter(String myName, int count, long timeSleep)
    {
        this.myName = myName;
        this.count = count;
        this.timeSleep = timeSleep;
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
            this.csvWriter = new CSVWriter(new FileWriter("CosListing.csv", true), ',', '"', '\000');

            List listingList = new ArrayList();
            for (int i = 0; i < 10000; i++)
            {
                String id = this.df.getRandomText(10, 25);
                int bucket = this.df.getNumberBetween(0, Integer.MAX_VALUE);
                String child = this.df.getRandomText(10, 25);
                String child_id = this.df.getRandomText(10, 25);
                Listing listing = new Listing(id, bucket, child, child_id);
                listingList.add(listing);
            }
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();

            mappingStrategy.setType(Listing.class);

            String[] columns = { "id", "bucket", "child", "child_id" };

            mappingStrategy.setColumnMapping(columns);

            BeanToCsv.write(mappingStrategy, this.csvWriter, listingList);

            return "ListingCsvWriter Task Done";
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
        return "ListingCsvWriter Task Done";
    }
}
