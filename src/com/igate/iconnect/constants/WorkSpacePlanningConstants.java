package com.igate.iconnect.constants;

public class WorkSpacePlanningConstants {

	public static final String IC_DU_MASTER_var = "icDuMaster";
	public static final String SELECT_DU_LIST ="Select DU_ID,UPPER(NAME) from IC_DU_MASTER WHERE DU_ID IN (Select DU from IC_PROJECT_MASTER where PROJECT_ID in" 
                 + "("
                 + " select  PMD.PROJECT_ID "
                 + " from ic_user_details us,IC_USER_DETAILS re,IC_PROJECT_MEMBER_DETAILS PMD,IC_PROJECT_MASTER PM"
                 + " left outer join IC_USER_DETAILS usd on usd.EMPLOYEE_ID = PM.MANAGER_ID"
                 + " where us.EMPLOYEE_ID = '58545' AND PMD.EMPLOYEE_ID = us.EMPLOYEE_ID"
                 + " AND PMD.PROJECT_ID = PM.PROJECT_ID AND PM.STATUS=1 and PMD.START_DATE <= GETDATE()" 
                 + " and PMD.END_DATE >= GETDATE() AND re.EMPLOYEE_ID = us.REPORTING_MANAGER"
                 + ") and STATUS='1')";
	public static final String SELECT_SBU_LIST =
		"   Select a.L6_BEF_CODE as ID ,UPPER(a.L6_BEF_NAME) as Name ,a.L6_BEF_HEAD as Head from (Select L6_BEF_CODE,L6_BEF_NAME,L6_BEF_HEAD from IC_SU_ORG_BEF_MASTER" +
		"	UNION" +
		"	Select L5_VERTICAL_CODE,L5_VERTICAL_NAME,L5_VERTICAL_HEAD from IC_SU_ORG_VERTICAL_MASTER) a " +
		"   where a.L6_BEF_HEAD= ?";
	
		
	
	//Commenting this code: This provides the list of all SBUs of an Employee
	/*public static final String SELECT_LOGGEDIN_SBU="Select distinct SBU_ID,UPPER(L6_SBU_NAME) as SBU_NAME,BU_ID,UPPER(BU_DESCRIPTION) as BU_DESCRIPTION,SBU_HEAD,NAME as SBU_HEAD_NAME from IC_SU_ORG_SBU_MASTER a,IC_USER_DETAILS iud WHERE BU_ID IN (Select "+
                        " mapping.L5_VERTICAL_CODE "+
                        " from IC_SU_ORG_DEPT_MASTER deptMaster, IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping "+
                        " WHERE deptMaster.DEPT_ID=mapping.DEPT_ID and mapping.DEPT_ID in (Select DU_ID from "+ 
                        " IC_USER_DETAILS WHERE EMPLOYEE_ID= ?)) and a.SBU_HEAD=iud.EMPLOYEE_ID order by SBU_NAME";*/
	//END : Commenting this code: This provides the list of all SBUs of an Employee
	//Adding  this code: This provides the list of the SBU to which an  Employee is mapped
/*	public static final String SELECT_LOGGEDIN_SBU="IF EXISTS" +
			"  (SELECT DISTINCT SBU_ID," +
			"                   UPPER(L6_SBU_NAME) AS SBU_NAME," +
			"                   BU_ID," +
			"                   UPPER(BU_DESCRIPTION) AS BU_DESCRIPTION," +
			"                   SBU_HEAD," +
			"                   NAME AS SBU_HEAD_NAME" +
			"   FROM IC_SU_ORG_SBU_MASTER a," +
			"        IC_USER_DETAILS iud" +
			"   WHERE BU_ID IN" +
			"       (SELECT mapping.L5_VERTICAL_CODE" +
			"        FROM IC_SU_ORG_DEPT_MASTER deptMaster," +
			"             IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping" +
			"        WHERE deptMaster.DEPT_ID=mapping.DEPT_ID" +
			"          AND mapping.DEPT_ID IN" +
			"            (SELECT DU_ID" +
			"             FROM IC_USER_DETAILS" +
			"             WHERE EMPLOYEE_ID= ?))" +
			"     AND a.SBU_HEAD=iud.EMPLOYEE_ID" +
			"     AND iud.EMPLOYEE_ID=?) BEGIN" +
			"	SELECT DISTINCT SBU_ID," +
			"                UPPER(L6_SBU_NAME) AS SBU_NAME," +
			"                BU_ID," +
			"                UPPER(BU_DESCRIPTION) AS BU_DESCRIPTION," +
			"                SBU_HEAD," +
			"                NAME AS SBU_HEAD_NAME" +
			" FROM IC_SU_ORG_SBU_MASTER a," +
			"     IC_USER_DETAILS iud" +
			"	WHERE BU_ID IN" +
			"    (SELECT mapping.L5_VERTICAL_CODE" +
			"     FROM IC_SU_ORG_DEPT_MASTER deptMaster," +
			"          IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping" +
			"     WHERE deptMaster.DEPT_ID=mapping.DEPT_ID" +
			"       AND mapping.DEPT_ID IN" +
			"         (SELECT DU_ID" +
			"          FROM IC_USER_DETAILS" +
			"          WHERE EMPLOYEE_ID= ?))" +
			"  AND a.SBU_HEAD=iud.EMPLOYEE_ID" +
			"  AND iud.EMPLOYEE_ID=?" +
			"	ORDER BY SBU_NAME END ELSE BEGIN" +
			"	SELECT sbuMaster.SBU_ID AS SBU_ID ," +
			"       UPPER(sbuMaster.L6_SBU_NAME)AS SBU_NAME ," +
			"       sbuMaster.BU_ID AS BU_ID ," +
			"       UPPER(sbuMaster.BU_DESCRIPTION) AS BU_DESCRIPTION ," +
			"       sbuMaster.SBU_HEAD AS SBU_HEAD ," +
			"       sbuMasterHead.NAME AS SBU_HEAD_NAME" +
			"	FROM IC_USER_DETAILS userDet," +
			"     IC_USER_DETAILS sbuMasterHead," +
			"     IC_SU_ORG_SBU_MASTER sbuMaster," +
			"     IC_SU_SBU_DEPARTMENT_MAPPING sbuDeptMpn" +
			"	WHERE userDet.DU_ID=sbuDeptMpn.DEPT_ID" +
			"	  AND userDet.IS_ACTIVE=1" +
			"  AND sbuDeptMpn.IS_ACTIVE=1" +
			"  AND sbuMaster.IS_ACTIVE='1'" +
			"  AND userDet.EMPLOYEE_ID=?" +
			"  AND sbuMaster.SBU_ID=sbuDeptMpn.L6_CODE" +
			"  AND sbuMaster.SBU_HEAD=sbuMasterHead.EMPLOYEE_ID END ";*/
	
	public static final String SELECT_LOGGEDIN_SBU =   " IF EXISTS " + 
	 "                     (SELECT DISTINCT SBU_ID, " + 
	 "                                      UPPER(L6_SBU_NAME) AS SBU_NAME, " + 
	 "                                      BU_ID, " + 
	 "                                      UPPER(BU_DESCRIPTION) AS BU_DESCRIPTION, " + 
	 "                                      SBU_HEAD, " + 
	 "                                      NAME AS SBU_HEAD_NAME " + 
	 "                      FROM IC_SU_ORG_SBU_MASTER a, " + 
	 "                           IC_USER_DETAILS iud " + 
	 "                      WHERE BU_ID IN " + 
	 "                          (SELECT mapping.L5_VERTICAL_CODE " + 
	 "                           FROM IC_SU_ORG_DEPT_MASTER deptMaster, " + 
	 "                               IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping " + 
	 "                           WHERE deptMaster.DEPT_ID=mapping.DEPT_ID " + 
	 "                             AND mapping.DEPT_ID IN " + 
	 "                               (SELECT DU_ID " + 
	 "                                FROM IC_USER_DETAILS " + 
	 "                                WHERE EMPLOYEE_ID= ?)) " + 
	 "                        AND a.SBU_HEAD=iud.EMPLOYEE_ID " + 
	 "                        AND iud.EMPLOYEE_ID=?)  " + 
	 "                        BEGIN " + 
	 "                         SELECT DISTINCT SBU_ID, " + 
	 "                                   UPPER(L6_SBU_NAME) AS SBU_NAME, " + 
	 "                                   BU_ID, " + 
	 "                                   UPPER(BU_DESCRIPTION) AS BU_DESCRIPTION, " + 
	 "                                   SBU_HEAD, " + 
	 "                                   iud.NAME AS SBU_HEAD_NAME, " + 
	 "                                   dhead.EMPLOYEE_ID as 'DEPT_HEAD_ID' , " + 
	 "                                   dhead.NAME as 'DEPT_HEAD_NAME' " + 
	 "                                    " + 
	 "                   FROM IC_SU_ORG_SBU_MASTER a, " + 
	 "                        IC_USER_DETAILS iud, " + 
	 "                        IC_SU_ORG_DEPT_MASTER dept, " + 
	 "                        IC_USER_DETAILS dhead " + 
	 "                         WHERE BU_ID IN " + 
	 "                       (SELECT mapping.L5_VERTICAL_CODE " + 
	 "                        FROM IC_SU_ORG_DEPT_MASTER deptMaster, " + 
	 "                             IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping " + 
	 "                        WHERE deptMaster.DEPT_ID=mapping.DEPT_ID " + 
	 "                          AND mapping.DEPT_ID IN " + 
	 "                            (SELECT DU_ID " + 
	 "                             FROM IC_USER_DETAILS " + 
	 "                             WHERE EMPLOYEE_ID= ?)) " + 
	 "                     AND a.SBU_HEAD=iud.EMPLOYEE_ID " + 
	 "                     AND iud.EMPLOYEE_ID=? " + 
	 "                     and dept.DEPT_ID = iud.DU_ID and dept.IS_ACTIVE = 1 " + 
	 "                     and dhead.EMPLOYEE_ID = dept.DEPT_HEAD " + 
	 "                      " + 
	 "                         ORDER BY SBU_NAME  " + 
	 "                          " + 
	 "                         END  " + 
	 "                         ELSE  " + 
	 "                          " + 
	 "                         BEGIN " + 
	 "                         SELECT sbuMaster.SBU_ID AS SBU_ID , " + 
	 "                          UPPER(sbuMaster.L6_SBU_NAME)AS SBU_NAME , " + 
	 "                          sbuMaster.BU_ID AS BU_ID , " + 
	 "                          UPPER(sbuMaster.BU_DESCRIPTION) AS BU_DESCRIPTION , " + 
	 "                          sbuMaster.SBU_HEAD AS SBU_HEAD , " + 
	 "                          sbuMasterHead.NAME AS SBU_HEAD_NAME, " + 
	 "                        dhead.EMPLOYEE_ID as 'DEPT_HEAD_ID' , " + 
	 "                       dhead.NAME as 'DEPT_HEAD_NAME' " + 
	 "                         FROM IC_USER_DETAILS userDet, " + 
	 "                        IC_USER_DETAILS sbuMasterHead, " + 
	 "                        IC_SU_ORG_SBU_MASTER sbuMaster, " + 
	 "                        IC_SU_SBU_DEPARTMENT_MAPPING sbuDeptMpn, " + 
	 "                                               IC_SU_ORG_DEPT_MASTER dept, " + 
	 "                        IC_USER_DETAILS dhead " + 
	 "                         WHERE userDet.DU_ID=sbuDeptMpn.DEPT_ID " + 
	 "                           AND userDet.IS_ACTIVE=1 " + 
	 "                     AND sbuDeptMpn.IS_ACTIVE=1 " + 
	 "                     AND sbuMaster.IS_ACTIVE='1' " + 
	 "                     AND userDet.EMPLOYEE_ID=? " + 
	 "                     AND sbuMaster.SBU_ID=sbuDeptMpn.L6_CODE " + 
	 "                     AND sbuMaster.SBU_HEAD=sbuMasterHead.EMPLOYEE_ID  " + 
	 "                     and dept.DEPT_ID = userDet.DU_ID " + 
	 "                     and dhead.EMPLOYEE_ID = dept.DEPT_HEAD " + 
	 "                     END " ; 
	 

	
	public static final String SELECT_CUSTOMER_NAME="Select distinct CUSTOMER_ID,UPPER(CUSTOMER_NAME) as CUSTOMER_NAME from IC_PROJECT_MASTER WHERE"+
	" STATUS=1 and START_DATE <= GETDATE() "+
    " and END_DATE >= GETDATE() and DU in ("+
    " Select distinct DEPT_ID  from IC_SU_SBU_DEPARTMENT_MAPPING a"+
    "           where a.L6_CODE= ? "+                 
    " ) order by CUSTOMER_NAME,CUSTOMER_ID";
	

 
/*	public static final String SELECT_PROJECT_NAME=" Select distinct PROJECT_ID,UPPER(a.NAME) as PROJECT_NAME ,dept.DEPT_ID ,dept.DEPT_HEAD,SBU_ID,SBU_HEAD,sbuUser.NAME as SBU_HEAD_NAME,deptUser.NAME as DEPT_HEAD_NAME				from IC_PROJECT_MASTER a ," +
			"						   IC_SU_ORG_DEPT_MASTER dept,IC_SU_SBU_DEPARTMENT_MAPPING mpn , IC_SU_ORG_SBU_MASTER sbu,IC_USER_DETAILS deptUser,IC_USER_DETAILS sbuUser WHERE" +
			"							STATUS=1 and START_DATE <= GETDATE()" +
			"						                   and END_DATE >= GETDATE() and CUSTOMER_ID in (?)" +
			"						                   and dept.DEPT_ID=a.DU" +
			"						                   and mpn.DEPT_ID=dept.DEPT_ID" +
			"						                   and mpn.L6_CODE=sbu.SBU_ID and mpn.L6_CODE=? " +
			"						                   and sbu.SBU_HEAD=sbuUser.EMPLOYEE_ID and dept.DEPT_HEAD=deptUser.EMPLOYEE_ID order by PROJECT_NAME"; */
	
