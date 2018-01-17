package com.igate.iconnect.dao;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public interface Delegate_Approvals_DAO {
	
	public Map<String, Object> getEmployeeDetails(String empId);
	public List<Map<String, Object>> getProjectsDetails(String empId);
	public List<Map<String, Object>> getRoleDetails(String empId);
	public String updateDelegateDate(JSONArray jsonArrayObj, JSONArray oldjsonArrayObj, String loginID);
	public String removeDelegateDate(String roleId,String projectId,String loginID,String delegatedTo);
	
}
