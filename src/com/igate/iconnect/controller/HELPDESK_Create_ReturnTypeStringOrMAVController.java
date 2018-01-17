/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class HELPDESK_Create_ReturnTypeStringOrMAVController {
	private static Logger log = Logger
	.getLogger(HELPDESK_Create_ReturnTypeStringOrMAVController.class);
	
	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";
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
