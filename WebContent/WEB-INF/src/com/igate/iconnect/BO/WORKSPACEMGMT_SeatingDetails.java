/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.util.Map;

public class WORKSPACEMGMT_SeatingDetails {

	
	private String data;
	private String columnid;
	

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
    //TODO: Why is this empty?
	public void setData(Map<String, Map<String, String>> parentmap) {
		
	}



	/**
	 * @param columnid the columnid to set
	 */
	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	/**
	 * @return the columnid
	 */
	public String getColumnid() {
		return columnid;
	}
	
	private String employeeid;
	private String employeename;
	/**
	 * @param employeedname the employeedname to set
	 */
	public void setEmployeename(String employeedname) {
		this.employeename = employeedname;
	}
	/**
	 * @return the employeedname
	 */
	public String getEmployeename() {
		return employeename;
	}
	/**
	 * @param employeeid the employeeid to set
	 */
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	/**
	 * @return the employeeid
	 */
	public String getEmployeeid() {
		return employeeid;
	}
	
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param locationid the locationid to set
	 */
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	/**
	 * @return the locationid
	 */
	public String getLocationid() {
		return locationid;
	}

	/**
	 * @param versionid the versionid to set
	 */
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	/**
	 * @return the versionid
	 */
	public String getVersionid() {
		return versionid;
	}

	/**
	 * @param locationname the locationname to set
	 */
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	/**
	 * @return the locationname
	 */
	public String getLocationname() {
		return locationname;
	}

	/**
	 * @param auditid the auditid to set
	 */
	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	/**
	 * @return the auditid
	 */
	public String getAuditid() {
		return auditid;
	}

	/**
	 * @param modifiedid the modifiedid to set
	 */
	public void setModifiedid(String modifiedid) {
		this.modifiedid = modifiedid;
	}

	/**
	 * @return the modifiedid
	 */
	public String getModifiedid() {
		return modifiedid;
	}

	/**
	 * @param modifieddate the modifieddate to set
	 */
	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}

	/**
	 * @return the modifieddate
	 */
	public String getModifieddate() {
		return modifieddate;
	}

	/**
	 * @param oldvalue the oldvalue to set
	 */
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}

	/**
	 * @return the oldvalue
	 */
	public String getOldvalue() {
		return oldvalue;
	}

	/**
	 * @param newvalue the newvalue to set
	 */
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

	/**
	 * @return the newvalue
	 */
	public String getNewvalue() {
		return newvalue;
	}

	/**
	 * @param columnname the columnname to set
	 */
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	/**
	 * @return the columnname
	 */
	public String getColumnname() {
		return columnname;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param locationpath the locationpath to set
	 */
	public void setLocationpath(String locationpath) {
		this.locationpath = locationpath;
	}

	/**
	 * @return the locationpath
	 */
	public String getLocationpath() {
		return locationpath;
	}

	private String remarks;
	private String locationid;
	private String versionid;
	private String locationname;
	private String auditid;
	private String modifiedid;
	private String modifieddate;
	private String oldvalue;
	private String newvalue;
	private String columnname;
	private String role;
	private String locationpath;
	 
	

}
