/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.igate.iconnect.BO.WORKFLOW_FieldPermission;
import com.igate.iconnect.BO.WORKFLOW_Master;
import com.igate.iconnect.BO.WORKFLOW_RecordPermission;
import com.igate.iconnect.BO.WORKFLOW_Role;
import com.igate.iconnect.BO.WORKFLOW_States;
import com.igate.iconnect.BO.WORKFLOW_UIField;
import com.igate.iconnect.BO.WORKFLOW_ValidTransition;

public interface WORKFLOW_DAO {

    public List<WORKFLOW_States> getAllStates(String workFlowId);

    public String getStateId(String stateName, String workFlowId);

    public String getStateName(String stateId, String workFlowId);

    public List<String> getNextValidTransition(String workFlowId,
            String currentState, String roleName);

    public Map<String, String> getIndividualRecordPermission(String workFlowId,
            String currentState, String roleName);

    public Map<String, List<String>> getAllStatesRecordPermission(
            String workFlowId, String roleName);

    public Map<String, List<String>> getAllPermissionForFields(
            String workFlowId, String roleName, String currentState);

    public List<String> getViewOnlyPermissionForField(String workFlowId,
            String roleName, String currentState);

    public List<String> getEditOnlyPermissionForField(String workFlowId,
            String roleName, String currentState);

    public List<String> getNoneOnlyPermissionForField(String workFlowId,
            String roleName, String currentState);

    // For UI
    public List<String> getTablesFromDB() throws SQLException;

    public String insertWorkFlowMaster(String name, String description)
            throws SQLException, ParseException;

    public List<WORKFLOW_Master> getExistingWorkFlow() throws SQLException;

    public List<WORKFLOW_States> getWorkFlowStates(String workFlowId);

    public List<WORKFLOW_Role> getDistinctRoles();

    public List<WORKFLOW_Role> getRolesMappedForWorkFlow(String workFlowId);

    public List<WORKFLOW_ValidTransition> getValidTransitionForWorkFlow(String workFlowId);

    public List<WORKFLOW_RecordPermission> getRecordPermissionForWorkFlow(
            String workFlowId);

    public List<WORKFLOW_UIField> getUIField();

    public List<WORKFLOW_FieldPermission> getFieldPermissionForWorkFlow(String workFlowId);

    public String insertWorkFlowState(String stateName, String workFlowId)
            throws SQLException, ParseException;

    public String insertWorkFlowTransition(String transitionName,
            String fromState, String toState, String workFlowId)
            throws SQLException, ParseException;

    public String insertWorkFlowRole(String roleName, String workFlowId)
            throws SQLException, ParseException;

    public String insertRoleForTransition(String workFlowId,
            String transitionId, String roleName, String customFields)
            throws SQLException, ParseException;

    public String insertRecordPermission(String workFlowId, String roleId,
            String state, String customFields, String permission)
            throws SQLException, ParseException;

    public String insertFieldPermission(String workFlowId, String roleId,
            String state, String customFields, String permission)
            throws SQLException, ParseException;

    public String getWorkflowID(String workflowname);

    public boolean resetPrepareDistinctWorkFlow();

    public boolean resetPrepareDistinctStatesForEachWorkFlow();

    public boolean resetPrepareDistinctRolesForEachWorkFlow();

    public boolean resetPrepareValidTransitionForEachWorkFlow();

    public boolean resetprepareRoleOrFieldAllowedForValidTransition();

    public boolean resetPrepareRecordPermissionForEachWorkFlow();

    public boolean resetPrepareFieldPermissionForEachWorkFlow();
    
    public boolean resetPrepareFunctionDefaultStateRoleMapping();

    /**
     * to get the default available states when user tries to change the function for each function on role wise
     */
  //By Nisha --changed for HR Helpdesk --start
    public List<String> getStatesForSelectedFunctionOnRoleWise(String categoryId, String subCategoryID, String functionName, int roleID); 
  //By Nisha --changed for HR Helpdesk --end
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jun 9, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
