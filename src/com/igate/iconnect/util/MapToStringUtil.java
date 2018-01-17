/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.util.Map;

public class MapToStringUtil {


	public static String getStringForMap(Map<String, Object> data){
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String,Object> entry :data.entrySet()){
				sb.append("('");
				sb.append(entry.getKey());
				sb.append("','");
				sb.append(String.valueOf(entry.getValue()).replace("'", "singquotessingquotes"));
				sb.append("'),");
		}
		return sb.substring(0, sb.length()-1);
	}
}

/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:714599
Changes Made on:Jun 16, 2011
End-------Version 1.0-------
            
-----------------------------------------------------------------------------*/