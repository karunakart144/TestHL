package com.igate.iconnect.BO;

import java.io.Serializable;

public class ADMIN_CreateUserBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4297240484757857801L;
	
	private String employeeId;
	private String employeeName;
	private String emailAddress;
	private String locationId;
	private String reportingManager;
	private String timeZone;
	private String duId;
	private String mobilenumber;
	private String extNumber;
	private String gender;
	private String employeeIdToSearch;
	private String status;
	private String buttonType;
	
	
	public String getButtonType() {
		return buttonType;
	}



	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getEmployeeIdToSearch() {
		return employeeIdToSearch;
	}



	public void setEmployeeIdToSearch(String employeeIdToSearch) {
		this.employeeIdToSearch = employeeIdToSearch;
	}



	public String getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}



	public String getEmployeeName() {
		return employeeName;
	}



	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



	public String getEmailAddress() {
		return emailAddress;
	}



	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getLocationId() {
		return locationId;
	}



	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}



	public String getReportingManager() {
		return reportingManager;
	}



	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}



	public String getTimeZone() {
		return timeZone;
	}



	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}



	public String getDuId() {
		return duId;
	}



	public void setDuId(String duId) {
		this.duId = duId;
	}



	public String getMobilenumber() {
		return mobilenumber;
	}



	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}



	public String getExtNumber() {
		return extNumber;
	}



	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
