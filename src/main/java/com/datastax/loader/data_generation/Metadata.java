package com.datastax.loader.data_generation;

public class Metadata
{
    String id;
    String doc;

    public Metadata(String id, String doc)
    {
        this.id = id;
        this.doc = doc;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDoc()
    {
        return this.doc;
    }

    public void setDoc(String doc)
    {
        this.doc = doc;
    }
}
