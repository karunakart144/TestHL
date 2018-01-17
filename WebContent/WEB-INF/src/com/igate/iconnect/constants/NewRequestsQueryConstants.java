package com.igate.iconnect.constants;

public class NewRequestsQueryConstants {

	
	public static final String GET_LOCATION_DETAILS="Select LOCATION_DETAIL_ID,NAME  from IC_SU_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure" +
			" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID" +
			" and suStructure.STRUCTURE_NAME= ? and IS_ACTIVE='A' and locDet.REFERENCE_ID = ?";
	
}
