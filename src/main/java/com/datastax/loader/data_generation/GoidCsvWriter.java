package com.datastax.loader.data_generation;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.fluttercode.datafactory.impl.DataFactory;

import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.Callable;

public class GoidCsvWriter
        implements Callable<String>
{
    private String myName;
    private int count;
    private final long timeSleep;
    static final String FILEPATH_WRITE = "CosGoid.csv";
    static final int num = 10000;
    public static final int MIN_CHAR_LEN = 10;
    public static final int MAX_CHAR_LEN = 25;
    public static final int MAX_OBJECTS = 30;
    public static final int MAX_RESGRPS = 5;
    CSVWriter csvWriter = null;
    DataFactory df = new DataFactory();

    GoidCsvWriter(String name, int newcount, long newtimeSleep)
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
            this.csvWriter = new CSVWriter(new FileWriter("CosGoid.csv", true), ',', '"', '\000');

            List goidList = new ArrayList();
            for (int i = 0; i < 10; i++)
            {
                MyGoidHashMap<String, Map<Integer, Long>> objects = new MyGoidHashMap();

                int maxObjects = getRandomNumberInRange(1, 30);
                for (int j = 0; j < maxObjects; j++)
                {
                    int innerObjectKey = this.df.getNumberBetween(0, Integer.MAX_VALUE);
                    long innterObjectVal = this.df.getNumberBetween(0, Integer.MAX_VALUE);
                    MyGoidTupleMap<Integer, Long> innerObjects = new MyGoidTupleMap();
                    innerObjects.put(Integer.valueOf(innerObjectKey), Long.valueOf(innterObjectVal));
                    String myGoidHashMapKey = this.df.getRandomText(10, 25);
                    objects.myGoidHashMapPut(myGoidHashMapKey, innerObjects);
                }
                int maxResGrps = getRandomNumberInRange(1, 5);
                Set<Integer> resgrp = new MyHashSet();
                for (int k = 0; k < maxResGrps; k++)
                {
                    int resGrp = this.df.getNumberBetween(0, Integer.MAX_VALUE);
                    resgrp.add(Integer.valueOf(resGrp));
                }
                String goid = this.df.getRandomText(10, 25);
                String id = this.df.getRandomText(10, 25);

                Goid goid1 = new Goid(goid, id, Boolean.valueOf(true), objects, resgrp);

                goidList.add(goid1);
            }
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();

            mappingStrategy.setType(Goid.class);

            String[] columns = { "goid", "id", "isOpen", "objects", "resgrp" };

            mappingStrategy.setColumnMapping(columns);

            BeanToCsv.write(mappingStrategy, this.csvWriter, goidList);

            return "GoidCsvWriter Task Done";
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
        return "GoidCsvWriter Task Done";
    }
}
