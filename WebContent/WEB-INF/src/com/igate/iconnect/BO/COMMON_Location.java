/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class COMMON_Location implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = -8613320719486547536L;
    public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}
	public String getORGANIZATION() {
		return ORGANIZATION;
	}
	public void setORGANIZATION(String oRGANIZATION) {
		ORGANIZATION = oRGANIZATION;
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
	public String getTIMEZONE_ID() {
		return TIMEZONE_ID;
	}
	public void setTIMEZONE_ID(String tIMEZONEID) {
		TIMEZONE_ID = tIMEZONEID;
	}
	public String getPS_LOCATION_ID() {
		return PS_LOCATION_ID;
	}
	public void setPS_LOCATION_ID(String pSLOCATIONID) {
		PS_LOCATION_ID = pSLOCATIONID;
	}
	public String getSET_ID() {
		return SET_ID;
	}
	public void setSET_ID(String sETID) {
		SET_ID = sETID;
	}
	private String LOCATION_ID;
	private String ORGANIZATION;
	private String COUNTRY;
	private String CITY;
	private String AREA;
	private String SHORT_NAME;
	private String TIMEZONE_ID;
	private String PS_LOCATION_ID;
	private String SET_ID;
	
}
