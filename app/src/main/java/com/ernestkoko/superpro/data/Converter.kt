package com.ernestkoko.superpro.data

import androidx.room.TypeConverter
import java.util.*

class Converter{
    //converts Long to date
    @TypeConverter
    fun fromTimeToDate(value: Long?): Date?{
        return value?.let { Date(it) }
    }
    //converts Date to Long
    @TypeConverter
    fun dateToTime(date: Date?): Long?{
        return date?.time?.toLong()
    }
}