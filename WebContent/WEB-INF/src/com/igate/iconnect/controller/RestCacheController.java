/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class RestCacheController {
	private static Logger log = Logger.getLogger(RestCacheController.class);
	
	@ExceptionHandler
	public String handleExceptionsF(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "ERROR";

	}

    /*@RequestMapping(value = "TECHCR_Detail.htm", method = RequestMethod.GET)
    public String showDetail(ModelMap model) {
        return "TECHCR_Detail";
    }*/

    @RequestMapping(value = "ADMIN_DataResetCache.htm", method = RequestMethod.GET)
    public String showResetCache(ModelMap model) {
        return "ADMIN_DataResetCache";
    }
    
    @RequestMapping(value = "ADMIN_WorkflowResetCache.htm", method = RequestMethod.GET)
    public String showResetCacheForWorkflow(ModelMap model) {
        return "ADMIN_WorkflowResetCache";
    }
    
    
    @RequestMapping(value = "getResetCacheMethodsForWorkflow.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getResetCacheMethodsForWorkflow(ModelMap model,
            HttpServletResponse response) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        List<String> resetCacheObj = new ArrayList<String>();
        Class c = workflowimpl.getClass();
        Method m[] = c.getDeclaredMethods();
        for (Method aM : m) {
            if (aM.getName().startsWith("reset")) {
                resetCacheObj.add(aM.getName());
            }
        }
        model.addAttribute("resetCacheObj", resetCacheObj);
        JsonUtility.sendData(resetCacheObj, response);
    }
    
    @RequestMapping(value = "getResetCacheMethods.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getResetCacheMethods(ModelMap model, HttpServletResponse response) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<String> resetCacheObj = new ArrayList<String>();
        Class c = commonDataForCacheObj.getClass();
        Method m[] = c.getDeclaredMethods();
        for (Method aM : m) {
            if (aM.getName().startsWith("reset")) {
                resetCacheObj.add(aM.getName());
            }
        }
        model.addAttribute("resetCacheObj", resetCacheObj);
        JsonUtility.sendData(resetCacheObj, response);
    }
    
    @RequestMapping(value = "invokeResetCacheMethods.htm", method = RequestMethod.GET)
    public @ResponseBody
    void invokeResetCacheMethods(String jsonstring, ModelMap model,
            HttpServletResponse response) throws SecurityException,
            NoSuchMethodException, ParseException, IOException, JSONException {
    	
    	JSONArray json = new JSONArray(jsonstring);
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        
	        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");
			Class c = commonDataForCacheObj.getClass();
			boolean result = false;
			try {
			    for (int i = 0; i < json.length(); i++) {
			        Method invokeMethod = c.getMethod(json.get(i).toString());
			        result = (Boolean) invokeMethod.invoke(commonDataForCacheObj);
			    }
			
			} catch (IllegalArgumentException e) {
			    log
			            .error("Illegal argument exception while invoking reset cache methods : "
			                    + e);
			} catch (IllegalAccessException e) {
			    log
			            .error("Illegal access exception while invoking reset cache methods : "
			                    + e);
			} catch (InvocationTargetException e) {
			    log
			            .error("Illegal invocation target exception while invoking reset cache methods : "
			                    + e);
			}
        JsonUtility.sendData(result, response);
    }
    
    @RequestMapping(value = "invokeResetCacheMethodsForWorkflow.htm", method = RequestMethod.GET)
    public @ResponseBody
    void invokeResetCacheMethodsForWorkflow(String jsonstring,ModelMap model,
            HttpServletResponse response) throws SecurityException,
            NoSuchMethodException, ParseException, IOException, JSONException {
    	 
    	 JSONArray json = new JSONArray(jsonstring);
         ApplicationContext ctx = COMMON_AppContext.getCtx();
         WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
                 .getBean("workFlowDAOImpl");
         Class c = workflowimpl.getClass();
         boolean result = false;
         try {
             for (int i = 0; i < json.length(); i++) {
                 Method invokeMethod = c.getMethod(json.get(i).toString());
                 result = (Boolean) invokeMethod.invoke(workflowimpl);
             }

         } catch (IllegalArgumentException e) {
             log
                     .error("Illegal argument exception while invoking reset cache methods for workflow: "
                             + e);
         } catch (IllegalAccessException e) {
             log
                     .error("Illegal access  exception while invoking reset cache methods for workflow: "
                             + e);
         } catch (InvocationTargetException e) {
             log
                     .error("Illegal invocation exception while invoking reset cache methods for workflow: "
                             + e);
         }
         
        JsonUtility.sendData(result, response);
    }

}
