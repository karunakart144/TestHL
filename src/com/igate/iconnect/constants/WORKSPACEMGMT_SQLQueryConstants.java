/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

import java.util.HashMap;
import java.util.Map;


public final class WORKSPACEMGMT_SQLQueryConstants {




	//public static final String UPDATE_NODE_MENU_MASTER = "UPDATE SU_LOCATION_DETAILS SET IS_ACTIVE='I' WHERE LOCATION_DETAIL_ID=?";

	public static final String UPDATE_NODE_MENU_MASTER ="WITH PreviousClaims(child,parent)"+ 
														"AS("+
														    " select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+
														    " WHERE LOCATION_DETAIL_ID = ? "+
														     " UNION ALL"+
														    " select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+
														     "INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID "+
														     ") "+
														  " update ssd set "+
														   " ssd.VALUE=ssd.VALUE- "+
														   " (select VALUE from IC_SU_SEATING_DETAIL "+
																  "  ssd1 where ssd1.LOCATION_DETAIL_ID= ? "+
																	   "  and ssd1.DB_COLUMN_ID=ssd.DB_COLUMN_ID) "+
																	   " FROM PreviousClaims pc join IC_SU_SEATING_DETAIL "+
																	   " ssd on pc.parent=ssd.LOCATION_DETAIL_ID and ssd.DB_COLUMN_ID not in (1,4,7,10,13,16,24)";
	
	

	public static final String UPDATE_LOCATION_VALUES=	"WITH PreviousClaims(child,parent)  " + 
														"AS(  " +
														 "    select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  " +
														 "    WHERE LOCATION_DETAIL_ID = ?  " +
														 "    UNION ALL  " +
														  "   select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  " +
														   "  INNER JOIN PreviousClaims parent ON parent.child = REFERENCE_ID  " +
														") " +
														"update ssd  set VALUE=0 from " +
														" PreviousClaims pc join  IC_SU_SEATING_DETAIL ssd  " +
														"on pc.child=ssd.LOCATION_DETAIL_ID" ;
														
	

public static final String UPDATE_LOCATION_IS_ACTIVE_CC="WITH PreviousClaims(child,parent) " + 
														"AS( " +
															 "    select LOCATION_DETAIL_ID,REFERENCE_ID from CC_LOCATION_DETAILS " +
															  "   WHERE LOCATION_DETAIL_ID = ?  " +
															   "  UNION ALL  " +
															  "   select LOCATION_DETAIL_ID,REFERENCE_ID from CC_LOCATION_DETAILS  " +
															   "  INNER JOIN PreviousClaims parent ON parent.child = REFERENCE_ID  " +
															") " +
															"update sld  set IS_ACTIVE='I',MODIFIED_BY=?,MODIFIED_DATE=CURRENT_TIMESTAMP from  " +
															"PreviousClaims pc join  CC_LOCATION_DETAILS sld " +
															" on pc.child=sld.LOCATION_DETAIL_ID";
	
	public static final String UPDATE_LOCATION_IS_ACTIVE_ODC_CC="Update IC_SU_LOCATION_ODC_DETAILS SET IS_ACTIVE=0,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() WHERE  LOCATION_DETAIL_ID = ?";
	
	
	public static final String UPDATE_NODE_MENU_MASTER_CC ="WITH PreviousClaims(child,parent)"+ 
	"AS("+
	    " select LOCATION_DETAIL_ID,REFERENCE_ID from CC_LOCATION_DETAILS "+
	    " WHERE LOCATION_DETAIL_ID = ? "+
	     " UNION ALL"+
	    " select LOCATION_DETAIL_ID,REFERENCE_ID from CC_LOCATION_DETAILS "+
	     "INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID "+
	     ") "+
	  " update ssd set "+
	   " ssd.VALUE=ssd.VALUE- "+
	   " (select VALUE from IC_SU_SEATING_DETAIL "+
			  "  ssd1 where ssd1.LOCATION_DETAIL_ID= ? "+
				   "  and ssd1.DB_COLUMN_ID=ssd.DB_COLUMN_ID) "+
				   " FROM PreviousClaims pc join IC_SU_SEATING_DETAIL "+
				   " ssd on pc.parent=ssd.LOCATION_DETAIL_ID and ssd.DB_COLUMN_ID not in (1,4,7,10,13,16,24)";



public static final String UPDATE_LOCATION_VALUES_CC=	"WITH PreviousClaims(child,parent)  " + 
	"AS(  " +
	 "    select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  " +
	 "    WHERE LOCATION_DETAIL_ID = ?  " +
	 "    UNION ALL  " +
	  "   select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  " +
	   "  INNER JOIN PreviousClaims parent ON parent.child = REFERENCE_ID  " +
	") " +
	"update ssd  set VALUE=0 from " +
	" PreviousClaims pc join  IC_SU_SEATING_DETAIL ssd  " +
	"on pc.child=ssd.LOCATION_DETAIL_ID" ;
	

public static final String UPDATE_LOCATION_IS_ACTIVE="WITH PreviousClaims(child,parent) " + 
	"AS( " +
		 "    select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS " +
		  "   WHERE LOCATION_DETAIL_ID = ?  " +
		   "  UNION ALL  " +
		  "   select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  " +
		   "  INNER JOIN PreviousClaims parent ON parent.child = REFERENCE_ID  " +
		") " +
		"update sld  set IS_ACTIVE='I',MODIFIED_BY=?,MODIFIED_DATE=CURRENT_TIMESTAMP from  " +
		"PreviousClaims pc join  IC_SU_LOCATION_DETAILS sld " +
		" on pc.child=sld.LOCATION_DETAIL_ID";
	
	
	public static final String UPDATE_RENAME_NODE = "UPDATE IC_SU_LOCATION_DETAILS SET NAME=?,MODIFIED_BY=?,MODIFIED_DATE=CURRENT_TIMESTAMP WHERE LOCATION_DETAIL_ID=?";

