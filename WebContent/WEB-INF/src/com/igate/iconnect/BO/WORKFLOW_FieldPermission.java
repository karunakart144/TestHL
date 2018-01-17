/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class WORKFLOW_FieldPermission implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4372946607199702219L;
    private String currentState;
    private String roleName;
    private String reservedForName;
    private String permission;
    private String fields;
    private String fieldPermissionId;
    private List<String> customFieldsList;
    private Map<String, List<String>> customFieldsWithPermissionList;

    public Map<String, List<String>> getCustomFieldsWithPermissionList() {
        return customFieldsWithPermissionList;
    }

    public void setCustomFieldsWithPermissionList(
            Map<String, List<String>> customFieldsWithPermissionList) {
        this.customFieldsWithPermissionList = customFieldsWithPermissionList;
    }

    public List<String> getCustomFieldsList() {
        return customFieldsList;
    }

    public void setCustomFieldsList(List<String> customFieldsList) {
        this.customFieldsList = customFieldsList;
    }

    public String getFieldPermissionId() {
        return fieldPermissionId;
    }

    public void setFieldPermissionId(String fieldPermissionId) {
        this.fieldPermissionId = fieldPermissionId;
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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
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
