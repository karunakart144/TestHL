/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StringToDateConverter {

    public static java.sql.Date getGMTDateForStorage() throws ParseException {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(
                "yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(
                "yyyy-MMM-dd HH:mm:ss");

        // Time in GMT
        return new java.sql.Date(dateFormatLocal.parse(
                dateFormatGmt.format(new Date())).getTime());
    }
}
