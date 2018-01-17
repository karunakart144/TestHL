package com.igate.iconnect.BO;

import java.io.Serializable;

public class Approval_DetailsBO implements Serializable{
	
	private String REQUEST_ID;
	private String APPROVAL_STATUS;
	private String APPROVER_ID;
	private String COMMENTS;
	private String SOURCE;
	public String getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}
	public String getREQUEST_ID() {
		return REQUEST_ID;
	}
	public void setREQUEST_ID(String rEQUEST_ID) {
		REQUEST_ID = rEQUEST_ID;
	}
	public String getAPPROVAL_STATUS() {
		return APPROVAL_STATUS;
	}
	public void setAPPROVAL_STATUS(String aPPROVAL_STATUS) {
		APPROVAL_STATUS = aPPROVAL_STATUS;
	}
	public String getAPPROVER_ID() {
		return APPROVER_ID;
	}
	public void setAPPROVER_ID(String aPPROVER_ID) {
		APPROVER_ID = aPPROVER_ID;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	
	

}
