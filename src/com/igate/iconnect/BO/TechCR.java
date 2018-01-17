/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class TechCR implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4290467890555878032L;
    private String LOCATION_ID;
    private String ISSUE_ID;
    private String ACTION_ID;
    private String ACTION_DESC;
    private List<String> editOnlyFields;
    private List<String> viewOnlyFields;
    private List<String> noneOnlyFields;
    private boolean editPermissionRenderingFlag = false;
    private String SCHEDULED_START_DATE;
    private String SCHEDULED_END_DATE;
    private String DESCRIPTION;
    private String ROLLBACK_COMMENTS;
    private String IS_CLIENT_INITIATED;
    private String RISK_INVOLVED;
    private String RISK_DETAILS;
    private String MITIGATION;
    private String CONTIGENCY;
    private String EFFORT;
    private String ENGINEER_COMMENTS;
    private String SOLUTION;
    private String PREVENTIVE_ACTION;
    private String COMMENTS;
    private CommonsMultipartFile EXCEPTIONAL_APPROVAL_PATH;
    private String WORKFLOW_STATE;
    private String WORKFLOW_STATE_NAME;
    private String mailID;
    private String NOCALERTS_RECIVED_DATE;
    private String LOCATION_NAME;
    private String IP_ADDRESS;
    private String VENDOR_INVOLVED;
    private String TICKET_TYPE;
    private String GROUP_ID;
    private String GROUP_NAME;
    private String SUBJECT;
    private String APPROVER_ID;
    private CommonsMultipartFile ATTACHMENT;
    private String PATH;
    private String ASSIGNED_TO;
    private String ASSIGNED_EMPIDANDNAME;
    private String ORIGINAL_FILE_NAME_FOR_ATTACHMENT;
    private String APPROVER_EMPIDANDNAME;
    private String SERVER_DEVICE;
    private String SEVERITY_ID;
    private String SEVERITY_NAME;
    private String CREATED_BY;
    private String CREATION_DATE;
    private String PARENT_CATEGORY_ID;
    private String PARENT_CATEGORY_NAME;
    private String CHILD_CATEGORY_ID;
    private String CHILD_CATEGORY_NAME;
    private String CURRENTSTATE;
    private String RCA_REQUIRED;
    private String IS_EXCEPTIONAL_APPROVAL;
    private String RISK_ID;
    private String RCA_ID;
    private String VENDOR_NAME;
    private String ENGINEER_NAME;
    private String VENDOR_LOCATION_ID;
    private boolean SCHEDULED_END_DT_FIELD_PERMISSION = false;
    private String ROLLBACK_CLOSED_COMMENTS;
    private CommonsMultipartFile APPROVER_ATTACHMENT_NAME;
    private String APPROVER_ATTACHMENT_PATH;
    private String jsonUpdate;
    private String SOURCE;
    private String ATTACHMENT_PATH_AV;
    private String ATTACHMENT_NAME_AV;
    
    
    public String getATTACHMENT_PATH_AV() {
		return ATTACHMENT_PATH_AV;
	}

	public void setATTACHMENT_PATH_AV(String aTTACHMENTPATHAV) {
		ATTACHMENT_PATH_AV = aTTACHMENTPATHAV;
	}

	public String getATTACHMENT_NAME_AV() {
		return ATTACHMENT_NAME_AV;
	}

	public void setATTACHMENT_NAME_AV(String aTTACHMENTNAMEAV) {
		ATTACHMENT_NAME_AV = aTTACHMENTNAMEAV;
	}

	public String getSOURCE() {
		return SOURCE;
	}

	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}

	public CommonsMultipartFile getAPPROVER_ATTACHMENT_NAME() {
		return APPROVER_ATTACHMENT_NAME;
	}

	public void setAPPROVER_ATTACHMENT_NAME(
			CommonsMultipartFile aPPROVERATTACHMENTNAME) {
		APPROVER_ATTACHMENT_NAME = aPPROVERATTACHMENTNAME;
	}

	public String getAPPROVER_ATTACHMENT_PATH() {
		return APPROVER_ATTACHMENT_PATH;
	}

	public void setAPPROVER_ATTACHMENT_PATH(String aPPROVERATTACHMENTPATH) {
		APPROVER_ATTACHMENT_PATH = aPPROVERATTACHMENTPATH;
	}

	public String getJsonUpdate() {
		return jsonUpdate;
	}

	public void setJsonUpdate(String jsonUpdate) {
		this.jsonUpdate = jsonUpdate;
	}

	public String getROLLBACK_CLOSED_COMMENTS() {
        return ROLLBACK_CLOSED_COMMENTS;
    }

    public void setROLLBACK_CLOSED_COMMENTS(String rOLLBACKCLOSEDCOMMENTS) {
        ROLLBACK_CLOSED_COMMENTS = rOLLBACKCLOSEDCOMMENTS;
    }

    public boolean isSCHEDULED_END_DT_FIELD_PERMISSION() {
        return SCHEDULED_END_DT_FIELD_PERMISSION;
    }

    public void setSCHEDULED_END_DT_FIELD_PERMISSION(
            boolean sCHEDULEDENDDTFIELDPERMISSION) {
        SCHEDULED_END_DT_FIELD_PERMISSION = sCHEDULEDENDDTFIELDPERMISSION;
    }

    public String getVENDOR_NAME() {
        return VENDOR_NAME;
    }

    public void setVENDOR_NAME(String vENDORNAME) {
        VENDOR_NAME = vENDORNAME;
    }

    public String getENGINEER_NAME() {
        return ENGINEER_NAME;
    }

    public void setENGINEER_NAME(String eNGINEERNAME) {
        ENGINEER_NAME = eNGINEERNAME;
    }

    public String getVENDOR_LOCATION_ID() {
        return VENDOR_LOCATION_ID;
    }

    public void setVENDOR_LOCATION_ID(String vENDORLOCATIONID) {
        VENDOR_LOCATION_ID = vENDORLOCATIONID;
    }

    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public void setIP_ADDRESS(String iPADDRESS) {
        IP_ADDRESS = iPADDRESS;
    }

    public String getVENDOR_INVOLVED() {
        return VENDOR_INVOLVED;
    }

    public void setVENDOR_INVOLVED(String vENDORINVOLVED) {
        VENDOR_INVOLVED = vENDORINVOLVED;
    }

    public String getTICKET_TYPE() {
        return TICKET_TYPE;
    }

    public void setTICKET_TYPE(String tICKETTYPE) {
        TICKET_TYPE = tICKETTYPE;
    }

    public String getSERVER_DEVICE() {
        return SERVER_DEVICE;
    }

    public void setSERVER_DEVICE(String sERVERDEVICE) {
        SERVER_DEVICE = sERVERDEVICE;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String lOCATIONNAME) {
        LOCATION_NAME = lOCATIONNAME;
    }

    public String getNOCALERTS_RECIVED_DATE() {
        return NOCALERTS_RECIVED_DATE;
    }

    public void setNOCALERTS_RECIVED_DATE(String nOCALERTSRECIVEDDATE) {
        NOCALERTS_RECIVED_DATE = nOCALERTSRECIVEDDATE;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public List<String> getEditOnlyFields() {
        return editOnlyFields;
    }

    public void setEditOnlyFields(List<String> editOnlyFields) {
        this.editOnlyFields = editOnlyFields;
    }

    public List<String> getViewOnlyFields() {
        return viewOnlyFields;
    }

    public void setViewOnlyFields(List<String> viewOnlyFields) {
        this.viewOnlyFields = viewOnlyFields;
    }

    public List<String> getNoneOnlyFields() {
        return noneOnlyFields;
    }

    public void setNoneOnlyFields(List<String> noneOnlyFields) {
        this.noneOnlyFields = noneOnlyFields;
    }

    public String getACTION_DESC() {
        return ACTION_DESC;
    }

    public void setACTION_DESC(String aCTIONDESC) {
        ACTION_DESC = aCTIONDESC;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String gROUPNAME) {
        GROUP_NAME = gROUPNAME;
    }

    public String getAPPROVER_EMPIDANDNAME() {
        return APPROVER_EMPIDANDNAME;
    }

    public void setAPPROVER_EMPIDANDNAME(String aPPROVEREMPIDANDNAME) {
        APPROVER_EMPIDANDNAME = aPPROVEREMPIDANDNAME;
    }

    public String getORIGINAL_FILE_NAME_FOR_ATTACHMENT() {
        return ORIGINAL_FILE_NAME_FOR_ATTACHMENT;
    }

    public void setORIGINAL_FILE_NAME_FOR_ATTACHMENT(
            String oRIGINALFILENAMEFORATTACHMENT) {
        ORIGINAL_FILE_NAME_FOR_ATTACHMENT = oRIGINALFILENAMEFORATTACHMENT;
    }

    public String getASSIGNED_EMPIDANDNAME() {
        return ASSIGNED_EMPIDANDNAME;
    }

    public void setASSIGNED_EMPIDANDNAME(String aSSIGNEDEMPIDANDNAME) {
        ASSIGNED_EMPIDANDNAME = aSSIGNEDEMPIDANDNAME;
    }

    public String getSEVERITY_NAME() {
        return SEVERITY_NAME;
    }

    public void setSEVERITY_NAME(String sEVERITYNAME) {
        SEVERITY_NAME = sEVERITYNAME;
    }

    public boolean isEditPermissionRenderingFlag() {
        return editPermissionRenderingFlag;
    }

    public void setEditPermissionRenderingFlag(
            boolean editPermissionRenderingFlag) {
        this.editPermissionRenderingFlag = editPermissionRenderingFlag;
    }

    public String getWORKFLOW_STATE_NAME() {
        return WORKFLOW_STATE_NAME;
    }

    public void setWORKFLOW_STATE_NAME(String wORKFLOWSTATENAME) {
        WORKFLOW_STATE_NAME = wORKFLOWSTATENAME;
    }

    public String getCREATION_DATE() {
        return CREATION_DATE;
    }

    public void setCREATION_DATE(String cREATIONDATE) {
        CREATION_DATE = cREATIONDATE;
    }

    public String getPARENT_CATEGORY_NAME() {
        return PARENT_CATEGORY_NAME;
    }

    public void setPARENT_CATEGORY_NAME(String pARENTCATEGORYNAME) {
        PARENT_CATEGORY_NAME = pARENTCATEGORYNAME;
    }

    public String getCHILD_CATEGORY_NAME() {
        return CHILD_CATEGORY_NAME;
    }

    public void setCHILD_CATEGORY_NAME(String cHILDCATEGORYNAME) {
        CHILD_CATEGORY_NAME = cHILDCATEGORYNAME;
    }

    public CommonsMultipartFile getEXCEPTIONAL_APPROVAL_PATH() {
        return EXCEPTIONAL_APPROVAL_PATH;
    }

    public void setEXCEPTIONAL_APPROVAL_PATH(
            CommonsMultipartFile eXCEPTIONALAPPROVALPATH) {
        EXCEPTIONAL_APPROVAL_PATH = eXCEPTIONALAPPROVALPATH;
    }

    public String getRCA_ID() {
        return RCA_ID;
    }

    public void setRCA_ID(String rCAID) {
        RCA_ID = rCAID;
    }

    public String getRISK_ID() {
        return RISK_ID;
    }

    public void setRISK_ID(String rISKID) {
        RISK_ID = rISKID;
    }

    public String getIS_EXCEPTIONAL_APPROVAL() {
        return IS_EXCEPTIONAL_APPROVAL;
    }

    public void setIS_EXCEPTIONAL_APPROVAL(String iSEXCEPTIONALAPPROVAL) {
        IS_EXCEPTIONAL_APPROVAL = iSEXCEPTIONALAPPROVAL;
    }

    public String getRCA_REQUIRED() {
        return RCA_REQUIRED;
    }

    public void setRCA_REQUIRED(String rCAREQUIRED) {
        RCA_REQUIRED = rCAREQUIRED;
    }

    public String getCURRENTSTATE() {
        return CURRENTSTATE;
    }

    public void setCURRENTSTATE(String cURRENTSTATE) {
        CURRENTSTATE = cURRENTSTATE;
    }

    public String getLOCATION_ID() {
        return LOCATION_ID;
    }

    public void setLOCATION_ID(String lOCATIONID) {
        LOCATION_ID = lOCATIONID;
    }

    public String getISSUE_ID() {
        return ISSUE_ID;
    }

    public void setISSUE_ID(String iSSUEID) {
        ISSUE_ID = iSSUEID;
    }

    public String getACTION_ID() {
        return ACTION_ID;
    }

    public void setACTION_ID(String aCTIONID) {
        ACTION_ID = aCTIONID;
    }

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String gROUPID) {
        GROUP_ID = gROUPID;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String sUBJECT) {
        SUBJECT = sUBJECT;
    }

    public String getAPPROVER_ID() {
        return APPROVER_ID;
    }

    public void setAPPROVER_ID(String aPPROVERID) {
        APPROVER_ID = aPPROVERID;
    }

    public CommonsMultipartFile getATTACHMENT() {
        return ATTACHMENT;
    }

    public void setATTACHMENT(CommonsMultipartFile aTTACHMENT) {
        ATTACHMENT = aTTACHMENT;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String pATH) {
        PATH = pATH;
    }

    public String getASSIGNED_TO() {
        return ASSIGNED_TO;
    }

    public void setASSIGNED_TO(String aSSIGNEDTO) {
        ASSIGNED_TO = aSSIGNEDTO;
    }

    public String getSEVERITY_ID() {
        return SEVERITY_ID;
    }

    public void setSEVERITY_ID(String sEVERITYID) {
        SEVERITY_ID = sEVERITYID;
    }

    public String getSCHEDULED_START_DATE() {
        return SCHEDULED_START_DATE;
    }

    public void setSCHEDULED_START_DATE(String sCHEDULEDSTARTDATE) {
        SCHEDULED_START_DATE = sCHEDULEDSTARTDATE;
    }

    public String getSCHEDULED_END_DATE() {
        return SCHEDULED_END_DATE;
    }

    public void setSCHEDULED_END_DATE(String sCHEDULEDENDDATE) {
        SCHEDULED_END_DATE = sCHEDULEDENDDATE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        DESCRIPTION = dESCRIPTION;
    }

    public String getROLLBACK_COMMENTS() {
        return ROLLBACK_COMMENTS;
    }

    public void setROLLBACK_COMMENTS(String rOLLBACKCOMMENTS) {
        ROLLBACK_COMMENTS = rOLLBACKCOMMENTS;
    }

    public String getIS_CLIENT_INITIATED() {
        return IS_CLIENT_INITIATED;
    }

    public void setIS_CLIENT_INITIATED(String iSCLIENTINITIATED) {
        IS_CLIENT_INITIATED = iSCLIENTINITIATED;
    }

    public String getRISK_INVOLVED() {
        return RISK_INVOLVED;
    }

    public void setRISK_INVOLVED(String rISKINVOLVED) {
        RISK_INVOLVED = rISKINVOLVED;
    }

    public String getRISK_DETAILS() {
        return RISK_DETAILS;
    }

    public void setRISK_DETAILS(String rISKDETAILS) {
        RISK_DETAILS = rISKDETAILS;
    }

    public String getMITIGATION() {
        return MITIGATION;
    }

    public void setMITIGATION(String mITIGATION) {
        MITIGATION = mITIGATION;
    }

    public String getCONTIGENCY() {
        return CONTIGENCY;
    }

    public void setCONTIGENCY(String cONTIGENCY) {
        CONTIGENCY = cONTIGENCY;
    }

    public String getEFFORT() {
        return EFFORT;
    }

    public void setEFFORT(String eFFORT) {
        EFFORT = eFFORT;
    }

    public String getENGINEER_COMMENTS() {
        return ENGINEER_COMMENTS;
    }

    public void setENGINEER_COMMENTS(String eNGINEERCOMMENTS) {
        ENGINEER_COMMENTS = eNGINEERCOMMENTS;
    }

    public String getSOLUTION() {
        return SOLUTION;
    }

    public void setSOLUTION(String sOLUTION) {
        SOLUTION = sOLUTION;
    }

    public String getPREVENTIVE_ACTION() {
        return PREVENTIVE_ACTION;
    }

    public void setPREVENTIVE_ACTION(String pREVENTIVEACTION) {
        PREVENTIVE_ACTION = pREVENTIVEACTION;
    }

    public String getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(String cOMMENTS) {
        COMMENTS = cOMMENTS;
    }

    public String getWORKFLOW_STATE() {
        return WORKFLOW_STATE;
    }

    public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
        WORKFLOW_STATE = wORKFLOWSTATE;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String cREATEDBY) {
        CREATED_BY = cREATEDBY;
    }

    public String getPARENT_CATEGORY_ID() {
        return PARENT_CATEGORY_ID;
    }

    public void setPARENT_CATEGORY_ID(String pARENTCATEGORYID) {
        PARENT_CATEGORY_ID = pARENTCATEGORYID;
    }

    public String getCHILD_CATEGORY_ID() {
        return CHILD_CATEGORY_ID;
    }

    public void setCHILD_CATEGORY_ID(String cHILDCATEGORYID) {
        CHILD_CATEGORY_ID = cHILDCATEGORYID;
    }
}
