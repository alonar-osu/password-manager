package com.alonar.android.passmanager.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    /**
     * Returns given date as string
     */
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        return dateFormat.format(date);
    }

}
