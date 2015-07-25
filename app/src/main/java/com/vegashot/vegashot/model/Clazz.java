package com.vegashot.vegashot.model;

import java.io.Serializable;

public class Clazz implements Serializable {
    public final String instructor;
    public final String name;
    public final String time;

    public Clazz(String time, String name, String instructor) {
        this.time = time;
        this.name = name;
        this.instructor = instructor;
    }
}
