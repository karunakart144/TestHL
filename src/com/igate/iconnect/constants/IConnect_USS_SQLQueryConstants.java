package com.igate.iconnect.constants;

public class IConnect_USS_SQLQueryConstants {
	
	public static final String GET_PENDING_WITH_DETAILS="SELECT TD.TICKET_ID,PMD.EMPLOYEE_ID,TD.DESCRIPTION,WSM.NAME 'STATUS',UD.NAME +'('+TD.REQUESTED_BY  +')' 'REQUESTED_BY' " 
 +" FROM IC_IHD_TICKET_DETAILS TD  "
           +" JOIN IC_WORKFLOW_STATE_MASTER WSM ON WSM.STATE_ID=TD.WORKFLOW_STATE "  
           +" AND  WSM.NAME like '%Approval%' "
           +" JOIN IC_PROJECT_MASTER PM ON  PM.STATUS=1  and  PM.PROJECT_ID=TD.PROJECT_ID "  
           +" JOIN IC_PROJECT_MEMBER_DETAILS PMD ON PMD.EMPLOYEE_ID=? AND PMD.IS_ACTIVE=1 "  
           +" and CONVERT(date,GETDATE()) between PMD.START_DATE and PMD.END_DATE " 
           +" AND PM.PROJECT_ID=PMD.PROJECT_ID " 
           +" JOIN IC_USER_DETAILS UD ON UD.EMPLOYEE_ID=TD.REQUESTED_BY AND UD.IS_ACTIVE=1 "
           +" and TD.TICKET_ID not in ( "       
          +" SELECT " 
           +" ISNULL(ticket.TICKET_ID, 'NA') AS 'TICKET_ID' " 
             +" FROM IC_IHD_TICKET_DETAILS AS ticket "  
            +" LEFT OUTER JOIN IC_WORKFLOW_STATE_MASTER AS workflow ON ticket.WORKFLOW_STATE = workflow.STATE_ID "  
                +" WHERE (workflow.NAME IN('Waiting For RO Approval') " 
                 +" AND (ticket.MANAGER_ID= PMD.EMPLOYEE_ID or (exists(select 1 from IC_DELEGATE_APPROVAL_DETAILS " 
                 +" where PROJECT_ID = ticket.PROJECT_ID and DELEGATE_TO = PMD.EMPLOYEE_ID and IS_ACTIVE = 1 and EMPLOYEE_ID = ticket.MANAGER_ID)))))"; 

}