	public static final String SELECT_PROJECT_NAME =  " Select distinct PROJECT_ID,UPPER(a.NAME) as PROJECT_NAME , " + 
							 " dept.DEPT_ID , " + 
							 " dept.DEPT_HEAD, " + 
							 " mpn.L6_CODE, " + 
							 " SBU_ID, " + 
							 " SBU_HEAD, " + 
							 " sbuUser.NAME as SBU_HEAD_NAME, " + 
							 " deptUser.NAME as DEPT_HEAD_NAME                         " + 
							 " from IC_PROJECT_MASTER a  " + 
							 " left outer join IC_USER_DETAILS usr on usr.EMPLOYEE_ID = ? " + 
							 " inner join IC_SU_ORG_DEPT_MASTER dept on dept.DEPT_ID=case when (usr.DU_ID like '%90' OR usr.DU_ID like '%01') then usr.DU_ID else a.DU end  " + 
							 " inner join IC_SU_SBU_DEPARTMENT_MAPPING mpn on mpn.DEPT_ID=dept.DEPT_ID and mpn.L6_CODE= ?  " + 
							 " inner join IC_SU_ORG_SBU_MASTER sbu on mpn.L6_CODE=sbu.SBU_ID " + 
							 " inner join IC_USER_DETAILS deptUser on dept.DEPT_HEAD=deptUser.EMPLOYEE_ID " + 
							 " inner join IC_USER_DETAILS sbuUser on sbu.SBU_HEAD=sbuUser.EMPLOYEE_ID   " + 
							 " WHERE " + 
							 " a.STATUS=1  " + 
							 " and a.START_DATE <= GETDATE() " + 
							 " and a.END_DATE >= GETDATE()  " + 
							 " and a.CUSTOMER_ID  in (?) " + 
							 " order by PROJECT_NAME " ; 
	
 public static final String GET_LOCATION_DETAILS="Select LOCATION_DETAIL_ID,NAME  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure" +
			" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID" +
			" and suStructure.STRUCTURE_NAME= ? and IS_ACTIVE='A' and locDet.REFERENCE_ID = ? ";
 
 public static final  String GET_AREA_VALUE_DETAILS="Select distinct locDet.LOCATION_DETAIL_ID,NAME,VALUE  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure, IC_SU_SEATING_DETAIL seat			 Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID"
		+	 " and suStructure.STRUCTURE_NAME= ? and IS_ACTIVE='A' and locDet.REFERENCE_ID = ?"
		+		" and seat.LOCATION_DETAIL_ID=locDet.LOCATION_DETAIL_ID and seat.DB_COLUMN_ID=15	";
 
 
 
 public static final String GET_QUARTERS="	declare @sdate date=dateadd(qq, +0,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER));" +
 		"	declare @edate date=dateadd(month, +11,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER)) ;" +
 		"	;with rs as" +
 		"	(" +
 		"	   select   1 r,@sdate s" +
 		"	   union all" +
 		"	   select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate)" +
 		"	)" +
 		"	select cast(year(s) as varchar) as 'Year',left(datename(mm,s),10) + ' - ' +cast(year(s) as varchar) as 'Month'," +
 		"	datepart(mm,s) as 'MonthNumber'," +
 		"		UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1'" +
 		"		 when datepart(mm,s) in (4,5,6) then 'Q2'" +
 		"		 when datepart(mm,s) in (7,8,9) then 'Q3'" +
 		"		 when datepart(mm,s) in (10,11,12) then 'Q4'" +
 		"	end )+ ' - ' +cast(year(s)as varchar )  as 'Quarter'" +
 		"	from rs"; 
 
 public static final String GET_QUARTERS_FOR_EDIT =  " declare @qtrdate date=dateadd(qq, +0,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER)); " + 
 " declare @seldate date=dateadd(qq, +0,(select cast (REQUESTED_YEAR   + '-' + cast(MONTH(CAST(REQUESTED_MONTH + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_MAIN_REQUEST_DETAIL where MAIN_REQUEST_ID = ? )); " + 
 " declare @sdate date = case when @seldate <= @qtrdate then @seldate else @qtrdate end " + 
 " declare @edate date=dateadd(month, +11,@sdate) ; " + 
 "                  ;with rs as " + 
 "                  ( " + 
 "                     select   1 r,@sdate s " + 
 "                     union all " + 
 "                     select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate) " + 
 "                  ) " + 
 "                  select cast(year(s) as varchar) as 'Year',left(datename(mm,s),10) + ' - ' +cast(year(s) as varchar) as 'Month', " + 
 "                  datepart(mm,s) as 'MonthNumber', " + 
 "                        UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1' " + 
 "                         when datepart(mm,s) in (4,5,6) then 'Q2' " + 
 "                         when datepart(mm,s) in (7,8,9) then 'Q3' " + 
 "                         when datepart(mm,s) in (10,11,12) then 'Q4' " + 
 "                  end )+ ' - ' +cast(year(s)as varchar )  as 'Quarter' " + 
 "                  from rs " ;
 
 public static final String GET_QUARTER_YEARS=
 		"	declare @sdate date=dateadd(qq, +0,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER));" +
 		"	declare @edate date=dateadd(month, +11,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER)) ;" +
 		"	;with rs as" +
 		"	(" +
 		"	   select   1 r,@sdate s" +
 		"	   union all" +
 		"	   select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate)" +
 		"	)" +
 		"	select	 distinct" +
 		"	UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1'" +
 		"		 when datepart(mm,s) in (4,5,6) then 'Q2'" +
 		"		 when datepart(mm,s) in (7,8,9) then 'Q3'" +
 		"		 when datepart(mm,s) in (10,11,12) then 'Q4'" +
 		"	end )+ ' - ' +cast(year(s)as varchar )  as Quarter	,cast(year(s) as int) as Year" +
 		"	from rs order by Year	";
 
 public static final String GET_QUARTER_YEARS_FOR_EDIT =  " declare @qtrdate date=dateadd(qq, +0,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER)); " + 
 " declare @seldate date=dateadd(qq, +0,(select cast (REQUESTED_YEAR   + '-' + cast(MONTH(CAST(REQUESTED_MONTH + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_MAIN_REQUEST_DETAIL where MAIN_REQUEST_ID = ? )); " + 
 " declare @sdate date = case when @seldate <= @qtrdate then @seldate else @qtrdate end " + 
 " declare @edate date=dateadd(month, +11,(select cast(LTRIM(SUBSTRING(PRESENT_QUARTER_SELECTED ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)+1,LEN(PRESENT_QUARTER_SELECTED))) + '-' + cast(MONTH(CAST(SUBSTRING(PRESENT_QUARTER_SELECTED ,0 ,CHARINDEX('-',PRESENT_QUARTER_SELECTED)) + '1 2010' AS datetime))as varchar) + '-01' as date) from IC_WP_QUARTER)) ; " + 
 " ;with rs as " + 
 " ( " + 
 "    select   1 r,@sdate s " + 
 "    union all " + 
 "    select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate) " + 
 " ) " + 
 " select	 distinct " + 
 " UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1' " + 
 " 	 when datepart(mm,s) in (4,5,6) then 'Q2' " + 
 " 	 when datepart(mm,s) in (7,8,9) then 'Q3' " + 
 " 	 when datepart(mm,s) in (10,11,12) then 'Q4' " + 
 " end )+ ' - ' +cast(year(s)as varchar )  as Quarter	,cast(year(s) as int) as Year " + 
 " from rs order by Year " ;
 
 
 public static final String GET_QUARTER_YEAR_FOR_RESET=
		"	declare @sdate date=dateadd(qq, +0, getdate());" +
		"	declare @edate date=dateadd(yyyy, +1, getdate()) ;" +
		"	;with rs as" +
		"	(" +
		"	   select   1 r,@sdate s" +
		"	   union all" +
		"	   select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate)" +
		"	)" +
		"	select	 distinct" +
		"	UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1'" +
		"		 when datepart(mm,s) in (4,5,6) then 'Q2'" +
		"		 when datepart(mm,s) in (7,8,9) then 'Q3'" +
		"		 when datepart(mm,s) in (10,11,12) then 'Q4'" +
		"	end )+ ' - ' +cast(year(s)as varchar )  as Quarter	,cast(year(s) as int) as Year" +
		"	from rs order by Year	";

 public static final String GET_QUARTERS_FOR_RESET="	declare @sdate date=dateadd(qq, +0, getdate());" +
	"	declare @edate date=dateadd(yyyy, +1, getdate()) ;" +
	"	;with rs as" +
	"	(" +
	"	   select   1 r,@sdate s" +
	"	   union all" +
	"	   select r+1, DATEADD(mm,1,s)  from rs where r<=datediff(mm,@sdate,@edate)" +
	"	)" +
	"	select cast(year(s) as varchar) as 'Year',left(datename(mm,s),10) + ' - ' +cast(year(s) as varchar) as 'Month'," +
	"	datepart(mm,s) as 'MonthNumber'," +
	"		UPPER(case when datepart(mm,s) in (1,2,3) then 'Q1'" +
	"		 when datepart(mm,s) in (4,5,6) then 'Q2'" +
	"		 when datepart(mm,s) in (7,8,9) then 'Q3'" +
	"		 when datepart(mm,s) in (10,11,12) then 'Q4'" +
	"	end )+ ' - ' +cast(year(s)as varchar )  as 'Quarter'" +
	"	from rs"; 
 public static final String INSERT_MAIN_REQ_DETAIL="Insert into IC_WP_MAIN_REQUEST_DETAIL(NEW_SPACE_REQUIREMENT_FLAG,NEW_SPACE_OR_EXPANSION_FLAG,ODC_FLAG,SUB_BUSINESS_UNIT_ID,OPPORTUNITY_ID,PROJECT_ID,CUSTOMER_ID," +
 		"	REQUESTED_QUARTER,REQUESTED_MONTH,REQUESTED_YEAR,STATE_ID,TOTAL_SEATS_REQUESTED,DATE_OF_REQUISITION,REQUESTOR_COMMENTS,DEPT_HEAD_ID,DELIVERY_HEAD_ID,CREATED_BY,CREATED_DATE)" +
 		"	VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate())";
 		
 		//Added by shruti
 //Added by shruti
/* public static final String getSBUdetails = "SELECT E.BU_DESCRIPTION,A.BUSINESS_UNIT_ID,E.L6_SBU_NAME,A.SUB_BUSINESS_UNIT_ID,D.NAME 'COUNTRY',D.LOCATION_DETAIL_ID 'COUNTRY_ID',C.NAME 'CITY',C.LOCATION_DETAIL_ID 'CITY_ID',B.NAME 'AREA',B.LOCATION_DETAIL_ID 'AREA_ID',A.REQUESTED_LOCATION_ID," +
 		" CAST(ISNULL(A.SOFT_ALLOCATION_COUNT_OF_SEATS ,0) as varchar)+'/'+CAST(ISNULL(A.REQUESTED_COUNT_OF_SEATS,0) as varchar) 'SEAT',ISNULL(A.SOFT_ALLOCATION_COUNT_OF_SEATS,0) 'SOFT_ALLOCATION_COUNT_OF_SEATS',A.REQUESTED_COUNT_OF_SEATS," +
 		" ISNULL(A.REQUESTED_COUNT_OF_SEATS-A.SOFT_ALLOCATION_COUNT_OF_SEATS,0) as 'REMAINING_SEATS',A.MAIN_REQUEST_ID,A.SUB_REQUEST_ID,F.PROJECT_ID,G.NAME 'PROJECT_NAME',G.CUSTOMER_NAME,F.REQUESTED_MONTH,F.REQUESTED_YEAR "+
 		" FROM IC_WP_SUB_REQUEST_LOCATION_DETAIL A,IC_SU_LOCATION_DETAILS B,IC_SU_LOCATION_DETAILS C,IC_SU_LOCATION_DETAILS D,IC_SU_ORG_SBU_MASTER E, " +
 		" IC_WP_MAIN_REQUEST_DETAIL F,IC_PROJECT_MASTER G "+
 		" WHERE A.REQUESTED_LOCATION_ID=B.LOCATION_DETAIL_ID AND B.REFERENCE_ID=C.LOCATION_DETAIL_ID AND C.REFERENCE_ID=D.LOCATION_DETAIL_ID" +
 		" AND E.SBU_ID=A.SUB_BUSINESS_UNIT_ID AND F.PROJECT_ID=G.PROJECT_ID and A.MAIN_REQUEST_ID=F.MAIN_REQUEST_ID AND A.MAIN_REQUEST_ID=?";*/
 
