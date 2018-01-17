/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class TECHCR_Attachment  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7323489659883072466L;
    private String ISSUE_ID;
    private String ATTACHMENT_NAME;
    private String ATTACHMENT_PATH;
    private String APPROVER_ATTACHMENT_NAME;
    private String APPROVER_ATTACHMENT_PATH;
    public String getAPPROVER_ATTACHMENT_NAME() {
		return APPROVER_ATTACHMENT_NAME;
	}
	public void setAPPROVER_ATTACHMENT_NAME(String aPPROVERATTACHMENTNAME) {
		APPROVER_ATTACHMENT_NAME = aPPROVERATTACHMENTNAME;
	}
	public String getAPPROVER_ATTACHMENT_PATH() {
		return APPROVER_ATTACHMENT_PATH;
	}
	public void setAPPROVER_ATTACHMENT_PATH(String aPPROVERATTACHMENTPATH) {
		APPROVER_ATTACHMENT_PATH = aPPROVERATTACHMENTPATH;
	}
	private String CREATED_BY;
    private String CREATED_NAME;
    private String CREATED_DATE;
    private String MODIFIED_BY;
    private String MODIFIED_NAME;
    private String MODIFIED_DATE;
    private CommonsMultipartFile ATTACHMENT;
    private String WORKFLOW_STATE;
    public String getWORKFLOW_STATE() {
        return WORKFLOW_STATE;
    }
    public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
        WORKFLOW_STATE = wORKFLOWSTATE;
    }
    public CommonsMultipartFile getATTACHMENT() {
        return ATTACHMENT;
    }
    public void setATTACHMENT(CommonsMultipartFile aTTACHMENT) {
        ATTACHMENT = aTTACHMENT;
    }
    public String getISSUE_ID() {
        return ISSUE_ID;
    }
    public void setISSUE_ID(String iSSUEID) {
        ISSUE_ID = iSSUEID;
    }
    public String getATTACHMENT_NAME() {
        return ATTACHMENT_NAME;
    }
    public void setATTACHMENT_NAME(String aTTACHMENTNAME) {
        ATTACHMENT_NAME = aTTACHMENTNAME;
    }
    public String getATTACHMENT_PATH() {
        return ATTACHMENT_PATH;
    }
    public void setATTACHMENT_PATH(String aTTACHMENTPATH) {
        ATTACHMENT_PATH = aTTACHMENTPATH;
    }
    public String getCREATED_BY() {
        return CREATED_BY;
    }
    public void setCREATED_BY(String cREATEDBY) {
        CREATED_BY = cREATEDBY;
    }
    public String getCREATED_NAME() {
        return CREATED_NAME;
    }
    public void setCREATED_NAME(String cREATEDNAME) {
        CREATED_NAME = cREATEDNAME;
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
    public String getMODIFIED_NAME() {
        return MODIFIED_NAME;
    }
    public void setMODIFIED_NAME(String mODIFIEDNAME) {
        MODIFIED_NAME = mODIFIEDNAME;
    }
    public String getMODIFIED_DATE() {
        return MODIFIED_DATE;
    }
    public void setMODIFIED_DATE(String mODIFIEDDATE) {
        MODIFIED_DATE = mODIFIEDDATE;
    }
    
}
