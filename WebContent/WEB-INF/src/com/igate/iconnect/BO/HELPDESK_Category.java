/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;


public class HELPDESK_Category  implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 3821235917912250530L;
    private String CATEGORY_ID;
	private String NAME;
	private String LINK;
	private String PARENT_ID;
	private String IS_CHANGE_REQUEST;

	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORYID) {
		CATEGORY_ID = cATEGORYID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getLINK() {
		return LINK;
	}
	public void setLINK(String lINK) {
		LINK = lINK;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENTID) {
		PARENT_ID = pARENTID;
	}
	public String getIS_CHANGE_REQUEST() {
		return IS_CHANGE_REQUEST;
	}
	public void setIS_CHANGE_REQUEST(String iSCHANGEREQUEST) {
		IS_CHANGE_REQUEST = iSCHANGEREQUEST;
	}
	
	
	
	private String FUNCTION_ID;
	private String FUNCTION_NAME;	
	private String CATEGORY_NAME;
	private String CATEGORY_STATUS;
	private String SUBCATEGORY_ID;
	private String SUBCATEGORY_NAME;
	private String SUBCATEGORY_STATUS;
	private String APPROVER_LEVEL_1;
	private String APPROVER_LEVEL_2;
	private String APPROVER_LEVEL_3;
	private String APPROVER_LEVEL_STATUS_1;
	private String APPROVER_LEVEL_STATUS_2;
	private String APPROVER_LEVEL_STATUS_3;
	public String getAPPROVER_LEVEL_STATUS_1() {
		return APPROVER_LEVEL_STATUS_1;
	}
	public void setAPPROVER_LEVEL_STATUS_1(String aPPROVERLEVELSTATUS_1) {
		APPROVER_LEVEL_STATUS_1 = aPPROVERLEVELSTATUS_1;
	}
	public String getAPPROVER_LEVEL_STATUS_2() {
		return APPROVER_LEVEL_STATUS_2;
	}
	public void setAPPROVER_LEVEL_STATUS_2(String aPPROVERLEVELSTATUS_2) {
		APPROVER_LEVEL_STATUS_2 = aPPROVERLEVELSTATUS_2;
	}
	public String getAPPROVER_LEVEL_STATUS_3() {
		return APPROVER_LEVEL_STATUS_3;
	}
	public void setAPPROVER_LEVEL_STATUS_3(String aPPROVERLEVELSTATUS_3) {
		APPROVER_LEVEL_STATUS_3 = aPPROVERLEVELSTATUS_3;
	}



	private String RECOMMENDED_PRIORITY;
	private String START_COUNT_PAGE;

	public String getSTART_COUNT_PAGE() {
		return START_COUNT_PAGE;
	}
	public void setSTART_COUNT_PAGE(String sTARTCOUNTPAGE) {
		START_COUNT_PAGE = sTARTCOUNTPAGE;
	}
	public String getFUNCTION_ID() {
		return FUNCTION_ID;
	}
	public void setFUNCTION_ID(String fUNCTIONID) {
		FUNCTION_ID = fUNCTIONID;
	}
	public String getFUNCTION_NAME() {
		return FUNCTION_NAME;
	}
	public void setFUNCTION_NAME(String fUNCTIONNAME) {
		FUNCTION_NAME = fUNCTIONNAME;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORYNAME) {
		CATEGORY_NAME = cATEGORYNAME;
	}
	public String getCATEGORY_STATUS() {
		return CATEGORY_STATUS;
	}
	public void setCATEGORY_STATUS(String cATEGORYSTATUS) {
		CATEGORY_STATUS = cATEGORYSTATUS;
	}
	public String getSUBCATEGORY_ID() {
		return SUBCATEGORY_ID;
	}
	public void setSUBCATEGORY_ID(String sUBCATEGORYID) {
		SUBCATEGORY_ID = sUBCATEGORYID;
	}
	public String getSUBCATEGORY_NAME() {
		return SUBCATEGORY_NAME;
	}
	public void setSUBCATEGORY_NAME(String sUBCATEGORYNAME) {
		SUBCATEGORY_NAME = sUBCATEGORYNAME;
	}
	public String getSUBCATEGORY_STATUS() {
		return SUBCATEGORY_STATUS;
	}
	public void setSUBCATEGORY_STATUS(String sUBCATEGORYSTATUS) {
		SUBCATEGORY_STATUS = sUBCATEGORYSTATUS;
	}
	public String getAPPROVER_LEVEL_1() {
		return APPROVER_LEVEL_1;
	}
	public void setAPPROVER_LEVEL_1(String aPPROVERLEVEL_1) {
		APPROVER_LEVEL_1 = aPPROVERLEVEL_1;
	}
	public String getAPPROVER_LEVEL_2() {
		return APPROVER_LEVEL_2;
	}
	public void setAPPROVER_LEVEL_2(String aPPROVERLEVEL_2) {
		APPROVER_LEVEL_2 = aPPROVERLEVEL_2;
	}
	public String getAPPROVER_LEVEL_3() {
		return APPROVER_LEVEL_3;
	}
	public void setAPPROVER_LEVEL_3(String aPPROVERLEVEL_3) {
		APPROVER_LEVEL_3 = aPPROVERLEVEL_3;
	}
	public String getRECOMMENDED_PRIORITY() {
		return RECOMMENDED_PRIORITY;
	}
	public void setRECOMMENDED_PRIORITY(String rECOMMENDEDPRIORITY) {
		RECOMMENDED_PRIORITY = rECOMMENDEDPRIORITY;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