 public static final String getSBUdetails = " select subdet.BUSINESS_UNIT_ID, subdet.SUB_BUSINESS_UNIT_ID, sbu.L6_SBU_NAME, subdet.SUB_BUSINESS_UNIT_ID, " + 
 " cntry.NAME 'COUNTRY', cntry.LOCATION_DETAIL_ID 'COUNTRY_ID', city.NAME 'CITY', city.LOCATION_DETAIL_ID 'CITY_ID', area.NAME 'AREA', area.LOCATION_DETAIL_ID 'AREA_ID', " + 
 " subdet.REQUESTED_LOCATION_ID, " + 
 " CAST(ISNULL(subdet.SOFT_ALLOCATION_COUNT_OF_SEATS, 0) as varchar)+'/'+CAST(ISNULL(subdet.REQUESTED_COUNT_OF_SEATS,0) as varchar) 'SEAT',  " + 
 " ISNULL(subdet.SOFT_ALLOCATION_COUNT_OF_SEATS,0) 'SOFT_ALLOCATION_COUNT_OF_SEATS', subdet.REQUESTED_COUNT_OF_SEATS, " + 
 " ISNULL(subdet.REQUESTED_COUNT_OF_SEATS-subdet.SOFT_ALLOCATION_COUNT_OF_SEATS,0) as 'REMAINING_SEATS', " + 
 " subdet.MAIN_REQUEST_ID, subdet.SUB_REQUEST_ID,  " + 
 " ISNULL(prj.PROJECT_ID, '')'PROJECT_ID',  ISNULL(prj.NAME, '') 'PROJECT_NAME' , ISNULL(prj.CUSTOMER_ID, '') 'CUSTOMER_ID', ISNULL(prj.CUSTOMER_NAME, '') 'CUSTOMER_NAME', " + 
 " main.REQUESTED_MONTH, main.REQUESTED_YEAR " + 
 " from IC_WP_SUB_REQUEST_LOCATION_DETAIL subdet " + 
 " INNER JOIN IC_WP_MAIN_REQUEST_DETAIL main on main.MAIN_REQUEST_ID = subdet.MAIN_REQUEST_ID and subdet.IS_ACTIVE = 1 " + 
 " INNER JOIN IC_SU_ORG_SBU_MASTER sbu on sbu.SBU_ID = subdet.SUB_BUSINESS_UNIT_ID and sbu.IS_ACTIVE =1 " + 
 " INNER  JOIN cc_location_details area ON area.LOCATION_DETAIL_ID = subdet.REQUESTED_LOCATION_ID AND area.IS_ACTIVE = 'A' " + 
 " INNER  JOIN cc_location_details city ON city.LOCATION_DETAIL_ID = area.REFERENCE_ID AND city.STRUCTURE_ID = 3 AND city.IS_ACTIVE = 'A' " + 
 " INNER  JOIN cc_location_details cntry ON cntry.LOCATION_DETAIL_ID = city.REFERENCE_ID AND cntry.STRUCTURE_ID = 2  AND cntry.IS_ACTIVE = 'A'  " + 
 " LEFT OUTER  JOIN IC_PROJECT_MASTER prj ON prj.PROJECT_ID = main.PROJECT_ID AND prj.STATUS = 1   " + 
 " where subdet.MAIN_REQUEST_ID = ? AND subdet.IS_ACTIVE=1 " ;
 
//added by shruti
/*public static final String getSBUdetails1 = "SELECT DISTINCT E.BU_DESCRIPTION,A.BUSINESS_UNIT_ID,E.L6_SBU_NAME,A.SUB_BUSINESS_UNIT_ID,D.NAME 'COUNTRY',D.LOCATION_DETAIL_ID 'COUNTRY_ID'," +
 			" C.NAME 'CITY',C.LOCATION_DETAIL_ID 'CITY_ID',B.NAME 'AREA',A.REQUESTED_LOCATION_ID," +
 			" CAST(ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1) ,0) as varchar)+'/'+CAST(ISNULL(A.REQUESTED_COUNT_OF_SEATS,0) as varchar) 'SEAT'," +
 			" ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0) 'SOFT_ALLOCATION_COUNT_OF_SEATS',A.REQUESTED_COUNT_OF_SEATS," +
 			" ISNULL(A.REQUESTED_COUNT_OF_SEATS-ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)from IC_WP_SOFT_ALLOCATION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0),0) as 'REMAINING_SEATS'," +
 			" A.MAIN_REQUEST_ID,A.SUB_REQUEST_ID,F.PROJECT_ID,G.NAME 'PROJECT_NAME',G.CUSTOMER_NAME,F.REQUESTED_MONTH,F.REQUESTED_YEAR " +
 			" FROM IC_WP_SUB_REQUEST_LOCATION_DETAIL A,IC_SU_LOCATION_DETAILS B,IC_SU_LOCATION_DETAILS C,IC_SU_LOCATION_DETAILS D,IC_SU_ORG_SBU_MASTER E, IC_WP_MAIN_REQUEST_DETAIL F,IC_PROJECT_MASTER G " +
 			" WHERE A.REQUESTED_LOCATION_ID=B.LOCATION_DETAIL_ID AND B.REFERENCE_ID=C.LOCATION_DETAIL_ID AND C.REFERENCE_ID=D.LOCATION_DETAIL_ID" +
 			" AND E.SBU_ID=A.SUB_BUSINESS_UNIT_ID AND F.PROJECT_ID=G.PROJECT_ID and A.MAIN_REQUEST_ID=F.MAIN_REQUEST_ID AND  A.MAIN_REQUEST_ID=? ";*/

//modified query 16/7/2014
/* public static final String getSBUdetails1 = " SELECT DISTINCT E.bu_description, A.business_unit_id, E.l6_sbu_name,  A.sub_business_unit_id,  D.name 'COUNTRY', " + 
" D.location_detail_id 'COUNTRY_ID', C.name 'CITY', C.location_detail_id 'CITY_ID', B.name 'AREA', A.requested_location_id, A.requested_location_id 'AREA_ID', " +
" Isnull((SELECT Sum(COUNT_OF_SEATS) FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id " +
" AND is_active = 1 group by sub_request_id), 0) as EXCEPTIONS_COUNT , " +
" CAST(( ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL " +
" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0) + ISNULL((Select SUM(COUNT_OF_SEATS)  from IC_WP_LOCATION_EXCEPTION_DETAIL " +
" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1 group by sub_request_id) ,0)) as varchar)+'/'+CAST(ISNULL(A.REQUESTED_COUNT_OF_SEATS,0) " +
" as varchar) 'SEAT', " +
" ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL  " +
" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0)'SOFT_ALLOCATION_COUNT_OF_SEATS',A.requested_count_of_seats," +
" CASE WHEN Isnull(A.requested_count_of_seats - Isnull((SELECT Sum(soft_allocation_count_of_seats)  FROM   ic_wp_soft_allocation_detail " +
" WHERE sub_request_id = A.sub_request_id AND is_active = 1), 0) - isnull((SELECT Sum(COUNT_OF_SEATS) " +
" FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id AND is_active = 1 group by sub_request_id), 0), 0) >0 then " +
" Isnull(A.requested_count_of_seats - Isnull((SELECT Sum(soft_allocation_count_of_seats)  FROM   ic_wp_soft_allocation_detail " +
" WHERE sub_request_id = A.sub_request_id AND is_active = 1), 0) - isnull((SELECT Sum(COUNT_OF_SEATS) " +
" FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id AND is_active = 1 group by sub_request_id), 0), 0) " +
" else 0 END AS 'REMAINING_SEATS', " +
" CAST(( ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0) +  " +
" ISNULL((Select SUM(COUNT_OF_SEATS)  from IC_WP_LOCATION_EXCEPTION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1 group by sub_request_id) ,0)) as varchar) " +
" 'TOTAL_ALLOCATED', " +
" A.main_request_id, A.sub_request_id,F.project_id, G.name 'PROJECT_NAME',G.customer_name,F.requested_month,F.requested_year " +
" FROM ic_wp_sub_request_location_detail A,ic_su_location_details B,ic_su_location_details C,ic_su_location_details D,ic_su_org_sbu_master E, " +
" ic_wp_main_request_detail F,ic_project_master G WHERE  A.requested_location_id = B.location_detail_id AND B.reference_id = C.location_detail_id " +
" AND C.reference_id = D.location_detail_id AND E.sbu_id = A.sub_business_unit_id AND F.project_id = G.project_id AND A.main_request_id = F.main_request_id " +
" AND A.main_request_id = ? " ;*/

//modified by shruti 23/7/2014
 public static final String getSBUdetails1 =" SELECT DISTINCT E.bu_description, A.business_unit_id, E.l6_sbu_name,  A.sub_business_unit_id,  D.name 'COUNTRY'," +
	" D.location_detail_id 'COUNTRY_ID', C.name 'CITY', C.location_detail_id 'CITY_ID', B.name 'AREA', A.requested_location_id, A.requested_location_id 'AREA_ID'," +
	" Isnull((SELECT Sum(COUNT_OF_SEATS) FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id " +
	" AND is_active = 1 group by sub_request_id), 0) as EXCEPTIONS_COUNT ," +
	" CAST(( ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL " +
	" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0) + ISNULL((Select SUM(COUNT_OF_SEATS)  from IC_WP_LOCATION_EXCEPTION_DETAIL " +
	" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1 group by sub_request_id) ,0)) as varchar)+'/'+CAST(ISNULL(A.REQUESTED_COUNT_OF_SEATS,0)as varchar) 'SEAT',"+ 
	" ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL " +
	" WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0)'SOFT_ALLOCATION_COUNT_OF_SEATS',A.requested_count_of_seats," +
	" CASE WHEN Isnull(A.requested_count_of_seats - Isnull((SELECT Sum(soft_allocation_count_of_seats)  FROM   ic_wp_soft_allocation_detail " +
	" WHERE sub_request_id = A.sub_request_id AND is_active = 1), 0) - isnull((SELECT Sum(COUNT_OF_SEATS)" +
	" FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id AND is_active = 1 group by sub_request_id), 0), 0) >0 then " +
	" Isnull(A.requested_count_of_seats - Isnull((SELECT Sum(soft_allocation_count_of_seats)  FROM   ic_wp_soft_allocation_detail " +
	" WHERE sub_request_id = A.sub_request_id AND is_active = 1), 0) - isnull((SELECT Sum(COUNT_OF_SEATS)" +
	" FROM   IC_WP_LOCATION_EXCEPTION_DETAIL WHERE  sub_request_id = A.sub_request_id AND is_active = 1 group by sub_request_id), 0), 0) else 0 END AS 'REMAINING_SEATS',"+ 
	" CAST(( ISNULL((Select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS)  from IC_WP_SOFT_ALLOCATION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1),0) + " +
	" ISNULL((Select SUM(COUNT_OF_SEATS)  from IC_WP_LOCATION_EXCEPTION_DETAIL WHERE SUB_REQUEST_ID=A.SUB_REQUEST_ID and IS_ACTIVE=1 group by sub_request_id) ,0)) as varchar) 'TOTAL_ALLOCATED', "+
	" A.main_request_id, A.sub_request_id,ISNULL(F.project_id,'') as project_id, ISNULL(G.name,'') 'PROJECT_NAME',ISNULL(G.customer_name,F.CUSTOMER_FOR_OPPORTUNITY) as customer_name,F.requested_month,F.requested_year,f.SPACE_PLANNING_TEAM_COMMENTS" +
	" FROM ic_wp_sub_request_location_detail A " +
	" inner join cc_location_details B on A.requested_location_id = B.location_detail_id" +
	" inner join cc_location_details C on B.reference_id = C.location_detail_id" +
	" inner join  cc_location_details D on C.reference_id = D.location_detail_id" +
	" inner join ic_su_org_sbu_master E ON  E.sbu_id = A.sub_business_unit_id" +
	" inner join  ic_wp_main_request_detail F ON A.main_request_id = F.main_request_id" +
	" left outer join  ic_project_master G ON F.project_id = G.project_id" +
	" WHERE A.main_request_id = ? AND A.IS_ACTIVE=1 ";


/*public static final String getLocdetails = "SELECT DISTINCT  layoutLocArea.LOCATION_DETAIL_ID AS'AREA_ID',"+ 
			       " layoutLocArea.NAME AS'AREA_NAME', "+
                   " layoutLocCity.LOCATION_DETAIL_ID AS 'CITY_ID', "+
                   " layoutLocCity.NAME AS 'CITY_NAME', "+
                   " layoutLocBuilding.LOCATION_DETAIL_ID AS 'BUILDING_ID', "+
                   " layoutLocBuilding.NAME AS 'BUILDING_NAME' , "+
                   " layoutLocFLOOR.LOCATION_DETAIL_ID AS 'FLOOR_ID',"+  
                   " layoutLocFLOOR.NAME AS 'FLOOR_NAME' , "+
                   " case when layoutLocFLOOR.LOCATION_DETAIL_ID=layoutLocParent.LOCATION_DETAIL_ID then '' else "+ 
                   " layoutLocParent.LOCATION_DETAIL_ID end as 'WING_ID', "+
                   " case when layoutLocFLOOR.LOCATION_DETAIL_ID=layoutLocParent.LOCATION_DETAIL_ID then '' else "+ 
                   " layoutLocParent.NAME end as 'WING_NAME' ,"+ 
                   " case when layoutLocFLOOR.LOCATION_DETAIL_ID=layoutLocParent.LOCATION_DETAIL_ID then "+ 
						" (Select VALUE from IC_SU_SEATING_DETAIL where LOCATION_DETAIL_ID=layoutLocFLOOR.LOCATION_DETAIL_ID and DB_COLUMN_ID=15)"+ 
					  " else "+
					  " (Select VALUE from IC_SU_SEATING_DETAIL where LOCATION_DETAIL_ID=layoutLocParent.LOCATION_DETAIL_ID and DB_COLUMN_ID=15)"+ 
                  " end as 'AVAILABLE_COUNT_OF_SEATS',"+
                  
                  " case when layoutLocFLOOR.LOCATION_DETAIL_ID=layoutLocParent.LOCATION_DETAIL_ID then "+
						" (Select ISNULL(SUM(B.SOFT_ALLOCATION_COUNT_OF_SEATS),0) from IC_WP_SUB_REQUEST_LOCATION_DETAIL A,IC_WP_SOFT_ALLOCATION_DETAIL B WHERE  MAIN_REQUEST_ID=mainRequestId and A.SUB_REQUEST_ID=B.SUB_REQUEST_ID and B.SOFT_ALLOCATION_LOCATION_ID=layoutLocFLOOR.LOCATION_DETAIL_ID)"+ 
					   " else "+
					  " (Select ISNULL(SUM(B.SOFT_ALLOCATION_COUNT_OF_SEATS),0) from IC_WP_SUB_REQUEST_LOCATION_DETAIL A,IC_WP_SOFT_ALLOCATION_DETAIL B WHERE  MAIN_REQUEST_ID=mainRequestId and A.SUB_REQUEST_ID=B.SUB_REQUEST_ID and B.SOFT_ALLOCATION_LOCATION_ID=layoutLocParent.LOCATION_DETAIL_ID) "+ 
                   " end as 'SOFT_ALLOC_COUNT_OF_SEATS' "+
				 " FROM IC_SU_LOCATION_DETAILS layoutLoc " +
				 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocParent ON layoutLocParent.LOCATION_DETAIL_ID=layoutLoc.REFERENCE_ID  and layoutLocParent.IS_ACTIVE='A' " +
				 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocFLOOR ON layoutLocFLOOR.LOCATION_DETAIL_ID= (CASE "+
                                                                                                 " WHEN layoutLocParent.STRUCTURE_ID =7 THEN layoutLocParent.REFERENCE_ID "+
                                                                                                 " ELSE layoutLoc.REFERENCE_ID "+
                                                                                             " END)   and layoutLocFLOOR.IS_ACTIVE='A'"+
  
				 " INNER JOIN IC_SU_LOCATION_DETAILS layoutLocBuilding ON layoutLocBuilding.LOCATION_DETAIL_ID=layoutLocFLOOR.REFERENCE_ID "+
				 " AND layoutLocBuilding.STRUCTURE_ID=5  and layoutLocBuilding.IS_ACTIVE='A' AND layoutLocBuilding.LOCATION_DETAIL_ID=buildingId "+
 
				 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocArea ON layoutLocArea.LOCATION_DETAIL_ID=layoutLocBuilding.REFERENCE_ID "+
				 " AND layoutLocArea.STRUCTURE_ID=4   and layoutLocArea.IS_ACTIVE='A' "+
  
				 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocCity ON layoutLocCity.LOCATION_DETAIL_ID=layoutLocArea.REFERENCE_ID "+
				 " AND layoutLocCity.STRUCTURE_ID=3   and layoutLocCity.IS_ACTIVE='A'";*/
 
