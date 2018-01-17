package com.igate.iconnect.constants;

public class MASTER_SQLQueryConstants {
	
	public static final String INSERT_MASTER_TICKET_QUERY="INSERT INTO [IC_IHD_MASTER_TICKET_DETAILS]([REQUESTED_BY],[FUNCTION_ID],[CATEGORY_ID],[SUB_CATEGORY_ID],[COPY_TO],[LOCATION_ID],[CONTACT_NO],[SUBJECT],[DESCRIPTION],[WORKFLOW_STATE],[ASSIGNED_GROUP],[ASSIGNED_TO],[ECT],[VERSION_NO],[CREATED_BY],[CREATED_DATE],[IFIRST_TICKET_ID])" +
			" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String SELECT_MASTER_TICKET_LIST="SELECT" +
			" ticketDet.TICKET_ID ,ticketDet.REQUESTED_BY ,ticketDet.FUNCTION_ID ," +
			" ticketDet.CATEGORY_ID ,ticketDet.SUB_CATEGORY_ID ,ticketDet.COPY_TO ," +
			" ticketDet.LOCATION_ID ,ticketDet.CONTACT_NO ,ticketDet.SUBJECT ,ticketDet.DESCRIPTION ,ticketDet.WORKFLOW_STATE ,stMaster.NAME as WORKFLOW_NAME," +
			" ticketDet.ASSIGNED_GROUP ," +
			" ticketDet.ASSIGNED_TO ,ticketDet.ECT ,ticketDet.CLOSED_DATE ,ticketDet.RESOLUTION_COMMENTS ," +
			" ticketDet.VERSION_NO ,userDet.NAME+'('+ticketDet.CREATED_BY+')' as CREATED_NAME, ticketDet.CREATED_BY ,ticketDet.CREATED_DATE ," +
			" ticketDet.MODIFIED_BY ,ticketDet.MODIFIED_DATE  FROM IC_IHD_MASTER_TICKET_DETAILS ticketDet," +
			"	IC_WORKFLOW_STATE_MASTER stMaster,IC_USER_DETAILS userDet" +
			"	WHERE ticketDet.FUNCTION_ID =(SELECT FUNCTION_ID FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID=?)" +
			" 	and ticketDet.WORKFLOW_STATE NOT IN (SELECT STATE_ID from IC_WORKFLOW_STATE_MASTER where NAME='Resolved/Closed')" +
			" 	and userDet.EMPLOYEE_ID=ticketDet.CREATED_BY" +
			"	 and stMaster.STATE_ID=ticketDet.WORKFLOW_STATE";
	
	/*public static final String SELECT_MASTER_TICKET_LIST="SELECT TICKET_ID ,REQUESTED_BY ,FUNCTION_ID ,CATEGORY_ID ,SUB_CATEGORY_ID ,COPY_TO ,LOCATION_ID ,CONTACT_NO ,SUBJECT ,DESCRIPTION ,WORKFLOW_STATE ,ASSIGNED_GROUP ,ASSIGNED_TO ,ECT ,CLOSED_DATE ,RESOLUTION_COMMENTS ,VERSION_NO ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE  FROM IC_IHD_MASTER_TICKET_DETAILS " +
			"	WHERE FUNCTION_ID =(SELECT FUNCTION_ID FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID=?) and WORKFLOW_STATE NOT IN (SELECT STATE_ID from IC_WORKFLOW_STATE_MASTER where NAME='Resolved/Closed')";
*/	
	public static final String UPDATE_CHILD_TO_MASTER_QUERY="UPDATE IC_IHD_TICKET_DETAILS SET REFERENCE_ID=?,WORKFLOW_STATE=?,ECT=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() WHERE TICKET_ID=?";

	public static final String IC_HD_INSERT_ATTACHMENTS = "insert into IC_IHD_MASTER_TICKET_ATTACHMENT_DETAILS(TICKET_ID,ATTACHMENT_NAME,ATTACHMENT_PATH,CREATED_BY,CREATED_DATE) values (?,?,?,?,?)";
	
	public static final String IC_IHD_CHILD_TICKET_DETAIL=" SELECT TICKET_ID,FUNCTION_ID,mst.NAME as 'categoryName', WORKFLOW_STATE,stMaster.NAME as 'workflowName',ECT FROM IC_IHD_TICKET_DETAILS  det,IC_IHD_CATEGORY_MASTER mst,IC_WORKFLOW_STATE_MASTER stMaster  where TICKET_ID=? " +
			"  and mst.CATEGORY_ID=det.FUNCTION_ID and stMaster.STATE_ID=det.WORKFLOW_STATE";
	
