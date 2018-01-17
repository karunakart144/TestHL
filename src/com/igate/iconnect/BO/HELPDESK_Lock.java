/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class HELPDESK_Lock implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 3060513489959313015L;
    private String TICKET_ID;
	private String EMPLOYEE_NAME;
	private String LOCKED_DATE;
	private String PREMIUM;
	public String getTICKET_ID() {
		return TICKET_ID;
	}
	public void setTICKET_ID(String tICKETID) {
		TICKET_ID = tICKETID;
	}
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	public void setEMPLOYEE_NAME(String eMPLOYEENAME) {
		EMPLOYEE_NAME = eMPLOYEENAME;
	}
	public String getLOCKED_DATE() {
		return LOCKED_DATE;
	}
	public void setLOCKED_DATE(String lOCKEDDATE) {
		LOCKED_DATE = lOCKEDDATE;
	}
	public String getPREMIUM() {
		return PREMIUM;
	}
	public void setPREMIUM(String pREMIUM) {
		PREMIUM = pREMIUM;
	}
}
