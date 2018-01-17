/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.exception.DateParsingException;
import com.igate.iconnect.util.COMMON_AppContext;

public class CustomDateFormatConstants {

    public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(
            "yyyy-MM-dd");
    

    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_ECT_MASTER = new SimpleDateFormat(
            "MM-dd-yyyy HH:mm:ss");    
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat MM_DD_YYYY_HH_MM_ss = new SimpleDateFormat(
            "MM/dd/yyyy HH:mm:ss");
    public static final SimpleDateFormat MM_DD_YYYY_HH_MM_AMORPM = new SimpleDateFormat(
            "MM/dd/yyyy HH:mm a");

	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_IST_TECHCR = new SimpleDateFormat(
    "MM/dd/yyyy HH:mm");
	
	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_IST_CompareDt = new SimpleDateFormat(
    "MM/dd/yyyy HH:mm:ss");

    private static List<String> dataType = new ArrayList<String>();
    public static String[] MONTHS = { "January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December" };

    // Commented out since the timings in the entire application was updated to EST from IST
    /*public static String convertToIST(String gmtDate) {

        if (!gmtDate.equalsIgnoreCase("-") && !gmtDate.equalsIgnoreCase("")) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ist_date = null;
            try {
                ist_date = sd.parse(gmtDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(ist_date);
            cal.add(Calendar.HOUR_OF_DAY, 5);
            cal.add(Calendar.MINUTE, 30);
            ist_date = cal.getTime();
            SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            return sd1.format(ist_date) + " IST";
        } else
            return "-";
    }*/
    
    public static String convertToEST(String gmtDate) {

        if (!gmtDate.equalsIgnoreCase("-") && !gmtDate.equalsIgnoreCase("")) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date est_date = null;
            try {
                est_date = sd.parse(gmtDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(est_date);
            cal.add(Calendar.HOUR_OF_DAY, -4);
            cal.add(Calendar.MINUTE, 0);
            est_date = cal.getTime();
            SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            return sd1.format(est_date) + " EST";
        } else
            return "-";
    }

    public static String showUserTimeZonewithRequest(String date,
            HttpServletRequest request) throws DateParsingException {

        String userLoginId = (String) request.getSession().getAttribute(
                "userLoginId");
        User userBean = (User) request.getSession().getAttribute(
                userLoginId);
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        Map<String, Object> timezone = MasterDataImpl.getTimeZone(Integer
                .parseInt(userBean.getTimeZoneId()));

        TimeZone zone = TimeZone.getTimeZone((String) timezone
                .get("INTERNAL_FORMAT"));

        Calendar calTZ = new GregorianCalendar(zone);
        Date dt = null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            dt = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DateParsingException("Date "+date+"can't be parsed.");
        }

        calTZ.setTime(dt);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
        if (String.valueOf(timezone.get("DAYLIGHT_APPL")).equalsIgnoreCase("Y")) {
            cal.add(Calendar.MINUTE, Integer.parseInt(timezone.get(
                    "DAYLIGHT_VALUE").toString()));
        }
        SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

        return sd1.format(cal.getTime()) + " "
                + timezone.get("DISPLAY_FORMAT").toString();
    }

    public static String showUserTimeZonewithTimezoneID(String date,
            int timezoneID) {

        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        Map<String, Object> timezone = MasterDataImpl.getTimeZone(timezoneID);

        TimeZone zone = TimeZone.getTimeZone((String) timezone
                .get("INTERNAL_FORMAT"));

        Calendar calTZ = new GregorianCalendar(zone);
        Date dt = null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            dt = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new DateParsingException("Date "+date+"can't be parsed");
        }

        calTZ.setTime(dt);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
        if (String.valueOf(timezone.get("DAYLIGHT_APPL")).equalsIgnoreCase("Y")) {
            cal.add(Calendar.MINUTE, Integer.parseInt(timezone.get(
                    "DAYLIGHT_VALUE").toString()));
        }
        SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

        return sd1.format(cal.getTime()) + " "
                + timezone.get("DISPLAY_FORMAT").toString();
    }

    public static String creationDateGMTFormat() {
        SimpleDateFormat YYYY_MM_DD_HH_MM_SS_GMT = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        YYYY_MM_DD_HH_MM_SS_GMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return YYYY_MM_DD_HH_MM_SS_GMT.format(new Date());
    }

    public static String getFileTypeExtension(String getFileName) {
        int mid = getFileName.lastIndexOf(".");
        String fname = getFileName.substring(0, mid);
        String ext = getFileName.substring(mid + 1, getFileName.length());
        return ext.toUpperCase();
    }

    public static List<String> getDataType() {
        if (dataType.size() > 0)
            return dataType;
        else {
            dataType.add("A6P");
            dataType.add("AC");
            dataType.add("AS");
            dataType.add("ACR");
            dataType.add("ACTION");
            dataType.add("AIR");
            dataType.add("APP");
            dataType.add("APP");
            dataType.add("JAVA");
            dataType.add("AWK");
            dataType.add("BAT");
            dataType.add("CGI");
            dataType.add("CMD");
            dataType.add("COM");
            dataType.add("CSS");
            dataType.add("CSH");
            dataType.add("DEK");
            dataType.add("DLD");
            dataType.add("DS");
            dataType.add("EBM");
            dataType.add("ESH");
            dataType.add("EXE");
            dataType.add("EZS");
            dataType.add("FKY");
            dataType.add("FRS");
            dataType.add("FXP");
            dataType.add("GADGET");
            dataType.add("HMS");
            dataType.add("HTA");
            dataType.add("ICD");
            dataType.add("INX");
            dataType.add("IPF");
            dataType.add("ISU");
            dataType.add("JAR");
            dataType.add("JS");
            dataType.add("JSE");
            dataType.add("JSX");
            dataType.add("KIX");
            dataType.add("LUA");
            dataType.add("MCR");
            dataType.add("MEM");
            dataType.add("MPX");
            dataType.add("MS");
            dataType.add("MST");
            dataType.add("OBS");
            dataType.add("PAF");
            dataType.add("PEX");
            dataType.add("PIF");
            dataType.add("PRC");
            dataType.add("PRG");
            dataType.add("PVD");
            dataType.add("PWC");
            dataType.add("PY");
            dataType.add("PYC");
            dataType.add("PYO");
            dataType.add("QPX");
            dataType.add("RBX");
            dataType.add("RGS");
            dataType.add("ROX");
            dataType.add("RPJ");
            dataType.add("SCAR");
            dataType.add("SCR");
            dataType.add("SCRIPT");
            dataType.add("SCT");
            dataType.add("SHB");
            dataType.add("SHS");
            dataType.add("SPR");
            dataType.add("TLB");
            dataType.add("TMS");
            dataType.add("U3P");
            dataType.add("UDF");
            dataType.add("VB");
            dataType.add("VBE");
            dataType.add("VBS");
            dataType.add("VBSCRIPT");
            dataType.add("WCM");
            dataType.add("WPK");
            dataType.add("WS");
            dataType.add("WSF");
            dataType.add("XQT");
            dataType.add("XPS");
            return dataType;
        }
    }

	public static String creationDateISTFormatForTechCR() {       
		Calendar cal = Calendar.getInstance();     
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");     
		String strDate = sdf.format(cal.getTime());     
		return strDate;
    }
	
	public static String creationDateISTFormat_iTrack() {
        SimpleDateFormat YYYY_MM_DD_HH_MM_SS_GMT = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        YYYY_MM_DD_HH_MM_SS_GMT.setTimeZone(TimeZone.getTimeZone("IST"));
        return YYYY_MM_DD_HH_MM_SS_GMT.format(new Date());
    }

}
