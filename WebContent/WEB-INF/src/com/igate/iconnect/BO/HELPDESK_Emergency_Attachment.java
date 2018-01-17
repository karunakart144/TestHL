package com.igate.iconnect.BO;

public class HELPDESK_Emergency_Attachment {
	 private String TICKET_ID;
	 private String REFERENCE_ID;
	 private String ATTACHMENT_PATH;
	 private String ATTACHMENT_NAME;
	 private boolean attachmentCkBox;
	 


	public boolean isAttachmentCkBox() {
		return attachmentCkBox;
	}
	public void setAttachmentCkBox(boolean attachmentCkBox) {
		this.attachmentCkBox = attachmentCkBox;
	}
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public void setTICKET_ID(String tICKETID) {
		TICKET_ID = tICKETID;
	}
	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}
	public void setREFERENCE_ID(String rEFERENCEID) {
		REFERENCE_ID = rEFERENCEID;
	}
	public String getATTACHMENT_PATH() {
		return ATTACHMENT_PATH;
	}
	public void setATTACHMENT_PATH(String aTTACHMENTPATH) {
		ATTACHMENT_PATH = aTTACHMENTPATH;
	}
	public String getATTACHMENT_NAME() {
		return ATTACHMENT_NAME;
	}
	public void setATTACHMENT_NAME(String aTTACHMENTNAME) {
		ATTACHMENT_NAME = aTTACHMENTNAME;
	}
}
