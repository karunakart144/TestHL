/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

public final class TECHCR_SQLQueryConstants {

    public static final String INSERT_IC_TCR_TICKET_DETAILS = "insert into IC_TCR_TICKET_DETAILS(ACTION_ID,GROUP_ID,ASSIGNED_TO,"
            + "CATEGORY_ID,SCHEDULED_START_DATE,SCHEDULED_END_DATE,SERVER_DEVICE,IP_ADDRESS,TICKET_TYPE,SUBJECT,DESCRIPTION,ATTACHMENT_PATH,"
            + "SEVERITY_ID,IS_CLIENT_INITIATED,WORKFLOW_STATE,ROLLBACK_COMMENTS,RISK_INVOLVED,VENDOR_INVOLVED,"
            + "CREATED_BY,CREATED_DATE,SUBCATEGORY_ID,ATTACHMENT_NAME,SOURCE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_IC_TCR_RISK_DETAILS = "insert into IC_TCR_RISK_DETAILS(ISSUE_ID,RISK_DETAILS,"
            + "CREATED_BY,CREATED_DATE)values(?,?,?,?)";

    public static final String INSERT_IC_TCR_VENDOR_DETAILS = "insert into IC_TCR_VENDOR_DETAILS(ISSUE_ID,VENDOR_NAME,ENGINEER_NAME,LOCATION,"
            + "CREATED_BY,CREATED_DATE)values(?,?,?,?,?,?)";

    public static final String INSERT_IC_TCR_RISK_MITIGATION = "insert into IC_TCR_RISK_MITIGATION(RISK_ID,MITIGATION,"
            + "CREATED_BY,CREATED_DATE)values(?,?,?,?)";

    public static final String INSERT_IC_TCR_RISK_CONTINGENCY = "insert into IC_TCR_RISK_CONTIGENCY(RISK_ID,CONTIGENCY,"
            + "CREATED_BY,CREATED_DATE)values(?,?,?,?)";
    public static final String INSERT_IC_TCR_APPROVAL_DETAILS = "insert into IC_TCR_TICKET_APPROVAL_DETAILS(ISSUE_ID,APPROVER_ID,"
            + "CREATED_BY,CREATED_DATE) values(?,?,?,?)";
    public static final String INSERT_IC_TCR_RCA_DETAILS = "insert into IC_TCR_RCA_DETAILS(ISSUE_ID"
            + ",CREATED_BY,CREATED_DATE)values(?,?,?)";

    public static final String SELECT_TCR_DETAILS = "SELECT TD.ISSUE_ID AS 'ISSUE_ID' "
            + " ,TD.LOCATION_ID AS 'LOCATION_ID' "
            + " ,AM.DESCRIPTION AS 'ACTION_DESC' "
            + " ,TD.ACTION_ID AS 'ACTION_ID' "
            + " ,GM.NAME AS 'GROUP_NAME' "
            + " ,TD.GROUP_ID AS 'GROUP_ID' "
            + " ,UD.NAME +'( '+UD.EMPLOYEE_ID+' )' AS 'ASSIGNED_EMPIDANDNAME' "
            + " ,UD.NAME +'( '+UD.EMPLOYEE_ID+' )'+' '+lm1.CITY AS 'ASSIGNED_EMPID_NAME_LOC' "
            + " ,TD.ASSIGNED_TO AS 'ASSIGNED_TO' "
            + " ,CM.NAME as 'PARENT_CATEGORY_NAME' "
            + " ,TD.CATEGORY_ID as 'PARENT_CATEGORY_ID' "
            + " ,TD.SCHEDULED_START_DATE AS 'SSTARTDATE' "
            + " ,TD.SCHEDULED_END_DATE AS 'SENDDATE' "
            + " ,TD.SERVER_DEVICE AS 'SOURCE' "
            + " ,TD.ROLLBACK_CLOSED_COMMENTS AS 'ROLLBACK_CLOSED_COMMENTS' "
            + " ,TD.IP_ADDRESS AS 'IP_ADDRESS' "
            + " ,TD.VENDOR_INVOLVED AS 'VENDOR_INVOLVED' "
            + " ,TD.TICKET_TYPE AS 'TICKET_TYPE' "
            + " ,TD.SUBJECT AS 'SUBJECT' "
            + " ,TD.DESCRIPTION AS 'DESCRIPTION' "
            + " ,TD.ATTACHMENT_PATH AS 'ATTACHMENT_PATH' "
            + " ,SM.DESCRIPTION AS 'SEVERITY_NAME' "
            + " ,TD.SEVERITY_ID AS 'SEVERITY_ID' "
            + " ,TD.IS_CLIENT_INITIATED AS 'IS_CLIENT_INITIATED' "
            + " ,WSM.NAME AS 'WORKFLOW_STATE_NAME' "
            + " ,TD.WORKFLOW_STATE AS 'WORKFLOW_STATE_ID' "
            + " ,TD.EFFORT AS 'EFFORT' "
            + " ,VD.VENDOR_NAME AS 'VENDOR_NAME'"
            + " ,VD.ENGINEER_NAME AS 'ENG_NAME'"
            + " ,VD.LOCATION AS 'VENDOR_LOC' "
            + " ,TD.ROLLBACK_COMMENTS AS 'ROLLBACK_COMMENTS' "
            + " ,TD.ENGINEER_COMMENTS AS 'ENGINEER_COMMENTS' "
            + " ,TD.RISK_INVOLVED AS 'RISK_INVOLVED' "
            + " ,TD.RCA_REQUIRED AS 'RCA_REQUIRED' "
            + " ,UDCREATEDBY.NAME + '('+UDCREATEDBY.EMPLOYEE_ID+')' AS 'CREATEDBY_NAME_EMPID' "
            + " ,UDCREATEDBY.NAME + '('+UDCREATEDBY.EMPLOYEE_ID+')'+' '+UDCREATEDBYLM.CITY AS 'CREATEDBY_NAME_EMPID_LOC' "
            + " ,TD.CREATED_BY  AS 'CREATEDBY_EMPID' "
            + " ,TD.CREATED_DATE AS 'CREATION_DATE' "
            + " ,UDMODIFIEDBY.NAME+'('+UDMODIFIEDBY.EMPLOYEE_ID+')' AS 'MODIFIEDBY_NAME_EMPID' "
            + " ,TD.MODIFIED_BY  "
            + " ,TD.MODIFIED_DATE "
            + " ,SCM.NAME AS 'CHILD_CATEGORY_NAME',TD.SUBCATEGORY_ID as 'CHILD_CATEGORY_ID' "
            + " ,ud1.NAME + '( '+UD1.EMPLOYEE_ID+' )' AS 'APPROVERIDNAME'   "
            + " ,ud1.NAME +'( '+UD1.EMPLOYEE_ID+' )'+' '+lm.CITY AS 'APPROVERIDNAME_LOC' "
            + ",tam.EMPLOYEE_ID AS 'APPROVER_ID' "
            + ",ttad.COMMENTS AS 'APPROVER_COMMENTS' "
            + " FROM ((((((((IC_TCR_TICKET_DETAILS TD LEFT OUTER JOIN "
            + " IC_USER_DETAILS UDMODIFIEDBY ON TD.MODIFIED_BY = UDMODIFIEDBY.EMPLOYEE_ID)"
            + "left outer join IC_TCR_CATEGORY_MASTER SCM on SCM.CATEGORY_ID=TD.SUBCATEGORY_ID)"
            + "left outer join IC_TCR_ACTION_MASTER AM on AM.ACTION_ID = TD.ACTION_ID) left outer join "
            + "IC_TCR_GROUP_MASTER GM on GM.GROUP_ID = TD.GROUP_ID)left outer join "
            + "IC_USER_DETAILS UD on UD.EMPLOYEE_ID = TD.ASSIGNED_TO)left outer join "
            + "IC_TCR_CATEGORY_MASTER CM on CM.CATEGORY_ID = TD.CATEGORY_ID)left outer join "
            + "IC_SEVERITY_MASTER SM on SM.SEVERITY_ID=TD.SEVERITY_ID)left outer join "
            + "IC_WORKFLOW_STATE_MASTER WSM on WSM.STATE_ID=TD.WORKFLOW_STATE)left outer join "
            + "IC_USER_DETAILS UDCREATEDBY on UDCREATEDBY.EMPLOYEE_ID=TD.CREATED_BY left outer join "
            + " IC_TCR_TICKET_APPROVAL_DETAILS ttad on ttad.ISSUE_ID=TD.ISSUE_ID left outer join  "
            + "IC_TCR_APPROVER_MASTER tam on tam.APPROVER_ID=ttad.APPROVER_ID left outer join "
            + "IC_USER_DETAILS ud1 on ud1.EMPLOYEE_ID=tam.EMPLOYEE_ID "
            + "left outer join IC_LOCATION_MASTER lm on lm.LOCATION_ID=ud1.LOCATION_ID "
            + "left outer join  IC_LOCATION_MASTER lm1 on lm1.LOCATION_ID=UD.LOCATION_ID "
            + "left outer join  IC_TCR_VENDOR_DETAILS VD on VD.ISSUE_ID=TD.ISSUE_ID "
            + "left outer join  IC_LOCATION_MASTER UDCREATEDBYLM on UDCREATEDBYLM.LOCATION_ID=UDCREATEDBY.LOCATION_ID "
            + "where  td.ISSUE_ID=?";
    public static final String TCR_LOCKED_DETAILS = "select ILTD.LOCKED_BY,IUD.NAME from IC_LOCKED_TICKET_DETAILS ILTD,IC_USER_DETAILS IUD where IUD.EMPLOYEE_ID=ILTD.LOCKED_BY AND ILTD.MENU_ID=? AND ILTD.REFERENCE_ID=?";

    public static final String TCR_INSERT_LOCKED_DETAILS = "insert into IC_LOCKED_TICKET_DETAILS(REFERENCE_ID"
            + ",MENU_ID,DATA,LOCKED_BY,LOCKED_DATE) values(?,?,?,?,?)";
    public static final String SELECT_TCR_RCA_DETAILS = "Select RD.ISSUE_ID,RD.RCA_ID,RD.SOLUTION,RD.PREVENTIVE_ACTION   "
            + "from IC_TCR_RCA_DETAILS RD,IC_TCR_TICKET_DETAILS TD "
            + "where RD.ISSUE_ID=TD.ISSUE_ID AND RD.ISSUE_ID=?";
    public static final String SELECT_TCR_RISK_DETAILS = "Select TRD.ISSUE_ID,TRD.RISK_ID,TRD.RISK_DETAILS"
            + ",RM.MITIGATION,RC.CONTIGENCY from ((IC_TCR_RISK_DETAILS TRD join "
            + "IC_TCR_TICKET_DETAILS TD on TRD.ISSUE_ID=TD.ISSUE_ID)"
            + "left outer join IC_TCR_RISK_MITIGATION RM on TRD.RISK_ID=RM.RISK_ID) "
            + "left outer join  IC_TCR_RISK_CONTIGENCY RC on TRD.RISK_ID=RC.RISK_ID "
            + " where  TRD.ISSUE_ID=?";
    public static final String SELECT_TCR_VERIFY_APPROVED_STATUS = "Select PREVIOUS_STATE,CURRENT_STATE "
            + "from IC_AUDIT_DETAILS where REFERENCE_ID=? AND MENU_ID=?";
    public static final String SELECT_TCR_GET_EMPLOYEE_ID = "Select EMPLOYEE_ID from "
            + "IC_TCR_APPROVER_MASTER AM,IC_TCR_TICKET_APPROVAL_DETAILS AD"
            + " where AM.APPROVER_ID=AD.APPROVER_ID " + "AND AD.ISSUE_ID=?";
    public static final String SELECT_TCR_GET_ASSIGNED_TO = "Select ASSIGNED_TO "
            + "from IC_TCR_TICKET_DETAILS where ISSUE_ID=?";
    public static final String SELECT_TCR_GET_CREATED_BY = "Select CREATED_BY "
            + "from IC_TCR_TICKET_DETAILS where ISSUE_ID=?";

    public static final String INSERT_IC_NOC_ALERTS_TICKET_DETAILS = "insert into IC_TCR_TICKET_DETAILS(ASSIGNED_TO,"
            + "SERVER_DEVICE,SUBJECT,DESCRIPTION,SEVERITY_ID,WORKFLOW_STATE,"
            + "CREATED_BY,CREATED_DATE) values(?,?,?,?,?,?,?,?)";
    public static final String IC_TCR_NOC_ALERTS_DELETE = "DELETE IC_NOC_ALERT_DETAILS WHERE MAIL_ID=?";
    public static final String IC_TCR_NOC_ALERTS_DISCARD_UPDATE = "UPDATE IC_NOC_ALERT_DETAILS SET STATUS=? WHERE MAIL_ID=?";

    public static final String UPDATE_IC_TCR_TICKET_DETAILS_APPROVAL_STATUS = "UPDATE IC_TCR_TICKET_DETAILS SET WORKFLOW_STATE=? WHERE ISSUE_ID=?";

    public static final String UPDATE_IC_TCR_TICKET_APPROVAL_DETAILS = "UPDATE IC_TCR_TICKET_APPROVAL_DETAILS SET COMMENTS=? WHERE ISSUE_ID=?";

    public static final String IC_EMPLOYEE_DETAILS_SQL = "select * from IC_USER_DETAILS where EMPLOYEE_ID=? AND IS_ACTIVE=1";
    
    public static final String IC_EMPLOYEE_DETAILS_VIEW_SQL = "SELECT EMPLOYEE_ID,NAME,EMAIL_ADDRESS from IC_USER_DETAILS where EMPLOYEE_ID=? ";
    
    public static final String IC_TCR_PREV_WORKFLOW_STATUS = "SELECT SM.NAME FROM IC_TCR_TICKET_DETAILS TD "
		+ "LEFT OUTER JOIN IC_WORKFLOW_STATE_MASTER SM ON SM.STATE_ID=TD.WORKFLOW_STATE "
		+ " WHERE TD.ISSUE_ID=?";
    
    public static final String GET_SLA="select CONVERT(VARCHAR(10), dateadd(hour,TCR_SLA_TIME,?), 101) + ' ' + RIGHT(CONVERT(VARCHAR, dateadd(hour,TCR_SLA_TIME,?), 108), 10) 'SLA' from IC_SEVERITY_MASTER where SEVERITY_ID=?";
    
    


}
