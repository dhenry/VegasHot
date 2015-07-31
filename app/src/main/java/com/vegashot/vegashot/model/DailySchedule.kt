package com.vegashot.vegashot.model

import java.io.Serializable
import java.util.ArrayList

public class DailySchedule(val date: String, val classes : List<Clazz> = ArrayList<Clazz>()) : Serializable
