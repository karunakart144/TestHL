package com.igate.iconnect.dao;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.igate.iconnect.BO.MASTER_Create;
import com.igate.iconnect.BO.User;

public interface MASTER_CreateDAO {
	
	public String insertMasterTicket(HashMap<String, Object> insertdetails) throws IOException;
	public List<Map<String, Object>> getMasterTicketList(String childId);
	public String saveChildToMaster(String childId,String masterId,String loginId);
	public MASTER_Create getIsChildLinked(String childId);
	public String  delinkChildFromMaster(String childId,String loginID);
	public List<MASTER_Create> getReqList(String dynamicQuery,
			HttpServletRequest request);
	public String updateHelpDeskRequest(Map<String, Object> daodetails);
	public List<Map<String, Object>> getChildTicketList(String masterId);
	public  MASTER_Create getMasterDet(String masterId);
	public Map<String, Object> lockMasterTicket(JSONObject jsonobj)
	throws JSONException;
	public int unlockMasterTickets(ArrayList<Object> ArgsList);
	public User getUserDetails(String empId);
}
