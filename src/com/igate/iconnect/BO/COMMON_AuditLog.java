/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package com.igate.iconnect.BO;

import java.io.Serializable;


public class COMMON_AuditLog implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 329108994130410049L;
    public long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}
	public String getPreviousState() {
		return previousState;
	}
	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public String getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDataChange() {
		return dataChange;
	}
	public void setDataChange(String dataChange) {
		this.dataChange = dataChange;
	}
	private long historyId;
    private String previousState;
    private String currentState;
    private String changedBy;
    private String changedDate;
    private String action;  
    private String dataChange;
    private String responseTime;
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
    

}
/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:712836

Changes Made on:JUNE 10,2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/

 



 

