package com.igate.iconnect.constants;

public class WORKSPACEPLANNING_UpdateConstants {

	 public static final String SELECT_ALL_TABLES = " SELECT NAME  FROM sys.tables WHERE (NAME LIKE '%IC_SU%') OR (NAME LIKE '%IC_WP%') OR (NAME LIKE '%CC%') order by NAME " ;
	 
	 public static final String SELECT_ALL_COLUMNS = " SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME like ? ORDER BY COLUMN_NAME ASC " ;
	
}