	public static final String SELECT_COUNT = "SELECT COUNT(*) FROM IC_SU_STRUCTURE WHERE PARENT_ID=(select STRUCTURE_ID from IC_SU_LOCATION_DETAILS where LOCATION_DETAIL_ID=?)";
	
	public static final String SELECT_COUNT_CC = "SELECT COUNT(*) FROM IC_SU_STRUCTURE WHERE PARENT_ID=(select STRUCTURE_ID from CC_LOCATION_DETAILS where LOCATION_DETAIL_ID=?)";

	public static final String SELECT_NODE_TYPE = "select Structure_Name,Structure_ID from IC_SU_STRUCTURE where Parent_Id=(select STRUCTURE_ID from IC_SU_LOCATION_DETAILS where LOCATION_DETAIL_ID=?)";
	
	public static final String SELECT_NODE_TYPE_CC = "select Structure_Name,Structure_ID from IC_SU_STRUCTURE where Parent_Id=(select STRUCTURE_ID from CC_LOCATION_DETAILS where LOCATION_DETAIL_ID=?)";

	public static final String SELECT_LOCATION_REMARKS = "SELECT REMARKS,LOCATION_DETAIL_ID FROM IC_SU_LOCATION_DETAILS where LOCATION_DETAIL_ID=?";

	public static final String UPDATE_REMARKS = "UPDATE IC_SU_LOCATION_DETAILS set REMARKS=?+isnull(REMARKS,'') where LOCATION_DETAIL_ID=?";

	public static final String SELECT_VERSION_DEATILS = "select VERSION_ID,LOCATION_DETAIL_ID,MODIFIED_BY from IC_SU_VERSION_DETAILS ISVD WHERE ISVD.LOCATION_DETAIL_ID=?";

	public static final String INSERT_LOCATION_ACCESS_DETAILS = "INSERT INTO IC_SU_LOCATION_ACCESS VALUES(?,?,'1',?,CURRENT_TIMESTAMP,null,null,?)";

	public static final String SELECT_LOCATION_ACCESS_DETAILS = "select IUD.NAME AS EMPLOYEE_NAME, ISLA.EMPLOYEE_ID,ISLD.NAME AS LOCATION_NAME from IC_SU_LOCATION_ACCESS ISLA,IC_SU_LOCATION_DETAILS ISLD,IC_USER_DETAILS IUD WHERE ISLA.LOCATION_DETAIL_ID=ISLD.LOCATION_DETAIL_ID AND ISLA.EMPLOYEE_ID=IUD.EMPLOYEE_ID AND ISLA.LOCATION_DETAIL_ID=? AND ISLA.EMPLOYEE_ID=? AND  ISLA.STATUS='1'";

	public static final String SELECT_ACCESS_DETAILS =  "WITH PreviousClaims(child,parent)  "+
														"AS( "+
														"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS WHERE LOCATION_DETAIL_ID =? "+
														"UNION ALL "+
														"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  "+
														"INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID) "+
														"select UD.NAME AS EMPLOYEE_NAME,la.EMPLOYEE_ID,LOD.NAME AS LOCATION_NAME,LOD.LOCATION_DETAIL_ID from PreviousClaims pc join IC_SU_LOCATION_ACCESS la on la.LOCATION_DETAIL_ID=pc.child JOIN IC_SU_LOCATION_DETAILS LOD ON LOD.LOCATION_DETAIL_ID=la.LOCATION_DETAIL_ID JOIN IC_USER_DETAILS UD ON UD.EMPLOYEE_ID=la.EMPLOYEE_ID where LA.EMPLOYEE_ID=? AND la.STATUS='1'";	


