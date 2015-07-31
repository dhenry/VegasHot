package com.vegashot.vegashot.model

import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable

public class Clazz(var time: String, var name: String, var instructor: String) : Externalizable {

    override fun readExternal(input: ObjectInput) {
        time = input.readObject() as String
        name = input.readObject() as String
        instructor = input.readObject() as String

    }

    override fun writeExternal(output: ObjectOutput) {
        output.writeObject(time)
        output.writeObject(name)
        output.writeObject(instructor)
    }
}
