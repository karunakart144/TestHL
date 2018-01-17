package com.igate.ai.util;

import org.springframework.context.ApplicationContext;

public class AppContext {

	private static ApplicationContext ctx;

	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static void setCtx(ApplicationContext ctx) {
		AppContext.ctx = ctx;
	}

}
