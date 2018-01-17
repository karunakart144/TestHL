package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MASTER_Create implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9157164395744954375L;
	private String MASTER_TICKET_ID;
	private String phonenumber;
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	private String FUNCTION;
	private String FUNCTION_NAME;
	private String CATEGORY;
	private String CATEGORY_NAME;
	private String SUB_CATEGORY;
	private String SUB_CATEGORY_NAME;
	private String PRIORITY;
	private String SUBJECT;
	private String DESCRIPTION;
	private String ATTACHMENT;
	private String ASSIGNED_GROUP;
	private String ASSIGNED_GROUP_NAME;
	private String ASSIGNED_TO;
	private String ASSIGNED_TO_NAME;
	public String getASSIGNED_TO_NAME() {
		return ASSIGNED_TO_NAME;
	}
	public void setASSIGNED_TO_NAME(String aSSIGNEDTONAME) {
		ASSIGNED_TO_NAME = aSSIGNEDTONAME;
	}
	private String ECT;
	private String LOCATION_ID;
	private String LOCATION_NAME;
	private String CC_TO_MAIL_ID;
	private String CONTACT_NO;
	private String WORKFLOW_STATE;
	private String WORKFLOW_STATE_NAME;
	private String REQUESTOR_NAME;
	private String CREATOR_NAME;
	private String VERSION_NO;
	private String WORKFLOW_NAME;
	private String IFIRST_TICKET_ID;
	
	
	public String getIFIRST_TICKET_ID() {
		return IFIRST_TICKET_ID;
	}
	public void setIFIRST_TICKET_ID(String iFIRST_TICKET_ID) {
		IFIRST_TICKET_ID = iFIRST_TICKET_ID;
	}
	public String getWORKFLOW_NAME() {
		return WORKFLOW_NAME;
	}
	public void setWORKFLOW_NAME(String wORKFLOWNAME) {
		WORKFLOW_NAME = wORKFLOWNAME;
	}
	public String getVERSION_NO() {
		return VERSION_NO;
	}
	public void setVERSION_NO(String vERSIONNO) {
		VERSION_NO = vERSIONNO;
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
	public String getFUNCTION_NAME() {
		return FUNCTION_NAME;
	}
	public void setFUNCTION_NAME(String fUNCTIONNAME) {
		FUNCTION_NAME = fUNCTIONNAME;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORYNAME) {
		CATEGORY_NAME = cATEGORYNAME;
	}
	public String getSUB_CATEGORY_NAME() {
		return SUB_CATEGORY_NAME;
	}
	public void setSUB_CATEGORY_NAME(String sUBCATEGORYNAME) {
		SUB_CATEGORY_NAME = sUBCATEGORYNAME;
	}
	public String getASSIGNED_GROUP_NAME() {
		return ASSIGNED_GROUP_NAME;
	}
	public void setASSIGNED_GROUP_NAME(String aSSIGNEDGROUPNAME) {
		ASSIGNED_GROUP_NAME = aSSIGNEDGROUPNAME;
	}
	public String getLOCATION_NAME() {
		return LOCATION_NAME;
	}
	public void setLOCATION_NAME(String lOCATIONNAME) {
		LOCATION_NAME = lOCATIONNAME;
	}
	public String getWORKFLOW_STATE_NAME() {
		return WORKFLOW_STATE_NAME;
	}
	public void setWORKFLOW_STATE_NAME(String wORKFLOWSTATENAME) {
		WORKFLOW_STATE_NAME = wORKFLOWSTATENAME;
	}
	private String RESOLUTION_COMMENTS;
	private String CREATED_BY;
	private String CREATED_DATE;
	private String OS_DETAILS;
	private String BROWSER_DETAILS;
	private String ASSIGNED_GROUP_ID;
	private String REFERENCE_ID;
	private String CLOSED_DATE;
	public String getCLOSED_DATE() {
		return CLOSED_DATE;
	}
	public void setCLOSED_DATE(String cLOSEDDATE) {
		CLOSED_DATE = cLOSEDDATE;
	}
	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}
	public void setREFERENCE_ID(String rEFERENCEID) {
		REFERENCE_ID = rEFERENCEID;
	}
	private CommonsMultipartFile problemfile;
	
	
	public CommonsMultipartFile getProblemfile() {
		return problemfile;
	}
	public void setProblemfile(CommonsMultipartFile problemfile) {
		this.problemfile = problemfile;
	}
	public String getASSIGNED_GROUP_ID() {
		return ASSIGNED_GROUP_ID;
	}
	public void setASSIGNED_GROUP_ID(String aSSIGNEDGROUPID) {
		ASSIGNED_GROUP_ID = aSSIGNEDGROUPID;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMASTER_TICKET_ID() {
		return MASTER_TICKET_ID;
	}
	public void setMASTER_TICKET_ID(String mASTERTICKETID) {
		MASTER_TICKET_ID = mASTERTICKETID;
	}
	public String getFUNCTION() {
		return FUNCTION;
	}
	public void setFUNCTION(String fUNCTION) {
		FUNCTION = fUNCTION;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getSUB_CATEGORY() {
		return SUB_CATEGORY;
	}
	public void setSUB_CATEGORY(String sUBCATEGORY) {
		SUB_CATEGORY = sUBCATEGORY;
	}
	public String getPRIORITY() {
		return PRIORITY;
	}
	public void setPRIORITY(String pRIORITY) {
		PRIORITY = pRIORITY;
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
	public String getATTACHMENT() {
		return ATTACHMENT;
	}
	public void setATTACHMENT(String aTTACHMENT) {
		ATTACHMENT = aTTACHMENT;
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
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public String getECT() {
		return ECT;
	}
	public void setECT(String eCT) {
		ECT = eCT;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}
	public String getCC_TO_MAIL_ID() {
		return CC_TO_MAIL_ID;
	}
	public void setCC_TO_MAIL_ID(String cCTOMAILID) {
		CC_TO_MAIL_ID = cCTOMAILID;
	}
	public String getWORKFLOW_STATE() {
		return WORKFLOW_STATE;
	}
	public String getCONTACT_NO() {
		return CONTACT_NO;
	}
	public void setCONTACT_NO(String cONTACTNO) {
		CONTACT_NO = cONTACTNO;
	}
	public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
		WORKFLOW_STATE = wORKFLOWSTATE;
	}
	public String getRESOLUTION_COMMENTS() {
		return RESOLUTION_COMMENTS;
	}
	public void setRESOLUTION_COMMENTS(String rESOLUTIONCOMMENTS) {
		RESOLUTION_COMMENTS = rESOLUTIONCOMMENTS;
	}
	
	
	

}
