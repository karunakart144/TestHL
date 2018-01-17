/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.dao;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.igate.iconnect.BO.MailTracker;

public interface MAILTRACKER_DAO {

	public MailTracker getMailTrackerDetails(String mailid);

	public int discardMailTracker(String mailid);

	public MailTracker getMailTrackerDetailsForHR(String mailid);

	public int discardMailTrackerForHR(String mailid);

	public Map<String, Object> lockMail(JSONObject json) throws JSONException;

}
