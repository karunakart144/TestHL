/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_SlaDAO;


public class HELPDESK_ECTPopulator {
	private static Logger log=Logger.getLogger(HELPDESK_ECTPopulator.class);
	public static String getECT(String currentstate, int priorityID,
			String creation_date, long category_id, String org,
			long location_id, long opr_id) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		Date actual_creation_date = null;
		try {
			actual_creation_date = sd.parse(creation_date);
		} catch (ParseException e) {
			/*e.printStackTrace();*/
			log.error("Date parse exception in ECT populator: "+e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(actual_creation_date);
		cal.add(Calendar.HOUR_OF_DAY, 5);
		cal.add(Calendar.MINUTE, 30);
		actual_creation_date = cal.getTime();
		HELPDESK_SlaDAO slaDAO = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
		String ect_date = "";
	
		if (actual_creation_date != null) {
			Date actual_ticket_creation_date = getActualTicketCreationDate(
					opr_id, priorityID, actual_creation_date, slaDAO,
					location_id, category_id, org);
			Date expected_completion_date = getExpectedCompletionDate(
					creation_date, currentstate, opr_id, priorityID, slaDAO,
					actual_ticket_creation_date, category_id, location_id, org);
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			ect_date = dateFormatGmt.format(expected_completion_date);
			
		}
		return ect_date;
	}

	private static Map<String, Object> slaMap(long opr_id, int priorityID,
			HELPDESK_SlaDAO slaDAO, long category_id, String org,
			Calendar creationCal) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		Map<String, Object> slaMap = new HashMap<String, Object>();
		/*
		 * List<Map<String, Object>> opr_time_list = slaDAO
		 * .getSLAOpertingTimeInfo(opr_id, org, getDay(creationCal
		 * .get(Calendar.DAY_OF_WEEK)));
		 */
		List<Map<String, Object>> opr_time_list = MasterDataImpl
				.getSLAOpertingTimeOrgWise(opr_id, org, getDay(creationCal
						.get(Calendar.DAY_OF_WEEK)));
		String start_working_time_string = "";
		String end_working_time_string = "";
		String next_working_day = "";
		int sla_time = 0;
		long sla_id = 0;

        for (Map<String, Object> stringObj : opr_time_list) {
            start_working_time_string = stringObj.get("START_TIME").toString();
            end_working_time_string = stringObj.get("END_TIME").toString();
            next_working_day = stringObj.get("NEXT_WORKING_DAY").toString();
            // sla_id = Long.parseLong(stringObj.get("SLA_ID").toString());
            // sla_time = Integer.parseInt(stringObj.get("TIME").toString());
        }
        //TODO:Why sla_id == 0 is required? It is always true.
		if (sla_id == 0 || sla_time == 0) {
			// if
			// (!slaDAO.getSLAID(category_id,priorityID).equalsIgnoreCase("")) {
			// sla_id = Long.parseLong(slaDAO.getSLAID(category_id,priorityID));
			// }
			Map<String, Object> sla = MasterDataImpl.getIHDCategoryPriority(
					(int) category_id, priorityID, org);

			if (sla.get("SLA_ID") != null) {
				sla_id = Integer.parseInt(sla.get("SLA_ID").toString().trim());
				sla_time = Integer.parseInt(sla.get("SLA_TIME").toString()
						.trim());
			}
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
		slaMap.put("next_working_day", next_working_day);
		slaMap.put("sla_time", sla_time);
		slaMap.put("sla_id", sla_id);
		slaMap.put("start_working_hour", start_working_hour);
		slaMap.put("start_working_minute", start_working_minute);
		slaMap.put("end_working_hour", end_working_hour);
		slaMap.put("end_working_minute", end_working_minute);
		return slaMap;
	}

	/**
	 * getExpectedCompletionDate() method will return the Expected completion
	 * date & time of the ticket. Expected completion date & time calculation
	 * involves (1) Addition of SLATime with the actual creation date & time.
	 * (2) Exclusion of non working day - If ECT day is non working day , then
	 * it will exclude that day & set next working day as created day. (3)
	 * Exclusion of holiday - If ECT is holiday, then it will exclude that day &
	 * set next working day as created day. (4) Exclusion of non working hours -
	 * If ECT is working day and created time is before start working time, then
	 * it will set start working time as created time. - If ECT is working day
	 * and created time is after end working time, then it will set next working
	 * day as created date and start working time as created time.
	 */
	private static Date getExpectedCompletionDate(String creation_date_gmt,
			String currentstate, long opr_id, int priorityID,
			HELPDESK_SlaDAO slaDAO, Date actual_ticket_creation_date,
			long category_id, long location_id, String org) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		Date ectDate = null;
		Calendar estimatedCompTimeCal = Calendar.getInstance();
		estimatedCompTimeCal.setTime(actual_ticket_creation_date);
		/*
		 * List<Map<String, Object>> sla_time_list = slaDAO
		 * .getSLAOpertingTimeInfo(opr_id, org,
		 * getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
		 */
 		List<Map<String, Object>> sla_time_list = MasterDataImpl
				.getSLAOpertingTimeOrgWise(opr_id, org,
						getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
		String start_working_time_string = "";
		String end_working_time_string = "";
		int sla_time = 0;
		long sla_id = 0;
        for (Map<String, Object> stringObj : sla_time_list) {
            start_working_time_string = stringObj.get("START_TIME").toString();
            end_working_time_string = stringObj.get("END_TIME").toString();
            // sla_id = Long.parseLong(stringObj.get("SLA_ID").toString());
            // sla_time = Integer.parseInt(stringObj.get("TIME").toString());
        }
		if (sla_id == 0 || sla_time == 0) {
			// if
			// (!slaDAO.getSLAID(category_id,priorityID).equalsIgnoreCase("")) {
			// sla_id = Long.parseLong(slaDAO.getSLAID(category_id,priorityID));
			// }
			Map<String, Object> sla = MasterDataImpl.getIHDCategoryPriority(
					(int) category_id, priorityID, org);

			if (sla.get("SLA_ID") != null) {
				sla_id = Integer.parseInt(sla.get("SLA_ID").toString().trim());
				sla_time = Integer.parseInt(sla.get("SLA_TIME").toString()
						.trim());
			}
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
		
		int limit = sla_time;//
		int time_spent = 0;
		limit = sla_time - time_spent;
		int minute = 0;
		int hour = 0;
		int second=0;
		Calendar currentcal = Calendar.getInstance();
		currentcal.setTime(actual_ticket_creation_date);
		Calendar endcal = Calendar.getInstance();
		endcal.setTime(actual_ticket_creation_date);
		Calendar startcal = Calendar.getInstance();
		startcal.setTime(actual_ticket_creation_date);
		int remainingdiff = 0;
		String day = "";
		 
		List<Map<String, Object>> sla_op_time_list = MasterDataImpl
				.getSLAOperatingTimeList(opr_id);
		limit= limit *60;
		if (sla_op_time_list.size() != 0) {
			while (limit != 0) {
				hour = estimatedCompTimeCal.get(Calendar.HOUR_OF_DAY);
				minute = estimatedCompTimeCal.get(Calendar.MINUTE);
				second = estimatedCompTimeCal.get(Calendar.SECOND);
				day = getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK))
						.trim().toUpperCase();
				
				
				boolean working_day = false;
                for (Map<String, Object> stringObj : sla_op_time_list) {
                    if (day.equalsIgnoreCase(stringObj.get("DAY").toString()
                            .trim().toUpperCase())) {
                        working_day = true;
                        break;
                    }

                }
                //L2:3866
				if (working_day) {
					sla_time_list = MasterDataImpl
					.getSLAOpertingTimeOrgWise(opr_id, org,
							getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
					for (Map<String, Object> stringObj : sla_time_list) {
						start_working_time_string = stringObj.get("START_TIME")
								.toString();
						end_working_time_string = stringObj.get("END_TIME")
								.toString();

					}
					if (!start_working_time_string.equalsIgnoreCase("")) {
						start_working_hour = Integer
								.parseInt(start_working_time_string.substring(
										0, 2));
						start_working_minute = Integer
								.parseInt(start_working_time_string.substring(
										3, 5));
					}
					if (!end_working_time_string.equalsIgnoreCase("")) {
						end_working_hour = Integer
								.parseInt(end_working_time_string.substring(0,
										2));
						end_working_minute = Integer
								.parseInt(end_working_time_string.substring(3,
										5));
					}
				}
				
                //
				boolean holiday_check = false;
				String holiday = "";
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * List<Map<String, Object>> slaExclusionDateList = slaDAO
				 * .getSLAExclusionDates(opr_id, location_id);
				 */

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
						if (hour >= start_working_hour
								&& hour <= end_working_hour) {
							timeLeftMin = (int) getTimeDiff(endcal, currentcal);
						} else {
							timeLeftMin = (int) getTimeDiff(endcal, startcal);
						}
					} else {
						
						timeLeftMin = (int) getTimeDiff(endcal, currentcal);
					}
					if (timeLeftMin >= limit) {
						estimatedCompTimeCal
								.setTimeInMillis(estimatedCompTimeCal
										.getTimeInMillis() + (limit * 1000));
						limit = 0;
						hour = estimatedCompTimeCal.get(Calendar.HOUR_OF_DAY);
						remainingdiff = (24 - end_working_hour)
								+ start_working_hour;
						if (hour == end_working_hour
								&& minute > end_working_minute)

							estimatedCompTimeCal
									.setTimeInMillis(estimatedCompTimeCal
											.getTimeInMillis() + (remainingdiff * 60 * 60 * 1000));
					} else {
						if(end_working_hour > start_working_hour )
						{
							remainingdiff = (24 - end_working_hour)
								+ start_working_hour;
						}
						else
						{
							remainingdiff = start_working_hour - end_working_hour;
							
						}
						estimatedCompTimeCal.add(Calendar.DATE, 1);
						//L2:3866
						sla_time_list = MasterDataImpl
						.getSLAOpertingTimeOrgWise(opr_id, org,
								getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
						for (Map<String, Object> stringObj : sla_time_list) {
							start_working_time_string = stringObj.get("START_TIME")
									.toString();
						}
						if (!start_working_time_string.equalsIgnoreCase("")) {
							start_working_hour = Integer
									.parseInt(start_working_time_string.substring(
											0, 2));
							start_working_minute = Integer
									.parseInt(start_working_time_string.substring(
											3, 5));
						}
						//
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
					//L2:3866
					sla_time_list = MasterDataImpl
					.getSLAOpertingTimeOrgWise(opr_id, org,
							getDay(estimatedCompTimeCal.get(Calendar.DAY_OF_WEEK)));
					for (Map<String, Object> stringObj : sla_time_list) {
						start_working_time_string = stringObj.get("START_TIME")
								.toString();
					}
					if (!start_working_time_string.equalsIgnoreCase("")) {
						start_working_hour = Integer
								.parseInt(start_working_time_string.substring(
										0, 2));
						start_working_minute = Integer
								.parseInt(start_working_time_string.substring(
										3, 5));
					}
					estimatedCompTimeCal.set(Calendar.HOUR_OF_DAY,
							start_working_hour);
					estimatedCompTimeCal.set(Calendar.MINUTE,
							start_working_minute);
					estimatedCompTimeCal.set(Calendar.SECOND, 0);

				}

			}// while
		}

		ectDate = estimatedCompTimeCal.getTime();
		return ectDate;
	}

	private static long getTimeDiff(Calendar endcal, Calendar currentcal) {
		return (endcal.getTimeInMillis() - currentcal
				.getTimeInMillis()) / (1000);
	}

	

	/**
	 * getActualTicketCreationDate() method will return the actual creation date
	 * & time of the ticket.\n Actual creation date & time calculation
	 * involves\n (1) Exclusion of non working day\n - If created day is non
	 * working day ,\n then it will exclude that day & set next working day as
	 * created day.\n (2) Exclusion of holiday\n - If created day is holiday,\n
	 * then it will exclude that day & set next working day as created day.\n
	 * (3) Exclusion of non working hours\n - If created day is working day and
	 * created time is before start working time,\n then it will set start
	 * working time as created time.\n - If created day is working day and
	 * created time is after end working time,\n then it will set next working
	 * day as created date and start working time as created time. \n
	 * 
	 * @param actual_creation_date
	 *            Creation date of the ticket
	 * @param location_id
	 *            location_id of the user
	 * @param org
	 *            lorganization of the user
	 * 
	 */
	private static Date getActualTicketCreationDate(long opr_id, int priorityID,
			Date actual_creation_date, HELPDESK_SlaDAO slaDAO, long location_id,
			long category_id, String org) {
		
		//The  code has been modified to fix the Holiday issue (SALI)
		Calendar creationCal = Calendar.getInstance();
		creationCal.setTime(actual_creation_date);
		Date actal_date=getActualDate(priorityID, actual_creation_date,
		opr_id, location_id, slaDAO, org, category_id);
				return actal_date;
	}
	private static Date getActualDate(int priorityID, Date creation_date,
			long opr_id, long location_id, HELPDESK_SlaDAO slaDAO, String org,
			long category_id) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
				.getSLAExclusionDateList(opr_id, location_id);
		int flag = 1;
		List<Map<String, Object>> slaList = MasterDataImpl
				.getSLAOperatingTimeList(opr_id);
		Calendar createdcal = Calendar.getInstance();
		String start_working_time_string = "";
		for (Map<String, Object> obj : slaList) {
			start_working_time_string = obj.get("START_TIME").toString();
		}
		while (flag > 0) {
			createdcal.setTime(creation_date);
			Date start_date = createdcal.getTime();
			String holiday = "";
			boolean holiday_check = false;
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> stringObj : slaExclusionDateList) {
				holiday = stringObj.get("DATE").toString();
				if (sd.format(start_date).equalsIgnoreCase(holiday)) {
					holiday_check = true;
				}
			}
			String day = getDay(createdcal.get(Calendar.DAY_OF_WEEK)).trim()
					.toUpperCase();
			boolean working_day = false;
			List<Map<String, Object>> sla_op_time_list = MasterDataImpl
					.getSLAOperatingTimeList(opr_id);
			for (Map<String, Object> stringObj : sla_op_time_list) {
				if (day.equalsIgnoreCase(stringObj.get("DAY").toString().trim()
						.toUpperCase())) {

					working_day = true;
					break;
				}

			}
			if (working_day && !holiday_check) {
				if (isWorkingHour(slaList, createdcal)) {
					flag = 0;

				} else {
					creation_date = workinghourCheck(opr_id, priorityID,
							slaDAO, createdcal.getTime(), org, category_id);
				}
			} else {
				createdcal.add(Calendar.DATE, 1);
				createdcal.set(Calendar.HOUR_OF_DAY, Integer
						.parseInt(start_working_time_string.substring(0, 2)));
				createdcal.set(Calendar.MINUTE, Integer
						.parseInt(start_working_time_string.substring(3, 5)));
				createdcal.set(Calendar.SECOND, 0);
				creation_date=createdcal.getTime();
			}

		}
		return createdcal.getTime();
	}
	private static boolean isWorkingHour(List<Map<String, Object>> slalist,Calendar createdcal) {
				Date date = createdcal.getTime();
				SimpleDateFormat smp = new SimpleDateFormat("E");
				String currentday = smp.format(date);
				currentday = currentday.toUpperCase();
				smp = new SimpleDateFormat("k");
				int currenthour = Integer.parseInt(smp.format(date));
				if(currenthour==24)
				{
					currenthour=0;
				}
					
				smp = new SimpleDateFormat("m");
				int currentminute = Integer.parseInt(smp.format(date));
				boolean opertaionHoursAvailable = false;
				for (Map<String, Object> rowobj : slalist) {
					String day = (String) rowobj.get("DAY");
					String startTime = String.valueOf(rowobj.get("START_TIME"));
					String endTime = String.valueOf(rowobj.get("END_TIME"));
					int starthour = Integer.parseInt(startTime.substring(0, 2));
					int endhour = Integer.parseInt(endTime.substring(0, 2));
					int startminute = Integer.parseInt(startTime.substring(3, 5));
					int endminute = Integer.parseInt(endTime.substring(3, 5));
					
					if (starthour < endhour) {
						
						//if loop is Added to fix 24 *7 group out of operation issue
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (starthour == 0 && endhour == 23)){
							opertaionHoursAvailable = true;
							break;
						}
						//if loop has been added to fix 8 :00 am op window msg removal
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute == 0 && currentminute == 0)
								&& (currenthour == starthour)) {
							opertaionHoursAvailable = true;
							break;
						}
						//if loop is added for startminute an dend minute is not zero
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute != 0 && startminute != 0)
								&& (currenthour == starthour && currentminute >=startminute && currenthour<endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute != 0 && startminute != 0)
								&& (currenthour > starthour && currenthour<endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute != 0 && startminute != 0)
								&& (currenthour ==endhour && currentminute<=endminute)) {
							opertaionHoursAvailable = true;
							break;
						}
						//
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (endminute != 0 && startminute == 0)
								&& (currenthour >= starthour && currenthour == endhour)
								&& (currentminute <= endminute)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (endminute != 0 && startminute == 0)
								&& (currenthour >= starthour && currenthour < endhour)
								) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute == 0 && currentminute == 0)
								&& (currenthour >= starthour && currenthour == endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute == 0)
								&& (currenthour >= starthour && currenthour < endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
						//Modified on May 22nd
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute != 0)
								&& (currenthour > starthour && currenthour <= endhour)
								) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute != 0)
								&& (currenthour == starthour)
								&& (currentminute >= startminute)) {
							opertaionHoursAvailable = true;
							break;
						}
						//L2:3866
						if (day != null
								&& day.equalsIgnoreCase(currentday)
								&& (endminute == 0 && startminute == 0)
								&& (currenthour >= starthour && currenthour < endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
					}

					//dEFECT FIX:265
					if (starthour > endhour) {
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (currenthour == starthour && currenthour > endhour)
								&& (currentminute >= startminute)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (currenthour > starthour && currenthour > endhour)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (currenthour < starthour && currenthour == endhour)
								&& (endminute >= currentminute)) {
							opertaionHoursAvailable = true;
							break;
						}
						if (day != null && day.equalsIgnoreCase(currentday)
								&& (currenthour < starthour && currenthour < endhour)
								) {
							opertaionHoursAvailable = true;
							break;
						}
					}
					
					
				}
				
				return opertaionHoursAvailable;
			
	}


	/**
	 * workinghourCheck() method is used to exclude non working hours in actual
	 * create date calculation.
	 *
	 */
	private static Date workinghourCheck(long opr_id, int priorityID,
			HELPDESK_SlaDAO slaDAO, Date actual_creation_date, String org,
			long category_id) {
		Calendar creationCal = Calendar.getInstance();
		creationCal.setTime(actual_creation_date);
		int created_hour = creationCal.get(Calendar.HOUR_OF_DAY);
		int created_minute = creationCal.get(Calendar.MINUTE);
		int created_second=creationCal.get(Calendar.SECOND);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> sla_time_list = MasterDataImpl
				.getSLAOpertingTimeOrgWise(opr_id, org, getDay(creationCal
						.get(Calendar.DAY_OF_WEEK)));
		Map<String, Object> slaMap = slaMap(opr_id, priorityID, slaDAO,
				category_id, org, creationCal);
		if (sla_time_list.size() != 0) {// Working day
			int start_working_hour = 0;
			int start_working_minute = 0;
			int end_working_hour = 0;
			int end_working_minute = 0;
			if (slaMap.get("start_working_hour") != null)
				start_working_hour = Integer.parseInt(slaMap.get(
						"start_working_hour").toString());
			if (slaMap.get("start_working_minute") != null)
				start_working_minute = Integer.parseInt(slaMap.get(
						"start_working_minute").toString());
			if (slaMap.get("end_working_hour") != null)
				end_working_hour = Integer.parseInt(slaMap.get(
						"end_working_hour").toString());
			if (slaMap.get("end_working_minute") != null)
				end_working_minute = Integer.parseInt(slaMap.get(
						"end_working_minute").toString());
			if (created_hour <start_working_hour) {

				creationCal.set(Calendar.HOUR_OF_DAY, start_working_hour);
				creationCal.set(Calendar.MINUTE, start_working_minute);
				creationCal.set(Calendar.SECOND, 0);
			}
			if (created_hour ==start_working_hour) {

				creationCal.set(Calendar.HOUR_OF_DAY, start_working_hour);
				creationCal.set(Calendar.MINUTE, created_minute);
				creationCal.set(Calendar.SECOND, created_second);
			}
			boolean flag = true;
			
			if (start_working_hour < end_working_hour) {
				if (created_hour >= end_working_hour) {
					for (int i = 1; i <= 7; i++) {
						if (flag) {
                            for (Map<String, Object> stringObj : sla_time_list) {

                                int day_of_week = creationCal
                                        .get(Calendar.DAY_OF_WEEK)
                                        + i;
                                if (day_of_week > 7) {
                                	//L2:3866
                                	 day_of_week = day_of_week
                                     - creationCal
                                     .get(Calendar.DAY_OF_WEEK);
                                }
                               
                                if (getDay(day_of_week).equalsIgnoreCase(
                                        stringObj.get("NEXT_WORKING_DAY")
                                                .toString().trim()
                                                .toUpperCase())) {
                                    if (created_hour == end_working_hour) {
                                        if (created_minute >= end_working_minute) {
                                            creationCal.add(Calendar.DATE, i);
                                            creationCal.set(
                                                    Calendar.HOUR_OF_DAY,
                                                    start_working_hour);
                                            creationCal.set(Calendar.MINUTE,
                                                    start_working_minute);
                                            creationCal.set(Calendar.SECOND, 0);
                                        }
                                    } else {
                                    	
                                        creationCal.add(Calendar.DATE, i);
                                        creationCal.set(Calendar.HOUR_OF_DAY,
                                                start_working_hour);
                                        creationCal.set(Calendar.MINUTE,
                                                start_working_minute);
                                        creationCal.set(Calendar.SECOND, 0);
                                        
                                    }
                                    flag = false;
                                    break;
                                }
                            }
						} else
							break;
					}
				}
			}
			//Added on May 4th
			if (start_working_hour > end_working_hour)
			{
				if(created_hour==start_working_hour && created_minute < start_working_minute)
				{
                     creationCal.set(Calendar.HOUR_OF_DAY,
                             start_working_hour);
                     creationCal.set(Calendar.MINUTE,
                             start_working_minute);
                     creationCal.set(Calendar.SECOND, 0);
				}
				if(created_hour< start_working_hour && created_hour < 24)
				{
                     creationCal.set(Calendar.HOUR_OF_DAY,
                             start_working_hour);
                     creationCal.set(Calendar.MINUTE,
                             start_working_minute);
                     creationCal.set(Calendar.SECOND, 0);
				}
			}
		}

		actual_creation_date = creationCal.getTime();
		return actual_creation_date;
	}

	private static String getDay(int day_of_week) {

		String day = "";
		if (day_of_week == 1) {
			day = "SUN";
		}
		if (day_of_week == 2) {
			day = "MON";
		}
		if (day_of_week == 3) {
			day = "TUE";
		}
		if (day_of_week == 4) {
			day = "WED";
		}
		if (day_of_week == 5) {
			day = "THU";
		}
		if (day_of_week == 6) {
			day = "FRI";
		}
		if (day_of_week == 7) {
			day = "SAT";
		}

		return day;
	}

}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 28, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
