/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.LoginAuthenticationDAO;
import com.igate.iconnect.util.COMMON_AppContext;



public class LoginValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    public void validate(Object obj, Errors errors) {
        User userBeanObj = (User) obj;
        
        if(userBeanObj.getLoginId() == null)
        {
        	errors.rejectValue("loginId", "error.login-not-specified", null,
            "You are not authorized to login to this application");	
        }else {   
        		String loginId = userBeanObj.getLoginId();
        		String passWord = userBeanObj.getPassword();            
            
			            ApplicationContext ctx = COMMON_AppContext.getCtx();
			            
			            // changes by mohit
			            boolean authenticationIgateOk= true;
			            
			            LoginAuthenticationDAO ldapCheck = (LoginAuthenticationDAO) ctx.getBean("ldapCheck");
			            try{
			          
			            authenticationIgateOk = ldapCheck.authenticate(userBeanObj.getSamaccountname(), passWord);
			            }
			            catch(Exception e)
			            {
			            	authenticationIgateOk=false;
			            }
			            if(!authenticationIgateOk)
			            {
			            	 errors.rejectValue("password",
				                        "error.login.authentication-fail", null,
				                        "User Name and Password doesn't match !");
			            }
        }

    }

	public void isUserExistInLdap(Object obj, Errors Error) {
		
		User userBeanObj = (User) obj;
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
         
         // changes by mohit
         String IgateUserExist="";
         LoginAuthenticationDAO ldapCheck = (LoginAuthenticationDAO) ctx.getBean("ldapCheck");
         IgateUserExist=ldapCheck.IsUserExist(userBeanObj);
         
         if(IgateUserExist==null)
         {
        	 Error.rejectValue("password","error.login.authentication-fail", null,"User Does Not Exist!");
         }else{
             userBeanObj.setLoginId(IgateUserExist.substring(0,IgateUserExist.indexOf("-")));
             userBeanObj.setSamaccountname(IgateUserExist.substring(IgateUserExist.indexOf("-")+1));

         }
		
	}

	

	public void MobileAndExtension(User userBeanObj) {
		List<String> ContactList=new ArrayList<String>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		LoginAuthenticationDAO ldapCheck = (LoginAuthenticationDAO) ctx.getBean("ldapCheck");
		ContactList=ldapCheck.MobileAndExtn(userBeanObj.getLoginId());
		if(ContactList!=null)
		{
			userBeanObj.setMobile(ContactList.get(0));
			userBeanObj.setExtension(ContactList.get(1));
		}
	}
	
	
	
	
	
	public List<String> MobileAndExtension(String empid) {
		List<String> ContactList=new ArrayList<String>();
		List<String> MobileAndExtension= new ArrayList<String>();
		MobileAndExtension.clear();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		LoginAuthenticationDAO ldapCheck = (LoginAuthenticationDAO) ctx.getBean("ldapCheck");
		ContactList=ldapCheck.MobileAndExtn(empid);
		if(ContactList!=null)
		{
			MobileAndExtension.add(ContactList.get(0));
			MobileAndExtension.add(ContactList.get(1));
		}
		return MobileAndExtension;
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
