/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;

public class WORKFLOW_RecordPermission implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2197304558351138535L;
    private String currentState;
    private String roleName;
    private String reservedForName;
    private String permission;
    private long recordPermissionId;
    
    public long getRecordPermissionId() {
        return recordPermissionId;
    }
    public void setRecordPermissionId(long recordPermissionId) {
        this.recordPermissionId = recordPermissionId;
    }
    public String getCurrentState() {
        return currentState;
    }
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
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
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    

}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:701901
Changes Made on:Sep 14, 2010
End-------Version 1.0-------
		
-----------------------------------------------------------------------------*/