	public static final String INSERT_IC_IHD_CHILD_DELINK_PREV_DET="INSERT INTO IC_IHD_TICKET_DELINK_DETAILS(TICKET_ID,PREVIOUS_STATE,PREVIOUS_ECT,CREATED_BY,CREATED_DATE) values (?,?,?,?,GETDATE())";
	
	public static final String SELECT_MASTER_TICKET_DET="select TICKET_ID,WORKFLOW_STATE,ECT,ASSIGNED_GROUP,ASSIGNED_TO,SUBJECT,DESCRIPTION, mst.NAME as 'WfName' FROM IC_IHD_MASTER_TICKET_DETAILS det," +
			" IC_WORKFLOW_STATE_MASTER mst where TICKET_ID=? and mst.STATE_ID=det.WORKFLOW_STATE";
	
	public static final String SELECT_IS_CHILD_LINKED_DET="select TICKET_ID,REFERENCE_ID from IC_IHD_TICKET_DETAILS where REFERENCE_ID in (select TICKET_ID from IC_IHD_MASTER_TICKET_DETAILS)and TICKET_ID=?";
	
	public static final String UPDATE_DELINK_DETAIL="UPDATE IC_IHD_TICKET_DETAILS set REFERENCE_ID=NULL," +
			"WORKFLOW_STATE=(SELECT PREVIOUS_STATE FROM IC_IHD_TICKET_DELINK_DETAILS where TICKET_ID=? )," +
			"ECT=(SELECT PREVIOUS_ECT FROM IC_IHD_TICKET_DELINK_DETAILS where TICKET_ID=? ),MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where TICKET_ID=?";
	
	public static final String DELETE_DELINK_DETAIL="DELETE FROM IC_IHD_TICKET_DELINK_DETAILS WHERE TICKET_ID=?";
	
	public static final String SELECT_MASTER_DETAILS="SELECT ticket.TICKET_ID as 'Ticket#',workflow.NAME as 'Status',workflow.STATE_ID as 'STATE_ID'," +
			" ticket.REQUESTED_BY as 'Requested By',icrequser.NAME+'('+ticket.REQUESTED_BY+')' as 'RequestedName',ticket.SUBJECT as 'Subject',ticket.DESCRIPTION as 'Description',ticket.COPY_TO as 'CC To'," +
			" ticket.ASSIGNED_GROUP as 'GroupID'," +
			" ihdGroup.NAME as 'Assigned Group',ticket.ASSIGNED_TO as 'Assigned To',iccAssgndTouser.NAME as 'Assigned To Name',ticket.CREATED_DATE as 'Creation Date'," +
			" ticket.ECT as 'ECT',ticket.LOCATION_ID as 'LocationID'," +
			" ticket.CREATED_BY as 'Created By',ticket.CONTACT_NO as 'Contact Number(Primary)'," +
			" ticket.RESOLUTION_COMMENTS as 'Resolution Comments'," +
			" ticket.FUNCTION_ID as 'FunctionID',ihdFuncCat.NAME as 'Function Name'," +
			" ticket.CATEGORY_ID as 'CategoryID',ihdCat.NAME as 'Category Name',ticket.SUB_CATEGORY_ID as 'SubCategoryID'," +
			" ihdSubCat.NAME as 'SubCategory Name', icrequser.NAME as 'Requestor Name',iccrtuser.NAME as 'Creator Name'," +
			" ticket.CLOSED_DATE as CLOSED_DATE," +
			" ticket.VERSION_NO as VERSION_NO,isnull(ticket.IFIRST_TICKET_ID,'') as 'IFIRST_TICKET_ID'," +
			" (select min(CHANGED_DATE) " +
			" from IC_AUDIT_DETAILS where CURRENT_STATE='HelpDesk Queue' and" +
			" PREVIOUS_STATE <> 'HelpDesk Queue' and PREVIOUS_STATE is not null" +
			" and REFERENCE_ID=''+ticket.TICKET_ID+'') as 'Approved Date',ihdSubCat.IS_CHANGE_REQUEST as 'Change Request'" +
			" FROM IC_IHD_MASTER_TICKET_DETAILS ticket left outer join IC_IHD_GROUP_MASTER ihdGroup" +
			" ON ticket.ASSIGNED_GROUP = ihdGroup.GROUP_ID  left outer join IC_WORKFLOW_STATE_MASTER workflow" +
			" ON ticket.WORKFLOW_STATE = workflow.STATE_ID left outer join IC_IHD_CATEGORY_MASTER ihdCat" +
			" ON ticket.CATEGORY_ID = ihdCat.CATEGORY_ID left outer join IC_IHD_CATEGORY_MASTER ihdFuncCat" +
			" ON ticket.FUNCTION_ID = ihdFuncCat.CATEGORY_ID" +
			" left outer join IC_IHD_CATEGORY_MASTER ihdSubCat" +
			" ON ticket.SUB_CATEGORY_ID = ihdSubCat.CATEGORY_ID left outer join IC_USER_DETAILS icrequser" +
			" ON ticket.REQUESTED_BY = icrequser.EMPLOYEE_ID left outer join IC_USER_DETAILS iccrtuser" +
			" ON  ticket.CREATED_BY = iccrtuser.EMPLOYEE_ID  left outer join IC_USER_DETAILS iccAssgndTouser ON ticket.ASSIGNED_TO =iccAssgndTouser.EMPLOYEE_ID  where TICKET_ID=";

	
	public static final String SELECT_ALL_CHILD_DET_LINKED_TO_MASTER="SELECT child.TICKET_ID,child.FUNCTION_ID,mst.NAME as 'FUNCTION_NAME',child.SUBJECT,child.DESCRIPTION,delinkDet.PREVIOUS_ECT as ECT,mastr.WORKFLOW_STATE ,stMster.NAME as 'WF_NAME' " +
	"	FROM IC_IHD_TICKET_DETAILS child,IC_IHD_MASTER_TICKET_DETAILS mastr, IC_IHD_CATEGORY_MASTER mst,IC_IHD_TICKET_DELINK_DETAILS delinkDet," +
	"	IC_WORKFLOW_STATE_MASTER stMster" +
	"	WHERE child.REFERENCE_ID=mastr.TICKET_ID" +
	"	AND delinkDet.TICKET_ID=child.TICKET_ID" +
	"	AND mst.CATEGORY_ID=child.FUNCTION_ID" +
	"	AND stMster.STATE_ID=child.WORKFLOW_STATE" +
	"	AND mastr.TICKET_ID=?";
	
