/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
public class JsonUtility implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 926573995234713924L;
    private static Logger log=Logger.getLogger(JsonUtility.class);
    public static void sendData(Object pojoObj,HttpServletResponse response){
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.ALL;
        if (jsonConverter.canWrite(pojoObj.getClass(), jsonMimeType)) {
            try {
                jsonConverter.write(pojoObj, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
            	log.error("IOException occured in JsonUtility", m_Ioe);
                /*m_Ioe.printStackTrace();*/
                // TODO: announce this exception somehow
            } catch (HttpMessageNotWritableException p_Nwe) {
            	log.error("Http Message writable exception occured in JsonUtility", p_Nwe);
               /* p_Nwe.printStackTrace();*/
            }
        }
    }
    public static void sendData_campusCrawler_updateExcel(Object pojoObj,HttpServletResponse response){
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType =MediaType.parseMediaType("text/plain") ;
        
            try {
                jsonConverter.write(pojoObj, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
            	log.error("IOException occured in JsonUtility", m_Ioe);
              //  m_Ioe.printStackTrace();
                // TODO: announce this exception somehow
            } catch (HttpMessageNotWritableException p_Nwe) {
            	log.error("Http Message writable exception occured in JsonUtility", p_Nwe);
               // p_Nwe.printStackTrace();
            }
       
    }
    
    public static void writedata(String output,HttpServletResponse response){
    	
    	try{
    		response.getWriter().write(output);
    	}
    	catch (IOException e) {
    		e.printStackTrace();
		}
    }
    public static String converthashmapToJson(Map<String,Object> values){
    	
    	StringBuilder sb = new StringBuilder();
    		sb.append("{");
    	for(Map.Entry<String,Object> entry :values.entrySet()){
			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":\"");
			sb.append(entry.getValue());
			sb.append("\",");
    	}
    	
    	return sb.substring(0, sb.length()-1)+"}";
    }

}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:701901
Changes Made on:Sep 22, 2010
End-------Version 1.0-------
		
-----------------------------------------------------------------------------*/