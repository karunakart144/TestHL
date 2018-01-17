/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.BO.WORKFLOW_Master;
import com.igate.iconnect.BO.WORKFLOW_Role;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class WORKFLOW_ReturnTypeVoidController {

    private static Map<String, List<String>> stateValuesMap = new HashMap<String, List<String>>();
    private static Map<String, String> stateIdMap = new HashMap<String, String>();

    private static Map<String, List<WORKFLOW_Role>> roleValuesMap = new HashMap<String, List<WORKFLOW_Role>>();
    private static Map<String, String> roleIdMap = new HashMap<String, String>();
    
    private static Logger log = Logger.getLogger(WORKFLOW_ReturnTypeVoidController.class);

    @ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}
    /*
     * @RequestMapping(value = "iConnect.htm", method = RequestMethod.GET)
     * public String showIHDPage(ModelMap model, HttpServletRequest request) {
     * return "iConnect"; }
     */

    @RequestMapping(value = "/getTablesFromDB", method = RequestMethod.GET)
    public @ResponseBody
    void getTablesFromDB(HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, List<String>> toSendData = new HashMap<String, List<String>>();
        List<String> tablesList = new ArrayList<String>();
        try {
            tablesList = workFlowDAOManager.getTablesFromDB();
            toSendData.put("Success", tablesList);
        } catch (SQLException e) {
            tablesList.add(e.getMessage());
            toSendData.put("Error", tablesList);
            // e.printStackTrace();
        }
        JsonUtility.sendData(toSendData, response);
    }

    @RequestMapping(value = "/insertMasterWFDetail", method = RequestMethod.GET)
    public @ResponseBody
    void insertMasterWFDetail(@RequestParam String name, String description,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        String generatedWorkFlowId = "0";
        try {
            generatedWorkFlowId = workFlowDAOManager.insertWorkFlowMaster(name,
                    description);
            toSendData.put("Success", generatedWorkFlowId);
        } catch (SQLException e) {
            toSendData.put("Error", e.getMessage());
            // e.printStackTrace();
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);
    }

    @RequestMapping(value = "/getExistingWorkFlow", method = RequestMethod.GET)
    public @ResponseBody
    void getExistingWorkFlow(HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        List<WORKFLOW_Master> existingWorkflowList = new ArrayList<WORKFLOW_Master>();
        Map<String, List<WORKFLOW_Master>> toSendData = new HashMap<String, List<WORKFLOW_Master>>();
        try {
            existingWorkflowList = workFlowDAOManager.getExistingWorkFlow();
            toSendData.put("Success", existingWorkflowList);
        } catch (SQLException sqle) {
            WORKFLOW_Master wf = new WORKFLOW_Master();
            wf.setErrorMessage(sqle.getMessage());
            existingWorkflowList.add(wf);
            toSendData.put("Error", existingWorkflowList);
        }

        JsonUtility.sendData(toSendData, response);
    }

    
    @RequestMapping(value = "getWorkFlowRole.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getWorkFlowRole(@RequestParam String workFlowId,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        JsonUtility.sendData(roleValuesMap.get(workFlowId), response);

    }

    @RequestMapping(value = "insertWorkFlowRole.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertWorkFlowRole(@RequestParam String roleName, String workFlowId,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {

            String generatedStateId = workFlowDAOManager.insertWorkFlowRole(
                    roleName, workFlowId);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                roleIdMap.put(roleName, generatedStateId);
                if (roleValuesMap.containsKey(workFlowId)) {
                    List<WORKFLOW_Role> roleList = roleValuesMap.get(workFlowId);
                    for (WORKFLOW_Role role : roleList) {
                        if (!roleName.equalsIgnoreCase(role.getRoleName())) {
                            roleList.add(role);
                            break;
                        }

                    }
                } else {
                    List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
                    WORKFLOW_Role role = new WORKFLOW_Role();
                    role.setRoleName(roleName);
                    roleList.add(role);
                    roleValuesMap.put(workFlowId, roleList);
                }
                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

    @RequestMapping(value = "insertWorkFlowState.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertWorkFlowState(@RequestParam String stateName, String workFlowId,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {
            String generatedStateId = workFlowDAOManager.insertWorkFlowState(
                    stateName, workFlowId);
            stateIdMap.put(generatedStateId, stateName);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                if (stateValuesMap.containsKey(workFlowId)) {
                    List<String> stateList = stateValuesMap.get(workFlowId);
                    stateList.add(stateName);
                    stateValuesMap.put(workFlowId, stateList);
                } else {
                    List<String> stateList = new ArrayList<String>();
                    stateList.add(stateName);
                    stateValuesMap.put(workFlowId, stateList);
                }

                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

    @RequestMapping(value = "insertWorkFlowTransition.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertWorkFlowTransition(@RequestParam String transitionName,
            String fromState, String toState, String workFlowId,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {

            String generatedStateId = workFlowDAOManager
                    .insertWorkFlowTransition(transitionName, fromState,
                            toState, workFlowId);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                /*
                 * if (stateValuesMap.containsKey(workFlowId)) { List<String>
                 * stateList = stateValuesMap.get(workFlowId);
                 * stateList.add(stateName); stateValuesMap.put(workFlowId,
                 * stateList); } else { List<String> stateList = new
                 * ArrayList<String>(); stateList.add(stateName);
                 * stateValuesMap.put(workFlowId, stateList); }
                 */

                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

    @RequestMapping(value = "insertRoleForTransition.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertRoleForTransition(@RequestParam String workFlowId,
            String roleName, String transitionId, String customFields,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {

            String generatedStateId = workFlowDAOManager
                    .insertRoleForTransition(workFlowId, transitionId,
                            roleName, customFields);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

    @RequestMapping(value = "insertRecordPermission.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertRecordPermission(@RequestParam String workFlowId, String roleId,
            String state, String customFields, String permission,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {

            String generatedStateId = workFlowDAOManager
                    .insertRecordPermission(workFlowId, roleId, state,
                            customFields, permission);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

    @RequestMapping(value = "insertFieldPermission.htm", method = RequestMethod.GET)
    public @ResponseBody
    void insertFieldPermission(@RequestParam String workFlowId, String roleId,
            String state, String customFields, String permission,
            HttpServletResponse response, HttpServletRequest request)
            throws ParseException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        Map<String, String> toSendData = new HashMap<String, String>();
        try {

            String generatedStateId = workFlowDAOManager.insertFieldPermission(
                    workFlowId, roleId, state, customFields, permission);
            if (generatedStateId.contains("already Exists")) {
                toSendData.put("Error", generatedStateId);
            } else {
                toSendData.put("Success", generatedStateId);
            }
        } catch (SQLException sqle) {
            toSendData.put("Error", sqle.getMessage());
        } catch (ParseException pe) {
            toSendData.put("Error", pe.getMessage());
        }
        JsonUtility.sendData(toSendData, response);

    }

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:May 20, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
