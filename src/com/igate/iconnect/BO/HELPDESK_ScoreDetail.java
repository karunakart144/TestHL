package com.igate.iconnect.BO;

import java.io.Serializable;

public class HELPDESK_ScoreDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String EMPLOYEE_ID;
	private String TICKET_ID;
	private String OLD_TOTAL_SCORE;
	private String NEW_TOTAL_SCORE;
	private String IS_FIRST_LOGIN;
	public String getIS_FIRST_LOGIN() {
		return IS_FIRST_LOGIN;
	}
	public void setIS_FIRST_LOGIN(String iSFIRSTLOGIN) {
		IS_FIRST_LOGIN = iSFIRSTLOGIN;
	}
	public String getOLD_TOTAL_SCORE() {
		return OLD_TOTAL_SCORE;
	}
	public void setOLD_TOTAL_SCORE(String oLDTOTALSCORE) {
		OLD_TOTAL_SCORE = oLDTOTALSCORE;
	}
	public String getNEW_TOTAL_SCORE() {
		return NEW_TOTAL_SCORE;
	}
	public void setNEW_TOTAL_SCORE(String nEWTOTALSCORE) {
		NEW_TOTAL_SCORE = nEWTOTALSCORE;
	}
	private String WEIGHTAGE;
	public String getWEIGHTAGE() {
		return WEIGHTAGE;
	}
	public void setWEIGHTAGE(String wEIGHTAGE) {
		WEIGHTAGE = wEIGHTAGE;
	}
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public void setTICKET_ID(String tICKETID) {
		TICKET_ID = tICKETID;
	}
	private String CREATED_DATE;
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATEDDATE) {
		CREATED_DATE = cREATEDDATE;
	}
	private String DATE_TIME;
	private String FROM_DATE;
	private String TO_DATE;
	private String DATE_CHANGE;
	private String CREATED_BY;
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATEDBY) {
		CREATED_BY = cREATEDBY;
	}
	public String getDATE_CHANGE() {
		return DATE_CHANGE;
	}
	public void setDATE_CHANGE(String dATECHANGE) {
		DATE_CHANGE = dATECHANGE;
	}
	public String getDATE_TIME() {
		return DATE_TIME;
	}
	public void setDATE_TIME(String dATETIME) {
		DATE_TIME = dATETIME;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}
	public void setEMPLOYEE_ID(String eMPLOYEEID) {
		EMPLOYEE_ID = eMPLOYEEID;
	}
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	public void setEMPLOYEE_NAME(String eMPLOYEENAME) {
		EMPLOYEE_NAME = eMPLOYEENAME;
	}
	public String getHIGH_SCORE() {
		return HIGH_SCORE;
	}
	public void setHIGH_SCORE(String hIGHSCORE) {
		HIGH_SCORE = hIGHSCORE;
	}
	public String getMEDIUM_SCORE() {
		return MEDIUM_SCORE;
	}
	public void setMEDIUM_SCORE(String mEDIUMSCORE) {
		MEDIUM_SCORE = mEDIUMSCORE;
	}
	public String getLOW_SCORE() {
		return LOW_SCORE;
	}
	public void setLOW_SCORE(String lOWSCORE) {
		LOW_SCORE = lOWSCORE;
	}
	public String getTOTAL_SCORE() {
		return TOTAL_SCORE;
	}
	public void setTOTAL_SCORE(String tOTALSCORE) {
		TOTAL_SCORE = tOTALSCORE;
	}
	private String EMPLOYEE_NAME;
	private String HIGH_SCORE;
	private String MEDIUM_SCORE;
	private String LOW_SCORE;
	private String TOTAL_SCORE;

}
