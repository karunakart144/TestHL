/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HELPDESK_Group implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 3173224422267985721L;
    public String getGROUP_ID() {
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUPID) {
		GROUP_ID = gROUPID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getMANAGER() {
		return MANAGER;
	}
	public void setMANAGER(String mANAGER) {
		MANAGER = mANAGER;
	}
	public String getEMAIL_ADDRESS() {
		return EMAIL_ADDRESS;
	}
	public void setEMAIL_ADDRESS(String eMAILADDRESS) {
		EMAIL_ADDRESS = eMAILADDRESS;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATIONID) {
		LOCATION_ID = lOCATIONID;
	}
	private String GROUP_ID;
	private String NAME;
	private String LEVEL;
	private String MANAGER;
	private String EMAIL_ADDRESS;
	private String LOCATION_ID;
private String function;
private String country;
private String newCountry;
private String city;
private int rowCountPage;
	public int getRowCountPage() {
	return rowCountPage;
}
public void setRowCountPage(int rowCountPage) {
	this.rowCountPage = rowCountPage;
}
	public String getFunction() {
	return function;
}
public void setFunction(String function) {
	this.function = function;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getNewCountry() {
	return newCountry;
}
public void setNewCountry(String newCountry) {
	this.newCountry = newCountry;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
	private String CATEGORY_ID;
	private String CATEGORY_NAME;
	private String SUB_CATEGORY_ID;
	private String SUB_CATEGORY_NAME;
	private String GROUP_NAME;
	private String DEPT_ID;
	private String DEPT_NAME;
	
	public String getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(String dEPTID) {
		DEPT_ID = dEPTID;
	}
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	public void setDEPT_NAME(String dEPTNAME) {
		DEPT_NAME = dEPTNAME;
	}
	private List<Map<String, Object>> GROUP_MAP=new ArrayList<Map<String, Object>>();
	
	public List<Map<String, Object>> getGROUP_MAP() {
		return GROUP_MAP;
	}
	public void setGROUP_MAP(List<Map<String, Object>> gROUPMAP) {
		GROUP_MAP = gROUPMAP;
	}
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORYID) {
		CATEGORY_ID = cATEGORYID;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORYNAME) {
		CATEGORY_NAME = cATEGORYNAME;
	}
	public String getSUB_CATEGORY_ID() {
		return SUB_CATEGORY_ID;
	}
	public void setSUB_CATEGORY_ID(String sUBCATEGORYID) {
		SUB_CATEGORY_ID = sUBCATEGORYID;
	}
	public String getSUB_CATEGORY_NAME() {
		return SUB_CATEGORY_NAME;
	}
	public void setSUB_CATEGORY_NAME(String sUBCATEGORYNAME) {
		SUB_CATEGORY_NAME = sUBCATEGORYNAME;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUPNAME) {
		GROUP_NAME = gROUPNAME;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