 /*public static final String getLocdetails ="SELECT layoutLocArea.LOCATION_DETAIL_ID AS'AREA_ID',layoutLocArea.NAME AS'AREA_NAME'" +
 		",layoutLocCity.LOCATION_DETAIL_ID AS 'CITY_ID',layoutLocCity.NAME AS 'CITY_NAME',layoutLocBuilding.LOCATION_DETAIL_ID AS 'BUILDING_ID'" +
 		",layoutLocBuilding.NAME AS 'BUILDING_NAME',layoutLocFLOOR.LOCATION_DETAIL_ID AS 'FLOOR_ID',layoutLocFLOOR.NAME AS 'FLOOR_NAME'" +
 		",layoutLocWing.LOCATION_DETAIL_ID as 'WING_ID',ISNULL(layoutLocWing.NAME,'') as 'WING_NAME'" +
 		",sum(case when pos.DEPT_ID = 'ITM02' then 1 end)as 'AVAILABLE_COUNT_OF_SEATS',COUNT(sf.SOFT_ALLOCATION_ID) as 'SOFT_ALLOC_COUNT_OF_SEATS'," +
 		"(sum(case when pos.DEPT_ID = 'ITM02' then 1 end))- (COUNT(sf.SOFT_ALLOCATION_ID)) as 'ALLOCATABLE_SEATS'  " +
 		"from  CC_LOCATION_DETAILS layoutLocBuilding " +
 		" JOIN CC_LOCATION_DETAILS layoutLocArea ON layoutLocArea.LOCATION_DETAIL_ID=layoutLocBuilding.REFERENCE_ID AND layoutLocArea.STRUCTURE_ID=4   " +
 		" and layoutLocArea.IS_ACTIVE='A' " +
 		" JOIN CC_LOCATION_DETAILS layoutLocCity ON layoutLocCity.LOCATION_DETAIL_ID=layoutLocArea.REFERENCE_ID " +
 		" AND layoutLocCity.STRUCTURE_ID=3   and layoutLocCity.IS_ACTIVE='A' " +
 		" LEFT OUTER JOIN CC_LOCATION_DETAILS layoutLocFLOOR ON layoutLocFLOOR.REFERENCE_ID = layoutLocBuilding.LOCATION_DETAIL_ID " +
 		" and layoutLocFLOOR.STRUCTURE_ID = 6 and layoutLocFLOOR.IS_ACTIVE = 'A' " +
 		" LEFT OUTER JOIN CC_LOCATION_DETAILS layoutLocWing On layoutLocWing.REFERENCE_ID = layoutLocFLOOR.LOCATION_DETAIL_ID " +
 		" and layoutLocWing.STRUCTURE_ID = 7 and layoutLocWing.IS_ACTIVE = 'A' " +
 		" left outer join CC_POSITION_MASTER pos on pos.REFERENCE_ID = case when exists " +
 		" (select 1 from CC_POSITION_MASTER where REFERENCE_ID = layoutLocWing.LOCATION_DETAIL_ID and pos.IS_ACTIVE = 1) " +
 		" then layoutLocWing.LOCATION_DETAIL_ID else layoutLocFLOOR.LOCATION_DETAIL_ID end left outer join CC_SOFT_ALLOCATION sf " +
 		" join IC_WP_SUB_REQUEST_LOCATION_DETAIL main on main.SUB_REQUEST_ID = sf.SUB_REQUEST_ID " +
 		" and main.IS_ACTIVE = 1 and main.MAIN_REQUEST_ID = mainRequestId on sf.POSITION_ID = pos.POSITION_ID and sf.IS_ACTIVE = 1 " +
 		" where layoutLocBuilding.LOCATION_DETAIL_ID= buildingId AND layoutLocBuilding.STRUCTURE_ID=5 " +
 		" and layoutLocBuilding.IS_ACTIVE='A' " +
 		" group by  layoutLocArea.LOCATION_DETAIL_ID ,layoutLocArea.NAME , layoutLocCity.LOCATION_DETAIL_ID " +
 		" ,layoutLocCity.NAME ,layoutLocBuilding.LOCATION_DETAIL_ID ,layoutLocBuilding.NAME " +
 		" ,layoutLocFLOOR.LOCATION_DETAIL_ID ,layoutLocFLOOR.NAME ,layoutLocWing.LOCATION_DETAIL_ID ,layoutLocWing.NAME ";*/
 
 public static final String getLocdetails ="Declare @SubReq int = mainRequestId ,@Loc int = buildingId ; " +
 		" SELECT  1 SUB_REQUEST_ID, city.NAME as CITY_NAME,city.LOCATION_DETAIL_ID as CITY_ID" +
 		",area.NAME as AREA_NAME ,area.LOCATION_DETAIL_ID as AREA_ID" +
 		",bldg.NAME as BUILDING_NAME,bldg.LOCATION_DETAIL_ID as BUILDING_ID,flr.NAME as FLOOR_NAME" +
 		",flr.LOCATION_DETAIL_ID as FLOOR_ID,ISNULL(loc.NAME,'')  as WING_NAME,ISNULL(loc.LOCATION_DETAIL_ID,'')  as WING_ID ," +
 		"(select COUNT(1) from CC_SOFT_ALLOCATION sf join CC_POSITION_MASTER po on po.POSITION_ID = sf.POSITION_ID and po.IS_ACTIVE = 1 " +
 		" and po.REFERENCE_ID = pos.REFERENCE_ID where sf.IS_ACTIVE = 1 and sf.SUB_REQUEST_ID = @SubReq)  SOFT_ALLOC_COUNT_OF_SEATS" +
 		",(select COUNT(1) from CC_POSITION_MASTER po where po.IS_ACTIVE = 1 and po.REFERENCE_ID = pos.REFERENCE_ID " +
 		" and not exists (select 1 from CC_EMPLOYEE_MAPPING where POSITION_ID = po.POSITION_ID and IS_ACTIVE = 1) " +
 		" and not exists (select 1 from CC_SOFT_ALLOCATION where POSITION_ID = po.POSITION_ID and IS_ACTIVE = 1)) ALLOCATABLE_SEATS " +
 		", 1 AVAILABLE_COUNT_OF_SEATS from CC_LOCATION_DETAILS bldg " +
 		" join CC_LOCATION_DETAILS area on area.LOCATION_DETAIL_ID = bldg.REFERENCE_ID and area.STRUCTURE_ID = 4 and area.IS_ACTIVE = 'A' " +
 		" join CC_LOCATION_DETAILS city on city.LOCATION_DETAIL_ID = area.REFERENCE_ID and city.STRUCTURE_ID = 3 and city.IS_ACTIVE = 'A' " +
 		" join CC_LOCATION_DETAILS flr on flr.REFERENCE_ID = bldg.LOCATION_DETAIL_ID and flr.STRUCTURE_ID = 6 and  flr.IS_ACTIVE = 'A' " +
 		" left outer join  CC_LOCATION_DETAILS wing on wing.REFERENCE_ID = flr.LOCATION_DETAIL_ID and wing.STRUCTURE_ID = 7 and wing.IS_ACTIVE = 'A' " +
 		" join CC_POSITION_MASTER pos on (pos.REFERENCE_ID = flr.LOCATION_DETAIL_ID or pos.REFERENCE_ID = wing.LOCATION_DETAIL_ID )  and pos.IS_ACTIVE = 1 " +
 		" LEFT outer join CC_LOCATION_DETAILS loc on loc.LOCATION_DETAIL_ID = pos.REFERENCE_ID and loc.STRUCTURE_ID = 7 and loc.IS_ACTIVE = 'A' " +
 		" where bldg.LOCATION_DETAIL_ID = @Loc and bldg.STRUCTURE_ID = 5 and bldg.IS_ACTIVE = 'A' group by city.NAME ,city.LOCATION_DETAIL_ID " +
 		" , area.NAME ,area.LOCATION_DETAIL_ID ,bldg.NAME ,bldg.LOCATION_DETAIL_ID, flr.NAME ,flr.LOCATION_DETAIL_ID , loc.NAME, loc.LOCATION_DETAIL_ID ," +
 		" pos.REFERENCE_ID "; 
 
 public static final  String GET_SPECIAL_REQ_DETAILS="Select REQUIREMENT_ID,UPPER(REQUIREMENT_NAME) as REQUIREMENT_NAME from IC_WP_SPECIAL_REQUIREMENT_MASTER WHERE IS_ACTIVE=1";
 
 public static final  String SELECT_SPECIAL_REQ_DETAILS_LIST = " Select splmain.MAIN_REQUEST_ID, splmain.REQUIREMENT_ID, UPPER(splmas.REQUIREMENT_NAME) as REQUIREMENT_NAME, REQUESTED_COUNT_OF_SEATS, splmain.IS_ACTIVE " +
 		"			 from  IC_WP_SPECIAL_REQUIREMENT_DETAIL splmain " +
 		"			 inner join IC_WP_SPECIAL_REQUIREMENT_MASTER splmas on splmas.REQUIREMENT_ID = splmain.REQUIREMENT_ID and splmas.IS_ACTIVE = 1 " +
 		"			 where splmain.IS_ACTIVE = 1 and " +
 		"			 splmain.MAIN_REQUEST_ID = ? " ;
 
