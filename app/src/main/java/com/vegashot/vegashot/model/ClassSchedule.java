package com.vegashot.vegashot.model;

import java.io.Serializable;
import java.util.List;

public class ClassSchedule implements Serializable {
    public final List<DailySchedule> dailySchedules;

    public ClassSchedule(List<DailySchedule> dailySchedules) {
        this.dailySchedules = dailySchedules;
    }
}
