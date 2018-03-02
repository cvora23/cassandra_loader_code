package com.datastax.loader.data_generation;

import java.util.Map;
import java.util.Set;

/**
 * Created by chivora on 5/22/2017.
 */
public class Goid {

    private String goid;
    private String id;
    private Boolean isOpen;
    private Map<String,Map<Integer,Long>> objects;
    private Set<Integer> resgrp;

    public Goid(String goid, String id, Boolean isOpen, Map<String, Map<Integer, Long>> objects, Set<Integer> resgrp) {
        this.goid = goid;
        this.id = id;
        this.isOpen = isOpen;
        this.objects = objects;
        this.resgrp = resgrp;
    }

    public String getGoid() {
        return goid;
    }

    public void setGoid(String goid) {
        this.goid = goid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsopen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getIsopen() {
        return isOpen;
    }

    public Map<String, Map<Integer, Long>> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Map<Integer, Long>> objects) {
        this.objects = objects;
    }

    public Set<Integer> getResgrp() {
        return resgrp;
    }

    public void setResgrp(Set<Integer> resgrp) {
        this.resgrp = resgrp;
    }
}