 public static final String GET_DATA_FOR_EXCEL =   " SELECT main.MAIN_REQUEST_ID , " + 
 "        ISNULL(sbu.BU_DESCRIPTION, '-')+'('+sbu.BU_ID+')' AS BU , " + 
 "        ISNULL(sbu.L6_SBU_NAME, '-')+'('+sbu.SBU_ID+')' AS SBU , " + 
 "        REPLACE(CONVERT(VARCHAR, main.DATE_OF_REQUISITION, 100), ' ', '-') AS SUBMISSION_DATE , " + 
 "          ISNULL(main.REQUESTED_MONTH, '-') AS FULFILLMENT_MONTH  , " + 
 "        ISNULL(main.REQUESTED_QUARTER + '-' + main.REQUESTED_YEAR,' - ') AS FULFILLMENT_QUARTER , " + 
 "        cntry.NAME AS COUNTRY , " + 
 "        city.NAME AS CITY , " + 
 "        area.NAME AS AREA , " + 
 //"        usr.NAME + ' ( ' + usr.EMPLOYEE_ID + ')' AS REQUESTOR , " +
 " ISNULL( usr.NAME + ' ( ' + usr.EMPLOYEE_ID + ')','-') AS REQUESTOR ,"+
 "        ISNULL(vh.NAME , '-') AS VERTICAL_HEAD_ID_NAME , " + 
 "        ISNULL(prj.NAME + ' ( ' + prj.PROJECT_ID + ' ) ' , '-') AS PROJECT , " + 
 "        ISNULL(ISNULL(prj.CUSTOMER_NAME + ' ( ' +prj.CUSTOMER_ID + ')' ,main.CUSTOMER_FOR_OPPORTUNITY),'-') AS CUSTOMER_NAME, " + 
 "        ISNULL(delhead.NAME + ' ( ' + delhead.EMPLOYEE_ID + ' ) ' , '-') AS DELIVERY_HEAD_ID_NAME , " + 
 "       ISNULL(depthead.NAME + ' ( ' + depthead.EMPLOYEE_ID + ' ) ' , '-') AS DEPT_HEAD_ID_NAME , " + 
 "        CASE " + 
 "            WHEN main.NEW_SPACE_REQUIREMENT_FLAG = 1 THEN 'New' " + 
 "            WHEN main.NEW_SPACE_REQUIREMENT_FLAG = 0 " + 
 "                 AND main.NEW_SPACE_OR_EXPANSION_FLAG = 1 THEN 'New' " + 
 "            ELSE 'Expansion' " + 
 "        END AS REQUIREMENT_TYPE , " + 
 "        CASE " + 
 "            WHEN main.ODC_FLAG = 1 THEN 'Yes' " + 
 "            ELSE 'No' " + 
 "        END AS ODC_REQUIREMENT , " + 
 "        sub.REQUESTED_COUNT_OF_SEATS AS 'NO_OF_SEATS_REQUIRED' , " + 
 "        ISNULL(LEFT(main.REQUESTED_MONTH,3) + ' - ' + RIGHT(main.REQUESTED_YEAR,2), '-') AS DATE_OF_REQUIREMENT , " + 
 "        ISNULL(LEFT(main.ACTUAL_FULFILLMENT_MONTH,3) + ' - ' + RIGHT(main.ACTUAL_FULFILLMENT_YEAR,2), '-') AS DATE_OF_FULLFILMENT , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 1) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 1) " + 
 "            ELSE '0' " + 
 "        END AS PM_CUBICLE , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 2) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 2) " + 
 "            ELSE '0' " + 
 "        END AS CLOSED_CABINS , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 3) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                   FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 3) " + 
 "            ELSE '0' " + 
 "        END AS MEETING_ROOM , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 4) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 4) " + 
 "            ELSE '0' " + 
 "        END AS STORE_ROOM , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 5) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                     AND splreq.REQUIREMENT_ID = 5) " + 
 "            ELSE '0' " + 
 "        END AS DEDICATED_LAB , " + 
 "        CASE " + 
 "            WHEN EXISTS " + 
 "                   (SELECT 1 " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 6) THEN " + 
 "                   (SELECT SUM(splreq.REQUESTED_COUNT_OF_SEATS) " + 
 "                    FROM IC_WP_SPECIAL_REQUIREMENT_DETAIL splreq " + 
 "                    WHERE splreq.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 "                      AND splreq.REQUIREMENT_ID = 6) " + 
 "            ELSE '0' " + 
 "        END AS TRAINING_ROOM , " + 
 "        ISNULL(main.REQUESTOR_COMMENTS, '-') AS REQUESTOR_COMMENTS , " + 
 "        ISNULL(main.DEPT_HEAD_COMMENTS, '-') AS DEPT_HEAD_COMMENTS , " + 
 "        ISNULL(main.DELIVERY_HEAD_COMMENTS, '-')AS DELIVERY_HEAD_COMMENTS , " + 
 "        main.REQUESTED_QUARTER , " + 
 "        CASE " + 
 "            WHEN main.ODC_FLAG = 1 THEN 'ODC' " + 
 "            ELSE 'Non ODC' " + 
 "        END AS 'ODC/Non ODC' , " + 
 "        ISNULL(wrkstat.NAME, '-') AS STATUS , " + 
 "        CASE " + 
 "           WHEN main.MODIFIED_DATE IS NULL THEN REPLACE(CONVERT(VARCHAR,ISNULL(main.CREATED_DATE,100)),' ','-') " + 
 "            ELSE REPLACE(CONVERT(VARCHAR,ISNULL(main.MODIFIED_DATE,100)),' ','-') " + 
 "        END AS MODIFIED_DATE " + 
 " FROM IC_WP_MAIN_REQUEST_DETAIL main " + 
 " INNER JOIN IC_WP_SUB_REQUEST_LOCATION_DETAIL sub " + 
 " LEFT OUTER  JOIN IC_SU_ORG_SBU_MASTER sbu ON sbu.SBU_ID = sub.SUB_BUSINESS_UNIT_ID " + 
 " AND sbu.IS_ACTIVE =1 " + 
 " LEFT OUTER  JOIN IC_SU_LOCATION_DETAILS area ON area.LOCATION_DETAIL_ID = sub.REQUESTED_LOCATION_ID " + 
 " AND area.IS_ACTIVE = 'A' " + 
 " LEFT OUTER  JOIN IC_SU_LOCATION_DETAILS city ON city.LOCATION_DETAIL_ID = area.REFERENCE_ID " + 
 " AND city.STRUCTURE_ID = 3 " + 
 " AND city.IS_ACTIVE = 'A' " + 
 " LEFT OUTER  JOIN IC_SU_LOCATION_DETAILS cntry ON cntry.LOCATION_DETAIL_ID = city.REFERENCE_ID " + 
 " AND cntry.STRUCTURE_ID = 2 " + 
 " AND cntry.IS_ACTIVE = 'A' ON sub.MAIN_REQUEST_ID = main.MAIN_REQUEST_ID " + 
 " LEFT OUTER  JOIN IC_USER_DETAILS usr ON usr.EMPLOYEE_ID = main.CREATED_BY " + 
 " AND usr.IS_ACTIVE = 1 " + 
 " LEFT OUTER  JOIN IC_PROJECT_MASTER prj ON prj.PROJECT_ID = main.PROJECT_ID " + 
 " AND prj.STATUS = 1 " + 
 " LEFT OUTER JOIN IC_USER_DETAILS delhead ON delhead.EMPLOYEE_ID = main.DELIVERY_HEAD_ID " + 
 " AND delhead.IS_ACTIVE = 1 " + 
 " LEFT OUTER JOIN IC_USER_DETAILS depthead ON depthead.EMPLOYEE_ID = main.DEPT_HEAD_ID " + 
 " AND depthead.IS_ACTIVE = 1 " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocParent ON layoutLocParent.LOCATION_DETAIL_ID=sub.SOFT_ALLOCATION_LOCATION_ID " + 
 " AND layoutLocParent.IS_ACTIVE='A' " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS layoutLocFLOOR ON layoutLocFLOOR.LOCATION_DETAIL_ID= (CASE " + 
 "                                                                                                  WHEN layoutLocParent.STRUCTURE_ID =7 THEN layoutLocParent.REFERENCE_ID " + 
 "                                                                                                  ELSE sub.SOFT_ALLOCATION_LOCATION_ID " + 
 "                                                                                              END) " + 
 " AND layoutLocFLOOR.IS_ACTIVE='A' " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS sbuild ON sbuild.LOCATION_DETAIL_ID = layoutLocFLOOR.REFERENCE_ID " + 
 " AND sbuild.STRUCTURE_ID = 5 " + 
 " AND sbuild.IS_ACTIVE = 'A' " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS sarea ON sarea.LOCATION_DETAIL_ID = sbuild.REFERENCE_ID " + 
 " AND sarea.STRUCTURE_ID = 4 " + 
 " AND sarea.IS_ACTIVE = 'A' " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS scity ON scity.LOCATION_DETAIL_ID = sarea.REFERENCE_ID " + 
 " AND scity.STRUCTURE_ID = 3 " + 
 " AND scity.IS_ACTIVE = 'A' " + 
 " LEFT OUTER JOIN IC_SU_LOCATION_DETAILS scntry ON scntry.LOCATION_DETAIL_ID = scity.REFERENCE_ID " + 
 " AND scntry.STRUCTURE_ID = 2 " + 
 " AND scntry.IS_ACTIVE = 'A' " + 
 " LEFT OUTER JOIN IC_WORKFLOW_STATE_MASTER wrkstat ON wrkstat.STATE_ID = main.STATE_ID " + 
 " AND wrkstat.WORKFLOW_ID = 26 " + 
 " LEFT OUTER JOIN IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING vhdmap ON vhdmap.DEPT_ID = prj.DU " + 
 " AND vhdmap.IS_ACTIVE = 1 " + 
 " LEFT OUTER JOIN IC_SU_ORG_VERTICAL_MASTER ver ON ver.L5_VERTICAL_CODE = vhdmap.L5_VERTICAL_CODE " + 
 " AND ver.IS_ACTIVE = 1 " + 
 " LEFT OUTER JOIN IC_USER_DETAILS vh ON vh.EMPLOYEE_ID = ver.L5_VERTICAL_HEAD " + 
 " AND vh.IS_ACTIVE = 1 " + 
 " INNER JOIN IC_USER_DETAILS loggedIn ON loggedIn.EMPLOYEE_ID = ? " + 
 " AND main.SUB_BUSINESS_UNIT_ID IN ( " + 
 "                                     (SELECT DISTINCT SBU_ID " + 
 "                                      FROM IC_SU_ORG_SBU_MASTER a, " + 
 "                                           IC_USER_DETAILS iud " + 
 "                                      WHERE BU_ID IN " + 
 "                                          (SELECT mapping.L5_VERTICAL_CODE " + 
 "                                           FROM IC_SU_ORG_DEPT_MASTER deptMaster, " + 
 "                                                IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping " + 
 "                                          WHERE deptMaster.DEPT_ID=mapping.DEPT_ID " + 
 "                                             AND mapping.DEPT_ID IN " + 
 "                                               (SELECT DU_ID " + 
 "                                                FROM IC_USER_DETAILS " + 
 "                                                WHERE EMPLOYEE_ID= loggedIn.EMPLOYEE_ID)) " + 
 "                                        AND a.SBU_HEAD=iud.EMPLOYEE_ID " + 
 "                                        AND iud.EMPLOYEE_ID=loggedIn.EMPLOYEE_ID " + 
 "                                      UNION SELECT sbuMaster.SBU_ID AS SBU_ID " + 
 "                                      FROM IC_USER_DETAILS userDet, " + 
 "                                           IC_USER_DETAILS sbuMasterHead, " + 
 "                                           IC_SU_ORG_SBU_MASTER sbuMaster, " + 
 "                                           IC_SU_SBU_DEPARTMENT_MAPPING sbuDeptMpn " + 
 "                                      WHERE userDet.DU_ID=sbuDeptMpn.DEPT_ID " + 
 "                                        AND userDet.IS_ACTIVE=1 " + 
 "                                        AND sbuDeptMpn.IS_ACTIVE=1 " + 
 "                                        AND sbuMaster.IS_ACTIVE='1' " + 
 "                                        AND userDet.EMPLOYEE_ID=loggedIn.EMPLOYEE_ID " + 
 "                                        AND sbuMaster.SBU_ID=sbuDeptMpn.L6_CODE " + 
 "                                        AND sbuMaster.SBU_HEAD=sbuMasterHead.EMPLOYEE_ID)) " + 
 " ORDER BY main.MAIN_REQUEST_ID " ;
 
 //Added by shruti
 public static final String GET_STATUS_LISTS = " SELECT A.TO_STATE,B.NAME " +
 								" from IC_WORKFLOW_TRANSITION_MASTER A join IC_WORKFLOW_STATE_MASTER B on a.WORKFLOW_ID=B.WORKFLOW_ID " +
 								" where A.TO_STATE=B.STATE_ID and A.FROM_STATE=? ";

 public static final String UPDATE_REQUEST_STATUS_DETAILS = "UPDATE IC_WP_MAIN_REQUEST_DETAIL SET STATE_ID=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE(),SPACE_PLANNING_TEAM_COMMENTS=? where MAIN_REQUEST_ID=? ";
 
 public static final String UPDATE_REQUEST_STATUS_DETAILS_SA = "UPDATE IC_WP_MAIN_REQUEST_DETAIL SET STATE_ID=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where MAIN_REQUEST_ID=? ";
 
 /*public static final String GET_REQUEST_SEAT_COUNT_FOR_VALIDATION = "select m.seat 'TOTAL_REQUESTED',s.seat 'TOTAL_ALLOCATED' from ( " +
 		" select sUM(REQUESTED_COUNT_OF_SEATS) seat, 1 AS Row  from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID = ?) m " +
 		" left outer join (select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS) seat,1 AS Row  from IC_WP_SOFT_ALLOCATION_DETAIL where SUB_REQUEST_ID in " +
 		" (select SUB_REQUEST_ID from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID= ?))s on s.Row = m.Row ";
 */
 
 /*Query modified by Preeti for exceptional Count on 18/7/14*/
 /*public static final String GET_REQUEST_SEAT_COUNT_FOR_VALIDATION = "select m.seat 'TOTAL_REQUESTED',isnull(s.seat, 0) + isnull((SELECT Sum(COUNT_OF_SEATS)" +
 	" FROM   IC_WP_LOCATION_EXCEPTION_DETAIL B,ic_wp_sub_request_location_detail A" +
 	" WHERE  B.SUB_REQUEST_ID = A.sub_request_id AND B.IS_ACTIVE = 1 and B.SUB_REQUEST_ID in" +
 	" (select SUB_REQUEST_ID from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID= ?) ), 0) 'TOTAL_ALLOCATED' from ( " +
	" select sUM(REQUESTED_COUNT_OF_SEATS) seat, 1 AS Row  from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID = ?) m " +
	" left outer join (select SUM(SOFT_ALLOCATION_COUNT_OF_SEATS) seat,1 AS Row  from IC_WP_SOFT_ALLOCATION_DETAIL where SUB_REQUEST_ID in " +
	" (select SUB_REQUEST_ID from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID= ?))s on s.Row = m.Row ";*/
 
 public static final String GET_REQUEST_SEAT_COUNT_FOR_VALIDATION="select REQUESTED_COUNT_OF_SEATS as 'TOTAL_REQUESTED' ," +
 		"	((select ISNULL(sum(SOFT_ALLOCATION_COUNT_OF_SEATS),0) from IC_WP_SOFT_ALLOCATION_DETAIL" +
 		"	where SUB_REQUEST_ID = sub.SUB_REQUEST_ID" +
 		"	and IS_ACTIVE = 1)	+	(select ISNULL(SUM(det.COUNT_OF_SEATS),0) from IC_WP_LOCATION_EXCEPTION_DETAIL det" +
 		"	where det.SUB_REQUEST_ID = sub.SUB_REQUEST_ID and det.IS_ACTIVE = 1 )) as 'TOTAL_ALLOCATED'" +
 		"	from IC_WP_SUB_REQUEST_LOCATION_DETAIL sub	where MAIN_REQUEST_ID = ? ";
 
 
 public static final String GET_SUB_LOC_SEATCOUNT = "select A.SUB_REQUEST_ID,A.REQUESTED_COUNT_OF_SEATS,SUM(ISNULL (B.SOFT_ALLOCATION_COUNT_OF_SEATS ,0)) 'SOFT_ALLOCATION_COUNT_OF_SEATS'" +
 		" from IC_WP_SUB_REQUEST_LOCATION_DETAIL A left outer join IC_WP_SOFT_ALLOCATION_DETAIL B on A.SUB_REQUEST_ID=B.SUB_REQUEST_ID" +
 		" where A.SUB_REQUEST_ID in(select SUB_REQUEST_ID from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID=?)" +
 		" group by A.SUB_REQUEST_ID,A.REQUESTED_COUNT_OF_SEATS";
 
/* public static String getAllocCountDetails="SELECT DISTINCT D.SUB_REQUEST_ID 'SUB_REQUEST_ID',CASE WHEN I.STRUCTURE_ID=3 THEN I.NAME ELSE J.NAME END AS 'CITY_NAME',CASE WHEN G.STRUCTURE_ID=5 THEN G.NAME ELSE H.NAME END AS 'BUIL_NAME'," +
 		" C.LOCATION_DETAIL_ID AS 'FLOOR_ID',C.NAME AS 'FLOOR_NAME',D.SOFT_ALLOCATION_COUNT_OF_SEATS,D.COMMENTS,E.REQUESTED_COUNT_OF_SEATS " +
 		" FROM IC_SU_LOCATION_DETAILS A " +
 		" LEFT OUTER JOIN IC_SU_LOCATION_DETAILS B ON A.LOCATION_DETAIL_ID=B.REFERENCE_ID AND A.IS_ACTIVE='A' " +
 		" LEFT OUTER JOIN IC_SU_LOCATION_DETAILS C ON C.LOCATION_DETAIL_ID= (CASE  WHEN B.STRUCTURE_ID =7 THEN B.REFERENCE_ID "+
                                                                " ELSE A.REFERENCE_ID  END) AND C.IS_ACTIVE='A' " +
        " JOIN IC_SU_LOCATION_DETAILS G ON G.LOCATION_DETAIL_ID=C.REFERENCE_ID AND G.IS_ACTIVE='A' " +
        " JOIN IC_SU_LOCATION_DETAILS H ON H.LOCATION_DETAIL_ID=G.REFERENCE_ID AND H.IS_ACTIVE='A' " +
        " JOIN IC_SU_LOCATION_DETAILS I ON I.LOCATION_DETAIL_ID=H.REFERENCE_ID AND I.IS_ACTIVE='A' " +
        " JOIN IC_SU_LOCATION_DETAILS J ON J.LOCATION_DETAIL_ID=I.REFERENCE_ID AND J.IS_ACTIVE='A' " +
        " JOIN  IC_WP_SOFT_ALLOCATION_DETAIL D ON A.REFERENCE_ID=D.SOFT_ALLOCATION_LOCATION_ID " +
        " JOIN  IC_WP_SUB_REQUEST_LOCATION_DETAIL E ON D.SUB_REQUEST_ID=E.SUB_REQUEST_ID " +
        " AND E.SUB_REQUEST_ID=mainRequestId " +
        " AND D.SOFT_ALLOCATION_COUNT_OF_SEATS !=0";*/
 
 /*public static String getAllocCountDetails="Declare @SubReq int = mainRequestId ; " +
 		" SELECT distinct sfalloc.SUB_REQUEST_ID SUB_REQUEST_ID,city.NAME as CITY_NAME " +
 		" ,city.LOCATION_DETAIL_ID as CITY_ID,area.NAME as AREA_NAME " +
 		",area.LOCATION_DETAIL_ID as AREA_ID,bldg.NAME as BUIL_NAME " +
 		",bldg.LOCATION_DETAIL_ID as BUILDING_ID,flr.NAME as FLOOR_NAME " +
 		",flr.LOCATION_DETAIL_ID as FLOOR_ID,wing.NAME as WING" +
 		",wing.LOCATION_DETAIL_ID as WING_ID,sfalloc.SOFT_ALLOCATION_COUNT_OF_SEATS,sfalloc.COMMENTS " +
 		",SREQ.REQUESTED_COUNT_OF_SEATS " +
 		",(select ISNULL(Sum(ISNULL(COUNT_OF_SEATS,0)),0) FROM IC_WP_LOCATION_EXCEPTION_DETAIL WHERE " +
 		" SUB_REQUEST_ID=sfalloc.SUB_REQUEST_ID and IS_ACTIVE=1)as EXCEPTION_COUNT " +
 		",(select COUNT(1) from CC_POSITION_MASTER pos where pos.REFERENCE_ID = loc.LOCATION_DETAIL_ID and pos.IS_ACTIVE = 1 and pos.DEPT_ID = 'ITM02' " +
 		" and not exists (select 1 from CC_EMPLOYEE_MAPPING where POSITION_ID = pos.POSITION_ID and IS_ACTIVE = 1) ) AS AVAILABLE_COUNT " +
 		" from CC_LOCATION_DETAILS loc " +
 		" join IC_WP_SOFT_ALLOCATION_DETAIL sfalloc on sfalloc.SOFT_ALLOCATION_LOCATION_ID = loc.LOCATION_DETAIL_ID and sfalloc.IS_ACTIVE = 1 " +
 		" JOIN IC_WP_SUB_REQUEST_LOCATION_DETAIL SREQ ON SREQ.SUB_REQUEST_ID = sfalloc.SUB_REQUEST_ID and SREQ.SUB_REQUEST_ID = @SubReq and SREQ.IS_ACTIVE = 1 " +
 		" join CC_LOCATION_DETAILS flr on flr.LOCATION_DETAIL_ID = case when loc.STRUCTURE_ID = 7 then loc.REFERENCE_ID else loc.LOCATION_DETAIL_ID end " +
 		" and flr.STRUCTURE_ID = 6 and  flr.IS_ACTIVE = 'A' " +
 		" join CC_LOCATION_DETAILS bldg on bldg.LOCATION_DETAIL_ID = flr.REFERENCE_ID and bldg.STRUCTURE_ID = 5 and bldg.IS_ACTIVE = 'A' " +
 		" join CC_LOCATION_DETAILS area on area.LOCATION_DETAIL_ID = bldg.REFERENCE_ID and area.STRUCTURE_ID = 4 and area.IS_ACTIVE = 'A' " +
 		" join CC_LOCATION_DETAILS city on city.LOCATION_DETAIL_ID = area.REFERENCE_ID and city.STRUCTURE_ID = 3 and city.IS_ACTIVE = 'A' " +
 		" left outer join CC_LOCATION_DETAILS wing on wing.LOCATION_DETAIL_ID = loc.LOCATION_DETAIL_ID and wing.STRUCTURE_ID = 7 and wing.IS_ACTIVE = 'A' " +
 		" where  loc.IS_ACTIVE = 'A' ";*/
 
 public static String getAllocCountDetails="Declare @SubReq int = mainRequestId ; " +
 		" SELECT distinct sfalloc.SUB_REQUEST_ID SUB_REQUEST_ID,city.NAME as CITY_NAME" +
 		" ,city.LOCATION_DETAIL_ID as CITY_ID,area.NAME as AREA_NAME " +
 		" ,area.LOCATION_DETAIL_ID as AREA_ID,bldg.NAME as BUIL_NAME " +
 		" ,bldg.LOCATION_DETAIL_ID as BUILDING_ID,flr.NAME as FLOOR_NAME " +
 		" ,flr.LOCATION_DETAIL_ID as FLOOR_ID,ISNULL(wing.NAME,'-') as WING " +
 		" ,ISNULL(wing.LOCATION_DETAIL_ID,'-') as WING_ID,sfalloc.SOFT_ALLOCATION_COUNT_OF_SEATS, sfalloc.COMMENTS " +
 		" ,SREQ.REQUESTED_COUNT_OF_SEATS " +
 		" ,(select ISNULL(Sum(ISNULL(COUNT_OF_SEATS,0)),0) FROM IC_WP_LOCATION_EXCEPTION_DETAIL WHERE " +
 		" SUB_REQUEST_ID=sfalloc.SUB_REQUEST_ID and IS_ACTIVE=1)as EXCEPTION_COUNT " +
 		" ,(select COUNT(1) from CC_POSITION_MASTER pos where pos.REFERENCE_ID = loc.LOCATION_DETAIL_ID and pos.IS_ACTIVE = 1 and pos.DEPT_ID = 'ITM02' " +
 		"  and not exists (select 1 from CC_EMPLOYEE_MAPPING where POSITION_ID = pos.POSITION_ID and IS_ACTIVE = 1) " +
 		"  and not exists (select 1 from CC_SOFT_ALLOCATION where POSITION_ID = pos.POSITION_ID and IS_ACTIVE = 1) ) AS AVAILABLE_COUNT " +
 		" ,case when not exists (select 1 from CC_SOFT_ALLOCATION sf " +
 		" join CC_POSITION_MASTER pos on pos.POSITION_ID = sf.POSITION_ID and pos.IS_ACTIVE = 1 " +
 		" and pos.REFERENCE_ID in (wing.LOCATION_DETAIL_ID,flr.LOCATION_DETAIL_ID ) where sf.IS_ACTIVE = 1 " +
 		" and sf.SUB_REQUEST_ID = @SubReq ) then 'Yes' else 'No' end 'Deallocate'  " +
 		"  from CC_LOCATION_DETAILS loc " +
 		"  join IC_WP_SOFT_ALLOCATION_DETAIL sfalloc on sfalloc.SOFT_ALLOCATION_LOCATION_ID = loc.LOCATION_DETAIL_ID and sfalloc.IS_ACTIVE = 1 " +
 		"  JOIN IC_WP_SUB_REQUEST_LOCATION_DETAIL SREQ ON SREQ.SUB_REQUEST_ID = sfalloc.SUB_REQUEST_ID and SREQ.SUB_REQUEST_ID = @SubReq and SREQ.IS_ACTIVE = 1 " +
 		"  join CC_LOCATION_DETAILS flr on flr.LOCATION_DETAIL_ID = case when loc.STRUCTURE_ID = 7 " +
 		" then loc.REFERENCE_ID else loc.LOCATION_DETAIL_ID end and flr.STRUCTURE_ID = 6 and  flr.IS_ACTIVE = 'A' " +
 		"  join CC_LOCATION_DETAILS bldg on bldg.LOCATION_DETAIL_ID = flr.REFERENCE_ID and bldg.STRUCTURE_ID = 5 and bldg.IS_ACTIVE = 'A' " +
 		"  join CC_LOCATION_DETAILS area on area.LOCATION_DETAIL_ID = bldg.REFERENCE_ID and area.STRUCTURE_ID = 4 and area.IS_ACTIVE = 'A' " +
 		"  join CC_LOCATION_DETAILS city on city.LOCATION_DETAIL_ID = area.REFERENCE_ID and city.STRUCTURE_ID = 3 and city.IS_ACTIVE = 'A' " +
 		"  left outer join CC_LOCATION_DETAILS wing on wing.LOCATION_DETAIL_ID = loc.LOCATION_DETAIL_ID and wing.STRUCTURE_ID = 7 and wing.IS_ACTIVE = 'A' " +
 		"  where  loc.IS_ACTIVE = 'A' and sfalloc.SOFT_ALLOCATION_COUNT_OF_SEATS >0 ";
 
 public static String getTotalAllocatedSeats="SELECT DISTINCT D.SUB_REQUEST_ID 'SUB_REQUEST_ID',CASE WHEN I.STRUCTURE_ID=3 THEN I.NAME ELSE J.NAME END AS 'CITY_NAME',CASE WHEN G.STRUCTURE_ID=5 THEN G.NAME ELSE H.NAME END AS 'BUIL_NAME'," +
	" C.LOCATION_DETAIL_ID AS 'FLOOR_ID',C.NAME AS 'FLOOR_NAME',D.SOFT_ALLOCATION_COUNT_OF_SEATS,E.REQUESTED_COUNT_OF_SEATS,D.COMMENTS," +
	" ISNULL((select Sum(ISNULL(COUNT_OF_SEATS,0))  FROM IC_WP_LOCATION_EXCEPTION_DETAIL WHERE SUB_REQUEST_ID=D.SUB_REQUEST_ID and IS_ACTIVE=1),0) as EXCEPTION_COUNT, ISNULL(K.VALUE,0) 'AVAILABLE_COUNT' " +
	" FROM CC_LOCATION_DETAILS A, IC_WP_SOFT_ALLOCATION_DETAIL D, IC_WP_SUB_REQUEST_LOCATION_DETAIL E, CC_LOCATION_DETAILS C, CC_LOCATION_DETAILS G, " +
	" CC_LOCATION_DETAILS H, CC_LOCATION_DETAILS I,  CC_LOCATION_DETAILS J,IC_SU_SEATING_DETAIL K " +
	" WHERE C.LOCATION_DETAIL_ID=A.REFERENCE_ID  AND C.IS_ACTIVE='A' AND " +
	" G.LOCATION_DETAIL_ID=C.REFERENCE_ID AND G.IS_ACTIVE='A' AND H.LOCATION_DETAIL_ID=G.REFERENCE_ID AND H.IS_ACTIVE='A' AND " +
	" I.LOCATION_DETAIL_ID=H.REFERENCE_ID AND I.IS_ACTIVE='A' AND J.LOCATION_DETAIL_ID=I.REFERENCE_ID AND J.IS_ACTIVE='A' AND " +
	" D.SUB_REQUEST_ID=E.SUB_REQUEST_ID AND A.REFERENCE_ID=D.SOFT_ALLOCATION_LOCATION_ID AND E.SUB_REQUEST_ID =? AND " +
	" D.SOFT_ALLOCATION_COUNT_OF_SEATS !=0 and K.DB_COLUMN_ID=15 and D.SOFT_ALLOCATION_LOCATION_ID=K.LOCATION_DETAIL_ID ";
 
//Added by preeti for exceptional approval 8/7/2014
 public static final String INSERT_EXCEPTIONAL_DETAIL= " Insert into IC_WP_LOCATION_EXCEPTION_DETAIL(SUB_REQUEST_ID,STATE_ID,COMMENTS,COUNT_OF_SEATS,IS_ACTIVE,CREATED_BY,CREATED_DATE) " +
" values(?,?,?,?,?,?,getdate()) ";
 
 public static final String GET_EXCEPTIONAL_APPROVAL_DETAILS=" select EXCEPTION_ID,SUB_REQUEST_ID,A.STATE_ID,B.NAME,COMMENTS,COUNT_OF_SEATS" +
 		" from IC_WP_LOCATION_EXCEPTION_DETAIL  A" +
 		" left outer join IC_WORKFLOW_STATE_MASTER B on A.STATE_ID=B.STATE_ID" +
 		" WHERE SUB_REQUEST_ID IN (SELECT SUB_REQUEST_ID FROM" +
 		" IC_WP_SUB_REQUEST_LOCATION_DETAIL WHERE MAIN_REQUEST_ID=?) and IS_ACTIVE=1";
 
 public static final String UPDATE_EXCEPTIONAL_DETAIL_ISACTIVE=" update IC_WP_LOCATION_EXCEPTION_DETAIL set IS_ACTIVE = 0 where EXCEPTION_ID = ? ";
 
 public static final String UPDATE_EXCEPTIONAL_DETAILS=" update IC_WP_LOCATION_EXCEPTION_DETAIL" +
 		" set COUNT_OF_SEATS=?, COMMENTS=?, MODIFIED_BY=?," +
 		" MODIFIED_DATE=getdate() where EXCEPTION_ID=?";
 public static final String GET_STATUS_FOR_EXCEPTION=" select distinct B.STATE_ID,A.NAME from IC_WORKFLOW_STATE_MASTER A "+
 " inner join IC_WP_LOCATION_EXCEPTION_DETAIL B on B.STATE_ID=A.STATE_ID ";
 
 public static final String GET_EXCEPTIONAL_APPROVAL_SUB_REQUEST_DETAILS="select EXCEPTION_ID,SUB_REQUEST_ID,A.STATE_ID,B.NAME,COMMENTS,COUNT_OF_SEATS" +
 		" from IC_WP_LOCATION_EXCEPTION_DETAIL  A" +
 		" left outer join IC_WORKFLOW_STATE_MASTER B on A.STATE_ID=B.STATE_ID" +
 		" WHERE SUB_REQUEST_ID IN (SELECT SUB_REQUEST_ID FROM" +
 		" IC_WP_SUB_REQUEST_LOCATION_DETAIL WHERE MAIN_REQUEST_ID=? and SUB_REQUEST_ID =?) and IS_ACTIVE=1";
//End Preeti
 
 public static final String INSERT_COMMENTS="insert into [IC_WP_COMMENTS_LOG]" +
 		"	(MAIN_REQUEST_ID,COMMENTS,ROLE,MAIL_TRIGERRED_DELIVERY_HEAD,MAIL_TRIGGERED_DEPT_HEAD,MAIL_TRIGERRED_ITIM," +
 		"	MAIL_TRIGERRED_REQUESTOR,CREATED_BY,CREATED_DATE)" +
 		"	VALUES (?,?,?,?,?,1,?,?,GETDATE())";
 
 
 public static final String SELECT_COMMENTS_LOG_FOR_REQ="Select a.MAIN_REQUEST_ID," +
 		"	   a.COMMENTS," +
 		"	  case when b.DELIVERY_HEAD_ID=a.CREATED_BY then ' By Delivery Head - '+(Select NAME FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=b.DELIVERY_HEAD_ID)" +
 		"					+'('+b.DELIVERY_HEAD_ID+')'" +
 		"			WHEN b.DEPT_HEAD_ID=a.CREATED_BY then ' By Department Head - '+(Select NAME FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=b.DEPT_HEAD_ID)" +
 		"					+'('+b.DELIVERY_HEAD_ID+')'" +
 		"			WHEN b.CREATED_BY=a.CREATED_BY then ' By Requestor - '+(Select NAME FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=a.CREATED_BY)" +
 		"				+'('+b.DELIVERY_HEAD_ID+')'" +
 		"			else ' By Space Planning Team - '+(Select NAME FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=a.CREATED_BY)+'('+a.CREATED_BY+')'" +
 		"	END as 'REPLIED_BY'," +
 		"	CONVERT(VARCHAR(25), a.CREATED_DATE, 113) as CREATED_DATE" +
 		" from [IC_WP_COMMENTS_LOG] a , IC_WP_MAIN_REQUEST_DETAIL b" +
 		"	WHERE a.MAIN_REQUEST_ID=b.MAIN_REQUEST_ID  and a.MAIN_REQUEST_ID=? order by a.CREATED_DATE desc ";
 
 //Added for Loading Campus Crawler Diagram
 
 public static final String GET_FLOORPATH_BY_FLRID_WINGID="Select distinct TOP(1) a.LOCATION_DETAIL_ID as BUILDING_ID " +
 		",a.NAME as BUILDING_NAME,b.LOCATION_DETAIL_ID as AREA_ID,b.NAME as AREA_NAME " +
 		"  ,c.LOCATION_DETAIL_ID as CITY_ID,c.NAME as CITY_NAME " +
 		" , c.NAME+'->'+b.NAME as CAMPUS_NAME , d.LOCATION_DETAIL_ID as FLOOR_ID " +
 		" ,d.NAME as FLOOR_NAME,LM.LOCATION_FILE_PATH AS FLOOR_PATH ,'' as WING,'' as WING_ID " +
 		" from CC_LOCATION_DETAILS a INNER JOIN CC_LOCATION_DETAILS b  " +
 		" INNER JOIN CC_LOCATION_DETAILS c ON c.IS_ACTIVE='A' AND b.REFERENCE_ID=c.LOCATION_DETAIL_ID and c.STRUCTURE_ID = 3  " +
 		" ON b.IS_ACTIVE='A' AND a.REFERENCE_ID=b.LOCATION_DETAIL_ID and b.STRUCTURE_ID = 4  " +
 		" INNER JOIN CC_LOCATION_DETAILS d ON d.REFERENCE_ID=a.LOCATION_DETAIL_ID AND d.STRUCTURE_ID=6 and d.IS_ACTIVE='A'  " +
 		" INNER JOIN CC_POSITION_MASTER LM ON LM.REFERENCE_ID= d.LOCATION_DETAIL_ID  " +
 		" WHERE  a.IS_ACTIVE='A' and a.STRUCTURE_ID = 5 AND d.LOCATION_DETAIL_ID IN(?,?)  union  " +
 		"  Select distinct TOP(1) a.LOCATION_DETAIL_ID as BUILDING_ID " +
 		" ,a.NAME as BUILDING_NAME,b.LOCATION_DETAIL_ID as AREA_ID,b.NAME as AREA_NAME ,c.LOCATION_DETAIL_ID as CITY_ID,c.NAME as CITY_NAME " +
 		" , c.NAME+'->'+b.NAME as CAMPUS_NAME , d.LOCATION_DETAIL_ID as FLOOR_ID " +
 		" ,d.NAME as FLOOR_NAME,LM.LOCATION_FILE_PATH AS FLOOR_PATH ,'' as WING,'' as WING_ID " +
 		" from CC_LOCATION_DETAILS a INNER JOIN CC_LOCATION_DETAILS b  " +
 		" INNER JOIN CC_LOCATION_DETAILS c ON c.IS_ACTIVE='A' AND b.REFERENCE_ID=c.LOCATION_DETAIL_ID and c.STRUCTURE_ID = 3  " +
 		" ON b.IS_ACTIVE='A' AND a.REFERENCE_ID=b.LOCATION_DETAIL_ID  and b.STRUCTURE_ID = 4  " +
 		" INNER JOIN CC_LOCATION_DETAILS d ON d.REFERENCE_ID=a.LOCATION_DETAIL_ID AND d.STRUCTURE_ID=6 and d.IS_ACTIVE='A'  " +
 		" INNER JOIN CC_LOCATION_DETAILS e ON e.IS_ACTIVE='A' AND e.REFERENCE_ID=d.LOCATION_DETAIL_ID and e.STRUCTURE_ID = 7  " +
 		" INNER JOIN CC_POSITION_MASTER LM ON LM.REFERENCE_ID= e.LOCATION_DETAIL_ID where e.LOCATION_DETAIL_ID IN(?,?) ";
 
 public static final String GET_CUBICLECODE_COORDNO_BY_FLRIDORWING="declare " +
 		" @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)), @SubReq  int = null; " +
 		" select pos.CUBICLE_CODE,pos.POSITION_ID,pos.COORDINATE_NUMBER, NULL SUB_REQUEST_ID from CC_POSITION_MASTER pos " +
 		" where REFERENCE_ID = @Ref and  @SubReq is null and pos.IS_ACTIVE = 1 and pos.POSITION_ID not in ( select emap.POSITION_ID " +
 		" from CC_EMPLOYEE_MAPPING emap join CC_POSITION_MASTER pmas on pmas.POSITION_ID = emap.POSITION_ID " +
 		" and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 where emap.IS_ACTIVE = 1 union select sall.POSITION_ID " +
 		" from CC_SOFT_ALLOCATION sall " +
 		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
 		" where sall.IS_ACTIVE = 1 ) union select sall.CUBICLE_CODE,pmas.POSITION_ID,pmas.COORDINATE_NUMBER, sall.SUB_REQUEST_ID " +
 		" from CC_SOFT_ALLOCATION sall " +
 		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
 		" where sall.IS_ACTIVE = 1 and ISNULL(@SubReq,0) = ISNULL(sall.SUB_REQUEST_ID,0) "; 
 
 public static final String GET_CUBICLECODE_COORDNO_BY_FLRIDORWINGORSUBREQID="declare " +
	" @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)), @SubReq  int = ?; " +
	" select pos.CUBICLE_CODE,pos.POSITION_ID,pos.COORDINATE_NUMBER, NULL SUB_REQUEST_ID from CC_POSITION_MASTER pos " +
	" where REFERENCE_ID = @Ref and  @SubReq is null and pos.IS_ACTIVE = 1 and pos.POSITION_ID not in ( select emap.POSITION_ID " +
	" from CC_EMPLOYEE_MAPPING emap join CC_POSITION_MASTER pmas on pmas.POSITION_ID = emap.POSITION_ID " +
	" and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 where emap.IS_ACTIVE = 1 union select sall.POSITION_ID " +
	" from CC_SOFT_ALLOCATION sall " +
	" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
	" where sall.IS_ACTIVE = 1 ) union select sall.CUBICLE_CODE,pmas.POSITION_ID,pmas.COORDINATE_NUMBER, sall.SUB_REQUEST_ID " +
	" from CC_SOFT_ALLOCATION sall " +
	" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
	" where sall.IS_ACTIVE = 1 and ISNULL(@SubReq,0) = ISNULL(sall.SUB_REQUEST_ID,0) "; 
 
public static final String GET_AREA_COORDS_FOR_SOFTALLOC="declare @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)) ,@SubReq  int = NULL; " +
		" SELECT STUFF((SELECT '' + AREA_COORDINATES FROM (" +
		" select '<area href=\"#\" odc-state=\"selected_id_For_CropBefore\" data-state=\"'+ ISNULL(TM.TYPE_NAME,'Allocated Programmer Seats') +'\" shape=\"poly\" id=\"'+" +
		" CONVERT(varchar,pos.COORDINATE_NUMBER)+'\" coords=\"'+pos.COORDINATES+'\" onmousedown=\"OnMouseDown(this,event)\"></area>' as AREA_COORDINATES " +
		" from CC_POSITION_MASTER pos " +
		" LEFT OUTER JOIN CC_SEAT_TYPE_MASTER TM ON TM.TYPE_ID = pos.POSITION_TYPE_ID and TM.IS_ACTIVE = 1 " +
		" where REFERENCE_ID = @Ref and pos.POSITION_ID not in (select emap.POSITION_ID  " +
		" from CC_EMPLOYEE_MAPPING emap " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = emap.POSITION_ID " +
		" and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" where emap.IS_ACTIVE = 1 union select sall.POSITION_ID " +
		" from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" where sall.IS_ACTIVE = 1 ) and @SubReq is null and pos.IS_ACTIVE = 1 union " +
		" select '<area href=\"#\" odc-state=\"selected_id_For_CropBefore\" data-state=\"'" +
		" + ISNULL(TM.TYPE_NAME,'Allocated Programmer Seats') +'\" shape=\"poly\" id=\"'+CONVERT(varchar,pmas.COORDINATE_NUMBER)+'\" " +
		" coords=\"'+pmas.COORDINATES+'\" onmousedown=\"OnMouseDown(this,event)\"></area>' as AREA_COORDINATES " +
		" from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" LEFT OUTER JOIN CC_SEAT_TYPE_MASTER TM ON TM.TYPE_ID = pmas.POSITION_TYPE_ID and TM.IS_ACTIVE = 1 " +
		" where sall.IS_ACTIVE = 1 and ISNULL(@SubReq,0) = ISNULL(sall.SUB_REQUEST_ID,0) " +
		" )tt FOR XML PATH(''), TYPE).value('.','NVARCHAR(MAX)'),1,0,'') AREA_COORDINATES ";

