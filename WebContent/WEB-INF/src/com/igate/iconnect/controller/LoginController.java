/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.igate.iconnect.BO.COMMON_Menu;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.BO.WORKFLOW_Role;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.LoginDAO;
import com.igate.iconnect.service.LoginValidator;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_ScoreCalculator;
import com.igate.iconnect.util.SessionListener;

@Controller
public class LoginController {
    private final LoginValidator loginValidator;   
    private static Logger log = Logger.getLogger(LoginController.class);

    @ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";

	}
    @Autowired
    public LoginController(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;

    }

    /**
     * RequestMapping : Login.htm - GET : To load the Login.jsp
     * 
     * @param userBeanObj
     *            : user Bean object
     */
    @RequestMapping(value = "Login.htm", method = RequestMethod.GET)
    public ModelAndView showLoginForm( ModelMap model, HttpServletRequest request) {   	
   
        User userBeanObj = new User();
        model.addAttribute("UserBean", userBeanObj);
       
        return new ModelAndView("Login");
    }

    @RequestMapping(value = "Logout.htm", method = RequestMethod.GET) 
    public ModelAndView showLoginPageAgain(ModelMap model,
            HttpServletRequest request) {

        if (request.getSession(false) != null) {
            ApplicationContext ctx = COMMON_AppContext.getCtx();
            LoginDAO loginDAO = (LoginDAO) ctx
                    .getBean("loginDAOImpl");
            loginDAO.insertLoggedOutDetails((String) request.getSession()
                    .getAttribute("userLoginId"),"OUT",(String) request.getSession()
                    .getAttribute("userLoginId"),false,request.getSession().getId());
			request.getSession().invalidate();
        }
        if (request.getSession().getAttribute("userLoginId") != null) {
           request.getSession().invalidate();
           SessionListener sessionListn=new SessionListener();
           sessionListn.sessionDestroyed(null);
        	
          
        }
        User userBeanObj = new User();
        model.addAttribute("UserBean", userBeanObj);
        return new ModelAndView(new RedirectView("Login.htm"));
    }

    /**
     * RequestMapping : Login.htm - POST : Submit action on Login.jsp
     * 
     * @param userBeanObj
     *            : user Bean object
     */
    @RequestMapping(value = "/Login.htm", method = RequestMethod.POST)
    public ModelAndView onSubmit(
            @ModelAttribute("UserBean") User userBeanObj,
            BindingResult result, HttpSession session,
            HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        LoginDAO loginDAO = (LoginDAO) ctx
                .getBean("loginDAOImpl");
        COMMON_CacheDAO commonCache = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        
        //L2: 2751
        if(request.getParameter("buttonName").equalsIgnoreCase("")){
        loginValidator.isUserExistInLdap(userBeanObj, result);
        }
     
        if (result.hasErrors()) {
            return new ModelAndView("Login");
        } else {
            if (request.getParameter("buttonName").trim().length() == 0) {
                // Check if the user exist and its authentication mode
                List<User> userBeanList = loginDAO
                        .isExistingUser(userBeanObj.getLoginId());

                // START:if userBeanList == null means that user doesn't exists.
                // Hence
                // redirect him to login page
                if (userBeanList != null && userBeanList.size() == 0) {
                    User userObj = new User();
                    loginValidator.validate(userObj, result);
                }
                
                
                // END:if userBeanList == null means that user doesn't exists.
                // Hence
                // redirect him to login page
                List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
                if (userBeanList != null && userBeanList.size() > 0) {
                    for (User user : userBeanList) {
                        WORKFLOW_Role role = new WORKFLOW_Role();
                        role.setRoleName(user.getUserRole());
                        role.setRoleId(Long.parseLong(user.getUserRoleId()));
                        roleList.add(role);

                    }
                    User userObj = userBeanList.get(0);
                    // Check the authentication mode, if 1 proceed
                    // authentication if
                    // 0
                    // by pass authentication
                    if (userObj.isAuthenticationRequired()) {
                    	userObj.setSamaccountname(userBeanObj.getSamaccountname());
                        userObj.setPassword(userBeanObj.getPassword());
                        userObj.setUserDomain(userBeanObj.getUserDomain());
                        loginValidator.validate(userObj, result);
                    } else {
                        if (userBeanList != null) {
                            User userObject = userBeanList.get(0);
                            userObject.setUserRoleList(roleList);
                            loginValidator.MobileAndExtension(userObject);
                            
                            
                            session.setAttribute("userLoginId", userBeanObj
                                    .getLoginId());
                            session.setAttribute(userBeanObj.getLoginId(),
                                    userObject);
                        }
                        if (roleList.size() > 1) {
                            ModelMap model = new ModelMap();
                            model.addAttribute("userRoleList", roleList);
                            model.addAttribute("roleListSize", roleList.size());
                            model.addAttribute("button", "continue");
                            // Prompt user to select one Role and proceed
                            return new ModelAndView("Login", model);
                        } else if (userObj.getUserRole().equalsIgnoreCase(
                                "User")) {
                        	
                            // Redirect user to iConnect Page

                            /*return new ModelAndView(
                                    new RedirectView("User.htm"));*/
//End AI
                        	return new ModelAndView(
                                    new RedirectView("goSearchHome.htm"));
                        } else {
                            // Redirect user to iConnect Page

                            return new ModelAndView(new RedirectView(
                                    "MainLayout.htm"));
                        }
                    }
                }
                // If result has errors,then directed to login page
                if (result.hasErrors()) {

                    return new ModelAndView("Login");
                } else {
                    if (userBeanList != null && userBeanList.size()>0) {
                        User userObj = userBeanList.get(0);
                        userObj.setUserRoleList(roleList);
                        loginValidator.MobileAndExtension(userObj);
                        
                        session.setAttribute("userLoginId", userBeanObj
                                .getLoginId());
                        session.setAttribute(userBeanObj.getLoginId(), userObj);
                        // More than one Role is mapped
                        if (roleList.size() > 1) {
                            ModelMap model = new ModelMap();
                            model.addAttribute("userRoleList", roleList);
                            model.addAttribute("roleListSize", roleList.size());
                            model.addAttribute("button", "continue");
                            // Prompt user to select one Role and proceed
                            return new ModelAndView("Login", model);
                        } else if (userObj.getUserRole().equalsIgnoreCase(
                                "User")) {
                            // Redirect user to iConnect Page

                            /*return new ModelAndView(
                                    new RedirectView("User.htm"));*/
                        	return new ModelAndView(
                                    new RedirectView("goSearchHome.htm"));
                        } else {
                            // Redirect user to iConnect Page
                            return new ModelAndView(new RedirectView(
                                    "MainLayout.htm"));
                        }
                    } else {
                        return new ModelAndView("Login");
                    }
                }
            } else {
                String empId = (String) request.getSession().getAttribute(
                        "userLoginId");
                User userObj = (User) request.getSession()
                        .getAttribute(empId);
                Map<String, Object> roleList=null;
                if (request.getParameter("userRoleId") != null) {
                    String roleId = request.getParameter("userRoleId");
                    roleList = commonCache.getRolesById(
                            Integer.parseInt(roleId)).get(0);
                    String roleName = (String) roleList.get("NAME");
                    userObj.setUserRoleId(roleId);
                    userObj.setUserRole(roleName);
                    loginDAO.insertLoggedinDetails(empId,"IN",null,request.getSession().getId());
                    /**********Auto Assignment******************/
                    boolean isfrstLogin=HELPDESK_ScoreCalculator.isFirstLogin(empId);   
                    if(isfrstLogin==true){
                    	HELPDESK_ScoreCalculator.getEngineerScoreForDay(empId);
                    }   
                    /*********End Auto Assignment***************/
                }
                // userObj.setUserRole(roleName);
                
                request.getSession().setAttribute(empId, userObj);
               
                //uncommented
                if(userObj.getUserRole().equalsIgnoreCase("User")){
                	 List<User> userBeanLst = loginDAO
                     .isExistingUser(userBeanObj.getLoginId());
               	 return new ModelAndView(new RedirectView("goSearchHome.htm"));
               }else{
               return new ModelAndView(new RedirectView("MainLayout.htm"));
               }
                //AI
              //  return new ModelAndView(new RedirectView("MainLayout.htm"));
                //End AI
            }
        }
      
    }

    @RequestMapping(value = "/RoleChanged.htm", method = RequestMethod.GET)
    public ModelAndView changeRoleforUser(@RequestParam String roleId,
            String roleName, HttpServletResponse response,
            HttpServletRequest request) throws ParseException {
        // String roleId = request.getParameter("roleId");
        // String roleName = request.getParameter("roleName");
        String empId = (String) request.getSession()
                .getAttribute("userLoginId");
        User userObj = (User) request.getSession().getAttribute(empId);
        userObj.setUserRole(roleName);
        userObj.setUserRoleId(roleId);
        request.getSession().setAttribute(empId, userObj);
        //Ai
      //  return new ModelAndView(new RedirectView("MainLayout.htm"));
        //End AI
        //uncommented
        
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        LoginDAO loginDAO = (LoginDAO) ctx
                .getBean("loginDAOImpl");
        List<User> userBeanLst = loginDAO
        .isExistingUser(userObj.getLoginId());
        List<WORKFLOW_Role> roleList = new ArrayList<WORKFLOW_Role>();
        if (userBeanLst != null && userBeanLst.size() > 0) {
            for (User user : userBeanLst) {
                WORKFLOW_Role role = new WORKFLOW_Role();
                role.setRoleName(user.getUserRole());
                role.setRoleId(Long.parseLong(user.getUserRoleId()));
                roleList.add(role);

            }
        }

        
        if(userObj.getUserRole().equalsIgnoreCase("User")){
        	 return new ModelAndView(new RedirectView("goSearchHome.htm"));
        }else{
        	return new ModelAndView(new RedirectView("MainLayout.htm"));
        }

    }
   
   
}



/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 15, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
