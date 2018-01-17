/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class MailTracker implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = -5322424305986747911L;
    private String MAIL_ID;
	private String TO_ADDRESS;
	private String FROM_ADDRESS;
	private String SUBJECT;
	private String DESCRIPTION;
	private String ATTACHMENT_PATH;
	private String SEVERITY_ID;
	private String STATUS;
	private String RECEIVED_DATE;
	private String SOURCE;
	private String REFERENCE_ID;
	private String REASON;
	private String CREATED_BY;
	private String CREATED_DATE;
	private String MODIFIED_BY;
	private String MODIFIED_DATE;
	private String ATTACHMENT_NAME;
	private String EMPLOYEE_ID;
	private String LOCK;
	private String LOCKED_BY;
	private String LOCKED_DATE;
	private String IS_PREMIUM_MAIL;
	private String CC_ADDRESS="";
	public String getCC_ADDRESS() {
		return CC_ADDRESS;
	}
	public void setCC_ADDRESS(String cCADDRESS) {
		CC_ADDRESS = cCADDRESS;
	}
	public String getIS_PREMIUM_MAIL() {
		return IS_PREMIUM_MAIL;
	}
	public void setIS_PREMIUM_MAIL(String iSPREMIUMMAIL) {
		IS_PREMIUM_MAIL = iSPREMIUMMAIL;
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
	public String getMAIL_ID() {
		return MAIL_ID;
	}
	public void setMAIL_ID(String mAILID) {
		MAIL_ID = mAILID;
	}
	public String getTO_ADDRESS() {
		return TO_ADDRESS;
	}
	public void setTO_ADDRESS(String tOADDRESS) {
		TO_ADDRESS = tOADDRESS;
	}
	
	public String getFROM_ADDRESS() {
		return FROM_ADDRESS;
	}
	public void setFROM_ADDRESS(String fROMADDRESS) {
		FROM_ADDRESS = fROMADDRESS;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getATTACHMENT_PATH() {
		return ATTACHMENT_PATH;
	}
	public void setATTACHMENT_PATH(String aTTACHMENTPATH) {
		ATTACHMENT_PATH = aTTACHMENTPATH;
	}
	public String getSEVERITY_ID() {
		return SEVERITY_ID;
	}
	public void setSEVERITY_ID(String sEVERITYID) {
		SEVERITY_ID = sEVERITYID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(String rECEIVEDDATE) {
		RECEIVED_DATE = rECEIVEDDATE;
	}
	public String getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}
	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}
	public void setREFERENCE_ID(String rEFERENCEID) {
		REFERENCE_ID = rEFERENCEID;
	}
	public String getREASON() {
		return REASON;
	}
	public void setREASON(String rEASON) {
		REASON = rEASON;
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
	public String getATTACHMENT_NAME() {
		return ATTACHMENT_NAME;
	}
	public void setATTACHMENT_NAME(String aTTACHMENTNAME) {
		ATTACHMENT_NAME = aTTACHMENTNAME;
	}
	public String getLOCK() {
		return LOCK;
	}
	public void setLOCK(String lOCK) {
		LOCK = lOCK;
	}
	public String getLOCKED_BY() {
		return LOCKED_BY;
	}
	public void setLOCKED_BY(String lOCKEDBY) {
		LOCKED_BY = lOCKEDBY;
	}
	public String getLOCKED_DATE() {
		return LOCKED_DATE;
	}
	public void setLOCKED_DATE(String lOCKEDDATE) {
		LOCKED_DATE = lOCKEDDATE;
	}
}