public static final String GET_AREA_COORDS_FOR_DEALLOC="declare @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)) ,@SubReq  int = ? ; " +
		" SELECT STUFF((SELECT '' + AREA_COORDINATES FROM (" +
		" select '<area href=\"#\" odc-state=\"selected_id_For_CropBefore\" data-state=\"'+ ISNULL(TM.TYPE_NAME,'Allocated Programmer Seats') +'\" shape=\"poly\" id=\"'+" +
		" CONVERT(varchar,pos.COORDINATE_NUMBER)+'\" coords=\"'+pos.COORDINATES+'\" onmousedown=\"OnMouseDown(this,event)\"></area>' as AREA_COORDINATES " +
		" from CC_POSITION_MASTER pos " +
		" LEFT OUTER JOIN CC_SEAT_TYPE_MASTER TM ON TM.TYPE_ID =pos.POSITION_TYPE_ID and TM.IS_ACTIVE = 1 " +
		" where REFERENCE_ID = @Ref and pos.POSITION_ID not in (select emap.POSITION_ID  " +
		" from CC_EMPLOYEE_MAPPING emap " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = emap.POSITION_ID " +
		" and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" where emap.IS_ACTIVE = 1 union select sall.POSITION_ID " +
		" from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" where sall.IS_ACTIVE = 1 ) and @SubReq is null and pos.IS_ACTIVE = 1 union " +
		" select '<area href=\"#\" odc-state=\"selected_id_For_CropBefore\" data-state=\"'" +
		" + ISNULL(TM.TYPE_NAME,'Allocated Programmer Seats') +'\" shape=\"poly\" id=\"'+CONVERT(varchar,pmas.COORDINATE_NUMBER)+'\" " +
		" coords=\"'+pmas.COORDINATES+'\" onmousedown=\"OnMouseDown(this,event)\"></area>' as AREA_COORDINATES " +
		" from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" LEFT OUTER JOIN CC_SEAT_TYPE_MASTER TM ON TM.TYPE_ID = pmas.POSITION_TYPE_ID and TM.IS_ACTIVE = 1 " +
		" where sall.IS_ACTIVE = 1 and ISNULL(@SubReq,0) = ISNULL(sall.SUB_REQUEST_ID,0) " +
		" )tt FOR XML PATH(''), TYPE).value('.','NVARCHAR(MAX)'),1,0,'') AREA_COORDINATES ";

