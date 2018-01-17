/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;

public class WORKFLOW_Role implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3992720712644201761L;
    private long roleId;
    private String roleName;
    private String workFlowId;
    
    
    public String getWorkFlowId() {
        return workFlowId;
    }
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }
    public long getRoleId() {
        return roleId;
    }
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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