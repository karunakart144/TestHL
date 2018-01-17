package com.igate.ai.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.igate.ai.bo.UserComment;
import com.igate.ai.dao.AIDAOManager;
import com.igate.ai.util.RunDataImport;
import com.igate.ai.util.RunUploadFilesForIndex;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class AIController {
	
	private static final String FTP_PROPERTY_FILE="ftpPropertyFile";
	private static final String STATUS_VARIABLE = "status";
	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final ResourceBundle bundle = ResourceBundle.getBundle("smartsearch");
	//private static final String url=bundle.getString("urlPath");
	private static final String searchFolderName=bundle.getString("searchFolderName");
	private static final ResourceBundle ftpbundle = ResourceBundle.getBundle("ftp");
	private static final String ftpIp=ftpbundle.getString("ftpIp");
	private static final String ftpUserName=ftpbundle.getString("ftpUser");
	private static final String ftpPassword=ftpbundle.getString("ftpPass");
	private static final int ftpPort=Integer.parseInt(ftpbundle.getString("ftpPort"));
	
	@RequestMapping(value = "goSearchHome.htm", method = RequestMethod.GET)
	public ModelAndView goSearchHome(ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (request.getParameter("empid") != null) {
			session.setAttribute("empid", request.getParameter("empid")
					.toString());
		}
		if (request.getParameter("location") != null) {
			session.setAttribute("location", request.getParameter("location")
					.toString());
		}
		if (request.getParameter("roleSize") != null) {
			session.setAttribute("roleSize", request.getParameter("roleSize")
					.toString());
		}
		if (request.getParameter("userName") != null) {
			session.setAttribute("userName", request.getParameter("userName")
					.toString());
		}

		if (request.getParameter("backToSrch") != null) {
			request.setAttribute("backToSrch", "true");
		} else {

			request.setAttribute("backToSrch", "false");
		}
		UserComment UserComment = new UserComment();
		model.put("UserComment", UserComment);
		return new ModelAndView("searchHome");
	}

	@RequestMapping(value = "goSearchResult.htm", method = RequestMethod.GET)
	public String goSearchResult(ModelMap model,HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String id = request.getParameter("id").toString();
		if(isValidParam(request.getQueryString())){
			UserComment UserComment = new UserComment();
			model.put("UserComment", UserComment);
			return "searchHome";
		}
		session.setAttribute("srchId", id);
		if (request.getParameter("srchTxt") != null) {
			session.setAttribute("srchTxt", request.getParameter("srchTxt")
					.toString());
		}
		if (session.getAttribute("srchTxtList") != null
				&& !session.getAttribute("srchTxtList").toString().contains(
						request.getParameter("srchTxt").toString())) {
			session.setAttribute("srchTxtList", session.getAttribute(
					"srchTxtList").toString()
					+ request.getParameter("srchTxt").toString() + ",");
		} else {
			session.setAttribute("srchTxtList", request.getParameter("srchTxt")
					.toString()
					+ ",");
		}

		if (id.startsWith("TG_")) {
			session.setAttribute("typeOfLink", "userSolution");
		} else if (id.startsWith("DC_")) {
			session.setAttribute("typeOfLink", "document");
			if (request.getParameter("docLink").trim().length() > 0) {
				session.setAttribute("docLink", request.getParameter("docLink"));
			}
		} else if (id.startsWith("PD_")) {
			session.setAttribute("typeOfLink", "policyDocument");
		} else {
			session.setAttribute("typeOfLink", "ticket");
		}
		return "searchResult";
	}
	
	public boolean isValidParam(String urlParams){
		
		if(urlParams.toLowerCase().contains("alert(")){
			return true;
		}else{
		return false;
		}
	}
	

	@RequestMapping(value = "getTicketDetail.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getTicketDetails(HttpServletRequest request,
			HttpServletResponse response) {
		ApplicationContext ctx =COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String json = aiDAO.getTicketDetails(session.getAttribute("srchId")
				.toString());
		return json;
	}

	@RequestMapping(value = "saveUserSubject.htm", method = RequestMethod.GET)
	public @ResponseBody
	void saveUserSubject(HttpServletRequest request,
			HttpServletResponse response) {
		try{
		HttpSession session = request.getSession(false);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		String createdBy = "User";
		if (session.getAttribute("empid") != null) {
			createdBy = session.getAttribute("empid").toString();
		}
		aiDAO.insertSubject(request.getParameter("subject").toString(),
				createdBy);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "getTagDetail.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getTagDetail(HttpServletRequest request, HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String json = aiDAO.getTagDetails(session.getAttribute("srchId")
				.toString());
		return json;
	}

	@RequestMapping(value = "getResultDetail.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getResultDetails(HttpServletRequest request,
			HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String srchId = session.getAttribute("srchId").toString();
		String ret = "{\"docCmnt\" : " + aiDAO.getUserComment(srchId)
				+ " , \"docVote\" : " + aiDAO.getUserVote(srchId) + "}";
		return ret;
	}

	@RequestMapping(value = "saveUserComment.htm", method = RequestMethod.GET)
	public @ResponseBody
	String saveUserComment(@RequestParam String usercomment,
			HttpServletRequest request, HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String srchId = session.getAttribute("srchId").toString();
		String createdBy = "User";
		if (session.getAttribute("empid") != null) {
			createdBy =session.getAttribute("userName").toString()+"(" +session.getAttribute("empid").toString()+")";
		}
		aiDAO.saveUserComments(srchId, usercomment, createdBy);
		String ret = "{\"docCmnt\" : " + aiDAO.getUserComment(srchId)
				+ " , \"docVote\" : " + aiDAO.getUserVote(srchId) + "}";
		return ret;
	}

	@RequestMapping(value = "saveUserVote.htm", method = RequestMethod.GET)
	public @ResponseBody
	String saveUserVote(@RequestParam String vote, HttpServletRequest request,
			HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String srchId = session.getAttribute("srchId").toString();
		String createdBy = "User";
		if (session.getAttribute("empid") != null) {
			createdBy = session.getAttribute("empid").toString();
		}
		aiDAO.saveVote(srchId, vote, createdBy);
		String ret = "{\"docCmnt\" : " + aiDAO.getUserComment(srchId)
				+ " , \"docVote\" : " + aiDAO.getUserVote(srchId) + "}";
		return ret;
	}

	
	/*
	 * Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To save the number of viewers for an article
	 */
	@RequestMapping(value = "saveUsageCount.htm", method = RequestMethod.GET)
	public @ResponseBody
	void updateUsageCount(@RequestParam String docId, HttpServletRequest request,
			HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		HttpSession session = request.getSession(false);
		String createdBy = null;
		if (session.getAttribute("empid") != null) {
			createdBy = session.getAttribute("empid").toString();
		}
		aiDAO.saveUsageCount(docId, createdBy);
	
	}
	
	/*
	 *  Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To get the number of viewers for an article on load of the page
	 */
	@RequestMapping(value = "getUsageCount.htm", method = RequestMethod.GET)
	public @ResponseBody
	int getUsageCount(@RequestParam String docId, HttpServletRequest request,
			HttpServletResponse response) {
		int count=0;
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		count=aiDAO.getUsageCount(docId);
		return count; 
	}
	/*
	 *  Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To get the top ten articles viewed.
	 */
	@RequestMapping(value = "getTopTenViewedDocuments.htm", method = RequestMethod.GET)
	public void getTopTenViewedDocuments(HttpServletRequest request,
			HttpServletResponse response){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		List<Map<String,Object>>  a=aiDAO.getTopTenViewedDocuments();
		
		JsonUtility.sendData(a, response);
		
		}

	
	
	
	@RequestMapping(value = "saveUserTag.htm", method = RequestMethod.POST)
	public @ResponseBody
	String saveUserTag(UserComment userComment, HttpServletRequest request,
			HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		String createdBy = "User";
		if (request.getSession(false).getAttribute("empid") != null) {
			createdBy =request.getSession(false).getAttribute("userName").toString()+"(" +request.getSession(false).getAttribute("empid").toString()+")";
		}
		aiDAO.saveUserTag(userComment.getTagName(), userComment.getTagDesc(),
				createdBy);
		
		String schema = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		String urlApp = schema + "://" + serverName + ":" + serverPort + contextPath;
		//System.out.println("path::"+urlApp);
		//String urlApp = url;//TODO
		String serverAddr = request.getScheme() + "://"
				+ request.getServerName();
		RunDataImport runimport = new RunDataImport();
		try {
			runimport.runImport(urlApp, serverAddr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"result\" : \"1\"}";
	}

	@RequestMapping(value = "goSearchResult_Universal.htm", method = RequestMethod.GET)
	public String goSearchResult_U(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		String id = request.getParameter("id").toString();
		session.setAttribute("srchId", id);
		if (request.getParameter("srchTxt") != null) {
			session.setAttribute("srchTxt", request.getParameter("srchTxt")
					.toString());
		}
		if (id.startsWith("TG_")) {
			session.setAttribute("typeOfLink", "userSolution");
		} else if (id.startsWith("DC_")) {
			session.setAttribute("typeOfLink", "document");
			if (request.getParameter("docLink").trim().length() > 0) {
				session.setAttribute("docLink", request.getParameter("docLink"));
			}
		} else if (id.startsWith("PD_")) {
			session.setAttribute("typeOfLink", "policyDocument");
		} else {
			session.setAttribute("typeOfLink", "ticket");
		}

		return "UNIVERSAL_Search_Result";
	}

	@RequestMapping(value = "UNIVERSAL_Searchh.htm", method = RequestMethod.GET)
	public String goSearchHome_UNIVERSAL(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
		if (request.getParameter("subject") != null) {
		String subjct=request.getParameter("subject").toString();
		subjct=subjct.replace("&lt;","<");
		subjct=subjct.replace("&gt;",">");
		subjct=subjct.replace("&quot;", "%22");
		subjct=subjct.replace("&amp;","&");
			session.setAttribute("subject", subjct);
			/*session.setAttribute("subject", request.getParameter("subject")
					.toString().replace("\"", "%22"));*/
		}else{
			session.setAttribute("subject","");
		}
		if (request.getParameter("empid") != null) {
			session.setAttribute("empid", request.getParameter("empid")
					.toString());
		}
		if (request.getParameter("location") != null) {
			session.setAttribute("location", request.getParameter("location")
					.toString());
		}
		if (request.getParameter("roleSize") != null) {
			session.setAttribute("roleSize", request.getParameter("roleSize")
					.toString());
		}
		if (request.getParameter("userName") != null) {
			session.setAttribute("userName", request.getParameter("userName")
					.toString());
		}
		if (request.getParameter("ticketID") != null) {
			session.setAttribute("ticketID", request.getParameter("ticketID")
					.toString());
		}
		if (request.getParameter("menuID") != null) {
			session.setAttribute("menuID", request.getParameter("menuID")
					.toString());
		}
		
	
		return "UNIVERSAL_Search";
	}
	
	@RequestMapping(value = "DownloadAttachmentForAI.htm", method = RequestMethod.GET)
	public void downloadFiles(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream out = null;
		byte[] b = null;
		request.getSession(false).setAttribute("typeOfLink", "document");
		try {

			ApplicationContext ctx =COMMON_AppContext.getCtx();

			FTPClient client = new FTPClient();
			
            client.connect(ftpIp, ftpPort);

            client.login(ftpUserName, ftpPassword);

            client.setFileType(FTP.BINARY_FILE_TYPE);

            String fileName =searchFolderName + request.getParameter("docLink").toString();
			
            File file = null;

			InputStream ins = client.retrieveFileStream(fileName);

			b = IOUtils.toByteArray(ins);
			file = new File(fileName);

			ins.close();
			client.logout();
			response.setContentType(new MimetypesFileTypeMap()
					.getContentType(file));
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ request.getParameter("docLink").toString() + "\"");

			out = response.getOutputStream();

			out.write(b);

			out.close();

		} finally {

			if (out != null)

				out.close();
		}
	}

	@RequestMapping(value = "getFileContent.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getFileContent(UserComment userComment, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			PrintWriter pw = response.getWriter();
			pw
					.println("<iframe id=\"contentFrame\" src=\"ftp://192.168.120.165/manageme/Asset/Search Docs/Live Meeting configuration.htm\" width=\"100%\" height=\"100%\"  scrolling=\"no\" frameborder=\"0\" onload=\"\"></iframe>");
			pw.println("User COmment 123");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "runUploadFiles.htm", method = RequestMethod.GET)
	public void runUploadFiles(UserComment userComment,
			HttpServletRequest request, HttpServletResponse response) {
		RunUploadFilesForIndex index = new RunUploadFilesForIndex();
		try {
			index.runUploadFiles(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/downloadFileToUpload.htm", method = RequestMethod.GET)
	public void downloadFileToAttach(@RequestParam String requestID,@RequestParam String docLink,
			HttpServletRequest request, HttpServletResponse response) {

		OutputStream out = null;
		byte[] b = null;
		String attachtype="5";
			ApplicationContext ctx =COMMON_AppContext.getCtx();
			AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
			.getBean(COMMON_CACHE_IMPL);
			String loginID = (String) request.getSession().getAttribute(
			"userLoginId");
				
				 String fileName =searchFolderName + docLink;
				byte[] attachdata = getFileContentAsByte(fileName);
				    
				    String fileNameForStorage=uploadFileForHelpdesk(
				    		attachdata, requestID, attachtype,docLink);
				    
				    List<Map<String,Object>> smartSearchAttchmentIds=MasterDataImpl.getSmartSearchAttachmenetIds();
				    
				    String result=aiDAO.insertFileDetails(fileNameForStorage,requestID,attachdata,docLink,attachtype,loginID,smartSearchAttchmentIds);
				    
				    JSONObject jsonforSuccess = new JSONObject();
					try {
						jsonforSuccess.put(STATUS_VARIABLE, result);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				com.igate.iconnect.util.JsonUtility.writedata(jsonforSuccess.toString(), response);

			}
	
	public String  uploadFileForHelpdesk(byte[] attachdata,String requestID,String attachtype, String fileName){

		String fileNameForStorage = null;
		InputStream fs = null;
		Date date = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		String[] months = CustomDateFormatConstants.MONTHS;
		Calendar cal = Calendar.getInstance();
		String month = months[cal.get(Calendar.MONTH)];
	
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		com.igate.iconnect.BO.COMMON_Ftp customFTP = (com.igate.iconnect.BO.COMMON_Ftp) ctx
				.getBean(FTP_PROPERTY_FILE);		
	
	
		String data[] = CustomDateFormatConstants.getDataType().toArray(
				new String[CustomDateFormatConstants.getDataType().size()]);
		if (!CustomDateFormatConstants.getDataType().contains(
				CustomDateFormatConstants
						.getFileTypeExtension(fileName))) {
			fs = new ByteArrayInputStream(attachdata); 
	
	
			try {
				FTPClient client = new FTPClient();
				client.connect(customFTP.getFtpIP().trim(), 21);
				client.login(customFTP.getFtpUser().trim(), customFTP
						.getFtpPassword().trim());

				int reply = client.getReplyCode();
				// Validate FTP server connection
				if (!FTPReply.isPositiveCompletion(reply)) {
					fs.close();
					client.logout();
					client.disconnect();
					System.err.println("FTP server refused connection.");
				}

				client.setFileType(FTP.BINARY_FILE_TYPE);
				if (!client.changeWorkingDirectory("iConnect")) {
					client.makeDirectory("iConnect");
				}

				client.changeWorkingDirectory("iConnect");
				if (!client.changeWorkingDirectory("FTP")) {
					client.makeDirectory("FTP");
				}

				client.changeWorkingDirectory("FTP");

				if (!client.changeWorkingDirectory(simpleDateformat
						.format(date))) {
					client.makeDirectory(simpleDateformat.format(date));
				}

				client.changeWorkingDirectory(simpleDateformat.format(date));

				if (!client.changeWorkingDirectory(month)) {
					client.makeDirectory(month);
				}

				client.changeWorkingDirectory(month);

				if (!client.changeWorkingDirectory("HD")) {
					client.makeDirectory("HD");
				}

				client.changeWorkingDirectory("HD");

				fileNameForStorage = requestID + "_" + attachtype + "_"
						+ fileName.replace("'", "").replace("+", "");// Added to handle Special Char issue as part of L2 : 4200 by Nazeeb

				client.storeFile(fileNameForStorage, fs);

				fileNameForStorage = client.printWorkingDirectory() + "/"
						+ fileNameForStorage;
				client.logout();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return fileNameForStorage;
	}

		
	
			
			@SuppressWarnings("finally")
			public byte[] getFileContentAsByte(String filepath) {
				FTPClient client = new FTPClient();
				byte[] data = null;
				try {
					 client.connect(ftpIp, ftpPort);

			            client.login(ftpUserName, ftpPassword);

		            client.setFileType(FTP.BINARY_FILE_TYPE);

					InputStream ins = client.retrieveFileStream(filepath);

					data = IOUtils.toByteArray(ins);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (client.isConnected()) {
							client.logout();
							client.disconnect();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					return data;
				}
			}

			
	

	@RequestMapping(value = "runSynonymIndex.htm", method = RequestMethod.GET)
	public void runSynonymIndex(UserComment userComment,
			HttpServletRequest request, HttpServletResponse response) {
		RunUploadFilesForIndex index = new RunUploadFilesForIndex();
		try {
			index.runSynonymIndex(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "DownloadAttachmentForSU.htm", method = RequestMethod.GET)
	public void downloadFilesSU(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream out = null;
		byte[] b = null;
		request.getSession(false).setAttribute("typeOfLink", "document");
		try {

			ApplicationContext ctx =COMMON_AppContext.getCtx();

			FTPClient client = new FTPClient();
			
			 client.connect(ftpIp, ftpPort);

	            client.login(ftpUserName, ftpPassword);

            client.setFileType(FTP.BINARY_FILE_TYPE);

            String fileName =searchFolderName+ request.getParameter("link").toString();
			
            File file = null;

			InputStream ins = client.retrieveFileStream(fileName);

			b = IOUtils.toByteArray(ins);
			file = new File(fileName);

			ins.close();
			client.logout();
			/*response.setContentType(new MimetypesFileTypeMap()
					.getContentType(file));
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ request.getParameter("docLink").toString() + "\"");*/
			
			response.setContentType("application/pdf");

			out = response.getOutputStream();

			out.write(b);

			out.close();

		} finally {

			if (out != null)

				out.close();
		}
	}
	
	@RequestMapping(value = "/inactivateAttachmentFromTicket.htm", method = RequestMethod.GET)
	public void removeAttachmentFromTicket(@RequestParam String requestID,@RequestParam String docLink,
			HttpServletRequest request, HttpServletResponse response) {
		ApplicationContext ctx =COMMON_AppContext.getCtx();
		AIDAOManager aiDAO = (AIDAOManager) ctx.getBean("aiDAOImpl");
		String loginID = (String) request.getSession().getAttribute(
		"userLoginId");
		String result=aiDAO.removeAttachmentfromTicket(requestID,docLink,loginID);
		 JSONObject jsonforSuccess = new JSONObject();
					try {
						jsonforSuccess.put(STATUS_VARIABLE, result);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				com.igate.iconnect.util.JsonUtility.writedata(jsonforSuccess.toString(), response);
	}

}
