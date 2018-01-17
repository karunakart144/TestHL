/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

public class HelpDesk implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = -8432506242842505335L;
    private String TICKET_ID;
	private String CATEGORY_ID;
	private String CATEGORY;
	private String FUNCTION_ID;
	private String FUNCTION;
	private String SUB_CATEGORY_ID;
	private String SUBCATEGORY;
	private String SUBJECT;
	private String SUBJECT_LIST;
	private String DESCRIPTION;
	private String COPY_TO;
	private String SOURCE;
	private String SEVERITY_ID;
	private String PRIORITY_ID;
	private String PRIORITY;
	private String ASSIGNED_GROUP_ID;
	private String ASSIGNED_GROUP;
	private String ASSIGNED_TO;
	private String REQUESTED_BY;
	private String REQUESTOR_NAME;
	private String CREATOR_NAME;
	private String CREATED_DATE;
	private String CREATED_DATE_STORAGE;
	private String ECT;
	private String ECT_STORAGE;
	private String SLA_STATUS;
	private String LOCATION_ID;
	private String LOCATION_COUNTRY;
	private String LOCATION_CITY;
	private String LOCATION_AREA;
	private String LOCATION_SHORTNAME;
	private String ON_BEHALF_OF;
	private String CREATED_BY;
	private String CONTACT_NO;
	private String ALTERNATE_CONTACT_NO;
	private String ADDITIONAL_INFO;
	private String RESOLUTION_COMMENTS;
	private String PRIVATE_NOTES;
	private String WORKFLOW_STATE;
	private String MESSAGE;
	private String USERS_IMPACTED;
	private String PROJECT_ID;
	private String PROJECT_NAME;
	private String OUT_OF_SLA_REASON;
	private String OUT_OF_SLA_COMMENTS;
	private String CLOSED_DATE;
	private String SUSPENDED_TILL;
	private String SUSPENDED_COMMENTS;
	private String ONHOLD_COMMENTS;
	private String REMINDER_DATE;
	private String FEEDBACK;
	private String REOPEN_COMMENTS;
	private String INVOICE_NO;
	private String VENDOR_NAME;
	private String VERSION_NO;
	private String CHECK_FCS;
	private String ORGANIZATION;
	private String CUBICLE_CODE;
	private String FILTER_GROUP_LOCATION;
	private String LOC_DET_ID;
	private String MANAGER_ID;
	private String ATTACHMENT;
	private String CHANGED_DATE_STORAGE;//Modified by Sali
	private String IS_CHANGE_REQUEST;//mODEIFIED BY SALI
	private String LOCK;
	// orchestration
	private String AUTOMATION;
	private String LOCKED_BY;
	private String PREMIUM;
	private String DEPT_NAME;
	private String OS_DETAILS;
	private String BROWSER_DETAILS;
	private String REFERENCE_ID;
	private String IS_MASTER_LINK;
	private String WORKFLOW_NAME;
	private String EXCEPTION_APP_ST_DATE;
    private String EXCEPTION_APP_END_DATE;
    private String DU_NAME;
    private String FEEDBACK_COMMENTS;
    private boolean SEARCH_REFERENCE;
    private String IS_EMERGENCY_TICKET;
    private String EMERGENCY_TYPE;
    private String LEVEL2_GROUP;
    private String LEVEL2_ASSIGNED_ENGINEER;
    private String SUB_STATUS;
    private String DEPLOYMENT_INSTRUCTION;
    private String REASON_SEND_BACK;
    private String ACTIVITY_TYPE;
    private String COLOUR_CODE;
    private String TIME_REMAINING;
    private String IS_APPROVAL_REQUIRED;
    private String GRADE;
    private String RESOLUTION_STATUS;
    private String LEVEL_2_PROJECT_ID;
    private String LEVEL_3_PROJECT_ID;
    private String LEVEL_2_PROJECT_NAME;
    private String LEVEL_3_PROJECT_NAME;
    //L2 1184 Start
    private String IS_AUTOMATION_REQUIRED;
    private boolean IS_ORCH_REQUIRED;
    private String CORE_ID;
    private String ALLOW_CLOSEABLE_FLAG;

	public String getCORE_ID() {
		return CORE_ID;
	}
	public void setCORE_ID(String cORE_ID) {
		CORE_ID = cORE_ID;
	}
	public String getALLOW_CLOSEABLE_FLAG() {
		return ALLOW_CLOSEABLE_FLAG;
	}
	public void setALLOW_CLOSEABLE_FLAG(String aLLOW_CLOSEABLE_FLAG) {
		ALLOW_CLOSEABLE_FLAG = aLLOW_CLOSEABLE_FLAG;
	}