	public static final String SELECT_ACCESS_DETAILS_AGAIN = "WITH PreviousClaims(child,parent)  "+
															"AS( "+
															"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS WHERE LOCATION_DETAIL_ID =? "+
															"UNION ALL "+
															"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS  "+
															"INNER JOIN PreviousClaims parent ON parent.child = REFERENCE_ID) "+
															"select UD.NAME AS EMPLOYEE_NAME,la.EMPLOYEE_ID,LOD.NAME AS LOCATION_NAME,LOD.LOCATION_DETAIL_ID from PreviousClaims pc join IC_SU_LOCATION_ACCESS la on la.LOCATION_DETAIL_ID=pc.child JOIN IC_SU_LOCATION_DETAILS LOD ON LOD.LOCATION_DETAIL_ID=la.LOCATION_DETAIL_ID JOIN IC_USER_DETAILS UD ON UD.EMPLOYEE_ID=la.EMPLOYEE_ID where LA.EMPLOYEE_ID=? AND la.STATUS='1'";	

	public static final String UPDATE_LOCATION_ACCESS_DETAILS = "UPDATE IC_SU_LOCATION_ACCESS SET MODIFIED_DATE=CURRENT_TIMESTAMP,ACCESS_REVOKE_BY=?,STATUS='0' WHERE LOCATION_DETAIL_ID=? AND EMPLOYEE_ID=?";

	public static final String SELECT_ASSIGNED_MEMBERS_DETAILS = "WITH PreviousClaims(child,parent) "+ 
																"AS( "+
																"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS WHERE LOCATION_DETAIL_ID = ? "+
																"UNION ALL "+
																"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+ 
																"INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID) "+ 
																"select UD.NAME AS EMPLOYEE_NAME,la.EMPLOYEE_ID ,LOD.NAME AS LOCATION_NAME,la.LOCATION_DETAIL_ID from PreviousClaims pc join IC_SU_LOCATION_ACCESS la on la.LOCATION_DETAIL_ID=pc.child JOIN IC_SU_LOCATION_DETAILS LOD ON LOD.LOCATION_DETAIL_ID=la.LOCATION_DETAIL_ID JOIN IC_USER_DETAILS UD ON UD.EMPLOYEE_ID=la.EMPLOYEE_ID AND la.STATUS='1'";


	
	public static final String SELECT_PARENT_VERSION_ID = "SELECT PVD.PARENT_ID, PVD.NEW_VERSION_ID FROM IC_SU_PARENT_VERSION_DETAILS PVD, IC_SU_LOCATION_DETAILS ISLD WHERE "+
															"ISLD.LOCATION_DETAIL_ID=PVD.PARENT_ID AND ISLD.REFERENCE_ID=0 ";


	public static final String SELECT_VERSION_ID =" WITH PreviousClaims(child,parent) "+
													"AS( "+
													"		select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+ 
													"		WHERE LOCATION_DETAIL_ID = ? "+ 
													"		UNION ALL "+
													"		select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+
													"		INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID "+ 
													"		) "+
													"		select pvd.parent_id,pvd.new_version_id "+
													"		FROM PreviousClaims PC JOIN IC_SU_PARENT_VERSION_DETAILS PVD ON "+ 
													"		PVD.PARENT_ID=PC.child ";


	public static final String SELECT_LOCATION_PATH = "WITH PreviousClaims(child,parent) "+
														"AS( "+
														"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+ 
														"WHERE LOCATION_DETAIL_ID = ?  "+
														"UNION ALL "+
														"select LOCATION_DETAIL_ID,REFERENCE_ID from IC_SU_LOCATION_DETAILS "+
														"INNER JOIN PreviousClaims parent ON parent.parent = LOCATION_DETAIL_ID "+
														") "+
														"select ISLD.NAME from PreviousClaims pc join IC_SU_LOCATION_DETAILS ISLD on ISLD.LOCATION_DETAIL_ID=child order by child";


	public static final String UPDATE_REVOKE_LOCATION_ACCESS_ = "UPDATE IC_SU_LOCATION_ACCESS SET STATUS='0' , MODIFIED_DATE=CURRENT_TIMESTAMP, ACCESS_REVOKE_BY=? WHERE EMPLOYEE_ID=? AND LOCATION_DETAIL_ID=?  AND STATUS=1 ";


	public static final String SELECT_SPACE_AUDIT_DETAILS = "select  AUDIT_ID,aud.LOCATION_DETAIL_ID,aud.VERSION_ID,isnull(aud.remarks,'') REMARKS,ud.NAME+'('+aud.MODIFIED_BY+')' MODIFIED_BY,MAX(aud.MODIFIED_DATE) MODIFIED_DATE from  IC_SU_SEATING_DETAILS_AUDITLOG aud join IC_USER_DETAILS ud on ud.EMPLOYEE_ID=aud.MODIFIED_BY  where aud.LOCATION_DETAIL_ID=' group by AUDIT_ID,aud.LOCATION_DETAIL_ID,aud.VERSION_ID,isnull(aud.remarks,''),ud.NAME+'('+aud.MODIFIED_BY+')'";

