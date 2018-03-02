package com.datastax.loader.data_generation;

/**
 * Created by root on 5/24/17.
 */
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by chivora on 5/23/2017.
 */
public class MyGoidHashMap <K,V> extends HashMap {

    public String toString() {

        Iterator<Entry<K, V>> i = entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append(':');
            sb.append(value == this ? "(this Map)" : value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(", ");
        }
    }

    public Object myGoidHashMapPut(String key,HashMap<Integer,Long> value){
        if(key != null){
            String lKey = key;
            lKey = "\\" + "\"" + key + "\\" + "\"";
            //objects.myGoidHashMapPut("\\\"abcd\\\"",innerObjects);
            return put(lKey,value);
        }
        return null;
    }

}

