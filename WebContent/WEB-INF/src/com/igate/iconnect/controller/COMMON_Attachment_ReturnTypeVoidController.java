/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.igate.iconnect.BO.HELPDESK_Attachment;
import com.igate.iconnect.BO.HELPDESK_Emergency_Attachment;
import com.igate.iconnect.BO.TECHCR_Attachment;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_AttachmentDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.HELPDESK_SlaDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_ECTPopulator;
import com.igate.iconnect.util.HELPDESK_FileUpload;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class COMMON_Attachment_ReturnTypeVoidController {

	private static Logger log = Logger
	.getLogger(COMMON_Attachment_ReturnTypeVoidController.class);
	HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter(); // Added Special Character Converter by Nazeeb as part of L2 : 4200
	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}
	
    @RequestMapping(value = "attachmentlist.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getAttachmentList(@RequestParam String ticketNo,
            HttpServletResponse response, HttpServletRequest request) {

        // List<AttachmentBean> attachList = new ArrayList<AttachmentBean>();
    	List<HELPDESK_Attachment> attachListHelpdesk = new ArrayList<HELPDESK_Attachment>();
        ApplicationContext ctx = COMMON_AppContext.getCtx();

        COMMON_AttachmentDAO attachInfoDao = (COMMON_AttachmentDAO) ctx
                .getBean("attachInfoDao");

        attachListHelpdesk = attachInfoDao.getAttachmentList(ticketNo);

        JsonUtility.sendData(attachListHelpdesk, response);
    }

    /**
     * @Description: Get attachment path by ticket id.
     */
    @RequestMapping(value = "attachmentlistForTCR.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getAttachmentListForTCR(@RequestParam String issueNo,
            HttpServletResponse response, HttpServletRequest request) {

        List<TECHCR_Attachment> attachList = new ArrayList<TECHCR_Attachment>();

        ApplicationContext ctx = COMMON_AppContext.getCtx();

        COMMON_AttachmentDAO attachInfoDao = (COMMON_AttachmentDAO) ctx
                .getBean("attachInfoDao");

        attachList = attachInfoDao.getAttachmentListForTCR(issueNo);

        JsonUtility.sendData(attachList, response);
    }

    @RequestMapping(value = "/attachmentlistForTCR.htm", method = RequestMethod.POST)
    public void UploadNewTechCRAttachment(TECHCR_Attachment attachmentbean,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HashMap<String, Object> values = new HashMap<String, Object>();
        MultipartFile file = attachmentbean.getATTACHMENT();
 //       try {
            if (!file.isEmpty()) {
                String status = fileValidationForTechCR(attachmentbean
                        .getATTACHMENT());
                if (!status.equalsIgnoreCase("")) {
                    values.put("problemfile", status);
                }
            }
            if (values.size() > 0) {
                values.put("error", "Error");
                JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
                        response);
            } else {
                createtechCRticket(attachmentbean, request, response);
            }
        /*} catch (Exception e) {
            values.put("exception", "exception");
            if (e.getMessage() == null)
                values.put("message",
                        "Problem encountered while updating the data !");
            else
                values.put("message", e.getMessage());

            JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
                    response);
        }*/
    }

    public void createtechCRticket(TECHCR_Attachment techCRBean,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_AttachmentDAO attachInfoDao = (COMMON_AttachmentDAO) ctx
                .getBean("attachInfoDao");
        MultipartFile fileAttachment = techCRBean
                .getATTACHMENT();
        com.igate.iconnect.BO.COMMON_Ftp customFTP = (com.igate.iconnect.BO.COMMON_Ftp) ctx
                .getBean("ftpPropertyFile");
        HashMap<String, Object> auditlogdetails = new HashMap<String, Object>();

        if (!fileAttachment.isEmpty()) {
            Date date = new Date();
            String[] months = CustomDateFormatConstants.MONTHS;
            Calendar cal = Calendar.getInstance();
            String month = months[cal.get(Calendar.MONTH)];
            int year = cal.get(Calendar.YEAR);

            String fileNameForStorage = null;

            //try {
                MultipartFile file = techCRBean.getATTACHMENT();// file
                // assignment
                InputStream fs = file.getInputStream();

                if (!CustomDateFormatConstants.getDataType()
                        .contains(
                                CustomDateFormatConstants
                                        .getFileTypeExtension(techCRBean
                                                .getATTACHMENT()
                                                .getOriginalFilename()))) {
                    FTPClient client = new FTPClient();
                    client.connect(customFTP.getFtpIP(), 21);
                    client.login(customFTP.getFtpUser(), customFTP
                            .getFtpPassword());
                    client.setFileType(FTP.BINARY_FILE_TYPE);
                    if (!client.changeWorkingDirectory("iConnect")) {
                        client.makeDirectory("iConnect");
                    }

                    client.changeWorkingDirectory("iConnect");
                    if (!client.changeWorkingDirectory("FTP")) {
                        client.makeDirectory("FTP");
                    }

                    client.changeWorkingDirectory("FTP");

                    if (!client.changeWorkingDirectory(String.valueOf(year))) {
                        client.makeDirectory(String.valueOf(year));
                    }

                    client.changeWorkingDirectory(String.valueOf(year));

                    if (!client.changeWorkingDirectory(month)) {
                        client.makeDirectory(month);
                    }

                    client.changeWorkingDirectory(month);

                    if (!client.changeWorkingDirectory("Tech-CR")) {
                        client.makeDirectory("Tech-CR");
                    }

                    client.changeWorkingDirectory("Tech-CR");

                    fileNameForStorage = "/manageme/" + "/iConnect/" + '/'+ "/FTP/" + '/' + year
                            + '/' + '/' + month + '/' + "/Tech-CR/"
                            + CustomDateFormatConstants.YYYY_MM_DD.format(date)
                            + "_" + cal.get(Calendar.HOUR_OF_DAY) + "_"
                            + cal.get(Calendar.MINUTE) + "_"
                            + cal.get(Calendar.SECOND) + '_'
                            + file.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","");// Added replace as part of Tech CR Doc not getting open when replacing-Done as part of L2 : 4200
                    client.storeFile(fileNameForStorage, fs);								 

                    client.logout();
                    techCRBean.setATTACHMENT_PATH(fileNameForStorage);
                    techCRBean.setATTACHMENT_NAME(converter.replaceSpecialChars(file.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")));// Added Replace by Nazeeb as part of L2 : 4200
                    /*techCRBean.setATTACHMENT_PATH(fileNameForStorage);
                    techCRBean.setATTACHMENT_NAME(file.getOriginalFilename());*/
                    auditlogdetails.put("ATTACHMENT", file
                            .getOriginalFilename());
                }
           /* } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
        if (fileAttachment.isEmpty()) {
            auditlogdetails.put("ATTACHMENT", "NULL");
        }
        String loginID = request.getSession().getAttribute("userLoginId")
                .toString();
        String result = attachInfoDao.updateAttachmentFortechCRTicket(
                techCRBean, auditlogdetails, loginID);
        if (result.contains("Success")) {
            result = "{\"status\":\"success\",\"techCRId\":\"" + result
                    + "\" }";
        }
        JsonUtility.writedata(result, response);

    }

    /**
     * @Description: To download the attachment. We are getting the full path
     * from request parameter.
     */
    @RequestMapping(value = "DownloadAttachment.htm", method = RequestMethod.GET)
    public void downloadFiles(@RequestParam String json,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

		OutputStream out = null;
		byte[] b;

		try {

			JSONObject jsonobj = new JSONObject(json.replace("*amper*", "&"));

			ApplicationContext ctx = COMMON_AppContext.getCtx();
			com.igate.iconnect.BO.COMMON_Ftp customFTP = (com.igate.iconnect.BO.COMMON_Ftp) ctx
					.getBean("ftpPropertyFile");

			FTPClient client = new FTPClient();

			client.connect(customFTP.getFtpIP().trim(), 21);

			client.login(customFTP.getFtpUser().trim(), customFTP
					.getFtpPassword().trim());

			client.setFileType(FTP.BINARY_FILE_TYPE);

			String attachmentPath = jsonobj.get("ATTACHMENT_PATH").toString();
			attachmentPath = attachmentPath.replace("*hash*", "#");

			File file;

			InputStream ins = client.retrieveFileStream(attachmentPath);		
			b = IOUtils.toByteArray(ins);
			file = new File(attachmentPath);

			String attachmentName = jsonobj.get("ATTACHMENT_NAME").toString();
			attachmentName = converter.convertSpecialChars(attachmentName.replace("*hash*", "#"));	// Added Special Character Converter by Nazeeb as part of L2 : 4200		
			ins.close();
			client.logout();
			response.setContentType(new MimetypesFileTypeMap()
					.getContentType(file));
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ attachmentName + "\"");

			out = response.getOutputStream();

			out.write(b);

			out.close();

		} finally {

			if (out != null)

				out.close();
		}
	}

    @RequestMapping(value = "/attachmentlist.htm", method = RequestMethod.POST)
    public void UploadNewAttachment(HELPDESK_Attachment attachmentbean,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception { 
        String ECT = null;  
        boolean attachmentFileFlage=false; 
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
                .getBean("IHDEditDAO");
        HELPDESK_CreateUpdateDAO helpDeskDAO = (HELPDESK_CreateUpdateDAO) ctx
                .getBean("HelpDeskTicket");
        WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
                .getBean("workFlowDAOImpl");
        COMMON_AttachmentDAO attachInfoDao = (COMMON_AttachmentDAO) ctx
        .getBean("attachInfoDao");
        HELPDESK_FileUpload fileUpload=new HELPDESK_FileUpload();
        MultipartFile approvalfile = attachmentbean
                .getApprovalfile();
        MultipartFile approvalfile_Emer = attachmentbean.getIS_HEAD_APP_FILE();
        
		if ((null != attachmentbean.getStatus() && !attachmentbean.getStatus()
				.equalsIgnoreCase(""))
				|| (null != attachmentbean.getAttachmentfile1() && !attachmentbean
						.getAttachmentfile1().toString().equalsIgnoreCase(""))
				|| (null != attachmentbean.getAttachmentfile2() && !attachmentbean
						.getAttachmentfile2().toString().equalsIgnoreCase(""))
			|| (null != attachmentbean.getAttachmentfile3() && !attachmentbean
			.getAttachmentfile3().toString().equalsIgnoreCase(""))
				|| (null != approvalfile && attachmentbean.getApprovalfile()
						.getSize() > 0)
				|| (null != approvalfile_Emer && attachmentbean
						.getIS_HEAD_APP_FILE().getSize() > 0)) {
			attachmentFileFlage = true;
		}
        String fileNameApp_Emer = "";     
     // Added Special Character Converter for all Files by Nazeeb as part of L2 : 4200
        if ((null!=attachmentbean.getIS_HEAD_APP_FILE()) && (attachmentbean.getIS_HEAD_APP_FILE().getSize()>0)){
        	fileNameApp_Emer = converter.replaceSpecialChars(approvalfile_Emer.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));  
        	attachmentFileFlage=true;
        }
        MultipartFile DBScriptfile_Emer = attachmentbean.getSCRIPT_FILE();
        String fileNameDB_Emer = "";
        if ((null!=attachmentbean.getSCRIPT_FILE()) && (attachmentbean.getSCRIPT_FILE().getSize()>0)){
        	fileNameDB_Emer = converter.replaceSpecialChars(DBScriptfile_Emer.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")); 
        	attachmentFileFlage=true;
        }
        MultipartFile TestReport_Emer = attachmentbean.getTEST_REPORT_FILE();
        String fileNameTest_Emer = "";
        if ((null!=attachmentbean.getTEST_REPORT_FILE()) && (attachmentbean.getTEST_REPORT_FILE().getSize()>0)){
        	fileNameTest_Emer = converter.replaceSpecialChars(TestReport_Emer.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile UATReport_Emer = attachmentbean.getUAT_REPORT_FILE();
        String fileNameUAT_Emer = "";
        if ((null!=attachmentbean.getUAT_REPORT_FILE()) && (attachmentbean.getUAT_REPORT_FILE().getSize()>0)){
        	fileNameUAT_Emer = converter.replaceSpecialChars(UATReport_Emer.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile approvalfile_Problem_Desc = attachmentbean.getApprovaldescriptionfile();
      //modified by 720307
        String Problem_Desc = "";
        if ((null!=attachmentbean.getApprovaldescriptionfile()) && (attachmentbean.getApprovaldescriptionfile().getSize()>0)){
        	Problem_Desc = converter.replaceSpecialChars(approvalfile_Problem_Desc.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile approvalfile_Reopen_Desc = attachmentbean.getReopendescriptionfile();
        String Reopen_Desc = "";
        if ((null!=attachmentbean.getReopendescriptionfile()) && (attachmentbean.getReopendescriptionfile().getSize()>0)){
        	Reopen_Desc = converter.replaceSpecialChars(approvalfile_Reopen_Desc.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile approvalfile_Approve_Reject_Desc = attachmentbean.getApprove_Reject_Description_File();
        String Approve_Reject_Desc = "";
        if ((null!=attachmentbean.getApprove_Reject_Description_File()) && (attachmentbean.getApprove_Reject_Description_File().getSize()>0)){
        	Approve_Reject_Desc = converter.replaceSpecialChars(approvalfile_Approve_Reject_Desc.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile approvalproblemfile = attachmentbean.getApprovalproblemfile();
        String problem_file = "";
        if ((null!=attachmentbean.getApprovalproblemfile()) && (attachmentbean.getApprovalproblemfile().getSize()>0)){
        	problem_file = converter.replaceSpecialChars(approvalproblemfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
        	attachmentFileFlage=true;
        }
        MultipartFile attachmentfile1 = attachmentbean.getAttachmentfile1();
        String attachment_file1 = "";
        if ((null!=attachmentbean.getAttachmentfile1()) && (attachmentbean.getAttachmentfile1().getSize()>0)){
       	 attachment_file1 = converter.replaceSpecialChars(attachmentfile1.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
       	attachmentFileFlage=true;
        }
        MultipartFile attachmentfile2 = attachmentbean.getAttachmentfile2();
        String attachment_file2 = "";
        if ((null!=attachmentbean.getAttachmentfile2()) && (attachmentbean.getAttachmentfile2().getSize()>0)){
       	 attachment_file2 = converter.replaceSpecialChars(attachmentfile2.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
       	attachmentFileFlage=true;
        }
        MultipartFile attachmentfile3 = attachmentbean.getAttachmentfile3();
        String attachment_file3 = "";
        if ((null!=attachmentbean.getAttachmentfile3()) && (attachmentbean.getAttachmentfile3().getSize()>0)){
       	 attachment_file3 = converter.replaceSpecialChars(attachmentfile3.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
       	attachmentFileFlage=true;
        }
        HashMap<String, Object> values = new HashMap<String, Object>();
        if(attachmentFileFlage==true){
        String workflowID;
        String approvalattachmetnpath;
        int validState;
        String verifiedby = (String) request.getSession().getAttribute(
                "userLoginId");
        String ticketid = attachmentbean.getTICKET_ID();
         if(ticketid.indexOf(',')!=-1){
        	 ticketid=ticketid.substring(0, ticketid.indexOf(','));
         }
        String referenceid = "";
        if(attachmentbean.getREFERENCE_ID()!=null)
        	referenceid=attachmentbean.getREFERENCE_ID();
        String FileName = ""; 
        if (null!=attachmentbean.getApprovalfile())
        	FileName = converter.replaceSpecialChars(approvalfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]",""));
     // End of Addition of Special Character Converter by Nazeeb as part of L2 : 4200
       
        
        List<HELPDESK_Emergency_Attachment> attachmentList=new ArrayList<HELPDESK_Emergency_Attachment>();
        List<HELPDESK_Emergency_Attachment> withoutAttachmentList=new ArrayList<HELPDESK_Emergency_Attachment>();
        //if(!approvalfile_Emer.isEmpty() && !TestReport_Emer.isEmpty()){
        boolean emer_FileValidChk=true;
         
        	if((attachmentbean.getREF_ID_APP_FILE()!=null) && (!attachmentbean.getREF_ID_APP_FILE().equalsIgnoreCase("")) 
        			&& (null!=attachmentbean.getIS_HEAD_APP_FILE()) && (attachmentbean.getIS_HEAD_APP_FILE().getSize()>0)){
        		String attachtype ="";
				attachtype="Approval Mail";
				String status =fileValidationAppMail(attachmentbean.getIS_HEAD_APP_FILE());
	            if (!status.equalsIgnoreCase("")) {
	                values.put("problemfile", status);
	            }
	           if (values.size() > 0) {
	        	emer_FileValidChk=false;
	            values.put("error", "Error");
	            values.put("msg", "Approval File");
	           }else{
	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
	        			   approvalfile_Emer, ticketid, attachtype);
	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
	        	   obj.setTICKET_ID(ticketid);
	        	   obj.setREFERENCE_ID("75");
	        	   obj.setATTACHMENT_NAME(fileNameApp_Emer);
	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
	        	   attachmentList.add(obj); 
	           }
        	}if((attachmentbean.getREF_ID_SCRIPT_FILE()!=null) && (!attachmentbean.getREF_ID_SCRIPT_FILE().equalsIgnoreCase("")) 
        			&& (null!=attachmentbean.getSCRIPT_FILE())&& (attachmentbean.getSCRIPT_FILE().getSize()>0) && (emer_FileValidChk)){
        		String attachtype ="";
				attachtype="DB Scripts";
				String status =fileValidationScriptFormat(attachmentbean.getSCRIPT_FILE());
	            if (!status.equalsIgnoreCase("")) {
	                values.put("problemfile", status);
	            }
	           if (values.size() > 0) {
	        	emer_FileValidChk=false;
	            values.put("error", "Error");
	            values.put("msg", "DB Scripts File");
	           }else{
	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
	        			   DBScriptfile_Emer, ticketid, attachtype);
	        	   HELPDESK_Emergency_Attachment obj1=new HELPDESK_Emergency_Attachment();
	        	   obj1.setTICKET_ID(ticketid);
	        	   obj1.setREFERENCE_ID("76");
	        	   obj1.setATTACHMENT_NAME(fileNameDB_Emer);
	        	   obj1.setATTACHMENT_PATH(approvalattachmetnpath);
	        	   attachmentList.add(obj1); 
	           }
        	}if((attachmentbean.getREF_ID_UAT_REPORT()!=null) && (!attachmentbean.getREF_ID_UAT_REPORT().equalsIgnoreCase("")) 
        			&& (null!=attachmentbean.getUAT_REPORT_FILE()) && (attachmentbean.getUAT_REPORT_FILE().getSize()>0) && (emer_FileValidChk)){
        		String attachtype ="";
				attachtype="UAT Report";
				String status =fileValidationReportFormat(attachmentbean.getUAT_REPORT_FILE());
	            if (!status.equalsIgnoreCase("")) {
	                values.put("problemfile", status);
	            }
	           if (values.size() > 0) {
	            emer_FileValidChk=false;
	            values.put("error", "Error");
	            values.put("msg", "UAT Report File");
	           }else{
	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
	        			   UATReport_Emer, ticketid, attachtype);
	        	   HELPDESK_Emergency_Attachment obj3=new HELPDESK_Emergency_Attachment();
	        	   obj3.setTICKET_ID(ticketid);
	        	   obj3.setREFERENCE_ID("78");
	        	   obj3.setATTACHMENT_NAME(fileNameUAT_Emer);
	        	   obj3.setATTACHMENT_PATH(approvalattachmetnpath);
	        	   attachmentList.add(obj3); 
	           }
        	}if((attachmentbean.getREF_ID_TEST_REPORT()!=null) && (!attachmentbean.getREF_ID_TEST_REPORT().equalsIgnoreCase("")) 
        			&& (null!=attachmentbean.getTEST_REPORT_FILE()) && (attachmentbean.getTEST_REPORT_FILE().getSize()>0) && (emer_FileValidChk)){
        		String attachtype ="";
				attachtype="Test Report";
				String status =fileValidationReportFormat(attachmentbean.getTEST_REPORT_FILE());
	            if (!status.equalsIgnoreCase("")) {
	                values.put("problemfile", status);
	                values.put("msg", "Test Report File");
	            }
	           if (values.size() > 0) {
	            emer_FileValidChk=false;
	            values.put("error", "Error");
	           }else{
	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
	        			   TestReport_Emer, ticketid, attachtype);
	        	   HELPDESK_Emergency_Attachment obj2=new HELPDESK_Emergency_Attachment();
	        	   obj2.setTICKET_ID(ticketid);
	        	   obj2.setREFERENCE_ID("77");
	        	   obj2.setATTACHMENT_NAME(fileNameTest_Emer);
	        	   obj2.setATTACHMENT_PATH(approvalattachmetnpath);
	        	   attachmentList.add(obj2); 
	           }
        	}
        	//modified by 720307
        	if((attachmentbean.getREF_ID_APP_PROBLEM_DESC_FILE()!=null&&(!attachmentbean.getREF_ID_APP_PROBLEM_DESC_FILE().equalsIgnoreCase("")) 
         			&& (null!=attachmentbean.getApprovaldescriptionfile())) && (attachmentbean.getApprovaldescriptionfile().getSize()>0)){
            	 String attachtype = "approval"; 
            	 String status = fileValidation(attachmentbean.getApprovaldescriptionfile());
            	 if (!status.equalsIgnoreCase("")) {
    	                values.put("problemfile", status);
    	            }
            	  if (values.size() > 0) {
      	        	emer_FileValidChk=false;
      	            values.put("error", "Error");
      	            values.put("msg", "Approval File");
      	           }else{
      	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
      	        			 approvalfile_Problem_Desc, ticketid, attachtype);
      	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
      	        	   obj.setTICKET_ID(ticketid);
      	        	   obj.setREFERENCE_ID("84");
      	        	   obj.setATTACHMENT_NAME(Problem_Desc);
      	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
      	        	   attachmentList.add(obj); 
      	           }
             }
            if((attachmentbean.getREF_ID_APP_RE_OPEN_DESC_FILE()!=null&&(!attachmentbean.getREF_ID_APP_RE_OPEN_DESC_FILE().equalsIgnoreCase("")) 
           			&& (null!=attachmentbean.getReopendescriptionfile())) && (attachmentbean.getReopendescriptionfile().getSize()>0)){
              	 String attachtype = "approval"; 
              	 String status = fileValidation(attachmentbean.getReopendescriptionfile());
              	 if (!status.equalsIgnoreCase("")) {
      	                values.put("problemfile", status);
      	            }
              	  if (values.size() > 0) {
        	        	emer_FileValidChk=false;
        	            values.put("error", "Error");
        	            values.put("msg", "Approval File");
        	           }else{
        	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
        	        			approvalfile_Reopen_Desc, ticketid, attachtype);
        	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
        	        	   obj.setTICKET_ID(ticketid);
        	        	   obj.setREFERENCE_ID("82");
        	        	   obj.setATTACHMENT_NAME(Reopen_Desc);
        	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
        	        	   attachmentList.add(obj); 
        	           }
               }	
             if((attachmentbean.getREF_ID_APPROVE_REJECT_DESC_FILE()!=null&&(!attachmentbean.getREF_ID_APPROVE_REJECT_DESC_FILE().equalsIgnoreCase("")) 
          			&& (null!=attachmentbean.getApprove_Reject_Description_File())) && (attachmentbean.getApprove_Reject_Description_File().getSize()>0)){
             	 String attachtype = "approval"; 
             	 String status = fileValidation(attachmentbean.getApprove_Reject_Description_File());
             	 if (!status.equalsIgnoreCase("")) {
     	                values.put("problemfile", status);
     	            }
             	  if (values.size() > 0) {
       	        	emer_FileValidChk=false;
       	            values.put("error", "Error");
       	            values.put("msg", "Approval File");
       	           }else{
       	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
       	        			approvalfile_Approve_Reject_Desc, ticketid, attachtype);
       	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
       	        	   obj.setTICKET_ID(ticketid);
       	        	   obj.setREFERENCE_ID("83");
       	        	   obj.setATTACHMENT_NAME(Approve_Reject_Desc);
       	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
       	        	   attachmentList.add(obj); 
       	           }
              }	
             if((attachmentbean.getREF_ID_PROBLEM_FILE()!=null&&(!attachmentbean.getREF_ID_PROBLEM_FILE().equalsIgnoreCase("")) 
           			&& (null!=attachmentbean.getApprovalproblemfile())) && (attachmentbean.getApprovalproblemfile().getSize()>0)){
              	 String attachtype = "approval"; 
              	 String status = fileValidation(attachmentbean.getApprovalproblemfile());
              	 if (!status.equalsIgnoreCase("")) {
      	                values.put("problemfile", status);
      	            }
              	  if (values.size() > 0) {
        	        	emer_FileValidChk=false;
        	            values.put("error", "Error");
        	            values.put("msg", "Approval File");
        	           }else{
        	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
        	        			   approvalproblemfile, ticketid, attachtype);
        	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
        	        	   obj.setTICKET_ID(ticketid);
        	        	   obj.setREFERENCE_ID("5");
        	        	   obj.setATTACHMENT_NAME(problem_file);
        	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
        	        	   attachmentList.add(obj); 
        	           }
               }	
             
             if((attachmentbean.getREFERENCE_ATTACHMENT1()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT1().equalsIgnoreCase("")) 
         			&& (null!=attachmentbean.getAttachmentfile1())) && (attachmentbean.getAttachmentfile1().getSize()>0)){
            	 String attachtype = "approval"; 
            	 String status = fileValidation(attachmentbean.getAttachmentfile1());
            	 if (!status.equalsIgnoreCase("")) {
    	                values.put("problemfile", status);
    	            }
            	  if (values.size() > 0) {
      	        	emer_FileValidChk=false;
      	            values.put("error", "Error");
      	            values.put("msg", "Approval File");
      	           }else{
      	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
      	        			 attachmentfile1, ticketid, attachtype);
      	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
      	        	   obj.setTICKET_ID(ticketid);
      	        	   obj.setREFERENCE_ID("7");
      	        	   obj.setATTACHMENT_NAME(attachment_file1);
      	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
      	        	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox1());
      	        	   attachmentList.add(obj); 
      	           }
             } 
             if((attachmentbean.getREFERENCE_ATTACHMENT2()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT2().equalsIgnoreCase("")) 
          			&& (null!=attachmentbean.getAttachmentfile2())) && (attachmentbean.getAttachmentfile2().getSize()>0)){
             	 String attachtype = "approval"; 
             	 String status = fileValidation(attachmentbean.getAttachmentfile2());
             	 if (!status.equalsIgnoreCase("")) {
     	                values.put("problemfile", status);
     	            }
             	  if (values.size() > 0) {
       	        	emer_FileValidChk=false;
       	            values.put("error", "Error");
       	            values.put("msg", "Approval File");
       	           }else{
       	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
       	        			 attachmentfile2, ticketid, attachtype);
       	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
       	        	   obj.setTICKET_ID(ticketid);
       	        	   obj.setREFERENCE_ID("9");
       	        	   obj.setATTACHMENT_NAME(attachment_file2);
       	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
       	        	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox2());
       	        	   
       	        	   attachmentList.add(obj); 
       	           }
              } 
             if((attachmentbean.getREFERENCE_ATTACHMENT3()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT3().equalsIgnoreCase("")) 
           			&& (null!=attachmentbean.getAttachmentfile3())) && (attachmentbean.getAttachmentfile3().getSize()>0)){
              	 String attachtype = "approval"; 
              	 String status = fileValidation(attachmentbean.getAttachmentfile3());
              	 if (!status.equalsIgnoreCase("")) {
      	                values.put("problemfile", status);
      	            }
              	  if (values.size() > 0) {
        	        	emer_FileValidChk=false;
        	            values.put("error", "Error");
        	            values.put("msg", "Approval File");
        	           }else{
        	        	   approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
        	        			 attachmentfile3, ticketid, attachtype);
        	        	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
        	        	   obj.setTICKET_ID(ticketid);
        	        	   obj.setREFERENCE_ID("10");
        	        	   obj.setATTACHMENT_NAME(attachment_file3);
        	        	   obj.setATTACHMENT_PATH(approvalattachmetnpath);
        	        	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox3());
        	        	   attachmentList.add(obj); 
        	           }
               } 
             if((attachmentbean.getREFERENCE_ATTACHMENT1()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT1().equalsIgnoreCase("")) 
          			&& (null!=attachmentbean.getAttachmentfile1())) && (attachmentbean.getAttachmentfile1().getSize()<=0)){
            	 if(attachmentbean.getREFERENCE_ATTACHMENT1().equalsIgnoreCase("7")){
            	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
            	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox1());
            	   obj.setTICKET_ID(ticketid);
	        	   obj.setREFERENCE_ID("7");
            	   withoutAttachmentList.add(obj);
            	 }
             }
             if((attachmentbean.getREFERENCE_ATTACHMENT2()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT2().equalsIgnoreCase("")) 
          			&& (null!=attachmentbean.getAttachmentfile2())) && (attachmentbean.getAttachmentfile2().getSize()<=0)){
            	 if(attachmentbean.getREFERENCE_ATTACHMENT2().equalsIgnoreCase("9")){
            	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
            	   obj.setTICKET_ID(ticketid);
	        	   obj.setREFERENCE_ID("9");
            	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox2());
            	   withoutAttachmentList.add(obj);
            	 }
             }
             if((attachmentbean.getREFERENCE_ATTACHMENT3()!=null&&(!attachmentbean.getREFERENCE_ATTACHMENT3().equalsIgnoreCase("")) 
          			&& (null!=attachmentbean.getAttachmentfile3())) && (attachmentbean.getAttachmentfile3().getSize()==0)){
            	 if(attachmentbean.getREFERENCE_ATTACHMENT3().equalsIgnoreCase("10")){
            	   HELPDESK_Emergency_Attachment obj=new HELPDESK_Emergency_Attachment();
            	   obj.setTICKET_ID(ticketid);
	        	   obj.setREFERENCE_ID("10");
            	   obj.setAttachmentCkBox(attachmentbean.isAttachmentCheckbox3());
            	   withoutAttachmentList.add(obj);
            	 }
             }
            if(withoutAttachmentList.size()>0){
            	attachInfoDao.batchUpdateWithoutAttachmentList(withoutAttachmentList, verifiedby); 
             }
        	if(attachmentList.size()>0){
        		attachInfoDao.batchInsert_Emergency(attachmentList, verifiedby);
        	}
       // }
        if ((null!=attachmentbean.getApprovalfile()) && (!approvalfile.isEmpty()) && (attachmentbean.getApprovalfile().getSize()>0)) {
        	if(attachmentbean.getStatus()==null)
        	{
        		if(referenceid.equalsIgnoreCase("7")||referenceid.equalsIgnoreCase("9")||referenceid.equalsIgnoreCase("10")||referenceid.equalsIgnoreCase("5"))
        		{
        			String attachtype ="";
       				attachtype="Sample Attachment";
        			String status =fileValidation(attachmentbean.getApprovalfile());
                    if (!status.equalsIgnoreCase("")) {
                        values.put("problemfile", status);
                        values.put("msg", "Attachment");
                    }
                   if (values.size() > 0) {
                	   values.put("error", "Error");
                   }else{
            		approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
                            approvalfile, ticketid, attachtype);
        			helpDeskDAO.insertSimpleAttachment(ticketid,referenceid,approvalattachmetnpath,FileName,verifiedby);
                   }
        		}    			
        		else
        		{
        		String attachtype = "approval";
        		fileValidation(attachmentbean.getApprovalfile());
        		approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
                        approvalfile, ticketid, attachtype);
        		validState = getIHDDAO.getValidState(attachmentbean.getSUBCATEGORY_ID(), referenceid);
        		
        		if (validState == 0) {
                    workflowID = workflowimpl.getWorkflowID(attachmentbean.getFUNCTION_NAME());
                    validState = helpDeskDAO.getWorkflowState(workflowID);
                }
        		
        		if(attachmentbean.getGET_WORKFLOW_STATE()!=null&&attachmentbean.getGET_WORKFLOW_STATE().equalsIgnoreCase("YES"))
        		{
        		int workflowid = Integer.parseInt(workflowimpl.getStateId(
        				attachmentbean.getWORKFLOW_STATE(), workflowimpl
    							.getWorkflowID(attachmentbean.getFUNCTION_NAME())));
        		attachmentbean.setWORKFLOW_STATE(String.valueOf(workflowid));
        		}
        		if (validState == 2 || validState == 16 || validState == 45
                        || validState == 66 || validState == 80 || validState == 94) {
                    String createdby = (String) request.getSession().getAttribute(
                            "userLoginId");
                    User userObj = (User) request.getSession()
                            .getAttribute(createdby);
                    HELPDESK_SlaDAO sladao = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
                    COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
                            .getBean("GetMasterData");
                    List<Map<String, Object>> ticketIngo = sladao
                            .getTicketInfo(Long.parseLong(ticketid));
                    String locationID = "";
                    String locationdetID = "";
                    String functionid = "";
                    String currentstate = "";
                    int priorityID = 0;
                    String subcategoryID = "";
                    String createddate = "";

                    for (Map<String, Object> stringObj : ticketIngo) {
                        locationID = stringObj.get("location_id").toString();
                        locationdetID = stringObj.get("loc_det_id").toString();
                        functionid = stringObj.get("function_id").toString();
                        subcategoryID = stringObj.get("sub_category_id").toString();
                        createddate = stringObj.get("CREATED_DATE").toString();
                        priorityID = Integer.parseInt(stringObj.get("PRIORITY_ID")
                                .toString());
                    }

                    long opr_id = MasterDataImpl.getServiceOPRID(Long
                            .valueOf(locationID), Long.valueOf(locationdetID), Long
                            .parseLong(functionid));
                    if (opr_id != 0) {
                        ECT = HELPDESK_ECTPopulator.getECT(currentstate, priorityID,
                                createddate, Long.valueOf(subcategoryID), userObj
                                        .getOrganization(), Long
                                        .valueOf(locationID), opr_id);
                    } else {
                        throw new Exception(
                                "Operation Time is not avalible for the selected location.Please contact helpdesk team to resove this.");
                    }
                }
        			helpDeskDAO.insertAttachmentDetails(attachmentbean,
                        validState, FileName, approvalattachmetnpath, ticketid,
                        referenceid, verifiedby);
        			helpDeskDAO.updateTicketStatus(validState, ticketid, ECT);
        			helpDeskDAO.updateTicketApprovalDetails(ticketid,verifiedby);
        		}
        	}
        	else
        	{
        		 String attachtype = "approval";
                 fileValidation(attachmentbean.getApprovalfile());
                 approvalattachmetnpath = fileUpload.uploadFileForHelpdesk(
                         approvalfile, ticketid, attachtype);
                 validState = getIHDDAO.getValidState(attachmentbean.getSUBCATEGORY_ID(), referenceid);
                 if (validState == 0) {
                     workflowID = workflowimpl.getWorkflowID(attachmentbean.getFUNCTION_NAME());
                     validState = helpDeskDAO.getWorkflowState(workflowID);
                 }
                 if (validState == 2 || validState == 16 || validState == 45
                         || validState == 66 || validState == 80 || validState == 94) {
                     String createdby = (String) request.getSession().getAttribute(
                             "userLoginId");
                     User userObj = (User) request.getSession()
                             .getAttribute(createdby);
                     HELPDESK_SlaDAO sladao = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
                     COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
                             .getBean("GetMasterData");
                     List<Map<String, Object>> ticketIngo = sladao
                             .getTicketInfo(Long.parseLong(ticketid));
                     String locationID = "";
                     String locationdetID = "";
                     String functionid = "";
                     String currentstate = "";
                     int priorityID = 0;
                     String subcategoryID = "";
                     String createddate = "";

                     for (Map<String, Object> stringObj : ticketIngo) {
                         locationID = stringObj.get("location_id").toString();
                         locationdetID = stringObj.get("loc_det_id").toString();
                         functionid = stringObj.get("function_id").toString();
                         subcategoryID = stringObj.get("sub_category_id").toString();
                         createddate = stringObj.get("CREATED_DATE").toString();
                         priorityID = Integer.parseInt(stringObj.get("PRIORITY_ID")
                                 .toString());
                     }

                     long opr_id = MasterDataImpl.getServiceOPRID(Long
                             .valueOf(locationID), Long.valueOf(locationdetID), Long
                             .parseLong(functionid));
                     if (opr_id != 0) {
                         ECT = HELPDESK_ECTPopulator.getECT(currentstate, priorityID,
                                 createddate, Long.valueOf(subcategoryID), userObj
                                         .getOrganization(), Long
                                         .valueOf(locationID), opr_id);
                     } else {
                         throw new Exception(
                                 "Operation Time is not avalible for the selected location.Please contact helpdesk team to resove this.");
                     }
                 }
                 helpDeskDAO.updateAttachmentTicketDetails(attachmentbean,
                         validState, FileName, approvalattachmetnpath, ticketid,
                         referenceid, verifiedby);
                 helpDeskDAO.updateTicketStatus(validState, ticketid, ECT);
                 helpDeskDAO.updateTicketApprovalDetails(ticketid,verifiedby);
            
        	}
           
        
        } else if (attachmentbean.getStatus()!=null && attachmentbean.getStatus().equalsIgnoreCase("Verified")) {
            validState = getIHDDAO.getValidState(attachmentbean.getSUBCATEGORY_ID(), referenceid);
            if (validState == 0) {
                workflowID = workflowimpl.getWorkflowID(attachmentbean.getFUNCTION_NAME());
                validState = helpDeskDAO.getWorkflowState(workflowID);
            }
            if (validState == 2 || validState == 16 || validState == 45
                    || validState == 66 || validState == 80 || validState == 94) {
                String createdby = (String) request.getSession().getAttribute(
                        "userLoginId");
                User userObj = (User) request.getSession()
                        .getAttribute(createdby);
                HELPDESK_SlaDAO sladao = (HELPDESK_SlaDAO) ctx.getBean("slaDao");
                COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
                        .getBean("GetMasterData");
                List<Map<String, Object>> ticketIngo = sladao
                        .getTicketInfo(Long.parseLong(ticketid));
                String locationID = "";
                String locationdetID = "";
                String functionid = "";
                String currentstate = "";
                int priorityID = 0;
                String subcategoryID = "";
                String createddate = "";

                for (Map<String, Object> stringObj : ticketIngo) {
                    locationID = stringObj.get("location_id").toString();
                    locationdetID = stringObj.get("loc_det_id").toString();
                    functionid = stringObj.get("function_id").toString();
                    subcategoryID = stringObj.get("sub_category_id").toString();
                    createddate = stringObj.get("CREATED_DATE").toString();
                    priorityID = Integer.parseInt(stringObj.get("PRIORITY_ID")
                            .toString());
                }
                long opr_id = MasterDataImpl.getServiceOPRID(Long
                        .valueOf(locationID), Long.valueOf(locationdetID), Long
                        .parseLong(functionid));
                if (opr_id != 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.HOUR_OF_DAY, -5);
                    cal.add(Calendar.MINUTE, -30);
                    Date modified_date = cal.getTime();
                    SimpleDateFormat sd = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    String modified_date_string = sd.format(modified_date);
                    ECT = HELPDESK_ECTPopulator.getECT(currentstate, priorityID,
                            modified_date_string, Long.valueOf(subcategoryID),
                            userObj.getOrganization(),
                            Long.valueOf(locationID), opr_id);
                } else {
                    throw new Exception(
                            "Operation Time is not avalible for the selected location.Please contact helpdesk team to resove this.");
                }
            }
            //
            helpDeskDAO.updateAttachmentDetails(validState, attachmentbean,
                    ticketid, referenceid, verifiedby);
            // Added to insert the data in TICKET APPROVAL DETAILS TABLE.
            helpDeskDAO.updateTicketApprovalDetails(ticketid,verifiedby);
            	
            // Changes END
            helpDeskDAO.updateTicketStatus(validState, ticketid, ECT);
        } else if (attachmentbean.getStatus()!=null && attachmentbean.getStatus().equalsIgnoreCase("Rejected")) {
            validState = 18;
            helpDeskDAO.updateTicketStatus(validState, ticketid, ECT);
            helpDeskDAO.updateAttachmentDetails(validState, attachmentbean,
                    ticketid, referenceid, verifiedby);

        }
        //HashMap<String, Object> values = new HashMap<String, Object>();
        if (values.size() > 0) {
            JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
                    response);
        }else{
        	values.put("status", "success");
	        JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
	                response);
        }
        }else {
        	values.put("status", "No Change");
	        JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
	                response);
        }
    }

    public String fileValidation(MultipartFile file) {

        String result = "";
        if (file.getSize() > 5242880) {
            result = "File size can't be more than 5 MB";
        }

        String mimeType = file.getContentType();

        String data[] = {
                "image/png",
                "image/gif",
                "application/octet-stream",
                "image/jpeg",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "text/html",
                "application/pdf",
                "text/rtf",
                "text/richtext",
                "text/plain",
                "application/zip",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel", "application/x-zip-compressed",
                "image/x-png", "image/pjpeg" };
        final Set<String> filetypes = new HashSet<String>(Arrays.asList(data));

        if (!filetypes.contains(mimeType)) {
            result += "File type <b>" + mimeType + "</b> is not supported";
        }
        return result;
    }
    
    //Added for Emergency L1 changes
    
    public String fileValidationAppMail(MultipartFile file) {

        String result = "";
        if (file.getSize() > 5242880) {
            result = "File size can't be more than 5 MB";
        }

        String mimeType = file.getContentType();
        String data[] = {
        		"msg" };
        final Set<String> filetypes = new HashSet<String>(Arrays.asList(data));
        String extn=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1,file.getOriginalFilename().length());
        if (!filetypes.contains(extn)) {
            result += "File type <b>" + extn + "</b> is not supported";
        }
        return result;
    }
    
    public String fileValidationReportFormat(MultipartFile file) {

        String result = "";
        if (file.getSize() > 5242880) {
            result = "File size can't be more than 5 MB";
        }

        String mimeType = file.getContentType();
        
        String data[] = {
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel" };

        final Set<String> filetypes = new HashSet<String>(Arrays.asList(data));
        String extn=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1,file.getOriginalFilename().length());

        if (!filetypes.contains(mimeType)) {
            result += "File type <b>" + extn + "</b> is not supported";
        }
        return result;
    }
    
    public String fileValidationScriptFormat(MultipartFile file) {

        String result = "";
        if (file.getSize() > 5242880) {
            result = "File size can't be more than 5 MB";
        }

        String mimeType = file.getContentType();
        
        String data[] = {
        "text/plain"};
       
        final Set<String> filetypes = new HashSet<String>(Arrays.asList(data));
        String extn=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1,file.getOriginalFilename().length());
        
        if (!filetypes.contains(mimeType)) {
            result += "File type <b>" + extn + "</b> is not supported";
        }
        return result;
    }
    
    //Emergency L1 ending here

    public String fileValidationForTechCR(MultipartFile file) {
        String result = "";
        if (file.getSize() > 10485760) {
            result = "file size can't be more than 10 MB/n";
        }
        if (CustomDateFormatConstants.getDataType().contains(
                CustomDateFormatConstants.getFileTypeExtension(file
                        .getOriginalFilename()))) {
            result += "File type is not supported";
        }
        return result;
    }
    
    @RequestMapping(value = "attachmentMasterlist.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getAttachmentMasterList(@RequestParam String ticketNo,
            HttpServletResponse response, HttpServletRequest request) {

        // List<AttachmentBean> attachList = new ArrayList<AttachmentBean>();
    	List<HELPDESK_Attachment> attachListHelpdesk = new ArrayList<HELPDESK_Attachment>();
        ApplicationContext ctx = COMMON_AppContext.getCtx();

        COMMON_AttachmentDAO attachInfoDao = (COMMON_AttachmentDAO) ctx
                .getBean("attachInfoDao");

        attachListHelpdesk = attachInfoDao.getAttachmentMasterList(ticketNo);

        JsonUtility.sendData(attachListHelpdesk, response);
    }
}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 13,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