	public static final String SELECT_ACCESS_ICON_LIST = "SELECT distinct IMAGE_ID,IMAGE_CLASS,IMAGE_URL,TITTLE,EMPLOYEE_ID FROM IC_SU_MENU_ACCESS_DETAILS WHERE  STATUS='1'";
	
	public static final String SELECT_AUDIT_LOG_DETAILS = "select AUDIT_ID,LOCATION_DETAIL_ID,VERSION_ID,OLD_VALUE,NEW_VALUE,DCFM.DISPLAY_NAME,UD.NAME+'('+SDA.MODIFIED_BY+')' MODIFIED_BY,SDA.MODIFIED_DATE,SDA.REMARKS  from IC_SU_SEATING_DETAILS_AUDITLOG SDA,IC_SU_DB_COLUMND_UI_FIELD_MAPPING DCFM,IC_USER_DETAILS UD WHERE DCFM.DB_COLUMN_ID=SDA.DB_COLUMN_ID  AND UD.EMPLOYEE_ID=SDA.MODIFIED_BY AND SDA.AUDIT_ID=? ORDER BY DCFM.DB_COLUMN_ID";

	public static final String SELECT_LAST_MODIFIED_DATE = "SELECT ISVD.LOCATION_DETAIL_ID,isnull(CONVERT(VARCHAR(10),ISVD.MODIFIED_DATE,6),'') MODIFIED_DATE FROM IC_SU_VERSION_DETAILS ISVD,IC_SU_LOCATION_DETAILS ISLD WHERE ISLD.LOCATION_DETAIL_ID=ISVD.LOCATION_DETAIL_ID AND ISLD.REFERENCE_ID=?";

	public static final String SELECT_LOCATION_LIST = "SELECT ISLD.NAME,ISLD.LOCATION_DETAIL_ID  FROM IC_SU_LOCATION_ACCESS ISLA,IC_USER_DETAILS IUD,IC_SU_LOCATION_DETAILS ISLD WHERE IUD.EMPLOYEE_ID=ISLA.EMPLOYEE_ID AND ISLA.STATUS='1' AND ISLD.LOCATION_DETAIL_ID=ISLA.LOCATION_DETAIL_ID AND ISLA.EMPLOYEE_ID=?  AND ISLD.IS_ACTIVE='A'";

	public static final String SELECT_VALID_EMPLOYEE = "SELECT IUD.NAME FROM IC_USER_DETAILS IUD,IC_USER_ROLE_DETAILS IURD WHERE IUD.EMPLOYEE_ID=? AND IUD.IS_ACTIVE='1' AND IUD.EMPLOYEE_ID=IURD.EMPLOYEE_ID AND (IURD.ROLE_ID=8 OR IURD.ROLE_ID=9) AND IURD.IS_ACTIVE='1'GROUP BY IUD.NAME";

	public static final String SELECT_USER_ROLE = "SELECT ROLE FROM  IC_SU_MENU_ACCESS_DETAILS WHERE EMPLOYEE_ID=? GROUP BY ROLE";

	public static final String INSERT_MENU_EDIT_ACCESS = "insert into IC_SU_MENU_ACCESS_DETAILS values(?,'Engineer','editdata','editdata','images/editdata.jpg','Edit','1','SYSTEM',CURRENT_TIMESTAMP,null,null)";

	public static final String INSERT_MENU_AUDIT_ACCESS = "insert into IC_SU_MENU_ACCESS_DETAILS values(?,'Engineer','auditlog','auditlog','images/audit.jpg','Audit Details','1','SYSTEM',CURRENT_TIMESTAMP,null,null)";

	public static final String INSERT_MENU_EDIT_ACCESS_SPOC = "insert into IC_SU_MENU_ACCESS_DETAILS values(?,'SPOC','editdata','editdata','images/editdata.jpg','Edit','1','SYSTEM',CURRENT_TIMESTAMP,null,null)";

	public static final String INSERT_MENU_AUDIT_ACCESS_SPOC = "insert into IC_SU_MENU_ACCESS_DETAILS values(?,'SPOC','auditlog','auditlog','images/audit.jpg','Audit Details','1','SYSTEM',CURRENT_TIMESTAMP,null,null)";



