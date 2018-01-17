/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_SlaDAO;

public class HELPDESK_SlaPopulator {
	private static Logger log = Logger.getLogger(HELPDESK_SlaPopulator.class);

	public static Date getDate(String current_date_string, SimpleDateFormat sd) {
		Date current_date = null;
		try {
			current_date = sd.parse(current_date_string);
		} catch (ParseException e) {
			log.error("Date parse exception in SLA populator: " + e);
			/* e.printStackTrace(); */
		}
		return current_date;
	}

	public static long getTimeDuration(String current_date_string,
			String ect_date_string, long location_id, long sla_id,
			int total_time_taken_for_response_in_minute) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
				.getSLAExclusionDateList(sla_id, location_id);
		List<Map<String, Object>> slaOperatingTimeList = MasterDataImpl
				.getSLAOperatingTimeList(sla_id);
		long time_duartion = 0;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
		Date current_date = getDate(current_date_string, sd);
		Date ect_date = getDate(ect_date_string, sd);
		Calendar currentCal = Calendar.getInstance();
		currentCal.setTime(current_date);
		currentCal.add(Calendar.HOUR_OF_DAY, 5);
		currentCal.add(Calendar.MINUTE, 30);
		current_date = currentCal.getTime();
		Calendar ectCal = Calendar.getInstance();
		ectCal.setTime(ect_date);
		// Added by sali on April 13th to modify the alogirthm
		// ectCal.add(Calendar.MINUTE, total_time_taken_for_response_in_minute);
		ect_date = getNewECT(total_time_taken_for_response_in_minute, ectCal
				.getTime(), sla_id, location_id);
		ectCal.setTime(ect_date);
		long working_time_in_minute = 0;
		String holiday = "";
		long holiday_working_time_in_minute = 0;
		while (ect_date.after(current_date)
				|| sd1.format(current_date).equalsIgnoreCase(
						sd1.format(ect_date))) {
			for (Map<String, Object> stringObj : slaOperatingTimeList) {
				working_time_in_minute = working_time_in_minute
						+ getWorkingTime(stringObj, currentCal, ectCal);
			}

			for (Map<String, Object> stringObj : slaExclusionDateList) {
				holiday = stringObj.get("DATE").toString();
				if (sd1.format(current_date).equalsIgnoreCase(holiday)) {
					try {
						holiday_working_time_in_minute = getHolidayWorkingTime(
								slaOperatingTimeList, holiday);
					} catch (ParseException e) {

						e.printStackTrace();
					}
				}
			}
			currentCal.add(Calendar.DATE, 1);
			currentCal.set(Calendar.HOUR_OF_DAY, 0);
			currentCal.set(Calendar.MINUTE, 0);
			current_date = currentCal.getTime();
		}
		time_duartion = working_time_in_minute - holiday_working_time_in_minute;
		return time_duartion;
	}

	private static Date getNewECT(long limit, Date ect_date, long opr_id,
			long location_id) {
		Date ectDate = null;
		Calendar estimatedCompTimeCal = Calendar.getInstance();
		estimatedCompTimeCal.setTime(ect_date);
		int minute = 0;
		int hour = 0;
		int second = 0;
		String day = "";
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> sla_time_list = MasterDataImpl
				.getSLAOpertingTimeOrgWise(opr_id, "C",
						getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
		String start_working_time_string = "";
		String end_working_time_string = "";
		for (Map<String, Object> stringObj : sla_time_list) {
			start_working_time_string = stringObj.get("START_TIME").toString();
			end_working_time_string = stringObj.get("END_TIME").toString();
		}
		int start_working_hour = 0;
		int end_working_hour = 0;
		int start_working_minute = 0;
		int end_working_minute = 0;
		if (!start_working_time_string.equalsIgnoreCase("")) {
			start_working_hour = Integer.parseInt(start_working_time_string
					.substring(0, 2));
			start_working_minute = Integer.parseInt(start_working_time_string
					.substring(3, 5));
		}
		if (!end_working_time_string.equalsIgnoreCase("")) {
			end_working_hour = Integer.parseInt(end_working_time_string
					.substring(0, 2));
			end_working_minute = Integer.parseInt(end_working_time_string
					.substring(3, 5));
		}
		List<Map<String, Object>> sla_op_time_list = MasterDataImpl
				.getSLAOperatingTimeList(opr_id);
		Calendar currentcal = Calendar.getInstance();
		currentcal.setTime(ect_date);
		Calendar endcal = Calendar.getInstance();
		endcal.setTime(ect_date);
		Calendar startcal = Calendar.getInstance();
		startcal.setTime(ect_date);
		int remainingdiff = 0;
		while (limit != 0) {
			hour = estimatedCompTimeCal.get(Calendar.HOUR_OF_DAY);
			minute = estimatedCompTimeCal.get(Calendar.MINUTE);
			second = estimatedCompTimeCal.get(Calendar.SECOND);
			day = getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)).trim()
					.toUpperCase();

			boolean working_day = false;
			for (Map<String, Object> stringObj : sla_op_time_list) {
				if (day.equalsIgnoreCase(stringObj.get("DAY").toString().trim()
						.toUpperCase())) {
					working_day = true;
					break;
				}

			}
			boolean holiday_check = false;
			String holiday = "";
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

			List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
					.getSLAExclusionDateList(opr_id, location_id);
			Date start_date = estimatedCompTimeCal.getTime();
			for (Map<String, Object> stringObj : slaExclusionDateList) {
				holiday = stringObj.get("DATE").toString();
				if (sd.format(start_date).equalsIgnoreCase(holiday)) {
					holiday_check = true;
				}
			}
			// if working day and not holiday
			if (working_day && !holiday_check) {
				if (start_working_hour > end_working_hour)// Startdate and
				// end
				// date are
				// different
				{
					Calendar dateCal = Calendar.getInstance();
					dateCal.setTime(new Date());
					dateCal.add(Calendar.DATE, 1);
					currentcal.setTime(new Date());
					endcal.setTime(dateCal.getTime());
				}// Tofind the time difference
				currentcal.set(Calendar.HOUR_OF_DAY, hour);
				currentcal.set(Calendar.MINUTE, minute);
				currentcal.set(Calendar.SECOND, second);
				endcal.set(Calendar.HOUR_OF_DAY, end_working_hour);
				endcal.set(Calendar.MINUTE, end_working_minute);
				endcal.set(Calendar.SECOND, 0);
				startcal.set(Calendar.HOUR_OF_DAY, start_working_hour);
				startcal.set(Calendar.MINUTE, start_working_minute);
				startcal.set(Calendar.SECOND, 0);
				int timeLeftMin = 0;
				if (start_working_hour < end_working_hour) {
					if (hour >= start_working_hour && hour <= end_working_hour) {
						timeLeftMin = (int) getTimeDiff(endcal, currentcal);
					} else {
						timeLeftMin = (int) getTimeDiff(endcal, startcal);
					}

				} else {

					timeLeftMin = (int) getTimeDiff(endcal, currentcal);
				}
				if (timeLeftMin >= limit) {
					/*
					 * estimatedCompTimeCal.setTimeInMillis(estimatedCompTimeCal
					 * .getTimeInMillis() + (limit * 1000));
					 */
					estimatedCompTimeCal.add(Calendar.MINUTE, Integer
							.parseInt(String.valueOf(limit)));
					limit = 0;
					hour = estimatedCompTimeCal.get(Calendar.HOUR_OF_DAY);
					remainingdiff = (24 - end_working_hour)
							+ start_working_hour;
					if (hour == end_working_hour && minute > end_working_minute)

						estimatedCompTimeCal
								.setTimeInMillis(estimatedCompTimeCal
										.getTimeInMillis()
										+ (remainingdiff * 60 * 60 * 1000));
				} else {
					if (end_working_hour > start_working_hour) {
						remainingdiff = (24 - end_working_hour)
								+ start_working_hour;
					} else {
						remainingdiff = start_working_hour - end_working_hour;

					}
					estimatedCompTimeCal.add(Calendar.DATE, 1);
					estimatedCompTimeCal.set(Calendar.HOUR_OF_DAY,
							start_working_hour);
					estimatedCompTimeCal.set(Calendar.MINUTE,
							start_working_minute);
					estimatedCompTimeCal.set(Calendar.SECOND, 0);
					limit -= timeLeftMin;

				}
			}// if working day
			else {
				estimatedCompTimeCal.add(Calendar.DATE, 1);

			}

		}// while
		ectDate = estimatedCompTimeCal.getTime();
		return ectDate;
	}

	public static String getSLAStatus(long ticket_id, long menu_id,
			long sla_id, long location_id, Date created_date_gmt,
			Date ect_date_gmt, long sla_time) throws ParseException {
		Calendar createcal = Calendar.getInstance();
		createcal.setTime(created_date_gmt);
		createcal.add(Calendar.HOUR_OF_DAY, 5);
		createcal.add(Calendar.MINUTE, 30);
		// Date created_date = createcal.getTime();

		Calendar ectcal = Calendar.getInstance();
		ectcal.setTime(ect_date_gmt);
		ectcal.add(Calendar.HOUR_OF_DAY, 5);
		ectcal.add(Calendar.MINUTE, 30);
		Date ect_date = ectcal.getTime();
		//
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_SlaDAO slaDAO = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
		Date closed_date = new Date();
		long total_time_taken_for_response_in_minute = 0l;
		String slaStaus = "NA";
		// responseTimeMap stores the responsetime from the auditlog.
		Map<String, Long> responseTimeMap = new HashMap<String, Long>();

		// Condition : if closed date is before ect date then slaStaus is IN.
		if (!closed_date.after(ect_date)) {
			slaStaus = "IN";
		} else {// if closed date is after ect date
			// auditLogList contains the history of the ticket.
			List<Map<String, Object>> auditLogList = slaDAO.getAuditLog(String
					.valueOf(ticket_id), menu_id);

			responseTimeMap = getResponseTimeMap(auditLogList, responseTimeMap);

			// total_time_taken_for_response_in_minute : The time spent for
			// getting response from user,client and suspended time.
			total_time_taken_for_response_in_minute = responseTimeMap
					.get("total_time_taken_for_response_in_minute");
			// total_response_time_in_minute : Total of response time from
			// the auditlog for a ticket.
			long total_response_time_in_minute = responseTimeMap
					.get("total_response_time");

			// closed_response_time_in_minute : Response time for closure of
			// the ticket from the last entry in the auditlog for a ticket.
			long closed_response_time_in_minute = getClosedResponseTime(String
					.valueOf(ticket_id), slaDAO, auditLogList, closed_date,
					sla_id, location_id);

			// time_spent_by_executive_in_minute : Time spent by executive
			// in the ticket.
			long time_spent_by_executive_in_minute = total_response_time_in_minute
					+ closed_response_time_in_minute
					- total_time_taken_for_response_in_minute;
			// Condition : if time_spent_by_executive_in_minute is less than
			// SLA TIME,then slaStatus is IN.else slaStatus is OUT.

			if (time_spent_by_executive_in_minute < sla_time) {
				slaStaus = "IN";
			} else {
				slaStaus = "OUT";
			}
		}
		return slaStaus;
	}

	/**
	 * getResponseTimeMap() method will return a map which contains user
	 * response time ,client response time , suspended time and total of
	 * response time from the auditlog for a ticket. Arguments passed :
	 * auditLogList which contains the history of the ticket. responseTimeMap in
	 * which response time has to be stored.
	 */
	public static Map<String, Long> getResponseTimeMap(
			List<Map<String, Object>> auditLogList,
			Map<String, Long> responseTimeMap) {
		long total_response_time_in_minute = 0l;
		ResourceBundle bundle = ResourceBundle.getBundle("sla");
		String sla_stop_states = bundle.getString("slastop_state");
		String[] sla_stop_list = sla_stop_states.split(",");
		long total_time_taken_for_response_in_minute = 0l;

		for (String aSla_stop_list : sla_stop_list) {
			for (Map<String, Object> stringObj : auditLogList) {
				total_time_taken_for_response_in_minute = getResponseTime(
						stringObj, aSla_stop_list,
						total_time_taken_for_response_in_minute);
			}

		}
		for (Map<String, Object> stringObj : auditLogList) {
			total_response_time_in_minute = getResponseTime(stringObj,
					"TOTALRESPONSE", total_response_time_in_minute);

		}
		responseTimeMap.put("total_time_taken_for_response_in_minute",
				total_time_taken_for_response_in_minute);
		responseTimeMap.put("total_response_time",
				total_response_time_in_minute);
		return responseTimeMap;

	}
	

	/**
	 * getTimeDiff() method will return the time difference between two timings
	 * in minute Arguments passed : Two calendar instanses
	 */
	private static int getTimeDiff(Calendar endcal, Calendar startcal) {
		
		return ((int) ((endcal.getTimeInMillis() - startcal.getTimeInMillis()) / (1000 * 60)));
	}

	/**
	 * getClosedResponseTime() method will return the response time from the
	 * last entry in the auditog and closure time. Arguments passed : TICKET
	 * ID,AUDIT LOG LIST,CLOSED DATE,SLA ID,LOCATION ID
	 */
	private static long getClosedResponseTime(String ticket_id,
			HELPDESK_SlaDAO slaDAO, List<Map<String, Object>> auditLogList,
			Date closed_date, long sla_id, long location_id) {
		
		/*
		 * List<Map<String, Object>> slaExclusionDateList = slaDAO
		 * .getSLAExclusionDates(sla_id, location_id);
		 */
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
				.getSLAExclusionDateList(sla_id, location_id);

		/*
		 * List<Map<String, Object>> slaOperatingTimeList = slaDAO
		 * .getSLAOperatingTime(sla_id);
		 */
		List<Map<String, Object>> slaOperatingTimeList = MasterDataImpl
				.getSLAOperatingTimeList(sla_id);

		Date lastChangeDate = getLastChangedDate(ticket_id, slaDAO);
		Calendar closedCal = Calendar.getInstance();
		closedCal.setTime(closed_date);
		Calendar lastChangeCal = Calendar.getInstance();
		lastChangeCal.setTime(lastChangeDate);
		// Modified by sali
		lastChangeCal.add(Calendar.HOUR_OF_DAY, 5);
		lastChangeCal.add(Calendar.MINUTE, 30);
		long closed_response_time = 0l;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String holiday = "";
		long working_time_in_minute = 0;
		long holiday_working_time_in_minute = 0l;

		while (closed_date.after(lastChangeDate)
				|| sd.format(closed_date).equalsIgnoreCase(
						sd.format(lastChangeDate))) {
			for (Map<String, Object> stringObj : slaOperatingTimeList) {
				working_time_in_minute = working_time_in_minute
						+ getWorkingTimeInaDay(stringObj, lastChangeCal, closedCal);
			}
			for (Map<String, Object> stringObj : slaExclusionDateList) {
				holiday = stringObj.get("DATE").toString();
				if (sd.format(lastChangeDate).equalsIgnoreCase(holiday)) {
					try {
						holiday_working_time_in_minute = getHolidayWorkingTime(
								slaOperatingTimeList, holiday);
					} catch (ParseException e) {

						e.printStackTrace();
					}
				}
			}
			lastChangeCal.add(Calendar.DATE, 1);
			lastChangeCal.set(Calendar.HOUR_OF_DAY, 0);
			lastChangeCal.set(Calendar.MINUTE, 0);
			lastChangeDate = lastChangeCal.getTime();
		}
		closed_response_time = working_time_in_minute
				- holiday_working_time_in_minute;
		
		//TODO SLA time break has to be added
		

		return closed_response_time;
	}

	public static long getlastStatusChangeResponseTime(String ticket_id,
			HELPDESK_SlaDAO slaDAO, List<Map<String, Object>> auditLogList,
			Date current_date, long sla_id, long location_id, long slaTime) {
		/*
		 * List<Map<String, Object>> slaExclusionDateList = slaDAO
		 * .getSLAExclusionDates(sla_id, location_id);
		 */
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
				.getSLAExclusionDateList(sla_id, location_id);
		List<Map<String, Object>> slaOperatingTimeList = MasterDataImpl
				.getSLAOperatingTimeList(sla_id);

		Date lastChangeDate = getLastChangedDate(ticket_id, slaDAO);
		Calendar currentCal = Calendar.getInstance();
		currentCal.setTime(current_date);
		Calendar lastChangeCal = Calendar.getInstance();
		lastChangeCal.setTime(lastChangeDate);
		// Modified by sali
		lastChangeCal.add(Calendar.HOUR_OF_DAY, 5);
		lastChangeCal.add(Calendar.MINUTE, 30);
		long response_time = 0l;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String holiday = "";
		long working_time_in_minute = 0;
		long last_changed_day_working_time_in_minute = 0;
		long holiday_working_time_in_minute = 0l;
		lastChangeDate = lastChangeCal.getTime();
		int last_changed_time_flag=0;
		
		while (current_date.after(lastChangeDate)
				|| sd.format(current_date).equalsIgnoreCase(
						sd.format(lastChangeDate))) {
			for (Map<String, Object> stringObj : slaOperatingTimeList) {
				working_time_in_minute = working_time_in_minute
						+ getWorkingTimeInaDay(stringObj, lastChangeCal, currentCal);
			}
			working_time_in_minute=working_time_in_minute+last_changed_day_working_time_in_minute;
			for (Map<String, Object> stringObj : slaExclusionDateList) {
				holiday = stringObj.get("DATE").toString();
				if (sd.format(lastChangeDate).equalsIgnoreCase(holiday)) {
					try {
						holiday_working_time_in_minute = getHolidayWorkingTime(
								slaOperatingTimeList, holiday);
					} catch (ParseException e) {

						e.printStackTrace();
					}
				}
			}
			response_time = working_time_in_minute
					- holiday_working_time_in_minute;
			
			if(response_time<0)
				response_time=0;
			if (response_time > slaTime) {
				break;
			}
			
			lastChangeCal.add(Calendar.DATE, 1);
			lastChangeCal.set(Calendar.HOUR_OF_DAY, 0);
			lastChangeCal.set(Calendar.MINUTE,0);
			lastChangeDate = lastChangeCal.getTime();
		}
		return response_time;
	}

	private static long getLastDayWorkingTime(Calendar lastChangeCal,
			long working_time_in_minute, Map<String, Object> stringObj) {
		// STEP 7:
		Calendar endcal = Calendar.getInstance();
		Calendar startcal = Calendar.getInstance();
		if (lastChangeCal.get(Calendar.HOUR_OF_DAY) >= Integer
				.parseInt(stringObj.get("START_TIME").toString()
						.substring(0, 2))) {
			endcal.set(Calendar.HOUR_OF_DAY, 23);
			endcal.set(Calendar.MINUTE, 59);
			endcal.set(Calendar.SECOND, 59);
			startcal.set(Calendar.HOUR_OF_DAY, Integer
					.parseInt(stringObj.get("START_TIME")
							.toString().substring(0, 2)));
			startcal.set(Calendar.MINUTE, Integer
					.parseInt(stringObj.get("START_TIME")
							.toString().substring(3, 5)));
			startcal.set(Calendar.SECOND, Integer
					.parseInt(stringObj.get("START_TIME")
							.toString().substring(6, 8)));
			working_time_in_minute = working_time_in_minute
					+ getTimeDiff(endcal, startcal);
		}
		if (lastChangeCal.get(Calendar.HOUR_OF_DAY) <= Integer
				.parseInt(stringObj.get("END_TIME").toString()
						.substring(0, 2)))
		{
			endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj
					.get("END_TIME").toString().substring(0, 2)));
		endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get(
				"END_TIME").toString().substring(6, 7)));
		startcal.set(Calendar.HOUR_OF_DAY, lastChangeCal
				.get(Calendar.HOUR_OF_DAY));
		startcal.set(Calendar.MINUTE, lastChangeCal
				.get(Calendar.MINUTE));
		startcal.set(Calendar.SECOND, lastChangeCal
				.get(Calendar.SECOND));
		}
		working_time_in_minute = working_time_in_minute
				+ getTimeDiff(endcal, startcal);
		return working_time_in_minute;
	}

	/**
	 * getHolidayWorkingTime() method will return working time on a holiday * @param
	 * holiday_working_time_in_minute : Working time in minute for a holiday.
	 */
	public static long getHolidayWorkingTime(
			List<Map<String, Object>> slaOperatingTimeList,
			String holiday_date_string) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date holiday_date = sd.parse(holiday_date_string);
		long holiday_working_time_in_minute = 0l;
		Calendar holidaycal = Calendar.getInstance();
		holidaycal.setTime(holiday_date);
		String holiday_day = getDay(holidaycal.get(Calendar.DAY_OF_WEEK))
				.trim().toUpperCase();
		Calendar endcal = Calendar.getInstance();
		Calendar startcal = Calendar.getInstance();
		int endhour = 0;
		int endminute = 0;
		int starthour = 0;
		int startminute = 0;
		for (Map<String, Object> slaObj : slaOperatingTimeList) {
			if (slaObj.get("DAY").toString().trim().toUpperCase()
					.equalsIgnoreCase(holiday_day)) {
				if (slaObj.get("END_TIME").toString().substring(0, 2) != null)
					endhour = Integer.parseInt(slaObj.get("END_TIME")
							.toString().substring(0, 2));
				if (slaObj.get("END_TIME").toString().substring(3, 5) != null)
					endminute = Integer.parseInt(slaObj.get("END_TIME")
							.toString().substring(3, 5));
				if (slaObj.get("START_TIME").toString().substring(0, 2) != null)
					starthour = Integer.parseInt(slaObj.get("START_TIME")
							.toString().substring(0, 2));
				if (slaObj.get("START_TIME").toString().substring(3, 5) != null)
					startminute = Integer.parseInt(slaObj.get("START_TIME")
							.toString().substring(3, 5));
				endcal.set(Calendar.HOUR_OF_DAY, endhour);
				endcal.set(Calendar.MINUTE, endminute);
				startcal.set(Calendar.HOUR_OF_DAY, starthour);
				startcal.set(Calendar.MINUTE, startminute);
				holiday_working_time_in_minute = getTimeDiff(endcal, startcal);
			}
		}
		return holiday_working_time_in_minute;
	}
	static long getWorkingTimeInaDay(Map<String, Object> stringObj,
			Calendar lastChangedCal, Calendar currentCal) {
		long working_time_in_minute = 0l;
		Calendar endcal = Calendar.getInstance();
		Calendar startcal = Calendar.getInstance();
		String op_day = stringObj.get("DAY").toString();
		String last_changed_day = getDay(lastChangedCal
				.get(Calendar.DAY_OF_WEEK));
		
		// if last modified  day is working day,
			//CONDITION 1// if op start time < Op end time
					//if last modified hour is zero and then set op start time as last modified time
						//if the current day and last modified day is same day,
							// find the working time in that day
							 //STEP :1// if last modified time < op start time and current time < Op End time ,
									//then start time=op start time ,working time= current time - op start time;
							// STEP :2 //if last modified time > op start time  && < op end time ,
										//A if current time < op end time ,then working time= current time - last modified time;
										//B if current time > Op End time ,then working time = Op end time - last modified time;
							// STEP :3 //if the last modified time > Op end time , then working time in that day is zero.
						// if the current day and last modified day is not same day,
							// if last modified time < op start time, then working time= Op end  time - op start time;
							// if last modified time > op start time  && < op end time ,then working time= Op end time - last modified time;
							// if the last modified time > Op end time , then working time in that day is zero.
		
			//CONDITION 2// if op start time > Op end time
					// if the current day and last modified day is same day
						// Find the working time in that day
							//STEP 1: // if the last modified time < Op.start time  and current time < Op. Start time,
											//then working time =0;
									 // If the last modified time && Current time > Op .End Time and < Op. start time,
											// then working time=0;
							//STEP 2 : // IF the last modified time > Op.start time && current time >Op.start time,
											//then working time = current time -last modified time;
							//STEP 3 : // If the last modified time < Op .End Time && Current time < Op. End Time,
											// then working time =current time -last modified time;
					// if the current day and last modified day is not the same day
					
							// STEP 4 :// IF the current time < Op. end time ,
											// then working time = current time(next date of the last modified Date) - last modified time;
							// 	STEP 5: // If the current time > Op .start time ,
											// then working time =(current time - Op.start time) + Op .end time;
					   	
		if (last_changed_day.trim().toUpperCase().equalsIgnoreCase(
				op_day.trim().toUpperCase())) {
			if (Integer.parseInt(stringObj.get("START_TIME").toString()
					.substring(0, 2)) < Integer.parseInt(stringObj.get(
					"END_TIME").toString().substring(0, 2))) {
				if (lastChangedCal.get(Calendar.HOUR_OF_DAY) == 0) 
					{
						lastChangedCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("START_TIME").toString()
								.substring(0, 2)));
						lastChangedCal.set(Calendar.MINUTE,Integer.parseInt(stringObj.get("START_TIME").toString()
								.substring(3, 5)));
						lastChangedCal.set(Calendar.SECOND,Integer.parseInt(stringObj.get("START_TIME").toString()
								.substring(6, 8)));
					}
				if (lastChangedCal.get(Calendar.DAY_OF_WEEK) == currentCal
						.get(Calendar.DAY_OF_WEEK)) {
					//STEP 1
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY)<= Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2))
							&&  currentCal.get(Calendar.HOUR_OF_DAY)<= Integer.parseInt(stringObj.get(
							"END_TIME").toString().substring(0, 2)))
							{
								if(currentCal.get(Calendar.HOUR_OF_DAY)==Integer.parseInt(stringObj.get("END_TIME").toString().substring(0,2))
								&& Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5))!=0	)
								{
									
									if(currentCal.get(Calendar.MINUTE)>Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)))
									{
										endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0,2)));
										endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
										endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
									}
									else
									{
										endcal.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY));
										endcal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
										endcal.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
									}
								}
								else
								{
									if(currentCal.get(Calendar.HOUR_OF_DAY)<Integer.parseInt(stringObj.get("END_TIME").toString().substring(0,2)))
									{
										endcal.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY));
										endcal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
										endcal.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
									}
									if(currentCal.get(Calendar.HOUR_OF_DAY)>=Integer.parseInt(stringObj.get("END_TIME").toString().substring(0,2)))
									{
									endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0,2)));
									endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
									endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
									}
								}
								
								startcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2)));
								if(lastChangedCal.get(Calendar.MINUTE)>Integer.parseInt(stringObj.get("START_TIME").toString().substring(3,5))
										&& Integer.parseInt(stringObj.get("START_TIME").toString().substring(3,5))!=0)
								{
									startcal.set(Calendar.MINUTE, lastChangedCal.get(Calendar.MINUTE));
									startcal.set(Calendar.SECOND, lastChangedCal.get(Calendar.SECOND));
								}
								else
								{
									startcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("START_TIME").toString().substring(3,5)));
								startcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("START_TIME").toString().substring(6, 8)));
								}
								working_time_in_minute =getTimeDiff(endcal, startcal);
							}
					//STEP 2 : A
					if (lastChangedCal.get(Calendar.HOUR_OF_DAY) > Integer
							.parseInt(stringObj.get("START_TIME").toString()
									.substring(0, 2))
							&& currentCal.get(Calendar.HOUR_OF_DAY) < Integer
									.parseInt(stringObj.get("END_TIME")
											.toString().substring(0, 2))
							&& lastChangedCal.get(Calendar.HOUR_OF_DAY) < Integer
									.parseInt(stringObj.get("END_TIME")
											.toString().substring(0, 2))) {
						working_time_in_minute = getTimeDiff(currentCal,
								lastChangedCal);
					}
					//STEP 2 : B
					if (lastChangedCal.get(Calendar.HOUR_OF_DAY) >= Integer
							.parseInt(stringObj.get("START_TIME").toString()
									.substring(0, 2))
							&& currentCal.get(Calendar.HOUR_OF_DAY) > Integer
									.parseInt(stringObj.get("END_TIME")
											.toString().substring(0, 2))
							&& lastChangedCal.get(Calendar.HOUR_OF_DAY) < Integer
									.parseInt(stringObj.get("END_TIME")
											.toString().substring(0, 2))) {
						endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)));
						endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
						endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6, 8)));
						startcal.set(Calendar.HOUR_OF_DAY, lastChangedCal.get(Calendar.HOUR_OF_DAY));
						startcal.set(Calendar.MINUTE, lastChangedCal.get(Calendar.MINUTE));
						startcal.set(Calendar.SECOND, lastChangedCal.get(Calendar.SECOND));
						working_time_in_minute =getTimeDiff(endcal, startcal);
					}
					//STEP 3:
					if (lastChangedCal.get(Calendar.HOUR_OF_DAY) > Integer
							.parseInt(stringObj.get("END_TIME").toString()
									.substring(0, 2)))
									{
									working_time_in_minute=0;
									}
					
				}
				else
				{
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY)<=Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2)))
					{
						endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)));
						endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
						endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
						startcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2)));
						startcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("START_TIME").toString().substring(3,5)));
						startcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("START_TIME").toString().substring(6, 8)));
						working_time_in_minute =getTimeDiff(endcal, startcal);
					}
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2))
							&& lastChangedCal.get(Calendar.HOUR_OF_DAY)<Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
					{
						endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)));
						endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
						endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
						startcal.set(Calendar.HOUR_OF_DAY,lastChangedCal.get(Calendar.HOUR_OF_DAY)  );
						startcal.set(Calendar.MINUTE, lastChangedCal.get(Calendar.MINUTE) );
						startcal.set(Calendar.SECOND, lastChangedCal.get(Calendar.SECOND) );
						working_time_in_minute =getTimeDiff(endcal, startcal);
					}
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY)> Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
					{
						working_time_in_minute=0;
					}
					
					
				}
				
			}
			else
			{
				if (lastChangedCal.get(Calendar.DAY_OF_WEEK) == currentCal
						.get(Calendar.DAY_OF_WEEK)) {
					//STEP 1:
					if (lastChangedCal.get(Calendar.HOUR_OF_DAY) < Integer
							.parseInt(stringObj.get("START_TIME").toString()
									.substring(0, 2))
							&& currentCal.get(Calendar.HOUR_OF_DAY) < Integer
									.parseInt(stringObj.get("START_TIME")
											.toString().substring(0, 2))
							&& currentCal.get(Calendar.HOUR_OF_DAY) > Integer
									.parseInt(stringObj.get("END_TIME")
											.toString().substring(0, 2))) {
						working_time_in_minute = 0;
						
					}
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2))
							&&  currentCal.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(stringObj.get(
							"END_TIME").toString().substring(0, 2)))
							{
								working_time_in_minute =0;
							}
					//STEP 2:
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2))
							&&  currentCal.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(stringObj.get(
							"START_TIME").toString().substring(0, 2)))
					{
						working_time_in_minute = getTimeDiff(currentCal,
								lastChangedCal);
					}
					//STEP 3:
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) < Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
					{
						if(currentCal.get(Calendar.HOUR_OF_DAY)<Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
								working_time_in_minute = getTimeDiff(currentCal,lastChangedCal);
						else if(currentCal.get(Calendar.HOUR_OF_DAY)>=Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
						{
							endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)));
							endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
							endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
							startcal.set(Calendar.HOUR_OF_DAY, lastChangedCal.get(Calendar.HOUR_OF_DAY));
							startcal.set(Calendar.MINUTE, lastChangedCal.get(Calendar.MINUTE));
							startcal.set(Calendar.SECOND, lastChangedCal.get(Calendar.SECOND));
							working_time_in_minute = getTimeDiff(endcal,startcal);
							
						}
						else
						{
							working_time_in_minute = getTimeDiff(currentCal,lastChangedCal);
						}
					}
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) == Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)))
					{
						if(lastChangedCal.get(Calendar.MINUTE)<=Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)))
						{
							endcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2)));
							endcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("END_TIME").toString().substring(3,5)));
							endcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("END_TIME").toString().substring(6,8)));
							startcal.set(Calendar.HOUR_OF_DAY, lastChangedCal.get(Calendar.HOUR_OF_DAY));
							startcal.set(Calendar.MINUTE, lastChangedCal.get(Calendar.MINUTE));
							startcal.set(Calendar.SECOND, lastChangedCal.get(Calendar.SECOND));
						}
						
						working_time_in_minute =getTimeDiff(endcal, startcal);
					}
					//STEP 4:
					if(lastChangedCal.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(stringObj.get("END_TIME").toString().substring(0, 2))
							&&  currentCal.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(stringObj.get(
							"START_TIME").toString().substring(0, 2)))
					{
						endcal.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY));
						endcal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
						endcal.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
						startcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stringObj.get("START_TIME").toString().substring(0, 2)));
						startcal.set(Calendar.MINUTE, Integer.parseInt(stringObj.get("START_TIME").toString().substring(3,5)));
						startcal.set(Calendar.SECOND, Integer.parseInt(stringObj.get("START_TIME").toString().substring(6, 8)));
						working_time_in_minute =getTimeDiff(endcal, startcal);
					}
				}
				
			}
		}
		if(working_time_in_minute<0)
			working_time_in_minute=0;
		return working_time_in_minute;
	}

	/**
	 * getWorkingTime() method will return the working time in a day.It excluded
	 * non working hours. Arguments passed : Two calendar instances
	 */
	static long getWorkingTime(Map<String, Object> stringObj,
			Calendar lastChangedCal, Calendar closedCal) {
		long working_time_in_minute = 0l;
		Calendar endcal = Calendar.getInstance();
		Calendar startcal = Calendar.getInstance();
		String op_day = stringObj.get("DAY").toString();
		String last_changed_day = getDay(lastChangedCal
				.get(Calendar.DAY_OF_WEEK));
		int endhour = 0;
		int endminute = 0;
		int starthour = 0;
		int startminute = 0;
		int endsecond = 0;
		int startsecond = 0;

		if (last_changed_day.trim().toUpperCase().equalsIgnoreCase(
				op_day.trim().toUpperCase())) {
			if (lastChangedCal.get(Calendar.HOUR_OF_DAY) == 0) {
				if (lastChangedCal.get(Calendar.DAY_OF_WEEK) == closedCal
						.get(Calendar.DAY_OF_WEEK)) {
					if (Integer.parseInt(stringObj.get("START_TIME").toString()
							.substring(0, 2)) > Integer.parseInt(stringObj.get(
							"END_TIME").toString().substring(0, 2))) {
						starthour = lastChangedCal.get(Calendar.HOUR_OF_DAY);
						endhour = closedCal.get(Calendar.HOUR_OF_DAY);
						startminute = lastChangedCal.get(Calendar.MINUTE);
						startsecond = lastChangedCal.get(Calendar.SECOND);
						endminute = closedCal.get(Calendar.MINUTE);
						endsecond = closedCal.get(Calendar.SECOND);
					} else {
						starthour = Integer.parseInt(stringObj
								.get("START_TIME").toString().substring(0, 2));
						endhour = closedCal.get(Calendar.HOUR_OF_DAY);
						startminute = Integer.parseInt(stringObj.get(
								"START_TIME").toString().substring(3, 5));
						startsecond = Integer.parseInt(stringObj.get(
								"START_TIME").toString().substring(6, 8));
						endminute = closedCal.get(Calendar.MINUTE);
						endsecond = closedCal.get(Calendar.SECOND);
					}
				} else {

					starthour = Integer.parseInt(stringObj.get("START_TIME")
							.toString().substring(0, 2));
					endhour = Integer.parseInt(stringObj.get("END_TIME")
							.toString().substring(0, 2));
					startminute = Integer.parseInt(stringObj.get("START_TIME")
							.toString().substring(3, 5));
					startsecond = Integer.parseInt(stringObj.get("START_TIME")
							.toString().substring(6, 8));
					endminute = Integer.parseInt(stringObj.get("END_TIME")
							.toString().substring(3, 5));
					endsecond = Integer.parseInt(stringObj.get("END_TIME")
							.toString().substring(6, 8));
				}

			} else {
				if (lastChangedCal.get(Calendar.DAY_OF_WEEK) == closedCal
						.get(Calendar.DAY_OF_WEEK)) {
					starthour = lastChangedCal.get(Calendar.HOUR_OF_DAY);
					
					startminute = lastChangedCal.get(Calendar.MINUTE);
					startsecond = lastChangedCal.get(Calendar.SECOND);

					if (closedCal.get(Calendar.HOUR_OF_DAY) < Integer
							.parseInt(stringObj.get("END_TIME").toString()
									.substring(0, 2))) {
						endhour = closedCal.get(Calendar.HOUR_OF_DAY);
						endminute = closedCal.get(Calendar.MINUTE);
						endsecond = closedCal.get(Calendar.SECOND);
					} else {
						endhour = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(0, 2));
						endminute = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(3, 5));
						endsecond = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(6, 8));
					}

				} else {
					starthour = lastChangedCal.get(Calendar.HOUR_OF_DAY);
					
					startminute = lastChangedCal.get(Calendar.MINUTE);
					startsecond = lastChangedCal.get(Calendar.SECOND);
					if (closedCal.get(Calendar.HOUR_OF_DAY) < Integer
							.parseInt(stringObj.get("END_TIME").toString()
									.substring(0, 2))) {
						endhour = closedCal.get(Calendar.HOUR_OF_DAY);
						endminute = closedCal.get(Calendar.MINUTE);
						endsecond = closedCal.get(Calendar.SECOND);
					} else {
						endhour = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(0, 2));
						endminute = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(3, 5));
						endsecond = Integer.parseInt(stringObj.get("END_TIME")
								.toString().substring(6, 8));
					}
				}
			}

			endcal.set(Calendar.HOUR_OF_DAY, endhour);
			endcal.set(Calendar.MINUTE, endminute);
			endcal.set(Calendar.SECOND, endsecond);
			startcal.set(Calendar.HOUR_OF_DAY, starthour);
			startcal.set(Calendar.MINUTE, startminute);
			startcal.set(Calendar.SECOND, startsecond);
			endcal.set(Calendar.DATE, closedCal.get(Calendar.DATE));
			startcal.set(Calendar.DATE, lastChangedCal.get(Calendar.DATE));
			if (endhour == 23 && endminute == 59 && endsecond == 59)
				working_time_in_minute = getTimeDiff(endcal, startcal) + 1;
			else
				working_time_in_minute = getTimeDiff(endcal, startcal);
			if (working_time_in_minute < 0)
				working_time_in_minute = 0;

		}
		return working_time_in_minute;
	}

	/**
	 * 
	 * getLastChangedDate() method will return the changed date from the last
	 * entry in the auditlog. Arguments passed : TICKET ID
	 */
	private static Date getLastChangedDate(String ticket_id,
			HELPDESK_SlaDAO slaDAO) {
		Date lastChangeDate = null;
		List<Map<String, Object>> auditLastResponseTimeList = slaDAO
				.getAuditLastResponseTime(ticket_id);
		for (Map<String, Object> stringObj : auditLastResponseTimeList) {
			lastChangeDate = (Date) stringObj.get("CHANGED_DATE");
		}
		return lastChangeDate;
	}

	/**
	 * getDay() method will return the day for day_of_week Arguments passed :
	 * DAY OF WEEK
	 */
	private static String getDay(int day_of_week) {
		String day = "";
		if (day_of_week == 1) {
			day = "SUN";
		} else if (day_of_week == 2) {
			day = "MON";
		} else if (day_of_week == 3) {
			day = "TUE";
		} else if (day_of_week == 4) {
			day = "WED";
		} else if (day_of_week == 5) {
			day = "THU";
		} else if (day_of_week == 6) {
			day = "FRI";
		} else {
			day = "SAT";
		}

		return day;
	}

	/**
	 * getResponseTime() method will return the response time
	 */
	private static long getResponseTime(Map<String, Object> stringObj,
			String status, long response_time_in_minute) {

		if (status.trim().toUpperCase().equalsIgnoreCase("TOTALRESPONSE")) {
			if (stringObj.get("RESPONSE_TIME") != null)
				response_time_in_minute = response_time_in_minute
						+ Integer.parseInt(stringObj.get("RESPONSE_TIME")
								.toString());
		} else {

			if (stringObj.get("PREVIOUS_STATE") != null
					&& stringObj.get("CURRENT_STATE") != null) {
				if (stringObj.get("PREVIOUS_STATE").toString().trim()
						.toUpperCase().equalsIgnoreCase(
								status.trim().toUpperCase())
						&& !stringObj.get("CURRENT_STATE").toString().trim()
								.toUpperCase().equalsIgnoreCase(
										status.trim().toUpperCase())) {

					if (stringObj.get("RESPONSE_TIME") != null) {
						if (Integer.parseInt(stringObj.get("RESPONSE_TIME")
								.toString()) > 0)
							response_time_in_minute = response_time_in_minute
									+ Integer.parseInt(stringObj.get(
											"RESPONSE_TIME").toString());
					}
				}
			}
					}// else
		return response_time_in_minute;
		
	}
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 10, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
