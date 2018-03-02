package com.datastax.loader.data_generation;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class CosCSVWriterExample
{
    public static final int MAX_TASKS = 4;
    public static final int MAX_THREADS = 1;
    private static Future taskList = null;
    private static int num;

    public static void main(String[] args)
            throws Exception
    {
        if (args.length < 1)
        {
            System.out.println("Choosing default number of entries to be created = 10000");
            num = 10;
        }
        for (int i = 0; i < args.length; i++) {
            num = Integer.parseInt(args[i]);
        }
        System.out.println(LocalDateTime.now());
        if (num < 10000)
        {
            System.out.println("Chosing default number of entries to be created = 10000");
            num = 10;
        }
        System.out.println("Number of entries = " + num);

        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        CompletionService<String>[] pool = new ExecutorCompletionService[MAX_TASKS];
        for (int i = 0; i < pool.length; i++) {
            pool[i] = new ExecutorCompletionService(threadPool);
        }
        for (int i = 0; i < Math.ceil(num / 10); i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    pool[j].submit(new GoidCsvWriter("GoidCsvWriter Task", 3, 200L));
                } else if (j == 1) {
                    pool[j].submit(new ListingCsvWriter("ListingCsvWriter Task", 3, 200L));
                } else if (j == 2) {
                    pool[j].submit(new LocksCsvWriter("LocksCsvWriter Task", 3, 200L));
                } else if (j == 3) {
                    pool[j].submit(new MetadataCsvWriter("MetadataCsvWriter Task", 3, 200L));
                }
            }
        }
        for (int i = 0; i < Math.ceil(num / 10); i++) {
            for (int j = 0; j < 4; j++)
            {
                String result = (String)pool[j].take().get();
                System.out.println("Result = " + result);
            }
        }
        threadPool.shutdown();
        System.out.println("-----------------------");

        threadPool.awaitTermination(1L, TimeUnit.SECONDS);
        System.out.println("All tasks are finished!");
        System.out.println(LocalDateTime.now());
    }
}
