package com.igate.ai.bo;

import org.springframework.beans.factory.annotation.Value;

 
public class FtpFileProperties 
{


	@Value("#{ftpProps[ftpIp]}")
	private String ftpIp; 
	
	@Value("#{ftpProps[ftpUser]}")
	private String ftpUser; 
	
	@Value("#{ftpProps[ftpPass]}")
	private String ftpPass; 
	
	@Value("#{ftpProps[ftpLocation]}")
	private String ftpLocation;

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPass() {
		return ftpPass;
	}

	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}

	public String getFtpLocation() {
		return ftpLocation;
	}

	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	} 

	

}
