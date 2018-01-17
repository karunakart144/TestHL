/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.igate.iconnect.dao.SESSION_ListenerDAO;

public class SessionListener implements HttpSessionListener,Serializable  {	
	private static final long serialVersionUID = 1L;
	private List<String> sessions = new ArrayList<String>();



	private static int sessionCount = 0;	
	public  SessionListener() {
    }
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session=sessionEvent.getSession();
		sessions.add(session.getId());
		session.setAttribute("counter", this);
		synchronized (this) {
			sessionCount++;
		}		
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		if(sessionEvent!=null){
			synchronized (this) {
				 sessionCount = sessionCount - 1;
			}          
			HttpSession session=sessionEvent.getSession();
			
					String empId =(String) session.getAttribute("userLoginId");
			if(empId!=null)
			{
				ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
				String filPath=bundle.getString("daoConfigfilePath");
				String realPath = sessionEvent.getSession().getServletContext().getRealPath("/"+filPath);
				Resource res = new FileSystemResource(realPath);	
				XmlBeanFactory factory = new XmlBeanFactory(res);				
				Scope requestScope=new SimpleThreadScope();
				factory.registerScope("request", requestScope);		
				
				SESSION_ListenerDAO loginDAO=(SESSION_ListenerDAO) factory.getBean("loginDAOImp");
				loginDAO.insertLoggedOutDetails(empId,"OUT","System",true,session.getId());
			}
			sessions.remove(session.getId());
	        session.setAttribute("counter", this);
		}	

}		
	public int getActiveSessionNumber() {			
        return sessionCount;
    }


}
