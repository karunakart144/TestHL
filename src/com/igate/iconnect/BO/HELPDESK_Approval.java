/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class HELPDESK_Approval  implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 2507118259105702628L;
    private String TICKET_ID;
	private String APPROVER_ID;
	private String APPROVER_NAME;
	private String COMMENTS;
	private String IS_EXCEPTIONAL_APPROVAL;
	private String CREATED_BY;
	private String CREATED_NAME;
	private String CREATED_DATE;
	private String MODIFIED_BY;
	private String MODIFIED_DATE;
	private String STATUS;
	private String EXCEPTION_START_DATE;
	private String EXCEPTION_END_DATE;
	public String getEXCEPTION_START_DATE() {
        return EXCEPTION_START_DATE;
    }
    public void setEXCEPTION_START_DATE(String eXCEPTIONSTARTDATE) {
        EXCEPTION_START_DATE = eXCEPTIONSTARTDATE;
    }
    public String getEXCEPTION_END_DATE() {
        return EXCEPTION_END_DATE;
    }
    public void setEXCEPTION_END_DATE(String eXCEPTIONENDDATE) {
        EXCEPTION_END_DATE = eXCEPTIONENDDATE;
    }
    public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public void setTICKET_ID(String tICKETID) {
		TICKET_ID = tICKETID;
	}
	public String getAPPROVER_ID() {
		return APPROVER_ID;
	}
	public void setAPPROVER_ID(String aPPROVERID) {
		APPROVER_ID = aPPROVERID;
	}
	public String getAPPROVER_NAME() {
		return APPROVER_NAME;
	}
	public void setAPPROVER_NAME(String aPPROVERNAME) {
		APPROVER_NAME = aPPROVERNAME;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	public String getCREATED_NAME() {
		return CREATED_NAME;
	}
	public void setCREATED_NAME(String cREATEDNAME) {
		CREATED_NAME = cREATEDNAME;
	}
	public String getIS_EXCEPTIONAL_APPROVAL() {
		return IS_EXCEPTIONAL_APPROVAL;
	}
	public void setIS_EXCEPTIONAL_APPROVAL(String iSEXCEPTIONALAPPROVAL) {
		IS_EXCEPTIONAL_APPROVAL = iSEXCEPTIONALAPPROVAL;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATEDBY) {
		CREATED_BY = cREATEDBY;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATEDDATE) {
		CREATED_DATE = cREATEDDATE;
	}
	public String getMODIFIED_BY() {
		return MODIFIED_BY;
	}
	public void setMODIFIED_BY(String mODIFIEDBY) {
		MODIFIED_BY = mODIFIEDBY;
	}
	public String getMODIFIED_DATE() {
		return MODIFIED_DATE;
	}
	public void setMODIFIED_DATE(String mODIFIEDDATE) {
		MODIFIED_DATE = mODIFIEDDATE;
	}
}
