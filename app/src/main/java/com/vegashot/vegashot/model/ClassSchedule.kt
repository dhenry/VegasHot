package com.vegashot.vegashot.model

import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable

public class ClassSchedule(var dailySchedules: List<DailySchedule>) : Externalizable {
    override fun readExternal(input: ObjectInput) {
        dailySchedules = input.readObject() as List<DailySchedule>
    }

    override fun writeExternal(output: ObjectOutput) {
        output.writeObject(dailySchedules)
    }
}
