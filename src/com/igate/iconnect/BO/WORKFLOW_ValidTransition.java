/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.List;

public class WORKFLOW_ValidTransition implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -245109053128467069L;
    private String currentState;
    private String nextState;
    private String roleName;
    private String reservedForName;
    private long transitionId;
    private String transitionName;
    private List<String> roleMapped;
    private List<String> customFields;
    private String workFlowId;

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public List<String> getRoleMapped() {
        return roleMapped;
    }

    public void setRoleMapped(List<String> roleMapped) {
        this.roleMapped = roleMapped;
    }

    public List<String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<String> customFields) {
        this.customFields = customFields;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public long getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(long transitionId) {
        this.transitionId = transitionId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getReservedForName() {
        return reservedForName;
    }

    public void setReservedForName(String reservedForName) {
        this.reservedForName = reservedForName;
    }

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Sep 15, 2010
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