	public static final String SELECT_DUMP ="DECLARE @Locationid INT DECLARE @getLocationid "+
" CURSOR DECLARE @temp TABLE (locationdetailid int, [Organization] varchar(100), "+
"                                               [Country] varchar(100), "+
"                                                                          [City] varchar(100), "+
"                                                                                 [Area] varchar(100), "+
"                                                                                        [Building] varchar(100), "+
"                                                                                                   [Floor] varchar(100), "+
"                                                                                                           [Wing] varchar(100), "+
"                                                                                                                  [ODC] varchar(100), "+
"                                                                                                                        [Customer/Project] varchar(100), "+
"                                                                                                                                           [Non ODC] varchar(100)) "+
" SET @getLocationid = "+
" CURSOR "+
" FOR "+
" SELECT location_detail_id "+
" FROM ic_su_location_details OPEN @getLocationid FETCH NEXT "+
" FROM @getLocationid INTO @Locationid WHILE @@FETCH_STATUS = 0 BEGIN WITH PreviousClaims(child, parent) AS "+
"   (SELECT LOCATION_DETAIL_ID, REFERENCE_ID "+
"    FROM IC_SU_LOCATION_DETAILS sd "+
"   WHERE sd.LOCATION_DETAIL_ID=@Locationid "+
"   UNION ALL SELECT LOCATION_DETAIL_ID, REFERENCE_ID "+
"   FROM IC_SU_LOCATION_DETAILS ld "+
"   INNER JOIN PreviousClaims parent ON parent.parent = ld.LOCATION_DETAIL_ID) "+
" INSERT INTO @temp "+
" SELECT @Locationid AS locationdetailid, "+
"    isnull([Organization], '') [Organization], "+
"     isnull([Country], '') [Country], "+
"     isnull([City], '') [City], "+
"     isnull([Area], '') [Area], "+
"     isnull([Building], '') [Building], "+
"       isnull([Floor], '') [Floor], "+
"       isnull([Wing], '') [Wing], "+
"       isnull([ODC], '') [ODC], "+
"       isnull([Customer/Project], '') [Customer/Project], "+
"      isnull([Non ODC], '') [Non ODC] "+
" FROM "+
"   (SELECT st.STRUCTURE_NAME, "+
"           ISLD.NAME "+
"    FROM PreviousClaims pc "+
"    JOIN IC_SU_LOCATION_DETAILS ISLD ON ISLD.LOCATION_DETAIL_ID=child "+
"    JOIN IC_SU_STRUCTURE st ON st.STRUCTURE_ID=ISLD.STRUCTURE_ID) up PIVOT (max(NAME) "+
"                                                                            FOR STRUCTURE_NAME IN ([Organization], [Country], [City], [Area], [Building], [Floor], [Wing], [ODC], [Customer/Project], [Non ODC])) AS pvt FETCH NEXT "+
" FROM @getLocationid INTO @Locationid END CLOSE @getLocationid DEALLOCATE @getLocationid "+
" SELECT [Organization], "+
"       [Country], "+
"       [City], "+
"       [Area], "+
"       [Building], "+
"       [Floor], "+
"        [Wing], "+
"        [ODC], "+
"        [Customer/Project], "+
"        [Non ODC], "+
"        [Total Prog. Seats], "+
"        [Allocated Progr. Seats], "+
"       [Utilized Prog. Seats], "+
"       [Avail. Prog Seats], "+
"       [Total TL Seats], "+
"       [Allocated TL Seats], "+
"       [Utilized TL Seats], "+
"       [Avail. TL Seats], "+
"       [Total PM Seats], "+
"      [Allocated PM Seats], "+
"       [Utilized PM Seats], "+
"       [Avail. PM Seats], "+
"        [Total Closed Cabins], "+
"        [Allocated Closed Cabins], "+
"        [Utilized Closed Cabins], "+
"        [Avail. Closed Cabins], "+
"       [Total Seats],[Total Allocated Seats], "+
"       [Total Utilized Seats], "+
"       [Total  Avail. Seats], "+
"       REMARKS "+
" FROM @temp AS tem "+
" JOIN "+
"  (SELECT ld.LOCATION_DETAIL_ID, "+
"          ld.NAME, "+
"          [Total Prog. Seats], "+
"          [Allocated Progr. Seats], "+
"          [Utilized Prog. Seats], "+
"          [Avail. Prog Seats], "+
"          [Total TL Seats], "+
"         [Allocated TL Seats], "+
"          [Utilized TL Seats], "+
"          [Avail. TL Seats], "+
"         [Total PM Seats], "+
"          [Allocated PM Seats], "+
"          [Utilized PM Seats], "+
"          [Avail. PM Seats], "+
"          [Total Closed Cabins], "+
"           [Allocated Closed Cabins], "+
"           [Utilized Closed Cabins], "+
"         [Avail. Closed Cabins], "+
"          [Total Seats],[Total Allocated Seats], "+
"          [Total Utilized Seats], "+
"          [Total  Avail. Seats], "+
"           isnull(ld.REMARKS, '') REMARKS "+
"   FROM "+
"     (SELECT LOCATION_DETAIL_ID, "+
"              [Total Prog. Seats], "+
"              [Allocated Progr. Seats], "+
"              [Utilized Prog. Seats], "+
"              [Avail. Prog Seats], "+
"             [Total TL Seats], "+
"              [Allocated TL Seats], "+
"              [Utilized TL Seats], "+
"              [Avail. TL Seats], "+
"              [Total PM Seats], "+
"              [Allocated PM Seats], "+
"              [Utilized PM Seats], "+
"              [Avail. PM Seats], "+
"              [Total Closed Cabins], "+
"              [Allocated Closed Cabins], "+
"             [Utilized Closed Cabins], "+
"              [Avail. Closed Cabins], "+
"              [Total Seats],[Total Allocated Seats], "+
"              [Total Utilized Seats], "+
"              [Total  Avail. Seats] "+
"       FROM "+
"         (SELECT sd.LOCATION_DETAIL_ID, "+
"                db.TOOLTIP, "+
"                sd.VALUE "+
"         FROM IC_SU_SEATING_DETAIL sd, "+
"                                   IC_SU_DB_COLUMND_UI_FIELD_MAPPING db "+
                                   "          WHERE db.DB_COLUMN_ID=sd.DB_COLUMN_ID)up pivot(sum(VALUE) "+
                                   "                                                       FOR TOOLTIP IN ( [Total Prog. Seats], [Allocated Progr. Seats], [Utilized Prog. Seats], [Avail. Prog Seats], [Total TL Seats], [Allocated TL Seats], [Utilized TL Seats], [Avail. TL Seats], [Total PM Seats], [Allocated PM Seats], [Utilized PM Seats], [Avail. PM Seats], [Total Closed Cabins], [Allocated Closed Cabins], [Utilized Closed Cabins], [Avail. Closed Cabins], [Total Seats],[Total Allocated Seats], [Total Utilized Seats], [Total  Avail. Seats]))AS pvt)pv "+
                                   "   JOIN IC_SU_LOCATION_DETAILS ld ON ld.LOCATION_DETAIL_ID=pv.LOCATION_DETAIL_ID "+
                                   "   AND ld.IS_ACTIVE='A') AS DATA ON DATA.LOCATION_DETAIL_ID=tem.locationdetailid "+
                                   " ORDER BY Organization";

