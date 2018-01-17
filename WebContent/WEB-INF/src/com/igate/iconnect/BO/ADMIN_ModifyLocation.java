package com.igate.iconnect.BO;

import java.io.Serializable;

public class ADMIN_ModifyLocation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7685903426961206512L;
	private String LOCATION_ID;
	private String CITY;
	private String BUILDING;
	private String FLOOR;
	private String START_COUNT_PAGE;
	private String LOCDETID;
	private String STATUS;


	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getLOCDETID() {
		return LOCDETID;
	}
	public void setLOCDETID(String lOCDETID) {
		LOCDETID = lOCDETID;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}
	public String getSTART_COUNT_PAGE() {
		return START_COUNT_PAGE;
	}
	public void setSTART_COUNT_PAGE(String sTARTCOUNTPAGE) {
		START_COUNT_PAGE = sTARTCOUNTPAGE;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getBUILDING() {
		return BUILDING;
	}
	public void setBUILDING(String bUILDING) {
		BUILDING = bUILDING;
	}
	public String getFLOOR() {
		return FLOOR;
	}
	public void setFLOOR(String fLOOR) {
		FLOOR = fLOOR;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
