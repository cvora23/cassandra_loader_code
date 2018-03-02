package com.datastax.loader.data_generation;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.fluttercode.datafactory.impl.DataFactory;

import java.io.FileWriter;
import java.util.*;

/**
 * Created by chivora on 5/22/2017.
 */
public class OpenCSVWriterExample {

    public static final int MIN_CHAR_LEN = 10;
    public static final int MAX_CHAR_LEN = 25;
    public static final int MAX_OBJECTS = 30;
    public static final int MAX_RESGRPS = 5;

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void generateGoidData(int num){

        CSVWriter csvWriter = null;
        DataFactory df = new DataFactory();

        try
        {
            //Create CSVWriter for writing to Employee.csv
            csvWriter = new CSVWriter(new FileWriter("Employee.csv"),',',CSVWriter.DEFAULT_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER);


            //Add Goid objects to a list
            List goidList = new ArrayList();

            for(int i=0;i<num;i++){

                System.out.println("COUNTER = " + i);

                // Creating Objects
                MyGoidHashMap<String,Map<Integer,Long>> objects = new MyGoidHashMap<String, Map<Integer, Long>>();

                int maxObjects = getRandomNumberInRange(1,MAX_OBJECTS);

                // Adding Objects
                for(int j=0;j<maxObjects;j++){
                    int innerObjectKey = df.getNumberBetween(0, Integer.MAX_VALUE);
                    long innterObjectVal = df.getNumberBetween(0, Integer.MAX_VALUE);
                    MyGoidTupleMap<Integer,Long> innerObjects = new MyGoidTupleMap<Integer, Long>();
                    innerObjects.put(innerObjectKey,innterObjectVal);
                    String myGoidHashMapKey = df.getRandomText(MIN_CHAR_LEN,MAX_CHAR_LEN);
                    objects.myGoidHashMapPut(myGoidHashMapKey,innerObjects);
                }
                //System.out.println(mapToString(objects));
                //System.out.println(objects);

                int maxResGrps = getRandomNumberInRange(1,MAX_RESGRPS);;
                Set<Integer> resgrp = new MyHashSet<Integer>();
                for(int k=0;k<maxResGrps;k++){
                    int resGrp = df.getNumberBetween(0, Integer.MAX_VALUE);
                    resgrp.add(resGrp);
                }

                String goid = df.getRandomText(MIN_CHAR_LEN,MAX_CHAR_LEN);
                String id = df.getRandomText(MIN_CHAR_LEN,MAX_CHAR_LEN);
                // Creating Goid
                Goid goid1 = new Goid(goid,id,true,objects,resgrp);

                goidList.add(goid1);
            }

            //mapping of columns with their positions
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            //Set mappingStrategy type to Employee Type
            mappingStrategy.setType(Goid.class);
            //Fields in Employee Bean
            String[] columns = new String[]{"goid","id","isOpen","objects","resgrp"};
            //Setting the colums for mappingStrategy
            mappingStrategy.setColumnMapping(columns);
            //Writing empList to csv file
            BeanToCsv.write(mappingStrategy,csvWriter,goidList);
            System.out.println("CSV File written successfully!!!");

        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                //closing the writer
                csvWriter.close();
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
        }

    }

    public static void main(String [] args) throws Exception{
        generateGoidData(1);
    }



}
