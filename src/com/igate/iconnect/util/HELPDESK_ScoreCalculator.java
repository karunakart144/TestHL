package com.igate.iconnect.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Map;


import org.springframework.context.ApplicationContext;

import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;

public class HELPDESK_ScoreCalculator {	

	/**
	 * getEngineerScoreForDay() method takes the login engineer's Employee ID.
	 * It fetches the list of open tickets that are Assigned to the login engineer.
	 * Then priority and status weight of each ticket from the ticket list is 
	 * calculated and added to the Score of the Engineer.
	 * The total score that has been calculated for the Engineer is then updated to the DB.
	 */
public static void getEngineerScoreForDay(String employeeID) {	
		ApplicationContext appContext=COMMON_AppContext.getCtx();		
		HELPDESK_EditDAO editDAO=(HELPDESK_EditDAO)appContext.getBean("IHDEditDAO");	
		COMMON_CacheDAO commonCacheDAO = (COMMON_CacheDAO)appContext.getBean("GetMasterData");
		double TOTAL_SCORE=0;	
		double HIGH_SCORE=0;
		double MEDIUM_SCORE=0;
		double LOW_SCORE=0;		
		double TICKET_SCORE=0;
		/*COMMENT: Find the number of Open Tickets from DB and Calculate Score*/
		List<Map<String,Object>> ticketList=editDAO.getTicketListForEngineer(employeeID);
		String data_change="<data_change>";
		String tickets="<tickets>";
		/**************************RESULT : ENGINEER SCORE***************************************/		
		if(ticketList.size()>0){			
				for(int i=0;i<ticketList.size();i++){
					Map<String,Object> hdObj=ticketList.get(i);
					String ticketID=hdObj.get("TICKET_ID").toString();					
					String wfStateID=hdObj.get("STATE_ID").toString();
					String priority=hdObj.get("PRIORITY_ID").toString();
					String priorityName=hdObj.get("PRIORITY_NAME").toString();													
					int priorityWt=commonCacheDAO.getPriorityWeightageForAutoAssignment(Integer.parseInt(priority));					
					double statusWt=0.00;
					statusWt=commonCacheDAO.getStatusWeightageForAutoAssignment(Integer.parseInt(wfStateID));					
					if(priorityName.equals("HIGH")){
						HIGH_SCORE=HIGH_SCORE+(statusWt*priorityWt);						
					}					
					if(priorityName.equals("MEDIUM")){
						MEDIUM_SCORE=MEDIUM_SCORE+(statusWt*priorityWt);
					}
					if(priorityName.equals("LOW")){
						LOW_SCORE=LOW_SCORE+(statusWt*priorityWt);
					}	
					TICKET_SCORE=statusWt*priorityWt;
					tickets+="<l"+i+">"+ticketID+"</l"+i+">";
					tickets+="<WT"+i+">"+TICKET_SCORE+"</WT"+i+">";
					
				}
				tickets+="</tickets>";
				if(HIGH_SCORE!=0){
				data_change+="<high><old>"+0+"</old><new>"+HIGH_SCORE+"</new></high>";
				}
				if(MEDIUM_SCORE!=0){
					data_change+="<medium><old>"+0+"</old><new>"+MEDIUM_SCORE+"</new></medium>";
					}
				if(LOW_SCORE!=0){
					data_change+="<low><old>"+0+"</old><new>"+LOW_SCORE+"</new></low>";
					}
				TOTAL_SCORE=TOTAL_SCORE+(HIGH_SCORE+MEDIUM_SCORE+LOW_SCORE);	
				if(TOTAL_SCORE!=0){
					data_change+="<total><old>"+0+"</old><new>"+TOTAL_SCORE+"</new></total>";
					}
				data_change+=tickets+"</data_change>";				
				editDAO.insertEngineerScore(employeeID, HIGH_SCORE, LOW_SCORE, MEDIUM_SCORE, TOTAL_SCORE, data_change);				
		/**************************RESULT : ENGINEER SCORE***************************************/
		}else{/*If number of Open tickets do not exist*/
			/***********Doubt****If we have to insert the audit log detail or not for Engineer Score 0*************/		
			data_change+="<data_change><low><old>0</old><new>0</new></low><medium><old>0</old><new>0</new></medium><high><old>0</old><new>0</new></high>" +
					"<tickets>None</tickets></data_change>";
			editDAO.insertEngineerScore(employeeID, HIGH_SCORE, LOW_SCORE, MEDIUM_SCORE, TOTAL_SCORE, data_change);			
		/**************************RESULT : ENGINEER SCORE***************************************/
		}
		
	}
	
	/**
	 * isFirstLogin() method finds out if the Engineer has logged in for first time in  a day.
	 * @return
	 */	
	public static boolean  isFirstLogin(String employeeID){
		ApplicationContext appContext=COMMON_AppContext.getCtx();		
		HELPDESK_EditDAO editDAO=(HELPDESK_EditDAO)appContext.getBean("IHDEditDAO");
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		double dateDiff=0;
		double hourDiff=0;		
		boolean isFstLogin=true;		
		
		String maxDateStr=editDAO.getMaxLoginTimeForEngineer(employeeID);
		Date maxDate=new Date();
		Calendar calMaxTime=Calendar.getInstance();
		
				if(maxDateStr!=null){
					try{
						maxDate=dateFormat.parse(maxDateStr);
					}catch(ParseException pe){
						maxDate=null;
					}
				}else{
					maxDate=null;
				}		
	  String prevMaxDateStr=editDAO.getPrevLoginTimeForEngineer(employeeID);
	  Date prevMaxDate=new Date();
	  Calendar calPrevTime=Calendar.getInstance();	 
				if(prevMaxDateStr!=null){
					try{
						prevMaxDate=dateFormat.parse(prevMaxDateStr);
					}catch(ParseException pe){
						prevMaxDate=null;
					}
				}else{
					prevMaxDate=null;
				}	
		
		if(maxDate!=null){
			if(prevMaxDate!=null){
				calMaxTime.setTime(maxDate);
				calPrevTime.setTime(prevMaxDate);	
				dateDiff=calMaxTime.getTimeInMillis()-calPrevTime.getTimeInMillis();
				hourDiff=dateDiff/ (60.00*60.00*1000.00000);
			}else{
				calMaxTime.setTime(maxDate);	
				dateDiff=calMaxTime.getTimeInMillis();
				hourDiff=dateDiff/ (60.00*60.00*1000.00000);
			}			
		}else{
			hourDiff=0.0;
		}	
		if(hourDiff>6.0 || hourDiff==0.0){			
			isFstLogin=true;			
		}else{
			isFstLogin=false;			
		}		
		return isFstLogin;

	}	
	
	
	
}
