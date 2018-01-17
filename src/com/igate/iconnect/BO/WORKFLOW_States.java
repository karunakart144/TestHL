/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;

public class WORKFLOW_States implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4234745847830591742L;
    private long stateId;
    private String state;
    private long workFlowId;
    private String menuName;
    public long getStateId() {
        return stateId;
    }
    public void setStateId(long stateId) {
        this.stateId = stateId;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public long getWorkFlowId() {
        return workFlowId;
    }
    public void setWorkFlowId(long workFlowId) {
        this.workFlowId = workFlowId;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:701901
Changes Made on:Nov 6, 2010
End-------Version 1.0-------
		
-----------------------------------------------------------------------------*/