package com.datastax.loader.data_generation;

public class Listing
{
    String id;
    int bucket;
    String child;
    String child_id;

    public Listing(String id, int bucket, String child, String child_id)
    {
        this.id = id;
        this.bucket = bucket;
        this.child = child;
        this.child_id = child_id;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getBucket()
    {
        return this.bucket;
    }

    public void setBucket(int bucket)
    {
        this.bucket = bucket;
    }

    public String getChild()
    {
        return this.child;
    }

    public void setChild(String child)
    {
        this.child = child;
    }

    public String getChild_id()
    {
        return this.child_id;
    }

    public void setChild_id(String child_id)
    {
        this.child_id = child_id;
    }
}
