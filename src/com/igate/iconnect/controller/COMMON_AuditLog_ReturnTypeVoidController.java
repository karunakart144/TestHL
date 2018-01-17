/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.igate.iconnect.BO.COMMON_AuditLog;
import com.igate.iconnect.BO.COMMON_AuditLogDetail;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.COMMON_AuditLogDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class COMMON_AuditLog_ReturnTypeVoidController {
	
	private static Logger log = Logger.getLogger(COMMON_AuditLog_ReturnTypeVoidController.class);

	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}

    /**
     * Description: Get ticket no from js by JSON object. Here we are injecting
     * the bean into Application Context. One validation is to check the no of
     * records. if it is empty,No need to hit the database. Passing the values
     * back to the js by JsonUtility.
     * 
     * @RequestMapping:To map with the jsp
     */

    @RequestMapping(value = "auditloglist.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getAuditLogInfoForm(@RequestParam String ticketNo, String menuID,
            HttpServletResponse response, HttpServletRequest request) {

        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_AuditLogDAO jdbcAuditInfoDao = (COMMON_AuditLogDAO) ctx
                .getBean("auditlogInfoDao");
        String userLoginId = (String) request.getSession().getAttribute(
                "userLoginId");
        User userBean = (User) request.getSession().getAttribute(
                userLoginId);
        // Defaulting userTime Zone to IST
        int userTimeZone = 67;
        if (userBean.getTimeZoneId() != null)
            userTimeZone = Integer.parseInt(userBean.getTimeZoneId());
        List<COMMON_AuditLog> auditLogList = jdbcAuditInfoDao.getAuditLogInfo(
                ticketNo, menuID,userTimeZone);
        JsonUtility.sendData(auditLogList, response);

    }

    /**
     * Description: Here we are injecting the bean into Application Context. We
     * are parsing the XML format by documentbuilder. We are extracting node
     * values by document class. One validation is to check the no of records.
     * if it is empty,No need to hit the database. Passing the values back to
     * the js by JsonUtility.
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * 
     * @RequestMapping:To map with the jsp
     */
    @RequestMapping(value = "auditloglistdetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getAuditLogDetailInfoForm(@RequestParam String historyID,
            String menuID, HttpServletResponse response,
            HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException {

        List<COMMON_AuditLog> auditLogList = new ArrayList<COMMON_AuditLog>();

        COMMON_AuditLogDetail auditLogDetail = new COMMON_AuditLogDetail();
        
        HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter();

        List<String> auditLogField = new ArrayList<String>();

        List<String> auditLogOldVal = new ArrayList<String>();

        List<String> auditLogNewVal = new ArrayList<String>();

        Map<String, COMMON_AuditLogDetail> tosend = new HashMap<String, COMMON_AuditLogDetail>();

        ApplicationContext ctx = COMMON_AppContext.getCtx();

        COMMON_AuditLogDAO jdbcAuditInfoDao = (COMMON_AuditLogDAO) ctx
                .getBean("auditlogInfoDao");

        COMMON_CacheDAO masterdataimpl = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        Map<String, String> uiDBFieldMap = masterdataimpl
                .getUIAndDBFieldForAuditLog(menuID);
        auditLogList = jdbcAuditInfoDao.getAuditLogDetailInfo(Integer
                .parseInt(historyID));

        String auditDetails = auditLogList.get(0).getDataChange();

        // try {
        DocumentBuilder db = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        InputSource is = new InputSource();
        
        is.setCharacterStream(new StringReader(auditDetails));

        Document doc = db.parse(is);

        NodeList nodes1 = doc.getDocumentElement().getChildNodes();

        for (int j = 0; j < nodes1.getLength(); j++) {
            Element nodeName = (Element) nodes1.item(j);
            String str = nodeName.getNodeName();

            auditLogField.add(uiDBFieldMap.get(str));

            NodeList nodes = doc.getElementsByTagName(str);
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                NodeList name = element.getElementsByTagName("old_value");
                Element line = (Element) name.item(0);
                if (str.equalsIgnoreCase("DESCRIPTION")
                        || str.equalsIgnoreCase("ADDITIONAL_INFO")
                        || str.equalsIgnoreCase("RESOLUTION_COMMENTS")
                        || str.equalsIgnoreCase("PRIVATE_NOTES")
                        || str.equalsIgnoreCase("OUT_OF_SLA_REASON")
                        || str.equalsIgnoreCase("OUT_OF_SLA_COMMENTS")
                        || str.equalsIgnoreCase("ONHOLD_COMMENTS")
                        || str.equalsIgnoreCase("REOPEN_COMMENTS")
                        || str.equalsIgnoreCase("SUBJECT")
                        || str.equalsIgnoreCase("ENGINEER_COMMENTS")
                        || str.equalsIgnoreCase("SERVER_DEVICE")
                        || str.equalsIgnoreCase("VENDOR_LOCATION_ID")
                        || str.equalsIgnoreCase("VENDOR_NAME")
                        || str.equalsIgnoreCase("IP_ADDRESS")
                        || str.equalsIgnoreCase("ENGINEER_NAME")
                        || str.equalsIgnoreCase("PREVENTIVE_ACTION")
                        || str.equalsIgnoreCase("SOLUTION")
                        || str.equalsIgnoreCase("CONTIGENCY")
                        || str.equalsIgnoreCase("MITIGATION")
                        || str.equalsIgnoreCase("RISK_DETAILS")
                        || str.equalsIgnoreCase("ROLLBACK_COMMENTS")
                        || str.equalsIgnoreCase("FEEDBACK_COMMENTS")) {
                    auditLogOldVal
                            .add(converter
                                    .convertSpecialChars(getCharacterDataFromElement(line)));

                } else {
                    auditLogOldVal.add(getCharacterDataFromElement(line));
                }

                NodeList title = element.getElementsByTagName("new_value");
                line = (Element) title.item(0);
                if (str.equalsIgnoreCase("DESCRIPTION")
                        || str.equalsIgnoreCase("ADDITIONAL_INFO")
                        || str.equalsIgnoreCase("RESOLUTION_COMMENTS")
                        || str.equalsIgnoreCase("PRIVATE_NOTES")
                        || str.equalsIgnoreCase("OUT_OF_SLA_REASON")
                        || str.equalsIgnoreCase("OUT_OF_SLA_COMMENTS")
                        || str.equalsIgnoreCase("ONHOLD_COMMENTS")
                        || str.equalsIgnoreCase("REOPEN_COMMENTS")
                        || str.equalsIgnoreCase("SUBJECT")
                        || str.equalsIgnoreCase("ENGINEER_COMMENTS")
                        || str.equalsIgnoreCase("SERVER_DEVICE")
                        || str.equalsIgnoreCase("VENDOR_LOCATION_ID")
                        || str.equalsIgnoreCase("VENDOR_NAME")
                        || str.equalsIgnoreCase("IP_ADDRESS")
                        || str.equalsIgnoreCase("ENGINEER_NAME")
                        || str.equalsIgnoreCase("PREVENTIVE_ACTION")
                        || str.equalsIgnoreCase("SOLUTION")
                        || str.equalsIgnoreCase("CONTIGENCY")
                        || str.equalsIgnoreCase("MITIGATION")
                        || str.equalsIgnoreCase("RISK_DETAILS")
                        || str.equalsIgnoreCase("ROLLBACK_COMMENTS")
                        || str.equalsIgnoreCase("FEEDBACK_COMMENTS")) {
                    auditLogNewVal
                            .add(converter
                                    .convertSpecialChars(getCharacterDataFromElement(line)));
                } else {
                    auditLogNewVal.add(getCharacterDataFromElement(line));
                }

            }
        }
        auditLogDetail.setField(auditLogField);
        auditLogDetail.setNewValue(auditLogNewVal);
        auditLogDetail.setOldValue(auditLogOldVal);
        tosend.put("success", auditLogDetail);
        /*
         * } catch (Exception e) { if (e.getMessage() == null) auditLogDetail
         * .setErrormessage("Problem Encountered while retrieving the data !");
         * else auditLogDetail.setErrormessage(e.getMessage());
         * tosend.put("error", auditLogDetail); }
         */
        JsonUtility.sendData(tosend, response);
    }

    /**
     * Description: We are extracting values of the node.
     */
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 10,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

