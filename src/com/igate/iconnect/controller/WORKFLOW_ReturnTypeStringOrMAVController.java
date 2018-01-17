/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.WORKFLOW_FieldPermission;
import com.igate.iconnect.BO.WORKFLOW_RecordPermission;
import com.igate.iconnect.BO.WORKFLOW_Role;
import com.igate.iconnect.BO.WORKFLOW_States;
import com.igate.iconnect.BO.WORKFLOW_UIField;
import com.igate.iconnect.BO.WORKFLOW_ValidTransition;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;

@Controller
public class WORKFLOW_ReturnTypeStringOrMAVController {
	private static Map<String, List<String>> stateValuesMap = new HashMap<String, List<String>>();
	private static Map<String, String> stateIdMap = new HashMap<String, String>();

	private static Map<String, List<WORKFLOW_Role>> roleValuesMap = new HashMap<String, List<WORKFLOW_Role>>();

	private static Map<String, List<WORKFLOW_ValidTransition>> transitionValuesMap = new HashMap<String, List<WORKFLOW_ValidTransition>>();
	private static Logger log = Logger
			.getLogger(WORKFLOW_ReturnTypeStringOrMAVController.class);

	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";

	}

	@RequestMapping(value = "workFlowDetail.htm", method = RequestMethod.GET)
	public ModelAndView getWorkFlowDetail(ModelMap model, HttpSession session) {
		return new ModelAndView("workFlowDetail");
	}

	@RequestMapping(value = "workFlowState.htm", method = RequestMethod.GET)
	public ModelAndView getworkFlowState(ModelMap model,
			HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		String workFlowId = request.getParameter("workFlowId");
		List<WORKFLOW_States> wfStatesList = workFlowDAOManager
				.getWorkFlowStates(workFlowId);

		model.addAttribute("wfStatesList", wfStatesList);
		return new ModelAndView("workFlowState", model);
	}

	@RequestMapping(value = "workFlowRole.htm", method = RequestMethod.GET)
	public ModelAndView getworkFlowRole(ModelMap model,
			HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		String workFlowId = request.getParameter("workFlowId");
		List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
		if (roleValuesMap.containsKey(workFlowId)) {
			roleList = roleValuesMap.get(workFlowId);
		} else {
			roleList = workFlowDAOManager.getRolesMappedForWorkFlow(workFlowId);
			roleValuesMap.put(workFlowId, roleList);
		}
		List<WORKFLOW_Role> applicationRolesList = workFlowDAOManager
				.getDistinctRoles();
		model.addAttribute("roleList", roleList);
		model.addAttribute("applicationRolesList", applicationRolesList);
		return new ModelAndView("workFlowRole", model);
	}
	
	@RequestMapping(value = "workFlowTransition.htm", method = RequestMethod.GET)
    public String getworkFlowTransition(ModelMap model,
            HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        String workFlowId = request.getParameter("workFlowId");

        List<String> stateList = new ArrayList<String>();
        if (stateValuesMap.containsKey(workFlowId)) {
            stateList = stateValuesMap.get(workFlowId);
        } else {
            List<WORKFLOW_States> wfStatesList = workFlowDAOManager
                    .getWorkFlowStates(workFlowId);
            for (WORKFLOW_States workFlowStates : wfStatesList) {
                stateIdMap.put(String.valueOf(workFlowStates.getStateId()),
                        workFlowStates.getState());
                stateList.add(workFlowStates.getState());

            }
            if (stateList.size() > 0) {
                stateValuesMap.put(workFlowId, stateList);
            } else {
                stateList.add("No State Defined");
            }
        }

        model.addAttribute("fromState", stateList);
        model.addAttribute("toState", stateList);
        List<WORKFLOW_ValidTransition> validTransitionList = new ArrayList<WORKFLOW_ValidTransition>();
        if (transitionValuesMap.containsKey(workFlowId)) {
            validTransitionList = transitionValuesMap.get(workFlowId);
        } else {
            validTransitionList = workFlowDAOManager
                    .getValidTransitionForWorkFlow(workFlowId);
            transitionValuesMap.put(workFlowId, validTransitionList);
        }
        model.addAttribute("validTransitionList", validTransitionList);
        return "workFlowTransition";
    }

    @RequestMapping(value = "workFlowRecordPermission.htm", method = RequestMethod.GET)
    public ModelAndView getworkFlowRecordPermission(ModelMap model,
            HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        String workFlowId = request.getParameter("workFlowId");
        List<String> stateList = new ArrayList<String>();
        if (stateValuesMap.containsKey(workFlowId)) {
            stateList = stateValuesMap.get(workFlowId);
        } else {
            stateList.add("No State Defined");
        }
        List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
        if (roleValuesMap.containsKey(workFlowId)) {
            roleList = roleValuesMap.get(workFlowId);
        } else {
            roleList = workFlowDAOManager.getRolesMappedForWorkFlow(workFlowId);
            roleValuesMap.put(workFlowId, roleList);
        }

        List<WORKFLOW_RecordPermission> recordPermissionList = workFlowDAOManager
                .getRecordPermissionForWorkFlow(workFlowId);
        WORKFLOW_UIField uiField = new WORKFLOW_UIField();
        uiField.setUiFieldName("None");
        uiField.setUiId("None");
        List<WORKFLOW_UIField> uiFieldList = workFlowDAOManager.getUIField();
        uiFieldList.add(uiField);
        model.addAttribute("state", stateList);
        model.addAttribute("roleNameList", roleList);
        model.addAttribute("recordPermissionList", recordPermissionList);
        model.addAttribute("uiFieldList", uiFieldList);
        return new ModelAndView("workFlowRecordPermission");
    }

    @RequestMapping(value = "workFlowFieldPermission.htm", method = RequestMethod.GET)
    public ModelAndView getworkFlowFieldPermission(ModelMap model,
            HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workFlowDAOManager = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        String workFlowId = request.getParameter("workFlowId");
        List<String> stateList = new ArrayList<String>();
        if (stateValuesMap.containsKey(workFlowId)) {
            stateList = stateValuesMap.get(workFlowId);
        } else {
            stateList.add("No State Defined");
        }
        List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
        if (roleValuesMap.containsKey(workFlowId)) {
            roleList = roleValuesMap.get(workFlowId);
        } else {
            roleList = workFlowDAOManager.getRolesMappedForWorkFlow(workFlowId);
            roleValuesMap.put(workFlowId, roleList);
        }
        List<WORKFLOW_FieldPermission> fieldPermissionList = workFlowDAOManager
                .getFieldPermissionForWorkFlow(workFlowId);
        List<WORKFLOW_UIField> uiFieldList = workFlowDAOManager.getUIField();
        model.addAttribute("state", stateList);
        model.addAttribute("roleNameList", roleList);
        model.addAttribute("fieldPermissionList", fieldPermissionList);
        model.addAttribute("uiFieldList", uiFieldList);
        return new ModelAndView("workFlowFieldPermission");
    }

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:706638
 Changes Made on:Mar 21, 2012
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
