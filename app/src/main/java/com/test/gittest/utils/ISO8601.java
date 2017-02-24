package com.test.gittest.utils;

/**
 * Created by F on 24/02/17.
 * This is a modified class from
 * http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Helper class for handling a most common subset of ISO 8601 strings
 * (in the following format: "2008-03-01T13:00:00+01:00"). It supports
 * parsing the "Z" timezone, but many other less-used features are
 * missing.
 */
public final class ISO8601 {

    private static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    /**
     * Transform Calendar to ISO 8601 string.
     */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat(ISO8601_PATTERN, Locale.ENGLISH)
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Get current date and time formatted as ISO 8601 string.
     */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Calendar toCalendar(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+0000");
        Date date = new SimpleDateFormat(ISO8601_PATTERN, Locale.ENGLISH).parse(s);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Transform ISO 8601 string to Date object
     */
    public static Date toDate(final String iso8601string) throws ParseException {
        String s = iso8601string.replace("Z", "+0000");
        Date date = new SimpleDateFormat(ISO8601_PATTERN, Locale.ENGLISH).parse(s);
        return date;
    }

    /**
     * Transform ISO 8601 string to milliseconds
     */
    public static long toMilliseconds(final String iso8601string) throws ParseException {
        String s = iso8601string.replace("Z", "+0000");
        Date date = new SimpleDateFormat(ISO8601_PATTERN, Locale.ENGLISH).parse(s);
        return date.getTime();
    }
}