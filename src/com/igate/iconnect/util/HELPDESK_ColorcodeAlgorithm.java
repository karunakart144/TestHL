/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.context.ApplicationContext;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_SlaDAO;

class HELPDESK_ColorcodeAlgorithm {
	static String getcolorcode(String status, String ticketId, String group_id,
			String ectdate, int functionId, int priorityid, long locationid,
			String org,int subCategoryId) {
		String colorcode = "";
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		int time = 0;
		long slaTime = 0;
		long opr_id = MasterDataImpl.getGroupOPRID(Long.valueOf(group_id),
				(long) functionId, (long) locationid);
		
		Map<String, Object> slamap = MasterDataImpl.getIHDCategoryPriority(
				subCategoryId, priorityid, org);
		if (slamap.get("COLOR_CODE_TIME") != null)
			time = (Integer) slamap.get("COLOR_CODE_TIME");

		if (slamap.get("SLA_TIME") != null)
			slaTime = Long.parseLong(slamap.get("SLA_TIME").toString());
		
		HELPDESK_SlaDAO slaDAO = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
		List<Map<String, Object>> auditLogList = slaDAO
				.getAuditLog(ticketId, 1);
		Map<String, Long> responseTimeMap = new HashMap<String, Long>();
		responseTimeMap = HELPDESK_SlaPopulator.getResponseTimeMap(
				auditLogList, responseTimeMap);
		int total_time_taken_for_response_in_minute = 0;
		long response_time_in_minute = 0;
		long timeSpentByExecutive = 0;
		ResourceBundle bundle = ResourceBundle.getBundle("sla");
		String statelist = bundle.getString("slastop_state");
		if (statelist.contains(status)) {
			colorcode = "images/white.jpg";
		} else {
			if (responseTimeMap.get("total_time_taken_for_response_in_minute") != null) {
				total_time_taken_for_response_in_minute = Integer
						.parseInt(String
								.valueOf(responseTimeMap
										.get("total_time_taken_for_response_in_minute")));
				response_time_in_minute = HELPDESK_SlaPopulator
						.getlastStatusChangeResponseTime(ticketId, slaDAO,
								auditLogList, new Date(), opr_id, locationid,
								slaTime);
				timeSpentByExecutive = (Integer.parseInt(String
						.valueOf(responseTimeMap.get("total_response_time"))) + response_time_in_minute)
						- total_time_taken_for_response_in_minute;

				if (timeSpentByExecutive > slaTime)
					colorcode = "images/red.jpg";
				else if (timeSpentByExecutive >= time && timeSpentByExecutive <= slaTime)
					colorcode = "images/amber.jpg";
				else
					colorcode = "images/green.jpg";

			}
		}
		return colorcode;
	}
}
