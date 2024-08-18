package com.example.easyfood.roomDB

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeCoverter {

    @TypeConverter
    fun fromAnythingToString(value: Any?): String {
        if (value == null) {
            return ""
        }
        return value.toString()
    }

    @TypeConverter
    fun fromStringToAnything(value: String): Any {
        if (value == null) {
            return ""
        }
        return value
    }

}