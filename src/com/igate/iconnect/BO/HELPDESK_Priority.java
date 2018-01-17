/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class HELPDESK_Priority implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 2643441937261406358L;
    public String getPRIORITYID() {
		return PRIORITYID;
	}
	public void setPRIORITYID(String pRIORITYID) {
		PRIORITYID = pRIORITYID;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	private String PRIORITYID;
	private String DESCRIPTION;
}