public String getIS_AUTOMATION_REQUIRED() {
		return IS_AUTOMATION_REQUIRED;
	}
	public void setIS_AUTOMATION_REQUIRED(String iS_AUTOMATION_REQUIRED) {
		IS_AUTOMATION_REQUIRED = iS_AUTOMATION_REQUIRED;
	}
	public boolean isIS_ORCH_REQUIRED() {
		return IS_ORCH_REQUIRED;
	}
	public void setIS_ORCH_REQUIRED(boolean iS_ORCH_REQUIRED) {
		IS_ORCH_REQUIRED = iS_ORCH_REQUIRED;
	}
	//L2 1184 End
public String getLEVEL_2_PROJECT_NAME() {
		return LEVEL_2_PROJECT_NAME;
	}
	public void setLEVEL_2_PROJECT_NAME(String lEVEL_2_PROJECT_NAME) {
		LEVEL_2_PROJECT_NAME = lEVEL_2_PROJECT_NAME;
	}
	public String getLEVEL_3_PROJECT_NAME() {
		return LEVEL_3_PROJECT_NAME;
	}
	public void setLEVEL_3_PROJECT_NAME(String lEVEL_3_PROJECT_NAME) {
		LEVEL_3_PROJECT_NAME = lEVEL_3_PROJECT_NAME;
	}
