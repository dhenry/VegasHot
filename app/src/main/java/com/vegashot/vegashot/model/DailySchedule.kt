package com.vegashot.vegashot.model

import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable
import java.util.ArrayList

public class DailySchedule(var date: String, var classes : ArrayList<Clazz> = ArrayList<Clazz>()) : Externalizable {

    override fun readExternal(input: ObjectInput) {
        date = input.readObject() as String
        classes = input.readObject() as ArrayList<Clazz>

    }

    override fun writeExternal(output: ObjectOutput) {
        output.writeObject(date)
        output.writeObject(classes)
    }
}
