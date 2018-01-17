/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ValidSessionInterceptor extends HandlerInterceptorAdapter {
    /*
     * private String redirect;
     * 
     * public void setRedirect(String redirect) { this.redirect = redirect; }
     */
	private static final Logger logger = Logger.getLogger(ValidSessionInterceptor.class);
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().substring(0);       
        FilterChain chain = null;
        if (url.contains("Login.htm") || url.contains("iSpaceHome.htm")
        		|| url.contains("UnifiedServiceRaiseTicket.htm") || url.contains("UnifiedServiceViewTicket.htm")) {
            return true;
        }
        String empId = (String) request.getSession().getAttribute("userLoginId");
        if (empId == null) {
            response.sendRedirect("Login.htm");
            return false;
        } else{
        	response.setHeader("Access-Control-Allow-Origin","*");
        return true;
        }
    }
    
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	throws Exception {
    	String sessionid = request.getSession().getId();
    	// be careful overwriting: JSESSIONID may have been set with other flags
    	//response.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; HttpOnly; secure;  Path="+request.getContextPath()+";");
	response.setHeader("Access-Control-Allow-Origin", "SAMEORIGIN");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0);
		response.setHeader("X-UA-Compatible", "IE=edge,chrome=1");
	
	

}

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Nov 18, 2010
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
