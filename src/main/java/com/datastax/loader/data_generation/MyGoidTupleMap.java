package com.datastax.loader.data_generation;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by chivora on 5/23/2017.
 */
public class MyGoidTupleMap<K,V> extends HashMap {

    public String toString() {

        Iterator<Entry<K, V>> i = entrySet().iterator();
        if (!i.hasNext())
            return "()";

        StringBuilder sb = new StringBuilder();
        sb.append("\\");
        sb.append("\"");
        sb.append('(');
        for (; ; ) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append(',');
            sb.append(value == this ? "(this Map)" : value);
            if (!i.hasNext()){
                sb.append(')');
                sb.append("\\");
                return sb.append("\"").toString();
            }
            sb.append(", ");
        }
    }
}