	public static final String IC_INSERT_LOCK_DETAILS = "insert into IC_LOCKED_TICKET_DETAILS(REFERENCE_ID,MENU_ID,DATA,LOCKED_BY,LOCKED_DATE) values(?,?,?,?,?)";
	
	public static final String IC_LOCKED_TICKET_DETAILS = "select ILTD.LOCKED_BY,IUD.NAME from IC_LOCKED_TICKET_DETAILS ILTD,IC_USER_DETAILS IUD where IUD.EMPLOYEE_ID=ILTD.LOCKED_BY AND ILTD.MENU_ID=? AND ILTD.REFERENCE_ID=?";
	
/*	public static final String SELECT_IC_DELINK_DETAILS="SELECT TICKET_ID,PREVIOUS_STATE,WSM.NAME as 'workflowName',PREVIOUS_ECT" +
			" FROM IC_IHD_TICKET_DELINK_DETAILS TLD,IC_WORKFLOW_STATE_MASTER WSM" +
			"	WHERE TLD.PREVIOUS_STATE=WSM.STATE_ID AND TICKET_ID=? ";*/
	
	public static final String SELECT_IC_DELINK_DETAILS="SELECT TLD.TICKET_ID,PREVIOUS_STATE,WSM.NAME as 'workflowName',PREVIOUS_ECT,masterTickt.ASSIGNED_GROUP,masterTickt.ASSIGNED_TO,grp.NAME as 'groupName',iud.NAME as 'employeeName'" +
			" FROM IC_IHD_TICKET_DELINK_DETAILS TLD,IC_WORKFLOW_STATE_MASTER WSM," +
			" IC_IHD_MASTER_TICKET_DETAILS masterTickt,IC_IHD_TICKET_DETAILS ticketDet,IC_IHD_GROUP_MASTER grp,IC_USER_DETAILS iud" +
			"	WHERE TLD.PREVIOUS_STATE=WSM.STATE_ID" +
			"	AND ticketDet.TICKET_ID=TLD.TICKET_ID" +
			"	and ticketDet.REFERENCE_ID=masterTickt.TICKET_ID" +
			"	AND iud.EMPLOYEE_ID=masterTickt.ASSIGNED_TO" +
			"	AND grp.GROUP_ID=masterTickt.ASSIGNED_GROUP" +
			"	AND TLD.TICKET_ID=?";
	
	
	public static final String IC_USER_DETAILS = "SELECT EMPLOYEE_ID,NAME,EMAIL_ADDRESS,NAME as 'USER_NAME' FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=?";
}
