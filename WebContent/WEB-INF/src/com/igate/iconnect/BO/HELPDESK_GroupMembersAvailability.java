/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

//Dhiren:Added Serializable
public class HELPDESK_GroupMembersAvailability implements Serializable{

	private String EMPLOYEE_NAME;
	private String STATUS;
	private String DATE_TIME;
	private String FROM_DATE;
	private String TO_DATE;
	public String getFROM_DATE() {
		return FROM_DATE;
	}
	public void setFROM_DATE(String fROMDATE) {
		FROM_DATE = fROMDATE;
	}
	public String getTO_DATE() {
		return TO_DATE;
	}
	public void setTO_DATE(String tODATE) {
		TO_DATE = tODATE;
	}
	private String LOGGED_OUT_BY;
	public String getDATE_TIME() {
		return DATE_TIME;
	}
	public void setDATE_TIME(String dATETIME) {
		DATE_TIME = dATETIME;
	}
	public String getLOGGED_OUT_BY() {
		return LOGGED_OUT_BY;
	}
	public void setLOGGED_OUT_BY(String lOGGEDOUTBY) {
		LOGGED_OUT_BY = lOGGEDOUTBY;
	}
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	public void setEMPLOYEE_NAME(String eMPLOYEENAME) {
		EMPLOYEE_NAME = eMPLOYEENAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
}
