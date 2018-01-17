/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class HELPDESK_RequestorInformation implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1399537259583799455L;
    private String EMPLOYEE_ID;
	private String EMP_NAME;
	private String ORGANIZATION;
	private String REPORTING_MANAGER_ID;
	private String REPORTING_MANAGER_NAME;
	private String DU_ID;
	private String DU_NAME;
	private String LOCATION_ID;
	private String COUNTRY;
	private String CITY;
	private String AREA;
	private String SHORT_NAME; 
	private String CUSTOMER_NAME;
	private String GRADE; // Added by Kruthi
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMERNAME) {
		CUSTOMER_NAME = cUSTOMERNAME;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getAREA() {
		return AREA;
	}
	public void setAREA(String aREA) {
		AREA = aREA;
	}
	public String getSHORT_NAME() {
		return SHORT_NAME;
	}
	public void setSHORT_NAME(String sHORTNAME) {
		SHORT_NAME = sHORTNAME;
	}
	public String getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}
	public void setEMPLOYEE_ID(String eMPLOYEEID) {
		EMPLOYEE_ID = eMPLOYEEID;
	}
	public String getEMP_NAME() {
		return EMP_NAME;
	}
	public void setEMP_NAME(String eMPNAME) {
		EMP_NAME = eMPNAME;
	}
	public String getORGANIZATION() {
		return ORGANIZATION;
	}
	public void setORGANIZATION(String oRGANIZATION) {
		ORGANIZATION = oRGANIZATION;
	}
	public String getREPORTING_MANAGER_ID() {
		return REPORTING_MANAGER_ID;
	}
	public void setREPORTING_MANAGER_ID(String rEPORTINGMANAGERID) {
		REPORTING_MANAGER_ID = rEPORTINGMANAGERID;
	}
	public String getREPORTING_MANAGER_NAME() {
		return REPORTING_MANAGER_NAME;
	}
	public void setREPORTING_MANAGER_NAME(String rEPORTINGMANAGERNAME) {
		REPORTING_MANAGER_NAME = rEPORTINGMANAGERNAME;
	}
	public String getDU_ID() {
		return DU_ID;
	}
	public void setDU_ID(String dUID) {
		DU_ID = dUID;
	}
	public String getDU_NAME() {
		return DU_NAME;
	}
	public void setDU_NAME(String dUNAME) {
		DU_NAME = dUNAME;
	}
	public String getGRADE() {
		return GRADE;
	}
	public void setGRADE(String gRADE) {
		GRADE = gRADE;
	}
	
}