	private static Map<String, String> spaceauditQueryMapping = new HashMap<String, String>();
	
	public static Map<String, String> getspaceauditquerymapping() {
		if (spaceauditQueryMapping.size() == 0) {
			spaceauditQueryMapping.put("auditid", "audit_id");
			spaceauditQueryMapping.put("modifiedid", "modified_by");
			spaceauditQueryMapping.put("modifieddate", "modified_date");
			spaceauditQueryMapping.put("remarks", "remarks");
		}
		return spaceauditQueryMapping;
	}
	

	
	public static String RENAME_LOCATION_NAME_IN_ODC_MASTER="if exists(Select * from IC_SU_LOCATION_ODC_DETAILS where LOCATION_DETAIL_ID=? ) AND (SELECT STRUCTURE_ID FROM IC_SU_LOCATION_DETAILS WHERE LOCATION_DETAIL_ID=?)>7     " +
			"	Update IC_SU_LOCATION_ODC_DETAILS SET ODC_NAME=?,BUSINESS_UNIT_ALIAS=?, CUSTOMER_GROUP_ID=?,BUSINESS_UNIT_ID=? ," +
			"				 MODIFIED_BY=?, MODIFIED_DATE=CURRENT_TIMESTAMP,PS_Customer_GRP=?" +
			"				WHERE  LOCATION_DETAIL_ID=?" +
			"	else" +
			"	INSERT INTO IC_SU_LOCATION_ODC_DETAILS(LOCATION_DETAIL_ID,ODC_NAME,GEN_ODC_NAME,IS_ACTIVE,CREATED_BY,CREATED_DATE,PS_Customer_GRP,BUSINESS_UNIT_ALIAS,CUSTOMER_GROUP_ID,BUSINESS_UNIT_ID)" +
			"	VALUES(?,?,?,?,?,GETDATE(),?,?,?,?)";
	
	/************BU DU Changes****************************************************************************************************************/
	public static final String SELECT_BLOCK_SEAT_DETAIL="Select blockSeatDet.LOCATION_DETAIL_ID,sldParent.NAME,blockSeatDet.CUSTOMER_LOCATION_ID,sldCustomer.NAME,BLOCKED_SEAT_COUNT,blockSeatDet.BLOCKED_DATE," +
			"	BLOCK_EXTENSION_DATE,BLOCK_EXTENSION_COMMENTS" +
			"	from IC_SU_BLOCKED_SEATING_DETAIL blockSeatDet , IC_SU_LOCATION_DETAILS sldParent,IC_SU_LOCATION_DETAILS sldCustomer" +
			"	where blockSeatDet.LOCATION_DETAIL_ID=sldParent.LOCATION_DETAIL_ID and sldParent.IS_ACTIVE='A'" +
			"	and blockSeatDet.CUSTOMER_LOCATION_ID=sldCustomer.LOCATION_DETAIL_ID and sldCustomer.IS_ACTIVE='A' and blockSeatDet.LOCATION_DETAIL_ID=?";
	
	public static String SELECT_CUSTOMER_MASTER="Select CUSTOMER_ID,CUSTOMER_GROUP_DESCR from IC_SU_CUSTOMER_GROUP_MASTER where IS_ACTIVE=1";
	
