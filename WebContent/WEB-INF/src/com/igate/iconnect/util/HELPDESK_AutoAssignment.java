package com.igate.iconnect.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.igate.iconnect.dao.HELPDESK_EditDAO;

public class HELPDESK_AutoAssignment {

	/**The method getAssignedToEmployeeForTicket_V1() assigns the ticket to an Executive in a group based on his Total Score.
	 * Initially comparison is made for Lowest Total score engineer.Ticket is assigned to an Engineer with the lowest score . 
	 * Other wise comparison is through High,Medium and Low Score respectively. 
	 * @param memberScoreList: Gives the Score of all the members in the group
	 * @param groupID: Gives the groupID selected 
	 * @param minimumScoreMap: Gives the Minimum Total Score ,Minimum High Score,Minimum Medium Score and Minimum Low Score
	 */
	
	public static String getAssignedToEmployeeForTicket_V1(List<Map<String,Object>> memberScoreList,String groupID,Map<String,Object> minimumScoreMap){
		ApplicationContext appContext=COMMON_AppContext.getCtx();		
		HELPDESK_EditDAO editDAO=(HELPDESK_EditDAO)appContext.getBean("IHDEditDAO");
		/*Get minimum Priority scores of Total,High ,Medium and Low  for each Employee*/
		double minTotalScore=(Double)minimumScoreMap.get("TOTAL_SCORE");
		double minHighScore=(Double)minimumScoreMap.get("HIGH_SCORE");
		double minMediumScore=(Double)minimumScoreMap.get("MEDIUM_SCORE");
		double minLowScore=(Double)minimumScoreMap.get("LOW_SCORE");

		/*Initialization of all the array lists required in the method*/
		List<Map<String,Object>> lowestTotalScoreList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> lowestHighPrtyScoreList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> lowestMediumPrtyScoreList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> lowestPrtyScoreList=new ArrayList<Map<String,Object>>();
		/*Initialize assignedToEmployee as null*/
		String assignedToEmployee=null;
		
		
		/*Fetch the score list of members and compare  the Total score with the lowest total score*/
		if(memberScoreList.size()>1){
		minTotalScore=(Double)memberScoreList.get(0).get("TOTAL_SCORE");
		
		for(int i=1;i<memberScoreList.size();i++){
			if(minTotalScore>(Double)memberScoreList.get(i).get("TOTAL_SCORE")){
				minTotalScore=(Double)memberScoreList.get(i).get("TOTAL_SCORE");
			}
		}	
		
		for(int i=0;i<memberScoreList.size();i++){
			if((Double)memberScoreList.get(i).get("TOTAL_SCORE")==minTotalScore){
				/*If the total score and lowest total score are equal then add the member to lowestTotalScoreList*/
				lowestTotalScoreList.add(memberScoreList.get(i));
			}
		}
		}else{
			lowestTotalScoreList.add(memberScoreList.get(0));
		}
		/*Check if the lowestTotalScoreList has one or more employees i.e.,if more than 1 member exist with
		 *  same lowest total score
		 */
		if(lowestTotalScoreList.size()>1){
			/*If more than one member  than compare the lowest high priority score for each member. Add the 
			 * member with the lowest high priority score to the lowestHighPrtyScoreList.*/
			minHighScore=(Double)lowestTotalScoreList.get(0).get("HIGH_SCORE");
			for(int i=1;i<lowestTotalScoreList.size();i++){
				if(minHighScore>(Double)lowestTotalScoreList.get(i).get("HIGH_SCORE")){
					minHighScore=(Double)lowestTotalScoreList.get(i).get("HIGH_SCORE");
				}
			}			
			for(int i=0;i<lowestTotalScoreList.size();i++){
				if((Double)lowestTotalScoreList.get(i).get("HIGH_SCORE")==minHighScore){
					lowestHighPrtyScoreList.add(lowestTotalScoreList.get(i));
				}
			}
			/*Check if the lowestHighPrtyScoreList has one or more employees i.e.,if more than 1 member exist with
			 *  same lowest high priority score
			 */
			if(lowestHighPrtyScoreList.size()>1){
				minMediumScore=(Double)lowestHighPrtyScoreList.get(0).get("MEDIUM_SCORE");
				for(int i=1;i<lowestHighPrtyScoreList.size();i++){					
					if(minMediumScore>(Double)lowestHighPrtyScoreList.get(i).get("MEDIUM_SCORE")){
						minMediumScore=(Double)lowestHighPrtyScoreList.get(i).get("MEDIUM_SCORE");
					}
				}
				/*If more than one member  than compare the lowest medium priority score for each member. Add the 
				 * member with the lowest medium priority score to the lowestMediumPrtyScoreList.*/
				for(int i=0;i<lowestHighPrtyScoreList.size();i++){
					if((Double)lowestHighPrtyScoreList.get(i).get("MEDIUM_SCORE")==minMediumScore){
						lowestMediumPrtyScoreList.add(lowestHighPrtyScoreList.get(i));
					}
				}
				/*Check if the lowestMediumPrtyScoreList has one or more employees i.e.,if more than 1 member exist with
				 *  same lowest medium priority score
				 */
				if(lowestMediumPrtyScoreList.size()>1){
					minLowScore=(Double)lowestMediumPrtyScoreList.get(0).get("LOW_SCORE");
					for(int i=1;i<lowestMediumPrtyScoreList.size();i++){
						if(minLowScore>(Double)lowestMediumPrtyScoreList.get(i).get("LOW_SCORE")){
							minLowScore=(Double)lowestMediumPrtyScoreList.get(i).get("LOW_SCORE");
						}
					}
					
					/*If more than one member  than compare the lowest low priority score for each member. Add the 
					 * member with the lowest low priority score to the lowestPrtyScoreList.*/
					for(int i=0;i<lowestMediumPrtyScoreList.size();i++){
						if((Double)lowestMediumPrtyScoreList.get(i).get("LOW_SCORE")==minLowScore){
							lowestPrtyScoreList.add(lowestMediumPrtyScoreList.get(i));
						}
					}
					if(lowestPrtyScoreList.size()>1){
						List<Map<String, Object>> ticketList=editDAO.getMaximumTicketIDList(lowestPrtyScoreList);
						
						//Assign the ticket based on last ticket assigned time 													
						StringBuffer queryParams=new StringBuffer();
						for(int i=0;i<ticketList.size();i++){
							queryParams.append("'");
							queryParams.append(ticketList.get(i).get("TICKET_ID").toString());	
							if(i==ticketList.size()-1){
								queryParams.append("'");
							}else{
								queryParams.append("',");
							}							
						}
						assignedToEmployee=editDAO.getAssignedToBasedOnTicketAssignedTime(queryParams);
						if(assignedToEmployee==null){
							assignedToEmployee=lowestPrtyScoreList.get(0).get("EMPLOYEE_ID").toString();
						}
					}else{
						//Assign the ticket to Engineer with minLowScore-Lowest Score
						assignedToEmployee=lowestPrtyScoreList.get(0).get("EMPLOYEE_ID").toString();
						
					}
				}else{
					//Assign the ticket to Engineer with minMediumScore-Lowest Score
					assignedToEmployee=lowestMediumPrtyScoreList.get(0).get("EMPLOYEE_ID").toString();
					
				}
			}else{
				//Assign the ticket to Engineer with minHighScore-Lowest Score
				assignedToEmployee=lowestHighPrtyScoreList.get(0).get("EMPLOYEE_ID").toString();
				
			}
		}else{
			//Assign the ticket to Engineer with minTotalScore			
			assignedToEmployee=lowestTotalScoreList.get(0).get("EMPLOYEE_ID").toString();
			
		}
		
		
	return assignedToEmployee;	
	}
	
}
