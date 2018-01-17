/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.constants;

public class COMMON_AttachmentSQLQueryConstants {

    public static final String SELECT_ATTACHMENTS_FOR_TICKET = "SELECT cat.name as FUNCTION_NAME,icattach.TICKET_ID as TICKET_ID,"
            + "icattach.REFERENCE_ID as REFERENCE_ID,"
            + "appmaster1.APPROVER_ID as NEXTAPPROVERID,"
            + "appmaster.DESCRIPTION as APPROVER,"
            + "appmaster1.DESCRIPTION as NEXTSTATE, "
            + "icattach.ATTACHMENT_NAME as ATTACHMENT_NAME,"
            + "icattach.ATTACHMENT_PATH as ATTACHMENT_PATH,"
            + "tikdet.CATEGORY_ID as CATEGORY_ID,"
            + "tikdet.SUB_CATEGORY_ID as SUBCATEGORY_ID,"
            + "tikdet.WORKFLOW_STATE as WORKFLOW_STATE,"
            + "ISNULL(icattach.VERIFIED_BY,'-') as VERIFIED_BY,"
            + "ISNULL(icuser1.NAME,'-') as VERIFIED_NAME,"
            + "ISNULL(icattach.VERIFICATION_COMMENTS,'-') as VERIFICATION_COMMENTS,"
            + "ISNULL(icattach.CREATED_BY,'-') as CREATED_BY,"
            + "ISNULL(icuser.NAME,'-') as CREATED_NAME,"
            + "icattach.CREATED_DATE as CREATED_DATE,"
            + "ISNULL(icattach.MODIFIED_BY,'-') as MODIFIED_BY,"
            + "ISNULL(icuser2.NAME,'-') as MODIFIED_NAME,"
            + "icattach.MODIFIED_DATE as MODIFIED_DATE,"
            + "IWSM.NAME as WORKFLOW_NAME,"
            +"tikdet.IS_EX_EMPLOYEE as IS_EX_EMPLOYEE,"
            +"icattach.IS_CHECKED as IS_CHECKED"
            + "          FROM  IC_IHD_TICKET_ATTACHMENT_DETAILS  icattach "
            + "          left outer join IC_USER_DETAILS icuser on icattach.CREATED_BY=icuser.NAME "
            + "          left outer join IC_USER_DETAILS icuser1 on icattach.VERIFIED_BY=icuser1.NAME "
            + "		   left outer join IC_USER_DETAILS icuser2 on icattach.MODIFIED_BY=icuser2.NAME "
            + "		   left outer join IC_IHD_APPROVER_MASTER appmaster on icattach.REFERENCE_ID = appmaster.APPROVER_ID"
            + "		   left outer join IC_IHD_TICKET_DETAILS tikdet on icattach.TICKET_ID=tikdet.TICKET_ID"
            + "		   left outer join IC_IHD_APPROVER_MASTER appmaster1 on appmaster1.WORKFLOW_STATE=tikdet.WORKFLOW_STATE "
            /***********ADDED by Poovamma(716302) to remove Attachment list duplication*************/
            + "		   and appmaster1.WORKFLOW_STATE= appmaster.APPROVER_ID,"	
            /***********END added by Poovamma(716302) to remove Attachment list duplication*********/
            //modified by 720307
            + "  IC_IHD_TICKET_DETAILS ticket,IC_IHD_CATEGORY_MASTER cat,IC_WORKFLOW_STATE_MASTER IWSM"
            + "		   where icattach.TICKET_ID=? and ticket.TICKET_ID=icattach.TICKET_ID and IWSM.STATE_ID= tikdet.WORKFLOW_STATE and cat.CATEGORY_ID=ticket.FUNCTION_ID and icattach.IS_ACTIVE=1 order by CREATED_DATE";

    public static final String SELECT_ATTACHMENTS_FOR_TCR_TICKET = "select tcrdetails.ISSUE_ID as ISSUE_ID,tcrdetails.ATTACHMENT_NAME as ATTACHMENT_NAME,tcrdetails.ATTACHMENT_PATH as ATTACHMENT_PATH, tcrdetails.APPROVER_ATTACHMENT_NAME as approver_ATTACHMENT_NAME, tcrdetails.APPROVER_ATTACHMENT_PATH as approver_ATTACHMENT_PATH,"
            + " ISNULL(tcrdetails.CREATED_BY,'-') as CREATED_BY,ISNULL(icuser.NAME,'-') as CREATED_NAME,tcrdetails.CREATED_DATE as CREATED_DATE,ISNULL(tcrdetails.MODIFIED_BY,'-') as MODIFIED_BY,ISNULL(icuser1.NAME,'-') as MODIFIED_NAME,tcrdetails.MODIFIED_DATE as MODIFIED_DATE "
            + "from IC_TCR_TICKET_DETAILS tcrdetails left outer join IC_USER_DETAILS icuser on tcrdetails.CREATED_BY=icuser.EMPLOYEE_ID left outer join IC_USER_DETAILS icuser1 on tcrdetails.MODIFIED_BY = icuser1.EMPLOYEE_ID where tcrdetails.ISSUE_ID=? ";
            //+ "AND tcrdetails.ATTACHMENT_NAME IS NOT NULL ";
    
    public static final String SELECT_ATTACHMENTS_FOR_MASTER_TICKET="SELECT cat.name as FUNCTION_NAME,icattach.TICKET_ID as TICKET_ID," +
    		"	icattach.ATTACHMENT_NAME as ATTACHMENT_NAME," +
    		"	icattach.ATTACHMENT_PATH as ATTACHMENT_PATH," +
    		"	tikdet.CATEGORY_ID as CATEGORY_ID," +
    		"	tikdet.SUB_CATEGORY_ID as SUBCATEGORY_ID," +
    		"	tikdet.WORKFLOW_STATE as WORKFLOW_STATE," +
    		"	ISNULL(icattach.CREATED_BY,'-') as CREATED_BY," +
    		"	ISNULL(icuser.NAME,'-') as CREATED_NAME," +
    		"	icattach.CREATED_DATE as CREATED_DATE," +
    		"	ISNULL(icattach.MODIFIED_BY,'-') as MODIFIED_BY," +
    		"	ISNULL(icuser2.NAME,'-') as MODIFIED_NAME," +
    		"	icattach.MODIFIED_DATE as MODIFIED_DATE" +
    		"   FROM  IC_IHD_Master_TICKET_ATTACHMENT_DETAILS  icattach" +
    		"   left outer join IC_USER_DETAILS icuser on icattach.CREATED_BY=icuser.NAME" +
    		"   left outer join IC_USER_DETAILS icuser1 on icattach.VERIFIED_BY=icuser1.NAME" +
    		"   left outer join IC_USER_DETAILS icuser2 on icattach.MODIFIED_BY=icuser2.NAME" +
    		"   left outer join IC_IHD_MASTER_TICKET_DETAILS tikdet on icattach.TICKET_ID=tikdet.TICKET_ID" +
    		"   left outer join IC_IHD_APPROVER_MASTER appmaster1 on appmaster1.WORKFLOW_STATE=tikdet.WORKFLOW_STATE," +
    		"	IC_IHD_MASTER_TICKET_DETAILS ticket,IC_IHD_CATEGORY_MASTER cat  where icattach.TICKET_ID=?" +
    		"	and ticket.TICKET_ID=icattach.TICKET_ID and cat.CATEGORY_ID=ticket.FUNCTION_ID order by CREATED_DATE";

}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 13,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