	public static final String SELECT_VERTICAL_MASTER ="Select distinct  org.L5_VERTICAL_CODE,org.L5_VERTICAL_HEAD,org.L5_VERTICAL_NAME" +
			"	 from IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping, IC_SU_ORG_VERTICAL_MASTER org  where" +
			"	 org.L5_VERTICAL_CODE in (" +
			"	Select L5_VERTICAL_CODE from IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING where" +
			"	 DEPT_ID in (SELECT BILLING_AUTHORITY FROM IC_SU_CUSTOMER_GROUP_MASTER WHERE CUSTOMER_ID=?) ) and mapping.is_ACTIVE=1 and org.IS_ACTIVE=1 ";
	public static final String SELECT_VERTICAL_MASTE="Select distinct b.L5_VERTICAL_CODE,b.L5_VERTICAL_NAME from  IC_SU_ORG_VERTICAL_MASTER b";
	
	
	public static final String SELECT_HORIZONTAL_MASTER ="Select distinct b.L6_HORIZONTAL_CODE,b.L6_HORIZONTAL_NAME,L6_BU_HEAD from  IC_SU_ORG_HORIZONTAL_MASTER b where b.IS_ACTIVE=1";
	
	public static final String SELECT_BEF_MASTER ="Select SYSTEM_BEF_ID,L6_BEF_CODE,L6_BEF_HEAD,L6_BEF_NAME from IC_SU_ORG_BEF_MASTER where IS_ACTIVE=1";
	
	public static final String SELECT_SALES_MASTER ="Select SYSTEM_SALES_ID,L5_BU_HEAD,L5_SALES_CODE,L5_SALES_NAME from IC_SU_ORG_SALES_MASTER where IS_ACTIVE=1" ;
	
	public static final String SELECT_DEPT_MASTER="Select " +
			"	case when mapping.L4_UNIT_CODE='VBU' then mapping.L5_VERTICAL_CODE else mapping.L6_HORIZONTAL_CODE end as MAPPING_NAME," +
			"	mapping.L4_UNIT_CODE,mapping.L5_VERTICAL_CODE,mapping.L6_HORIZONTAL_CODE,deptMaster.DEPT_ID,deptMaster.L8_DESCRIPTION" +
			"	from IC_SU_ORG_DEPT_MASTER deptMaster, IC_SU_VERTICAL_HORIZONTAL_DEPARTMENT_MAPPING mapping" +
			"	WHERE deptMaster.DEPT_ID=mapping.DEPT_ID";

	//added by shruti
	private static Map<String,String> workspaceApprovalListMapping=new HashMap<String,String>();
	
	public static Map<String, String> getWorkspaceAppListColumnValues() {
		if (workspaceApprovalListMapping.size() == 0)
		{
			workspaceApprovalListMapping.put("MAIN_REQUEST_ID_FOR_LIST", "A.MAIN_REQUEST_ID");
			workspaceApprovalListMapping.put("SUB_BUSINESS_UNIT_ID", "SUB_BUSINESS_UNIT_ID");
			workspaceApprovalListMapping.put("BUSINESS_UNIT", "B.BU_DESCRIPTION");			
			workspaceApprovalListMapping.put("SBU_ID", "B.L6_SBU_NAME");
			workspaceApprovalListMapping.put("CUSTOMER_NAME", "C.CUSTOMER_NAME");
			workspaceApprovalListMapping.put("OPPORTUNITY_IDORPROJ","OPPORTUNITY_IDORPROJ");
			workspaceApprovalListMapping.put("NAME","PROJECT_NAME");
			workspaceApprovalListMapping.put("QUARTER_MONTH","QUARTER & MONTH");
			workspaceApprovalListMapping.put("STATUS_ID","STATUS_ID");
			workspaceApprovalListMapping.put("STATUS","STATUS");
			workspaceApprovalListMapping.put("TOTAL_SEATS_REQUESTED","TOTAL_SEATS_REQUESTED");
		}
		return workspaceApprovalListMapping;
	}
	
