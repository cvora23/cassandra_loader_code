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

public class LocksCsvWriter
        implements Callable<String>
{
    private String myName;
    private int count;
    private final long timeSleep;
    static final String FILEPATH_WRITE = "CosLocks.csv";
    static final int num = 10000;
    public static final int MIN_CHAR_LEN = 10;
    public static final int MAX_CHAR_LEN = 25;
    CSVWriter csvWriter = null;
    DataFactory df = new DataFactory();

    LocksCsvWriter(String name, int newcount, long newtimeSleep)
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
            this.csvWriter = new CSVWriter(new FileWriter("CosLocks.csv", true), ',', '"', '\000');

            List locksList = new ArrayList();
            for (int i = 0; i < 10000; i++)
            {
                String id = this.df.getRandomText(10, 25);
                String owner = this.df.getRandomText(10, 25);
                Locks locks = new Locks(id, owner);
                locksList.add(locks);
            }
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();

            mappingStrategy.setType(Locks.class);

            String[] columns = { "id", "owner" };

            mappingStrategy.setColumnMapping(columns);

            BeanToCsv.write(mappingStrategy, this.csvWriter, locksList);

            return "LocksCsvWriter Task Done";
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
        return "LocksCsvWriter Task Done";
    }
}
