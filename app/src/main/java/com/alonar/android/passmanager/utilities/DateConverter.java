package com.alonar.android.passmanager.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    /**
     * Returns given date as string
     */
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        return dateFormat.format(date);
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