	private static Map<String,String> workspaceReqListMapping=new HashMap<String,String>();
	public static Map<String, String> getWorkspaceReqListColumnValues() {
		if (workspaceReqListMapping.size() == 0)
		{
			workspaceReqListMapping.put("MAIN_REQUEST_ID_FOR_LIST", "A.MAIN_REQUEST_ID");
			workspaceReqListMapping.put("SUB_BUSINESS_UNIT_ID", "SUB_BUSINESS_UNIT_ID");
			workspaceReqListMapping.put("BUSINESS_UNIT", "B.BU_DESCRIPTION");
			workspaceReqListMapping.put("SBU_ID", "B.L6_SBU_NAME");
			workspaceReqListMapping.put("CUSTOMER_NAME", "C.CUSTOMER_NAME");
			workspaceReqListMapping.put("OPPORTUNITY_IDORPROJ","OPPORTUNITY_IDORPROJ");
			workspaceReqListMapping.put("NAME","PROJECT_NAME");
			workspaceReqListMapping.put("QUARTER_MONTH","QUARTER & MONTH");
			workspaceReqListMapping.put("STATUS_ID","STATUS_ID");
			workspaceReqListMapping.put("STATUS","STATUS");
			workspaceReqListMapping.put("TOTAL_SEATS_REQUESTED","TOTAL_SEATS_REQUESTED");
		}
		return workspaceReqListMapping;
}
	//Added for space allocation by shruti
	private static Map<String,String> workspaceAllocationMapping=new HashMap<String,String>();
	public static Map<String, String> getWorkspaceAllocationColumnValues() {
		if (workspaceAllocationMapping.size() == 0)
		{
			workspaceAllocationMapping.put("LOCATION_NAME", "CITY_NAME");
			workspaceAllocationMapping.put("BUILDING_NAME", "BUILDING_NAME");
			workspaceAllocationMapping.put("FLOOR_NAME", "FLOOR_NAME");
			workspaceAllocationMapping.put("WING_NAME", "WING_NAME");
			workspaceAllocationMapping.put("TOTAL_SEATS_REQUESTED","AVAILABLE_COUNT_OF_SEATS");
			workspaceAllocationMapping.put("ALREDY_SOFT_ALL","SOFT_ALLOC_COUNT_OF_SEATS");
			workspaceAllocationMapping.put("AREA_ID","AREA_ID");
			workspaceAllocationMapping.put("CITY_ID","CITY_ID");
			workspaceAllocationMapping.put("BUILDING_ID","BUILDING_ID");
			workspaceAllocationMapping.put("FLOOR_ID","FLOOR_ID");
			workspaceAllocationMapping.put("WING_ID","WING_ID");
			workspaceAllocationMapping.put("WING_NAME","WING");
			workspaceAllocationMapping.put("EXCEPTION_COUNT","EXCEPTION_COUNT");
			workspaceAllocationMapping.put("AVAILABLE_COUNT","AVAILABLE_COUNT");
			workspaceAllocationMapping.put("DEACTIVATE_SOFT_ALLOC_SEATS","Deallocate");
		}
		return workspaceAllocationMapping;
		}
	
	//Added for space allocation pop up by shruti
	private static Map<String,String> workspaceAllocationCountMapping=new HashMap<String,String>();
	public static Map<String, String> getWorkspaceAllocationCountColumnValues() {
		if (workspaceAllocationMapping.size() == 0)
		{
			workspaceAllocationCountMapping.put("SUB_REQUEST_ID", "SUB_REQUEST_ID");
			workspaceAllocationCountMapping.put("LOCATION_NAME", "CITY_NAME");
			workspaceAllocationCountMapping.put("BUILDING_NAME", "BUIL_NAME");
			workspaceAllocationCountMapping.put("FLOOR_NAME", "FLOOR_NAME");
			workspaceAllocationCountMapping.put("ALREDY_SOFT_ALL","D.SOFT_ALLOCATION_COUNT_OF_SEATS");
			workspaceAllocationCountMapping.put("REMARKS","D.COMMENTS");
			workspaceAllocationCountMapping.put("FLOOR_ID","FLOOR_ID");
			workspaceAllocationCountMapping.put("REQUESTED_COUNT_OF_SEATS","REQUESTED_COUNT_OF_SEATS");
		}
		return workspaceAllocationCountMapping;
		}
	//Added by shruti to add "PENDING_WITH" column
	private static Map<String,String> workspaceExistingReqListMapping=new HashMap<String,String>();
	public static Map<String, String> getWorkspaceExistingReqListColumnValues() {
		if (workspaceReqListMapping.size() == 0)
		{
			workspaceExistingReqListMapping.put("MAIN_REQUEST_ID_FOR_LIST", "A.MAIN_REQUEST_ID");
			workspaceExistingReqListMapping.put("SUB_BUSINESS_UNIT_ID", "SUB_BUSINESS_UNIT_ID");
			workspaceExistingReqListMapping.put("BUSINESS_UNIT", "B.BU_DESCRIPTION");			
			workspaceExistingReqListMapping.put("SBU_ID", "B.L6_SBU_NAME");
			workspaceExistingReqListMapping.put("CUSTOMER_NAME", "C.CUSTOMER_NAME");
			workspaceExistingReqListMapping.put("OPPORTUNITY_IDORPROJ","OPPORTUNITY_IDORPROJ");
			workspaceExistingReqListMapping.put("NAME","PROJECT_NAME");
			workspaceExistingReqListMapping.put("QUARTER_MONTH","QUARTER & MONTH");
			workspaceExistingReqListMapping.put("STATUS_ID","STATUS_ID");
			workspaceExistingReqListMapping.put("STATUS","STATUS");
			workspaceExistingReqListMapping.put("TOTAL_SEATS_REQUESTED","TOTAL_SEATS_REQUESTED");
			workspaceExistingReqListMapping.put("PENDING_WITH","PENDING_WITH");
			
		}
		return workspaceExistingReqListMapping;
}
	
	/************BU DU Changes****************************************************************************************************************/
	}
		
		