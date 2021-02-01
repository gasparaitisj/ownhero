package com.gasparaiciukas.ownhero;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Time;

// Formats date and time into YYYY-MM-DD XX:XX:XX formats
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Time timeFromTimestamp(Long value) {
        return value == null ? null : new Time(value);
    }

    @TypeConverter
    public static Long timeToTimestamp(Time time) {
        return time == null ? null : time.getTime();
    }
}
