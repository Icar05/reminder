package com.example.icar.my_notification.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icar on 08.03.17.
 */
public class TimeHelper {

    public static String formatDateToString( long timeToFormat) {

        Date date = new Date(timeToFormat); // *1000 is to convert seconds to milliseconds

        SimpleDateFormat sdf = new SimpleDateFormat(" d MMMM yyyy"); // the format of your date
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


//    public static String formatDateToString( long timeToFormat) {
//
//        Date date = new Date(timeToFormat); // *1000 is to convert seconds to milliseconds
//
//
//
//        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
//        curFormater.setTimeZone(TimeZone.getDefault());
//
//
//        String formattedDate = curFormater.format(date);
//        return formattedDate;
//    }


}