public static final String VIEW_ALL_CUBICLECODE_COORDNO_BY_FLRIDORWINGORSUBREQID="declare @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)) ; " +
		" select sall.CUBICLE_CODE,pmas.POSITION_ID,pmas.COORDINATE_NUMBER, sall.SUB_REQUEST_ID,sub.MAIN_REQUEST_ID " +
		" from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" join IC_WP_SUB_REQUEST_LOCATION_DETAIL sub on sub.SUB_REQUEST_ID = sall.SUB_REQUEST_ID and sub.IS_ACTIVE = 1 " +
		" where sall.IS_ACTIVE = 1 ";

	public static final String GET_AREA_COORDS_FOR_VIEW_SOFT_ALLOC=" declare @Ref int = (SELECT TOP(1) REFERENCE_ID FROM CC_POSITION_MASTER WHERE  REFERENCE_ID IN(?,?)) ; " +
		" SELECT STUFF((SELECT '' + AREA_COORDINATES FROM " +
		" (select '<area href=\"#\" odc-state=\"ARRAY_ODC1\" data-state=\"'+ ISNULL(TM.TYPE_NAME,'Allocated Programmer Seats') " +
		" +'\" shape=\"poly\" id=\"'+CONVERT(varchar,pmas.COORDINATE_NUMBER)+'\" coords=\"'+pmas.COORDINATES+'\" " +
		" onmousedown=\"OnMouseDown(this,event)\"></area>' as AREA_COORDINATES from CC_SOFT_ALLOCATION sall " +
		" join CC_POSITION_MASTER pmas on pmas.POSITION_ID = sall.POSITION_ID and pmas.REFERENCE_ID = @Ref and pmas.IS_ACTIVE = 1 " +
		" join IC_WP_SUB_REQUEST_LOCATION_DETAIL sub on sub.SUB_REQUEST_ID = sall.SUB_REQUEST_ID and sub.IS_ACTIVE = 1 " +
		" LEFT OUTER JOIN CC_SEAT_TYPE_MASTER TM ON TM.TYPE_ID = pmas.POSITION_ID and TM.IS_ACTIVE = 1 where sall.IS_ACTIVE = 1 " +
		" )tt FOR XML PATH(''), TYPE).value('.','NVARCHAR(MAX)'),1,0,'') AREA_COORDINATES ";
	
	public static final String CHK_ALLOW_ALLOCATE_SPACE_OR_NOT="declare @SubReq int = ? ; select " +
			" case when  exists(select 1 from IC_WP_SOFT_ALLOCATION_DETAIL where SUB_REQUEST_ID = @SubReq and IS_ACTIVE  = 1 " +
			" and SOFT_ALLOCATION_COUNT_OF_SEATS > 0) and not exists (select 1 from CC_SOFT_ALLOCATION where " +
			" SUB_REQUEST_ID = @SubReq and IS_ACTIVE = 1 ) then 'No' else 'Yes' end  IS_Allow "; 
	
	public static final String GET_REMAINING_COUNT_CC_SOFTALLOCATION="declare @SubReq int = ? ; select " +
			" case when  exists(select 1 from IC_WP_SOFT_ALLOCATION_DETAIL where SUB_REQUEST_ID = @SubReq and IS_ACTIVE  = 1 " +
			" and SOFT_ALLOCATION_COUNT_OF_SEATS > 0) " +
			" and not exists (select 1 from CC_SOFT_ALLOCATION where SUB_REQUEST_ID = @SubReq and IS_ACTIVE = 1 ) " +
			" then 'No' else 'Yes' end  IS_Allow,(select req.REQUESTED_COUNT_OF_SEATS - (SELECT ISNULL(SUM(ISNULL(ex.COUNT_OF_SEATS,0)),0) " +
			" FROM IC_WP_LOCATION_EXCEPTION_DETAIL EX WHERE EX.SUB_REQUEST_ID = @SubReq and EX.IS_ACTIVE = 1) - (select COUNT(1) from CC_SOFT_ALLOCATION " +
			" where SUB_REQUEST_ID = @SubReq and IS_ACTIVE = 1) Remaining_Seats from IC_WP_SUB_REQUEST_LOCATION_DETAIL req " +
			" where req.SUB_REQUEST_ID = @SubReq and req.IS_ACTIVE= 1 ) Remaining_Seats ";
	
	//Added for Space Planning Approval in Mapps and USS
	public static final String SELECT_PROJECT_NAME_FOR_MAPPS_USS =  " Select distinct PROJECT_ID,UPPER(a.NAME) as PROJECT_NAME , " + 
	 " dept.DEPT_ID , " + 
	 " dept.DEPT_HEAD, " + 
	 " mpn.L6_CODE, " + 
	 " SBU_ID, " + 
	 " SBU_HEAD, " + 
	 " sbuUser.NAME as SBU_HEAD_NAME, " + 
	 " deptUser.NAME as DEPT_HEAD_NAME                         " + 
	 " from IC_PROJECT_MASTER a  " + 
	 " left outer join IC_USER_DETAILS usr on usr.EMPLOYEE_ID = ? " + 
	 " inner join IC_SU_ORG_DEPT_MASTER dept on dept.DEPT_ID=case when (usr.DU_ID like '%90' OR usr.DU_ID like '%01') then usr.DU_ID else a.DU end  " + 
	 " inner join IC_SU_SBU_DEPARTMENT_MAPPING mpn on mpn.DEPT_ID=dept.DEPT_ID and mpn.L6_CODE= (SELECT SUB_BUSINESS_UNIT_ID FROM IC_WP_MAIN_REQUEST_DETAIL WHERE MAIN_REQUEST_ID=?)  " + 
	 " inner join IC_SU_ORG_SBU_MASTER sbu on mpn.L6_CODE=sbu.SBU_ID " + 
	 " inner join IC_USER_DETAILS deptUser on dept.DEPT_HEAD=deptUser.EMPLOYEE_ID " + 
	 " inner join IC_USER_DETAILS sbuUser on sbu.SBU_HEAD=sbuUser.EMPLOYEE_ID   " + 
	 " WHERE " + 
	 " a.STATUS=1  " + 
	 " and a.START_DATE <= GETDATE() " + 
	 " and a.END_DATE >= GETDATE()  " + 
	 " and a.CUSTOMER_ID  in ((SELECT CUSTOMER_ID FROM IC_WP_MAIN_REQUEST_DETAIL WHERE MAIN_REQUEST_ID=?)) " +
	 " and PROJECT_ID in((SELECT PROJECT_ID FROM IC_WP_MAIN_REQUEST_DETAIL WHERE MAIN_REQUEST_ID=? )) " + 
	 " order by PROJECT_NAME " ; 
	
	public static final String SELECT_DELIVERY_HEAD_ID="select DELIVERY_HEAD_ID from IC_WP_MAIN_REQUEST_DETAIL where MAIN_REQUEST_ID=? ";

}
