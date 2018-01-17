/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class HELPDESK_Attachment implements Serializable {
    
	/**
     * 
     */
    private static final long serialVersionUID = 3434728958817144909L;
    private String status;
	private String CATEGORY_ID;
    private String SUBCATEGORY_ID;
    private String WORKFLOW_STATE;
    private String TICKET_ID;
    private String REFERENCE_ID;
    private String ATTACHMENT_NAME;
    private String APPROVER;
	private String ATTACHMENT_PATH;
    private String VERIFIED_BY;
    private String VERIFIED_NAME;
    private String VERIFICATION_COMMENTS;
    private String CREATED_BY;
    private String CREATED_NAME;
	private String CREATED_DATE;
	private CommonsMultipartFile approvalfile;
	private String FUNCTION_NAME;
	private String NEXTSTATE;
	private String NEXTAPPROVERID;
	private String GET_WORKFLOW_STATE;
	private String REF_ID_APP_FILE;
	private String REF_ID_SCRIPT_FILE;
	private String REF_ID_TEST_REPORT;
	private String REF_ID_UAT_REPORT;
	private CommonsMultipartFile IS_HEAD_APP_FILE;
	private CommonsMultipartFile SCRIPT_FILE;
	private CommonsMultipartFile TEST_REPORT_FILE;
	private CommonsMultipartFile UAT_REPORT_FILE;
	//modified by 720307
	private String WORKFLOW_NAME;
	private String REF_ID_APPROVE_REJECT_DESC_FILE;
	private String REF_ID_APP_PROBLEM_DESC_FILE;
	private String REF_ID_APP_RE_OPEN_DESC_FILE;
	private CommonsMultipartFile approvaldescriptionfile;
	private CommonsMultipartFile reopendescriptionfile;
	private CommonsMultipartFile Approve_Reject_Description_File;
	private CommonsMultipartFile approvalproblemfile;
	private String REF_ID_PROBLEM_FILE;
	private CommonsMultipartFile attachmentfile1;
	private CommonsMultipartFile attachmentfile2;
	private CommonsMultipartFile attachmentfile3;
	private String REFERENCE_ATTACHMENT1;
	private String REFERENCE_ATTACHMENT2;
	private String REFERENCE_ATTACHMENT3;
	private boolean attachmentCheckbox1;
	private boolean attachmentCheckbox2;
	private boolean attachmentCheckbox3;
	private String IS_EX_EMPLOYEE;
	private String IS_CHECKED;
	
	public String getIS_CHECKED() {
		return IS_CHECKED;
	}
	public void setIS_CHECKED(String iSCHECKED) {
		IS_CHECKED = iSCHECKED;
	}
	public boolean isAttachmentCheckbox2() {
		return attachmentCheckbox2;
	}
	public void setAttachmentCheckbox2(boolean attachmentCheckbox2) {
		this.attachmentCheckbox2 = attachmentCheckbox2;
	}
	public boolean isAttachmentCheckbox3() {
		return attachmentCheckbox3;
	}
	public void setAttachmentCheckbox3(boolean attachmentCheckbox3) {
		this.attachmentCheckbox3 = attachmentCheckbox3;
	}
	public String getIS_EX_EMPLOYEE() {
		return IS_EX_EMPLOYEE;
	}
	public void setIS_EX_EMPLOYEE(String iSEXEMPLOYEE) {
		IS_EX_EMPLOYEE = iSEXEMPLOYEE;
	}
	public boolean isAttachmentCheckbox1() {
		return attachmentCheckbox1;
	}
	public void setAttachmentCheckbox1(boolean attachmentCheckbox1) {
		this.attachmentCheckbox1 = attachmentCheckbox1;
	}
	public CommonsMultipartFile getAttachmentfile1() {
		return attachmentfile1;
	}
	public void setAttachmentfile1(CommonsMultipartFile attachmentfile1) {
		this.attachmentfile1 = attachmentfile1;
	}
	public CommonsMultipartFile getAttachmentfile2() {
		return attachmentfile2;
	}
	public void setAttachmentfile2(CommonsMultipartFile attachmentfile2) {
		this.attachmentfile2 = attachmentfile2;
	}
	public CommonsMultipartFile getAttachmentfile3() {
		return attachmentfile3;
	}
	public void setAttachmentfile3(CommonsMultipartFile attachmentfile3) {
		this.attachmentfile3 = attachmentfile3;
	}
	public String getREFERENCE_ATTACHMENT1() {
		return REFERENCE_ATTACHMENT1;
	}
	public void setREFERENCE_ATTACHMENT1(String rEFERENCEATTACHMENT1) {
		REFERENCE_ATTACHMENT1 = rEFERENCEATTACHMENT1;
	}
	public String getREFERENCE_ATTACHMENT2() {
		return REFERENCE_ATTACHMENT2;
	}
	public void setREFERENCE_ATTACHMENT2(String rEFERENCEATTACHMENT2) {
		REFERENCE_ATTACHMENT2 = rEFERENCEATTACHMENT2;
	}
	public String getREFERENCE_ATTACHMENT3() {
		return REFERENCE_ATTACHMENT3;
	}
	public void setREFERENCE_ATTACHMENT3(String rEFERENCEATTACHMENT3) {
		REFERENCE_ATTACHMENT3 = rEFERENCEATTACHMENT3;
	}
	public String getREF_ID_PROBLEM_FILE() {
		return REF_ID_PROBLEM_FILE;
	}
	public void setREF_ID_PROBLEM_FILE(String rEFIDPROBLEMFILE) {
		REF_ID_PROBLEM_FILE = rEFIDPROBLEMFILE;
	}
	public CommonsMultipartFile getApprovalproblemfile() {
		return approvalproblemfile;
	}
	public void setApprovalproblemfile(CommonsMultipartFile approvalproblemfile) {
		this.approvalproblemfile = approvalproblemfile;
	}
	public String getREF_ID_UAT_REPORT() {
		return REF_ID_UAT_REPORT;
	}
	public void setREF_ID_UAT_REPORT(String rEFIDUATREPORT) {
		REF_ID_UAT_REPORT = rEFIDUATREPORT;
	}
	public CommonsMultipartFile getUAT_REPORT_FILE() {
		return UAT_REPORT_FILE;
	}
	public void setUAT_REPORT_FILE(CommonsMultipartFile uATREPORTFILE) {
		UAT_REPORT_FILE = uATREPORTFILE;
	}
	
	public String getREF_ID_APP_FILE() {
		return REF_ID_APP_FILE;
	}
	public void setREF_ID_APP_FILE(String rEFIDAPPFILE) {
		REF_ID_APP_FILE = rEFIDAPPFILE;
	}
	public String getREF_ID_SCRIPT_FILE() {
		return REF_ID_SCRIPT_FILE;
	}
	public void setREF_ID_SCRIPT_FILE(String rEFIDSCRIPTFILE) {
		REF_ID_SCRIPT_FILE = rEFIDSCRIPTFILE;
	}
	public String getREF_ID_TEST_REPORT() {
		return REF_ID_TEST_REPORT;
	}
	public void setREF_ID_TEST_REPORT(String rEFIDTESTREPORT) {
		REF_ID_TEST_REPORT = rEFIDTESTREPORT;
	}
	public CommonsMultipartFile getIS_HEAD_APP_FILE() {
		return IS_HEAD_APP_FILE;
	}
	public void setIS_HEAD_APP_FILE(CommonsMultipartFile iSHEADAPPFILE) {
		IS_HEAD_APP_FILE = iSHEADAPPFILE;
	}
	public CommonsMultipartFile getSCRIPT_FILE() {
		return SCRIPT_FILE;
	}
	public void setSCRIPT_FILE(CommonsMultipartFile sCRIPTFILE) {
		SCRIPT_FILE = sCRIPTFILE;
	}
	public CommonsMultipartFile getTEST_REPORT_FILE() {
		return TEST_REPORT_FILE;
	}
	public void setTEST_REPORT_FILE(CommonsMultipartFile tESTREPORTFILE) {
		TEST_REPORT_FILE = tESTREPORTFILE;
	}
	public String getGET_WORKFLOW_STATE() {
		return GET_WORKFLOW_STATE;
	}
	public void setGET_WORKFLOW_STATE(String gETWORKFLOWSTATE) {
		GET_WORKFLOW_STATE = gETWORKFLOWSTATE;
	}
	public String getNEXTAPPROVERID() {
		return NEXTAPPROVERID;
	}
	public void setNEXTAPPROVERID(String nEXTAPPROVERID) {
		NEXTAPPROVERID = nEXTAPPROVERID;
	}
	public String getFUNCTION_NAME() {
		return FUNCTION_NAME;
	}
	public void setFUNCTION_NAME(String fUNCTIONNAME) {
		FUNCTION_NAME = fUNCTIONNAME;
	}
	public CommonsMultipartFile getApprovalfile() {
		return approvalfile;
	}
	public void setApprovalfile(CommonsMultipartFile approvalfile) {
		this.approvalfile = approvalfile;
	}
    public String getAPPROVER() {
		return APPROVER;
	}
	public void setAPPROVER(String aPPROVER) {
		APPROVER = aPPROVER;
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
	private String MODIFIED_BY;
    private String MODIFIED_NAME;
    private String MODIFIED_DATE;
    public String getVERIFIED_NAME() {
		return VERIFIED_NAME;
	}
	public void setVERIFIED_NAME(String vERIFIEDNAME) {
		VERIFIED_NAME = vERIFIEDNAME;
	}
	public String getCREATED_NAME() {
		return CREATED_NAME;
	}
	public void setCREATED_NAME(String cREATEDNAME) {
		CREATED_NAME = cREATEDNAME;
	}
	public String getMODIFIED_NAME() {
		return MODIFIED_NAME;
	}
	public void setMODIFIED_NAME(String mODIFIEDNAME) {
		MODIFIED_NAME = mODIFIEDNAME;
	}
	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}
	public void setREFERENCE_ID(String rEFERENCEID) {
		REFERENCE_ID = rEFERENCEID;
	}
	public String getVERIFIED_BY() {
		return VERIFIED_BY;
	}
	public void setVERIFIED_BY(String vERIFIEDBY) {
		VERIFIED_BY = vERIFIEDBY;
	}
	public String getVERIFICATION_COMMENTS() {
		return VERIFICATION_COMMENTS;
	}
	public void setVERIFICATION_COMMENTS(String vERIFICATIONCOMMENTS) {
		VERIFICATION_COMMENTS = vERIFICATIONCOMMENTS;
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
	public void setTICKET_ID(String tICKET_ID) {
		TICKET_ID = tICKET_ID;
	}
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORYID) {
		CATEGORY_ID = cATEGORYID;
	}
	public String getSUBCATEGORY_ID() {
		return SUBCATEGORY_ID;
	}
	public void setSUBCATEGORY_ID(String sUBCATEGORYID) {
		SUBCATEGORY_ID = sUBCATEGORYID;
	}
	public String getWORKFLOW_STATE() {
		return WORKFLOW_STATE;
	}
	public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
		WORKFLOW_STATE = wORKFLOWSTATE;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setNEXTSTATE(String nEXTSTATE) {
		NEXTSTATE = nEXTSTATE;
	}
	public String getNEXTSTATE() {
		return NEXTSTATE;
	}
	public String getWORKFLOW_NAME() {
		return WORKFLOW_NAME;
	}
	public void setWORKFLOW_NAME(String wORKFLOWNAME) {
		WORKFLOW_NAME = wORKFLOWNAME;
	}
	public String getREF_ID_APP_PROBLEM_DESC_FILE() {
		return REF_ID_APP_PROBLEM_DESC_FILE;
	}
	public void setREF_ID_APP_PROBLEM_DESC_FILE(String rEFIDAPPPROBLEMDESCFILE) {
		REF_ID_APP_PROBLEM_DESC_FILE = rEFIDAPPPROBLEMDESCFILE;
	}
	public CommonsMultipartFile getApprovaldescriptionfile() {
		return approvaldescriptionfile;
	}
	public void setApprovaldescriptionfile(
			CommonsMultipartFile approvaldescriptionfile) {
		this.approvaldescriptionfile = approvaldescriptionfile;
	}
	public CommonsMultipartFile getReopendescriptionfile() {
		return reopendescriptionfile;
	}
	public void setReopendescriptionfile(CommonsMultipartFile reopendescriptionfile) {
		this.reopendescriptionfile = reopendescriptionfile;
	}
	public String getREF_ID_APPROVE_REJECT_DESC_FILE() {
		return REF_ID_APPROVE_REJECT_DESC_FILE;
	}
	public void setREF_ID_APPROVE_REJECT_DESC_FILE(String rEFIDAPPROVEREJECTDESCFILE) {
		REF_ID_APPROVE_REJECT_DESC_FILE = rEFIDAPPROVEREJECTDESCFILE;
	}
	public CommonsMultipartFile getApprove_Reject_Description_File() {
		return Approve_Reject_Description_File;
	}
	public void setApprove_Reject_Description_File(
			CommonsMultipartFile approveRejectDescriptionFile) {
		Approve_Reject_Description_File = approveRejectDescriptionFile;
	}
	public String getREF_ID_APP_RE_OPEN_DESC_FILE() {
		return REF_ID_APP_RE_OPEN_DESC_FILE;
	}
	public void setREF_ID_APP_RE_OPEN_DESC_FILE(String rEFIDAPPREOPENDESCFILE) {
		REF_ID_APP_RE_OPEN_DESC_FILE = rEFIDAPPREOPENDESCFILE;
	}
   
}
/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:712836

Changes Made on:JUNE 13,2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/

 

