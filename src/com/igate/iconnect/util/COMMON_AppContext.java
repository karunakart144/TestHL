/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import org.springframework.context.ApplicationContext;

public class COMMON_AppContext {

	private static ApplicationContext ctx;

	public static ApplicationContext getCtx() {
		return ctx;
	}

	static void setCtx(ApplicationContext ctx) {
		COMMON_AppContext.ctx = ctx;
	}

}
