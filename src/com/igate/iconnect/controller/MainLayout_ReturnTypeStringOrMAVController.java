/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.COMMON_Menu;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;
import com.igate.iconnect.util.SessionListener;

@Controller
public class MainLayout_ReturnTypeStringOrMAVController {
	private static Logger log = Logger.getLogger(MainLayout_ReturnTypeStringOrMAVController.class);
	
/*	static ResourceBundle bundle = ResourceBundle.getBundle("gamification");
	private static final String LevelB = bundle.getString("gamification.LevelB");*/

	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";

	}
	
    @RequestMapping(value = "/MainLayout.htm", method = RequestMethod.GET)
    public ModelAndView showiConnectForm(ModelMap model,
    		HttpServletRequest request) {
    	String empId = (String) request.getSession()
    	.getAttribute("userLoginId");
    	User userObj = (User) request.getSession().getAttribute(empId);
    	String roleId = userObj.getUserRoleId();
    	ApplicationContext ctx = COMMON_AppContext.getCtx();
    	COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
    	.getBean("GetMasterData");
    	//GamificationWebService service = (GamificationWebService) ctx.getBean("gamificationService");
    	List<COMMON_Menu> parentMenuList = MasterDataImpl.getParentMenuList(roleId);

    	request.getSession().setAttribute("parentMenu", parentMenuList);

    	/********** L2 # 1175 For Gamification Start Added by Anjana ********/
    	
    	
   /* 	String gameFlag = "false";
    	String msg = "";
    	String dataMessage = "0";
    	String rank = "0";
    	try {
    		List<String> levelBList = new ArrayList<String>(Arrays.asList(LevelB.split(",")));
    		// Calling the Gamification getProfile Service to Get the score and Rank
    		log.info("Calling getProfile from MainLayout_ReturnTypeStringOrMAVController");
    		msg = service.getProfile(empId);
    		JSONObject jobj = new JSONObject(msg);
    		dataMessage = (String) jobj.getString("points");
    		rank = (String) jobj.getString("position");
    		request.getSession().setAttribute("gamificationScore", dataMessage);
    		request.getSession().setAttribute("rank", rank);
			if(roleId.equalsIgnoreCase("1")){
				List<Map<String, Object>> groupMembersID = MasterDataImpl.getIHDGroup(empId);
				for (Map<String, Object> ctg : groupMembersID) {
					String groupId = ctg.get("GROUP_ID").toString();
					if(levelBList.contains(groupId)){
						gameFlag = "true";
					}
				}

			}
			request.getSession().setAttribute("gameFlag",gameFlag);
		} catch (Exception e) {
			request.getSession().setAttribute("gamificationScore", 0);
			request.getSession().setAttribute("rank", 0);
			request.getSession().setAttribute("gameFlag",gameFlag);
			log.error("Error Encountered while MainLayout_ReturnTypeStringOrMAVController- Gamification" + e);
		}*/
    	/********** For Gamification End ********/
    	return new ModelAndView("MainLayout");
    }
    @RequestMapping(value = "/User.htm", method = RequestMethod.GET)
    public ModelAndView showUserForm(ModelMap model,
            HttpServletRequest request) {
    	//request.setAttribute("userLoginId", request.getParameter("empid"));
        return new ModelAndView("User");
    }
    @RequestMapping(value = "/UserHome1.htm", method = RequestMethod.GET)
    public void userHomePage(@RequestParam String modname,HttpServletRequest request,HttpSession session) {
    	session.setAttribute("modname1", modname);
    }
  
    
    @RequestMapping(value = "/UserHome.htm", method = RequestMethod.GET)
    public ModelAndView showUserHomePage(ModelMap model,
            HttpServletRequest request,HttpSession session) {
    	
    	String modname=(String) session.getAttribute("modname1");
    	if(modname.equalsIgnoreCase("RaiseNewTicket")){    		
    		String subject=request.getParameter("subjectText");
    		if(subject==null){
    			subject="";
    		}else{
    			if(subject.toString().trim().length()==0)
    			subject="";
    		}
    		try {
    			if(subject.toString().trim().length()>0)
				subject=URLEncoder.encode(subject.toString(), "UTF-8");	
	
			} catch (UnsupportedEncodingException e) {	
				log.error("URL Encoding Error :"+e);
			}    		
    		model.put("url", "HELPDESK_Create.htm?subjectText="+subject);
    	}else if(modname.equalsIgnoreCase("ExistingTickets")){
    		model.put("url", "UNIVERSAL_ListPage.htm?menuId=38&parentMenuId=1&menuName=Existing Tickets&parentMenuName=HelpDesk");
    	}else if(modname.equalsIgnoreCase("WaitingForApproval")){
    		model.put("url", "UNIVERSAL_ListPage.htm?menuId=51&parentMenuId=1&menuName=Waiting For My Approval&parentMenuName=HelpDesk");
    	}else if(modname.equalsIgnoreCase("ApprovedByMe")){
    		model.put("url", "UNIVERSAL_ListPage.htm?menuId=94&parentMenuId=1&menuName=Approved by me&parentMenuName=HelpDesk");
    	}else if(modname.equalsIgnoreCase("RejectedByMe")){
    		model.put("url", "UNIVERSAL_ListPage.htm?menuId=95&parentMenuId=1&menuName=Rejected by me&parentMenuName=HelpDesk");
    	}else if(modname.equalsIgnoreCase("ContactInfo")){
    		model.put("url", "CONTACTUS_HelpDeskAndEscalation.htm");
    	}else if(modname.equalsIgnoreCase("CreateMasterTicket")){
    		model.put("url", "MASTER_Create.htm");
    	}else if(modname.equalsIgnoreCase("ViewMasterTicket")){
    		model.put("url", "UNIVERSAL_ListPage.htm?menuId=114&parentMenuId=114&menuName=View Master Ticket&parentMenuName=Helpdesk");
    	}
    	
        return new ModelAndView("UserHome",model);
    }
    
    @RequestMapping(value = "/CONTACTUS_HelpDeskAndEscalation.htm", method = RequestMethod.GET)
    public ModelAndView showContactPage(ModelMap model,
            HttpServletRequest request) {
    	ApplicationContext ctx = COMMON_AppContext.getCtx();
    	COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
    	.getBean("GetMasterData");
    	List<Map<String,Object>> functionList = MasterDataImpl.getFunctionForEscaltionMatrix();
    	List<Map<String,Object>> functiondataList = MasterDataImpl.getFunctionDetailsForEscaltionMatrix();
    	model.put("functionList", functionList);
    	model.put("functiondataList", functiondataList);
        return new ModelAndView("CONTACTUS_HelpDeskAndEscalation");
    }
    @RequestMapping(value = "/numberOfUsers.htm", method = RequestMethod.GET)
    public @ResponseBody  void  getNumberOfUser(HttpServletResponse response,
           HttpServletRequest request) throws ParseException {
   	
    	SessionListener counter = (SessionListener) request.getSession().getAttribute("counter");
   	 JsonUtility.sendData(counter.getActiveSessionNumber(), response);
   }
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 20, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
