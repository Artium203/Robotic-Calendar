package org.example.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataContainer {
    private Map<Integer, List<Integer>> actions;
    private List<Integer> dateATime;
    private String nameOf;

    public DataContainer(List<Integer> list, Map<Integer, List<Integer>> map,String name){
        this.actions= map;
        this.dateATime=list;
        this.nameOf = name;
    }
    public Map<Integer, List<Integer>> getActions() {
        return actions;
    }

    public void setActions(Map<Integer, List<Integer>> actions) {
        this.actions = actions;
    }

    public List<Integer> getDateATime() {
        return dateATime;
    }

    public void setDateATime(List<Integer> dateATime) {
        this.dateATime = dateATime;
    }

    public String getNameOf() {
        return nameOf;
    }

    public void setNameOf(String nameOf) {
        this.nameOf = nameOf;
    }
}
