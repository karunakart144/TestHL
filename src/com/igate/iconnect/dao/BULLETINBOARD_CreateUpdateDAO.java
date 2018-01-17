package com.igate.iconnect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BULLETINBOARD_CreateUpdateDAO {
	public String insertBulletinBoard(HashMap<String, Object> insertdetails);
	public List<Map<String, Object>>  getBulletinMessage();
	 public int removeBulletinBoard(String bulletin_id);
	 public int removeMasterTicketFromBulletinBoard(String master_ticketId);
}
