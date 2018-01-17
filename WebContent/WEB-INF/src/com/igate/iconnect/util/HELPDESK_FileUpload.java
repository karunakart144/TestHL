/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.daoimpl.HELPDESK_CreateUpdateDAOImpl;

public class HELPDESK_FileUpload {
	private static Logger log=Logger.getLogger(HELPDESK_CreateUpdateDAOImpl.class);
	private static final String FTP_PROPERTY_FILE="ftpPropertyFile";
	public String uploadFileForHelpdesk(MultipartFile multipartFile,
	String ticketid, String attachtype) throws IOException {
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
	
		// Size validation
		if (multipartFile.getSize() > 0
				&& multipartFile.getSize() < 10485760) {
	
			fs = multipartFile.getInputStream();
		}
	
		String mimeType = multipartFile.getContentType();
	
		String data[] = CustomDateFormatConstants.getDataType().toArray(
				new String[CustomDateFormatConstants.getDataType().size()]);
		// for (int i = 0; i < data.length; i++) {
		if (!CustomDateFormatConstants.getDataType().contains(
				CustomDateFormatConstants
						.getFileTypeExtension(multipartFile
								.getOriginalFilename()))) {
	
			// if (!mimeType.equalsIgnoreCase(data[i])) {
	
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
	
			fileNameForStorage = ticketid + "_" + attachtype + "_"
					+ multipartFile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","");// Added to handle Special Char issue as part of L2 : 4200 by Nazeeb
	
			client.storeFile(fileNameForStorage, fs);
	
			fileNameForStorage = client.printWorkingDirectory() + "/"
					+ fileNameForStorage;
			client.logout();
		}
		// }
	return fileNameForStorage;
	}
}
