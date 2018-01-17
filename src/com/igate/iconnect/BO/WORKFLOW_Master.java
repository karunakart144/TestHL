/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;

public class WORKFLOW_Master implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1033274776998364071L;
    private String workFlowName;
    private String startState;
    private Long workFlowId;
    private Long startStateId;
    private String workFlowDesc;
    private String errorMessage;
    
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getWorkFlowDesc() {
        return workFlowDesc;
    }
    public void setWorkFlowDesc(String workFlowDesc) {
        this.workFlowDesc = workFlowDesc;
    }
    public Long getStartStateId() {
        return startStateId;
    }
    public void setStartStateId(Long startStateId) {
        this.startStateId = startStateId;
    }
    public Long getWorkFlowId() {
        return workFlowId;
    }
    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }
    public String getWorkFlowName() {
        return workFlowName;
    }
    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }
    public String getStartState() {
        return startState;
    }
    public void setStartState(String startState) {
        this.startState = startState;
    }
    

}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:701901
Changes Made on:Sep 12, 2010
End-------Version 1.0-------
		
-----------------------------------------------------------------------------*/