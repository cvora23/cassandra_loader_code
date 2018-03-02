package com.datastax.loader.data_generation;

public class Locks
{
    String id;
    String owner;

    public Locks(String id, String owner)
    {
        this.id = id;
        this.owner = owner;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }
}
