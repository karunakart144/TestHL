package com.igate.iconnect.constants;

public class HELPDESK_EmployeeProfileSQLQueryConstants {
	
	//Added for Employee Profile-by 717170

	public static final String IC_EMPLOYEE_PROFILE = "select Distinct (IU.EMPLOYEE_ID),IU.NAME,isnull(IU.EMAIL_ADDRESS,'') 'EMAIL_ADDRESS',isnull(IU.GRADE_DESC,'') as 'GRADE', "
			+" loc.CITY as 'LOCATION',isnull(iu.MOBILE_NUMBER,'') 'MOBILE_NUMBER',isnull(dm.DESIGNATION_NAME,'') 'DESIGNATION',isnull(iu1.NAME+'('+IU.REPORTING_MANAGER+')','') as 'REPORTING_MANAGER_ID', "
			+" 'ORGANIZATION'=CASE IU.ORGANIZATION  WHEN  'I' THEN 'iGATE' ELSE 'PATNI' END,'RPT MGR ORGANIZATION'=case IU1.ORGANIZATION when 'I' then 'iGATE' else 'PATNI' END, "
			+" isnull(dept.NAME,'') as 'DEPT_NAME',IS_ACTIVE=CASE IU.IS_ACTIVE WHEN 1 THEN 'Active' ELSE 'Inactive' END "
			+" from IC_USER_DETAILS IU "   
			+" left outer join IC_USER_DETAILS IU1 on IU1.EMPLOYEE_ID=IU.REPORTING_MANAGER and IU1.IS_ACTIVE=1 left outer join IC_DU_MASTER dept on IU.DU_ID=dept.DU_ID " 
			+" left outer join IC_LOCATION_MASTER loc on loc.LOCATION_ID=IU.LOCATION_ID left outer join IC_DESIGNATION_MASTER DM on dm.DESIGNATION_ID=IU.DESIGNATION_ID " 
			+" where IU.EMPLOYEE_ID=? AND IU.IS_ACTIVE=1";

}