private String TOTAL_SLA_TIME;
    
    public String getTOTAL_SLA_TIME() {
		return TOTAL_SLA_TIME;
	}
	public void setTOTAL_SLA_TIME(String tOTAL_SLA_TIME) {
		TOTAL_SLA_TIME = tOTAL_SLA_TIME;
	}

	public String getLEVEL_2_PROJECT_ID() {
		return LEVEL_2_PROJECT_ID;
	}
	public void setLEVEL_2_PROJECT_ID(String lEVEL_2_PROJECT_ID) {
		LEVEL_2_PROJECT_ID = lEVEL_2_PROJECT_ID;
	}
	public String getLEVEL_3_PROJECT_ID() {
		return LEVEL_3_PROJECT_ID;
	}
	public void setLEVEL_3_PROJECT_ID(String lEVEL_3_PROJECT_ID) {
		LEVEL_3_PROJECT_ID = lEVEL_3_PROJECT_ID;
	}
	public String getRESOLUTION_STATUS() {
		return RESOLUTION_STATUS;
	}
	public void setRESOLUTION_STATUS(String rESOLUTIONSTATUS) {
		RESOLUTION_STATUS = rESOLUTIONSTATUS;
	}
	public String getIS_APPROVAL_REQUIRED() {
		return IS_APPROVAL_REQUIRED;
	}
	public void setIS_APPROVAL_REQUIRED(String iSAPPROVALREQUIRED) {
		IS_APPROVAL_REQUIRED = iSAPPROVALREQUIRED;
	}
	public String getGRADE() {
		return GRADE;
	}
	public void setGRADE(String gRADE) {
		GRADE = gRADE;
	}
	public String getTIME_REMAINING() {
		return TIME_REMAINING;
	}
	public void setTIME_REMAINING(String tIMEREMAINING) {
		TIME_REMAINING = tIMEREMAINING;
	}
	public String getCOLOUR_CODE() {
		return COLOUR_CODE;
	}
	public void setCOLOUR_CODE(String cOLOURCODE) {
		COLOUR_CODE = cOLOURCODE;
	}
	public String getACTIVITY_TYPE() {
		return ACTIVITY_TYPE;
	}
	public void setACTIVITY_TYPE(String aCTIVITYTYPE) {
		ACTIVITY_TYPE = aCTIVITYTYPE;
	}
	public String getREASON_SEND_BACK() {
		return REASON_SEND_BACK;
	}
	public void setREASON_SEND_BACK(String rEASONSENDBACK) {
		REASON_SEND_BACK = rEASONSENDBACK;
	}
	public String getDEPLOYMENT_INSTRUCTION() {
		return DEPLOYMENT_INSTRUCTION;
	}
	public void setDEPLOYMENT_INSTRUCTION(String dEPLOYMENTINSTRUCTION) {
		DEPLOYMENT_INSTRUCTION = dEPLOYMENTINSTRUCTION;
	}
	public String getIS_EMERGENCY_TICKET() {
		return IS_EMERGENCY_TICKET;
	}
	public void setIS_EMERGENCY_TICKET(String iSEMERGENCYTICKET) {
		IS_EMERGENCY_TICKET = iSEMERGENCYTICKET;
	}
	public String getEMERGENCY_TYPE() {
		return EMERGENCY_TYPE;
	}
	public void setEMERGENCY_TYPE(String eMERGENCYTYPE) {
		EMERGENCY_TYPE = eMERGENCYTYPE;
	}
	public String getLEVEL2_GROUP() {
		return LEVEL2_GROUP;
	}
	public void setLEVEL2_GROUP(String lEVEL2GROUP) {
		LEVEL2_GROUP = lEVEL2GROUP;
	}
	public String getLEVEL2_ASSIGNED_ENGINEER() {
		return LEVEL2_ASSIGNED_ENGINEER;
	}
	public void setLEVEL2_ASSIGNED_ENGINEER(String lEVEL2ASSIGNEDENGINEER) {
		LEVEL2_ASSIGNED_ENGINEER = lEVEL2ASSIGNEDENGINEER;
	}
	public String getSUB_STATUS() {
		return SUB_STATUS;
	}
	public void setSUB_STATUS(String sUBSTATUS) {
		SUB_STATUS = sUBSTATUS;
	}
	public boolean isSEARCH_REFERENCE() {
		return SEARCH_REFERENCE;
	}
	public void setSEARCH_REFERENCE(boolean sEARCHREFERENCE) {
		SEARCH_REFERENCE = sEARCHREFERENCE;
	}
	public String getFEEDBACK_COMMENTS() {
		return FEEDBACK_COMMENTS;
	}
	public void setFEEDBACK_COMMENTS(String fEEDBACKCOMMENTS) {
		FEEDBACK_COMMENTS = fEEDBACKCOMMENTS;
	}
	public String getEXCEPTION_APP_ST_DATE() {
        return EXCEPTION_APP_ST_DATE;
    }
    public void setEXCEPTION_APP_ST_DATE(String eXCEPTIONAPPSTDATE) {
        EXCEPTION_APP_ST_DATE = eXCEPTIONAPPSTDATE;
    }
    public String getEXCEPTION_APP_END_DATE() {
        return EXCEPTION_APP_END_DATE;
    }
    public void setEXCEPTION_APP_END_DATE(String eXCEPTIONAPPENDDATE) {
        EXCEPTION_APP_END_DATE = eXCEPTIONAPPENDDATE;
    }
	public String getWORKFLOW_NAME() {
		return WORKFLOW_NAME;
	}
	public void setWORKFLOW_NAME(String wORKFLOWNAME) {
		WORKFLOW_NAME = wORKFLOWNAME;
	}
	public String getIS_MASTER_LINK() {
		return IS_MASTER_LINK;
	}
	public void setIS_MASTER_LINK(String iSMASTERLINK) {
		IS_MASTER_LINK = iSMASTERLINK;
	}
	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}
	public void setREFERENCE_ID(String rEFERENCEID) {
		REFERENCE_ID = rEFERENCEID;
	}
	public String getOS_DETAILS() {
		return OS_DETAILS;
	}
	public void setOS_DETAILS(String oSDETAILS) {
		OS_DETAILS = oSDETAILS;
	}
	public String getBROWSER_DETAILS() {
		return BROWSER_DETAILS;
	}
	public void setBROWSER_DETAILS(String bROWSERDETAILS) {
		BROWSER_DETAILS = bROWSERDETAILS;
	}
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	public void setDEPT_NAME(String dEPTNAME) {
		DEPT_NAME = dEPTNAME;
	}
	public String getIS_CHANGE_REQUEST() {
		return IS_CHANGE_REQUEST;
	}
	public void setIS_CHANGE_REQUEST(String iSCHANGEREQUEST) {
		IS_CHANGE_REQUEST = iSCHANGEREQUEST;
	}
	public String getCHANGED_DATE_STORAGE() {
		return CHANGED_DATE_STORAGE;
	}
	public void setCHANGED_DATE_STORAGE(String cHANGEDDATESTORAGE) {
		CHANGED_DATE_STORAGE = cHANGEDDATESTORAGE;
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
	private String BUILDING;
	private String FLOOR;
	
	public String getMANAGER_ID() {
		return MANAGER_ID;
	}
	public void setMANAGER_ID(String mANAGERID) {
		MANAGER_ID = mANAGERID;
	}
	public String getLOC_DET_ID() {
		return LOC_DET_ID;
	}
	public void setLOC_DET_ID(String lOCDETID) {
		LOC_DET_ID = lOCDETID;
	}
	public String getFILTER_GROUP_LOCATION() {
		return FILTER_GROUP_LOCATION;
	}
	public void setFILTER_GROUP_LOCATION(String fILTERGROUPLOCATION) {
		FILTER_GROUP_LOCATION = fILTERGROUPLOCATION;
	}
	public String getCUBICLE_CODE() {
		return CUBICLE_CODE;
	}
	public void setCUBICLE_CODE(String cUBICLECODE) {
		CUBICLE_CODE = cUBICLECODE;
	}
	public String getORGANIZATION() {
        return ORGANIZATION;
    }
    public void setORGANIZATION(String oRGANIZATION) {
        ORGANIZATION = oRGANIZATION;
    }
    public String getPRIORITY() {
        return PRIORITY;
    }
    public void setPRIORITY(String pRIORITY) {
        PRIORITY = pRIORITY;
    }
    public String getCREATED_DATE_STORAGE() {
		return CREATED_DATE_STORAGE;
	}
	public void setCREATED_DATE_STORAGE(String cREATEDDATESTORAGE) {
		CREATED_DATE_STORAGE = cREATEDDATESTORAGE;
	}
	public String getECT_STORAGE() {
		return ECT_STORAGE;
	}
	public void setECT_STORAGE(String eCTSTORAGE) {
		ECT_STORAGE = eCTSTORAGE;
	}
	public String getCHECK_FCS() {
		return CHECK_FCS;
	}
	public void setCHECK_FCS(String cHECKFCS) {
		CHECK_FCS = cHECKFCS;
	}
	public String getOUT_OF_SLA_REASON() {
		return OUT_OF_SLA_REASON;
	}
	public void setOUT_OF_SLA_REASON(String oUTOFSLAREASON) {
		OUT_OF_SLA_REASON = oUTOFSLAREASON;
	}
	public String getOUT_OF_SLA_COMMENTS() {
		return OUT_OF_SLA_COMMENTS;
	}
	public void setOUT_OF_SLA_COMMENTS(String oUTOFSLACOMMENTS) {
		OUT_OF_SLA_COMMENTS = oUTOFSLACOMMENTS;
	}
	public String getCLOSED_DATE() {
		return CLOSED_DATE;
	}
	public void setCLOSED_DATE(String cLOSEDDATE) {
		CLOSED_DATE = cLOSEDDATE;
	}
	public String getSUSPENDED_TILL() {
		return SUSPENDED_TILL;
	}
	public void setSUSPENDED_TILL(String sUSPENDEDTILL) {
		SUSPENDED_TILL = sUSPENDEDTILL;
	}
	public String getSUSPENDED_COMMENTS() {
		return SUSPENDED_COMMENTS;
	}
	public void setSUSPENDED_COMMENTS(String sUSPENDEDCOMMENTS) {
		SUSPENDED_COMMENTS = sUSPENDEDCOMMENTS;
	}
	public String getONHOLD_COMMENTS() {
		return ONHOLD_COMMENTS;
	}
	public void setONHOLD_COMMENTS(String oNHOLDCOMMENTS) {
		ONHOLD_COMMENTS = oNHOLDCOMMENTS;
	}
	public String getREMINDER_DATE() {
		return REMINDER_DATE;
	}
	public void setREMINDER_DATE(String rEMINDERDATE) {
		REMINDER_DATE = rEMINDERDATE;
	}
	public String getFEEDBACK() {
		return FEEDBACK;
	}
	public void setFEEDBACK(String fEEDBACK) {
		FEEDBACK = fEEDBACK;
	}
	public String getREOPEN_COMMENTS() {
		return REOPEN_COMMENTS;
	}
	public void setREOPEN_COMMENTS(String rEOPENCOMMENTS) {
		REOPEN_COMMENTS = rEOPENCOMMENTS;
	}
	public String getINVOICE_NO() {
		return INVOICE_NO;
	}
	public void setINVOICE_NO(String iNVOICENO) {
		INVOICE_NO = iNVOICENO;
	}
	public String getVENDOR_NAME() {
		return VENDOR_NAME;
	}
	public void setVENDOR_NAME(String vENDORNAME) {
		VENDOR_NAME = vENDORNAME;
	}
	public String getVERSION_NO() {
		return VERSION_NO;
	}
	public void setVERSION_NO(String vERSIONNO) {
		VERSION_NO = vERSIONNO;
	}
	public String getUSERS_IMPACTED() {
		return USERS_IMPACTED;
	}
	public void setUSERS_IMPACTED(String uSERSIMPACTED) {
		USERS_IMPACTED = uSERSIMPACTED;
	}
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public void setTICKET_ID(String tICKETID) {
		TICKET_ID = tICKETID;
	}
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORYID) {
		CATEGORY_ID = cATEGORYID;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getFUNCTION() {
		return FUNCTION;
	}
	public void setFUNCTION(String fUNCTION) {
		FUNCTION = fUNCTION;
	}
	public String getSUBCATEGORY() {
		return SUBCATEGORY;
	}
	public void setSUBCATEGORY(String sUBCATEGORY) {
		SUBCATEGORY = sUBCATEGORY;
	}
	public String getFUNCTION_ID() {
		return FUNCTION_ID;
	}
	public void setFUNCTION_ID(String fUNCTIONID) {
		FUNCTION_ID = fUNCTIONID;
	}
	public String getSUB_CATEGORY_ID() {
		return SUB_CATEGORY_ID;
	}
	public void setSUB_CATEGORY_ID(String sUBCATEGORYID) {
		SUB_CATEGORY_ID = sUBCATEGORYID;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getSUBJECT_LIST() {
		return SUBJECT_LIST;
	}
	public void setSUBJECT_LIST(String sUBJECTLIST) {
		SUBJECT_LIST = sUBJECTLIST;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getCOPY_TO() {
		return COPY_TO;
	}
	public void setCOPY_TO(String cOPYTO) {
		COPY_TO = cOPYTO;
	}
	public String getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}
	public String getSEVERITY_ID() {
		return SEVERITY_ID;
	}
	public void setSEVERITY_ID(String sEVERITYID) {
		SEVERITY_ID = sEVERITYID;
	}
	public String getPRIORITY_ID() {
		return PRIORITY_ID;
	}
	public void setPRIORITY_ID(String pRIORITYID) {
		PRIORITY_ID = pRIORITYID;
	}
	public String getASSIGNED_GROUP_ID() {
		return ASSIGNED_GROUP_ID;
	}
	public void setASSIGNED_GROUP_ID(String aSSIGNEDGROUPID) {
		ASSIGNED_GROUP_ID = aSSIGNEDGROUPID;
	}
	public String getASSIGNED_GROUP() {
		return ASSIGNED_GROUP;
	}
	public void setASSIGNED_GROUP(String aSSIGNEDGROUP) {
		ASSIGNED_GROUP = aSSIGNEDGROUP;
	}
	public String getASSIGNED_TO() {
		return ASSIGNED_TO;
	}
	public void setASSIGNED_TO(String aSSIGNEDTO) {
		ASSIGNED_TO = aSSIGNEDTO;
	}
	public String getREQUESTED_BY() {
		return REQUESTED_BY;
	}
	public void setREQUESTED_BY(String rEQUESTEDBY) {
		REQUESTED_BY = rEQUESTEDBY;
	}
	public String getREQUESTOR_NAME() {
		return REQUESTOR_NAME;
	}
	public void setREQUESTOR_NAME(String rEQUESTORNAME) {
		REQUESTOR_NAME = rEQUESTORNAME;
	}
	public String getCREATOR_NAME() {
		return CREATOR_NAME;
	}
	public void setCREATOR_NAME(String cREATORNAME) {
		CREATOR_NAME = cREATORNAME;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATEDDATE) {
		CREATED_DATE = cREATEDDATE;
	}
	public String getECT() {
		return ECT;
	}
	public void setECT(String eCT) {
		ECT = eCT;
	}
	public String getSLA_STATUS() {
		return SLA_STATUS;
	}
	public void setSLA_STATUS(String sLASTATUS) {
		SLA_STATUS = sLASTATUS;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}	
	public String getLOCATION_COUNTRY() {
		return LOCATION_COUNTRY;
	}
	public void setLOCATION_COUNTRY(String lOCATIONCOUNTRY) {
		LOCATION_COUNTRY = lOCATIONCOUNTRY;
	}
	public String getLOCATION_CITY() {
		return LOCATION_CITY;
	}
	public void setLOCATION_CITY(String lOCATIONCITY) {
		LOCATION_CITY = lOCATIONCITY;
	}
	public String getLOCATION_AREA() {
		return LOCATION_AREA;
	}
	public void setLOCATION_AREA(String lOCATIONAREA) {
		LOCATION_AREA = lOCATIONAREA;
	}
	public String getLOCATION_SHORTNAME() {
		return LOCATION_SHORTNAME;
	}
	public void setLOCATION_SHORTNAME(String lOCATIONSHORTNAME) {
		LOCATION_SHORTNAME = lOCATIONSHORTNAME;
	}

	public String getON_BEHALF_OF() {
		return ON_BEHALF_OF;
	}
	public void setON_BEHALF_OF(String oNBEHALFOF) {
		ON_BEHALF_OF = oNBEHALFOF;
	}
	public String getCREATED_BY() {
		return CREATOR_NAME+"("+CREATED_BY+")";
	}
	public void setCREATED_BY(String cREATEDBY) {
		CREATED_BY = cREATEDBY;
	}
	public String getCONTACT_NO() {
		return CONTACT_NO;
	}
	public void setCONTACT_NO(String cONTACTNO) {
		CONTACT_NO = cONTACTNO;
	}
	public String getALTERNATE_CONTACT_NO() {
		return ALTERNATE_CONTACT_NO;
	}
	public void setALTERNATE_CONTACT_NO(String aLTERNATECONTACTNO) {
		ALTERNATE_CONTACT_NO = aLTERNATECONTACTNO;
	}
	public String getADDITIONAL_INFO() {
		return ADDITIONAL_INFO;
	}
	public void setADDITIONAL_INFO(String aDDITIONALINFO) {
		ADDITIONAL_INFO = aDDITIONALINFO;
	}
	public String getRESOLUTION_COMMENTS() {
		return RESOLUTION_COMMENTS;
	}
	public void setRESOLUTION_COMMENTS(String rESOLUTIONCOMMENTS) {
		RESOLUTION_COMMENTS = rESOLUTIONCOMMENTS;
	}
	public String getPRIVATE_NOTES() {
		return PRIVATE_NOTES;
	}
	public void setPRIVATE_NOTES(String pRIVATENOTES) {
		PRIVATE_NOTES = pRIVATENOTES;
	}
	public String getWORKFLOW_STATE() {
		return WORKFLOW_STATE;
	}
	public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
		WORKFLOW_STATE = wORKFLOWSTATE;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String toString() {

		return new ToStringCreator(this).append("TICKET_ID", TICKET_ID).append(
				"ASSIGNED_GROUP", ASSIGNED_GROUP).toString();
	}
	public String getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setPROJECT_ID(String pROJECTID) {
		PROJECT_ID = pROJECTID;
	}
	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}
	public void setPROJECT_NAME(String pROJECTNAME) { 
		PROJECT_NAME = pROJECTNAME;
	}
	public String getATTACHMENT() {
		return ATTACHMENT;
	}
	public void setATTACHMENT(String aTTACHMENT) {
		ATTACHMENT = aTTACHMENT;
	}

	public String getDEPT_ID() {
		return DEPT_ID;
	}

	public void setDEPT_ID(String dEPTID) {
		DEPT_ID = dEPTID;
	}

	public String getODC_ID() {
		return ODC_ID;
	}

	public void setODC_ID(String oDCID) {
		ODC_ID = oDCID;
	}

	public String getLOCK() {
		return LOCK;
	}
	public void setLOCK(String tICKETLOCK) {
		LOCK = tICKETLOCK;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String DEPT_ID;
	private String ODC_ID;

	public String getLOCKED_BY() {
		return LOCKED_BY;
	}
	public void setLOCKED_BY(String lOCKEDBY) {
		LOCKED_BY = lOCKEDBY;
	}
	public String getPREMIUM() {
		return PREMIUM;
	}
	public void setPREMIUM(String pREMIUM) {
		PREMIUM = pREMIUM;
	}
	public String getDU_NAME() {
		return DU_NAME;
	}
	public void setDU_NAME(String dUNAME) {
		DU_NAME = dUNAME;
	}
	public void setAUTOMATION(String aUTOMATION) {
		AUTOMATION = aUTOMATION;
	}
	public String getAUTOMATION() {
		return AUTOMATION;
	}
}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:706638
Changes Made on:Jun 20, 2011
End-------Version 1.0-------
            
-----------------------------------------------------------------------------*/
