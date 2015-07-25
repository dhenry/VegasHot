package com.vegashot.vegashot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailySchedule implements Serializable {
    public final String date;
    private List<Clazz> classes;

    public DailySchedule(String date) {
        this.date = date;
    }

    public List<Clazz> getClasses() {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        return classes;
    }
}
