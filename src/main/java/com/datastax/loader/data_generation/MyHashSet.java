package com.datastax.loader.data_generation;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by chivora on 5/23/2017.
 */
public class MyHashSet <E> extends HashSet {

    public String toString() {

        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }

    }
}
