/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.naming.directory.Attributes;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.ADMIN_CreateUserBO;
import com.igate.iconnect.BO.ADMIN_ModifyLocation;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.ADMIN_SettingsSQLQueryConstants;
import com.igate.iconnect.constants.COMMON_CacheSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.constants.LoginSQLQueryConstants;
import com.igate.iconnect.dao.ADMIN_SettingsDAO;

@Transactional(rollbackFor = Exception.class)
public class ADMIN_SettingsDAOImpl implements ADMIN_SettingsDAO {
	private static Logger log = Logger.getLogger(ADMIN_SettingsDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	private final static String TICKET_ID = "TICKET_ID";
	private final static String STATUS = "status";
	private final static String TICKET_IDE = "TicketID";
    private LdapTemplate ldapTemplate;
    private static final String SAME_ACCOUNT_NAME="samaccountname";
    private static final String EMPLOYEE_ID="employeeid";

    public void setldapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Map<String, Object> verifyAssignedTicket(String groupID,
			String groupMemID, String loginId) {
		List<Map<String, Object>> asssignedDetails = new ArrayList<Map<String, Object>>();
		StringBuilder appendTicketNo = new StringBuilder();
		Map<String, Object> assignedStatus = new HashMap<String, Object>();
		asssignedDetails = this.jdbcTemplate
				.queryForList(
						ADMIN_SettingsSQLQueryConstants.IC_IHD_TICKET_DETAILS_VERIFY_GROUP,
						groupID, groupMemID);
		if (asssignedDetails.isEmpty()) {
			this.jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_UPDATE_MEMBER_INACTIVE,
							"0", loginId, CustomDateFormatConstants
									.creationDateGMTFormat(), groupMemID,
							groupID);
			assignedStatus.put(STATUS, "success");
		} else {
			for (Map<String, Object> assignedTicketListInfo : asssignedDetails) {
				appendTicketNo.append(assignedTicketListInfo.get(TICKET_ID)
						.toString());
				appendTicketNo.append(",");
			}
			appendTicketNo.deleteCharAt(appendTicketNo.length() - 1);
			assignedStatus.put(TICKET_IDE, appendTicketNo);
			assignedStatus.put(STATUS, "error");
		}
		return assignedStatus;
	}

	public int addGroupMemberDetails(String roleId, String groupID,
			String groupMemID, String loginId, String accessLevel,
			String assignmentReqd) {
		int addGroupMem = 0;
		String managerRoleIdListStr = "";
		// String assgnmentReqd="1";
		/*
		 * 1 Level 0 Executive 3 IS Executive 13 Finance Executive 15 Admin
		 * Executive 17 HR Executive 25 CHCS Executive 29 iLearn Executive
		 */
		int toInsrtRoleId = Integer.parseInt(accessLevel);
		if (toInsrtRoleId == 0) {
			toInsrtRoleId = 2;
		}
		List<Map<String, Object>> checkGroupMemIsActive = new ArrayList<Map<String, Object>>();
		checkGroupMemIsActive = this.jdbcTemplate.queryForList(
				ADMIN_SettingsSQLQueryConstants.IC_IHD_CHECK_GROUP_MEMBER,
				groupMemID, groupID, "1");
		if (checkGroupMemIsActive.isEmpty()) {
			addGroupMem = this.jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_ADD_GROUP_MEMBER_DETAILS,
							groupMemID, groupID, "0", "1", loginId,
							CustomDateFormatConstants.creationDateGMTFormat(),
							toInsrtRoleId, assignmentReqd, groupMemID, groupID,
							groupMemID, groupID, null, "1", loginId,
							CustomDateFormatConstants.creationDateGMTFormat(),
							toInsrtRoleId, assignmentReqd);
		}else{
			ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
			managerRoleIdListStr = bundle
					.getString("adminConsoleGroupManagerRoles");
			String[] managerRoleList=managerRoleIdListStr.split(",");
			for(int i=0;i<managerRoleList.length;i++){
				if(managerRoleList[i].equalsIgnoreCase(roleId)){
					
					addGroupMem = this.jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_UPDATE_GROUP_MEMBER_DETAILS,
							toInsrtRoleId,loginId,CustomDateFormatConstants.creationDateGMTFormat(),
							groupMemID, groupID, "1");
					
				}
			}
			
			
		}
		return addGroupMem;

	}

	public int userRoleInactive(String empId, String roleId, String loginId) {
		return this.jdbcTemplate.update(
				ADMIN_SettingsSQLQueryConstants.IC_IHD_UPDATE_ROLE_AS_INACTIVE,
				"0", loginId,
				CustomDateFormatConstants.creationDateGMTFormat(), empId,
				roleId);
	}

	public int addUserRoleDetails(String roleID, String empID, String loginId) {
		int count = this.jdbcTemplate.queryForInt(
				ADMIN_SettingsSQLQueryConstants.IC_IHD_CHECK_USER_ROLE_ACTIVE,
				empID, roleID);
		int result = 0;
		if (count > 0) {
			result = 0;
		} else {
			result = this.jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_ADD_ROLE_TO_GROUP_MEMBER,
							empID, roleID, "0", "1", loginId,
							CustomDateFormatConstants.creationDateGMTFormat(),
							empID, roleID, empID, roleID, "1", loginId,
							CustomDateFormatConstants.creationDateGMTFormat());
		}
		return result;
	}

	public String createGroup(final JSONObject jsonObj, final String loginId)
			throws JSONException {

		int row = jdbcTemplate.update(
				ADMIN_SettingsSQLQueryConstants.IC_IHD_CREATE_GROUP, jsonObj
						.get("GROUP_NAME").toString(), jsonObj.get(
						"GROUP_MANAGER").toString(), jsonObj.get("LOCATION_ID")
						.toString(), "1", jsonObj.get("AUTO_ASSIGNMENT")
						.toString(), loginId, CustomDateFormatConstants
						.creationDateGMTFormat());
		if (row > 0) {
			return jdbcTemplate.queryForObject(ADMIN_SettingsSQLQueryConstants.IC_MAX_GROUP,
					String.class);
		} else {
			return "0";
		}
	}

	public int groupFunctionMapping(final String groupId,
			final JSONObject jsonObj, final int slaId, final String loginId)
			throws DataAccessException, JSONException {

		int row = 0;

		if (jsonObj.get("FUNCTION_ID").toString().equalsIgnoreCase("1")
				|| jsonObj.get("FUNCTION_ID").toString()
						.equalsIgnoreCase("256")) {

			final List<Map<String, Object>> locationList = this.jdbcTemplate
					.queryForList(ADMIN_SettingsSQLQueryConstants.IC_LOCATION_LIST,jsonObj.get("FUNCTION_ID").toString());

			int[] rowCount = jdbcTemplate
					.batchUpdate(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING,
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps,
										int i) throws SQLException {
									try {
										ps.setString(1, groupId);
										ps.setString(2, jsonObj.get(
												"FUNCTION_ID").toString());
										ps.setString(3, locationList.get(i)
												.get("LOCATION_ID").toString());
										ps.setInt(4, slaId);
										ps.setString(5, "1");
										ps.setString(6, loginId);
										ps
												.setString(
														7,
														CustomDateFormatConstants
																.creationDateGMTFormat());

									} catch (JSONException e) {
										e.printStackTrace();
									}

								}

								public int getBatchSize() {
									return locationList.size();
								}
							});
			if (rowCount.length > 0) {
				row = 1;
			}

		} else {

			row = jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING,
							groupId, jsonObj.get("FUNCTION_ID").toString(),
							jsonObj.get("LOCATION_ID").toString(), slaId, "1",
							loginId, CustomDateFormatConstants
									.creationDateGMTFormat());

		}
		if (row > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public int groupMemberDetails(final String groupId,
			final JSONArray jsonMem, final String loginId) {

		int[] row = jdbcTemplate
				.batchUpdate(
						ADMIN_SettingsSQLQueryConstants.IC_IHD_ADD_GROUP_MEMBER_DETAILS_INSERT,
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								try {
									JSONArray subMemArray = (JSONArray) jsonMem
											.get(i);
									ps.setString(1, subMemArray.get(0)
											.toString());
									ps.setString(2, groupId);
									ps.setString(3, subMemArray.get(2)
											.toString());
									ps.setString(4, subMemArray.get(3)
											.toString());
									ps.setString(5, "1");
									ps.setString(6, loginId);
									ps.setString(7, CustomDateFormatConstants
											.creationDateGMTFormat());

								} catch (JSONException e) {
									e.printStackTrace();
								}

							}

							public int getBatchSize() {
								return jsonMem.length();
							}

						});

		if (row.length > 0)
			return 1;
		else
			return 0;
	}

	public List<Map<String, Object>> getGroupDetailsForFunction(
			String functionId, String memberId, String status, String loginID,
			String roleName) {

		String roleNameListStr = "";
		String SQL = ADMIN_SettingsSQLQueryConstants.IC_IHD_GROUP_DETAIL_LIST_SQL;
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		roleNameListStr = bundle.getString("adminConsoleGroupRole");

		String[] roleNameList = roleNameListStr.split(",");
		int isRequiredFlag = 1;

		// Check to Find if the Logged in manager's role is Administrator or
		// Level 0 Manager. If no then populate the manager's group accordingly
		for (int i = 0; i < roleNameList.length; i++) {
			if (roleName.equalsIgnoreCase(roleNameList[i])) {
				isRequiredFlag = 0;
				break;
			}
		}

		if (!memberId.trim().equalsIgnoreCase("")) {
			if (isRequiredFlag == 1) {
				SQL = SQL
						.replace("where",
								"join IC_IHD_GROUP_MEMBER_DETAILS gmd on gmd.GROUP_ID=gm.GROUP_ID where");
				SQL = SQL.replace("order by gm.NAME", "and gmd.MEMBER_ID=? and gmd.MEMBER_ID=? order by gm.NAME");
				
				return this.jdbcTemplate.queryForList(SQL, new Object[] { functionId,
						status,loginID.trim(),memberId.trim() });
			} else {
				SQL = SQL
						.replace("where",
								"join IC_IHD_GROUP_MEMBER_DETAILS gmd on gmd.GROUP_ID=gm.GROUP_ID where");
				SQL = SQL.replace("order by gm.NAME", "and gmd.MEMBER_ID=? order by gm.NAME");
				
				return this.jdbcTemplate.queryForList(SQL, new Object[] { functionId,
						status,memberId.trim() });
			}
		} else {
			if (isRequiredFlag == 1) {
				SQL = SQL
						.replace("where",
								"join IC_IHD_GROUP_MEMBER_DETAILS gmd on gmd.GROUP_ID=gm.GROUP_ID where");
				SQL = SQL.replace("order by gm.NAME", "and gmd.MEMBER_ID=? order by gm.NAME");
				
				return this.jdbcTemplate.queryForList(SQL, new Object[] { functionId,
						status,loginID.trim(),memberId.trim() });
			}
		}

		return this.jdbcTemplate.queryForList(SQL, new Object[] { functionId,
				status });
	}

	public String modifyGroup(JSONArray jsonArrayObj,
			JSONArray oldjsonArrayObj, String loginID) throws JSONException {

		COMMON_CacheDAOImpl commonCacheDao = new COMMON_CacheDAOImpl();

		String managerRoleIdListStr = "";
		String nonPrivilageRoleIdListStr = "";
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		managerRoleIdListStr = bundle
				.getString("adminConsoleGroupManagerRoles");
		nonPrivilageRoleIdListStr = bundle.getString("userNonPrivilegeRoles");
		
		

		String[] managerRoleIdList = managerRoleIdListStr.split(",");
		String[] nonPrivilageRoleIdList = nonPrivilageRoleIdListStr.split(",");

		int status = 0;

		for (int i = 0; i < jsonArrayObj.length(); i++) {
			JSONArray subjsonArray = (JSONArray) jsonArrayObj.get(i);
			LinkedHashMap<String,Object> queryMap = new LinkedHashMap<String,Object>();
			LinkedHashMap<String,Object> queryMapForWhereCondition = new LinkedHashMap<String,Object>();
			for (int j = 0; j < oldjsonArrayObj.length(); j++) {
				JSONArray subOldjsonArray = (JSONArray) oldjsonArrayObj.get(i);

				boolean flag = false;

				if (subjsonArray.get(0).toString().equalsIgnoreCase(
						subOldjsonArray.get(0).toString())) {
					// same group id

					
					queryMap.put("MODIFIED_BY", loginID);
					queryMap.put("MODIFIED_DATE", CustomDateFormatConstants.creationDateGMTFormat());
					queryMapForWhereCondition.put("GROUP_ID", subjsonArray.get(0).toString());
					int queryChange = 0;
					int assignedTicketsofManager = 0;

					if (!((String) subjsonArray.get(1))
							.equalsIgnoreCase((String) subOldjsonArray.get(1))) {
						// group name changed
						queryMap.put("NAME", subjsonArray.get(1).toString().trim());
						queryChange = 1;

					}
					if (!((String) subjsonArray.get(2))
							.equalsIgnoreCase((String) subOldjsonArray.get(2))) {
						// group manager changed
						queryMap.put("MANAGER", subjsonArray.get(2).toString().trim());
						queryChange = 1;

						List<Map<String, Object>> getGroups = commonCacheDao
								.getIHDGroup(subOldjsonArray.get(2).toString());
						for (Map<String, Object> groupListInfo : getGroups) {
							if (Integer.parseInt(groupListInfo.get("GROUP_ID")
									.toString()) != Integer
									.parseInt(subjsonArray.get(0).toString())) {
								flag = true;
							}
						}

						String roleId = "";

						Map<String, Object> getStatus = verifyAssignedTicket(
								subjsonArray.get(0).toString(), subOldjsonArray
										.get(2).toString(), loginID);
						if ((!flag) && (getStatus.get("status") != "error")) {
							List<Map<String, Object>> getRoles = commonCacheDao
									.getUserRoleById(subOldjsonArray.get(2)
											.toString());
							for (Map<String, Object> groupListInfo : getRoles) {
								for (int privilage = 0; privilage < nonPrivilageRoleIdList.length; privilage++) {
									if (groupListInfo
											.get("ROLE_ID")
											.toString()
											.equalsIgnoreCase(
													nonPrivilageRoleIdList[privilage])) {
										/*userRoleInactive(subOldjsonArray.get(2)
												.toString(), groupListInfo.get(
												"ROLE_ID").toString(), loginID);*/
										
									
										for (int role = 0; role < managerRoleIdList.length; role++) {
											if (groupListInfo
													.get("ROLE_ID")
													.toString()
													.equalsIgnoreCase(
															managerRoleIdList[role])) {
												roleId = groupListInfo.get(
														"ROLE_ID").toString();
												break;
											}
										}
									}
								}
							
							}
							status = addGroupMemberDetails(roleId, subjsonArray
									.get(0).toString(), subjsonArray.get(2)
									.toString(), loginID, "0", "1");

							status = addUserRoleDetails(roleId, subjsonArray
									.get(2).toString(), loginID);
							
							updateUserRoleDetails(subOldjsonArray.get(2)
									.toString(), roleId, loginID);
						} else {
							if (getStatus.get("status") != "error") {
								List<Map<String, Object>> getRoles = commonCacheDao
										.getUserRoleById(subOldjsonArray.get(2)
												.toString());
								for (Map<String, Object> groupListInfo : getRoles) {
									if (Integer.parseInt(groupListInfo.get(
											"ROLE_ID").toString()) != 6) {
										for (int role = 0; role < managerRoleIdList.length; role++) {
											if (groupListInfo
													.get("ROLE_ID")
													.toString()
													.equalsIgnoreCase(
															managerRoleIdList[role])) {
												roleId = groupListInfo.get(
														"ROLE_ID").toString();
												break;
											}
										}
									}
								}
								status = addGroupMemberDetails(roleId,
										subjsonArray.get(0).toString(),
										subjsonArray.get(2).toString(),
										loginID, "0", "1");

								status = addUserRoleDetails(roleId,
										subjsonArray.get(2).toString(), loginID); 
								
								updateUserRoleDetails(subOldjsonArray.get(2)
										.toString(), roleId, loginID);
							} else {
								assignedTicketsofManager = 1;
							}
						}

						/*
						 * status=addGroupMemberDetails(roleId,
						 * subjsonArray.get(0).toString(),
						 * subjsonArray.get(2).toString(), loginID);
						 * 
						 * status=addUserRoleDetails(roleId,
						 * subjsonArray.get(2).toString(), loginID);
						 */

					}
					/*
					 * if(!((String)
					 * subjsonArray.get(3)).equalsIgnoreCase((String)
					 * subOldjsonArray.get(3))){ //group manager role changed
					 * --> update user role details //queryChange=1; status =
					 * addUserRoleDetails
					 * (subjsonArray.get(3).toString(),subjsonArray
					 * .get(2).toString(),loginID);
					 * 
					 * }
					 */
					if (!((String) subjsonArray.get(3))
							.equalsIgnoreCase((String) subOldjsonArray.get(3))) {
						// auto assignment changed
						queryMap.put("IS_AUTO_ASSIGNMENT_REQUIRED", subjsonArray.get(3).toString().trim());
						queryChange = 1;
					}
					if (!((String) subjsonArray.get(4))
							.equalsIgnoreCase((String) subOldjsonArray.get(4))) {
						// status changed

						List<Map<String, Object>> asssignedDetails = new ArrayList<Map<String, Object>>();
						asssignedDetails = this.jdbcTemplate
								.queryForList(
										ADMIN_SettingsSQLQueryConstants.IC_IHD_TICKET_DETAILS_OF_GROUP,
										subjsonArray.get(0).toString());

						if (asssignedDetails.isEmpty()
								|| !subjsonArray.get(4).toString()
										.equalsIgnoreCase("0")) {
							queryMap.put("IS_ACTIVE", subjsonArray.get(4).toString().trim());
							queryChange = 1;

							if (!subjsonArray.get(4).toString()
									.equalsIgnoreCase("1")) {

								status = this.jdbcTemplate
										.update(ADMIN_SettingsSQLQueryConstants.IC_UPDATE_GROUP_MEMBER_DETAILS,
												subjsonArray.get(4).toString()
														.trim(),
												loginID,
												CustomDateFormatConstants
														.creationDateGMTFormat(),
												subjsonArray.get(0).toString()
														.trim());

								List<Map<String, Object>> groupMemberList = commonCacheDao
										.getIHDGroupMemberID(subjsonArray
												.get(0).toString());

								for (int groupList = 0; groupList < groupMemberList
										.size(); groupList++) {

									if (subjsonArray.get(4).toString()
											.equalsIgnoreCase("0")) {
										flag = false;
										List<Map<String, Object>> getGroups = commonCacheDao
												.getIHDGroup(groupMemberList
														.get(groupList).get(
																"member_id")
														.toString());
										for (Map<String, Object> groupListInfo : getGroups) {
											if (Integer
													.parseInt(groupListInfo
															.get("GROUP_ID")
															.toString()) != Integer
													.parseInt(subjsonArray.get(
															0).toString())) {
												flag = true;
											}
										}

										Map<String, Object> getStatus = verifyAssignedTicket(
												subjsonArray.get(0).toString(),
												groupMemberList.get(groupList)
														.get("member_id")
														.toString(), loginID);
										if ((!flag)
												&& (getStatus.get("status") != "error")) {
											List<Map<String, Object>> getRoles = commonCacheDao
													.getUserRoleById(groupMemberList
															.get(groupList)
															.get("member_id")
															.toString());
											for (Map<String, Object> groupListInfo : getRoles) {
												for (int privilage = 0; privilage < nonPrivilageRoleIdList.length; privilage++) {
													if (groupListInfo
															.get("ROLE_ID")
															.toString()
															.equalsIgnoreCase(
																	nonPrivilageRoleIdList[privilage])) {
														userRoleInactive(
																groupMemberList
																		.get(
																				groupList)
																		.get(
																				"member_id")
																		.toString(),
																groupListInfo
																		.get(
																				"ROLE_ID")
																		.toString(),
																loginID);
													}
												}
											}
										}
									} else {
										status = addUserRoleDetails(
												subjsonArray.get(3).toString(),
												groupMemberList.get(groupList)
														.get("member_id")
														.toString(), loginID);
									}
								}

							}

							status = this.jdbcTemplate
									.update(ADMIN_SettingsSQLQueryConstants.IC_UPDATE_GROUP_FUNCTION_MAPPING,
											subjsonArray.get(4).toString()
													.trim(), loginID,
											CustomDateFormatConstants
													.creationDateGMTFormat(),
											subjsonArray.get(0).toString()
													.trim());

						} else {

							status = -555;
						}

					}

					// Auditlog
					StringBuffer dataChange = new StringBuffer();
					dataChange.append("<data_changed><GROUP>");
					dataChange.append("<ID>").append("<OLD_VALUE>").append(
							subOldjsonArray.get(0).toString()).append(
							"</OLD_VALUE>").append("<NEW_VALUE>").append(
							subjsonArray.get(0).toString()).append(
							"</NEW_VALUE>").append("</ID>");
					dataChange.append("<GROUP_NAME>").append("<OLD_VALUE>")
							.append(subOldjsonArray.get(1).toString()).append(
									"</OLD_VALUE>").append("<NEW_VALUE>")
							.append(subjsonArray.get(1).toString()).append(
									"</NEW_VALUE>").append("</GROUP_NAME>");
					dataChange.append("<GROUP_MANAGER>").append("<OLD_VALUE>")
							.append(subOldjsonArray.get(2).toString()).append(
									"</OLD_VALUE>").append("<NEW_VALUE>")
							.append(subjsonArray.get(2).toString()).append(
									"</NEW_VALUE>").append("</GROUP_MANAGER>");
					dataChange.append("<AUTO_ASSIGNMENT>")
							.append("<OLD_VALUE>").append(
									subOldjsonArray.get(3).toString()).append(
									"</OLD_VALUE>").append("<NEW_VALUE>")
							.append(subjsonArray.get(3).toString()).append(
									"</NEW_VALUE>")
							.append("</AUTO_ASSIGNMENT>");
					dataChange.append("<IS_ACTIVE>").append("<OLD_VALUE>")
							.append(subOldjsonArray.get(4).toString()).append(
									"</OLD_VALUE>").append("<NEW_VALUE>")
							.append(subjsonArray.get(4).toString()).append(
									"</NEW_VALUE>").append("</IS_ACTIVE>");
					dataChange
							.append("</GROUP><GROUP_MEMBER_DETAILS></GROUP_MEMBER_DETAILS><data_changed>");

					String auditData = dataChange.toString();
					LinkedList<String> setValues=new LinkedList<String>();
					LinkedList<String> whereValues=new LinkedList<String>();

					if (queryChange == 1 && assignedTicketsofManager == 0) {
						
						StringBuffer query = new StringBuffer();
						query.append("UPDATE IC_IHD_GROUP_MASTER SET ");
						
						Set<Entry<String, Object>> entrySet = queryMap.entrySet();
						Iterator<Entry<String, Object>> mapData = entrySet.iterator();
					    while(mapData.hasNext()){
					        Entry<String, Object> element = mapData.next();
					        query.append(element.getKey());
					        query.append("=");
					        query.append("?");
					        query.append(",");
					        setValues.add(element.getValue().toString());
					    }
					    query.replace(query.length()-1, query.length(), "");
					    query.append(" WHERE ");
					    
						Set<Entry<String, Object>> entrySetForWhereCondition = queryMapForWhereCondition.entrySet();
						Iterator<Entry<String, Object>> mapDataForWhereCondition = entrySetForWhereCondition.iterator();
					    while(mapDataForWhereCondition.hasNext()){
					        Entry<String, Object> element = mapDataForWhereCondition.next();
					        query.append(element.getKey());
					        query.append("=");
					        query.append("?");
					        whereValues.add(element.getValue().toString());
					    }
						System.out.println("Group update query::::"+query);
						final String finalQuery=query.toString();
						final LinkedList<String> finalSetValues=setValues;
						final LinkedList<String> finalWhereValues=whereValues;
						
						status=this.jdbcTemplate.update(new PreparedStatementCreator() {
							
							public PreparedStatement createPreparedStatement(java.sql.Connection connection)
									throws SQLException {
								PreparedStatement ps = null;								
								ps = connection.prepareStatement(finalQuery);
								int i=1;
								for(String value:finalSetValues){
									ps.setString(i, value);
									i++;
								}
								for(String value:finalWhereValues){
									ps.setString(i, value);
									i++;
								}
								
								return ps;
							}
						});
						
						this.jdbcTemplate
								.update(
										ADMIN_SettingsSQLQueryConstants.IC_AUDIT_LOG_DETAILS,
										subOldjsonArray.get(0).toString(),"135",
										"U", auditData, loginID,
										CustomDateFormatConstants
												.creationDateGMTFormat(),
										loginID,CustomDateFormatConstants
										.creationDateISTFormat_iTrack(),"1");
					} else if (assignedTicketsofManager == 1) {
						status = -555;
					} else if (status != -555) {
						status = -1;
					}

				}

			}

		}

		if (status > 0) {
			return "Success";
		} else if (status == -1) {
			return "NoChange";
		} else if (status == -555) {
			return "ExistingTickets";
		} else {
			return "Fail";
		}
	}
	
	public void updateUserRoleDetails(String employeeID,String roleID,String loginID){
		
		List<Map<String,Object>> roleDetails=this.jdbcTemplate.queryForList(
				ADMIN_SettingsSQLQueryConstants.SELECT_GROUP_FOR_ROLE,new Object[] { employeeID, roleID });
		
		if(roleDetails.size()==0){
			userRoleInactive( employeeID, roleID ,loginID);
		}
		
		
	}

	public int addLocationMasterDetail(String country, String city,
			String timezoneID, String isActive, String createdBy,
			String createdDate) {
		int result = this.jdbcTemplate.update(
				ADMIN_SettingsSQLQueryConstants.IC_INSERT_LOCATION_MASTER,
				country, city, null, isActive, createdBy, createdDate);
		int locnID = 0;
		List<Map<String, Object>> locnList = new ArrayList<Map<String, Object>>();
		locnList = null;

		if (result == 1) {
			locnList = this.jdbcTemplate.queryForList(
					ADMIN_SettingsSQLQueryConstants.SELECT_LOCN_ID,
					new Object[] { city, country });
			if (locnList != null) {
				locnID = Integer.parseInt(locnList.get(0).get("LOCATION_ID")
						.toString());
			}
		}

		return locnID;
	}

	/**
	 * If sla id is null then insert the operation time details Step 1: First
	 * insert the sla details the IC_IHD_SLA_MASTER Step 2: Second insert the
	 * operating time details for this location and sla ID Step3: Insert the
	 * Holiday Exclusion Detail for the location Step4: Insert the Function
	 * Location mapping for the location
	 */
	public String insertSLAMasterAdminConsoleLocAdd(String sla,
			final List<Map<String, Object>> operationTimeList,
			final String cityName, final String createdBy,
			final String createdDate, final int locnIDe,
			final String functionID, final int locnDetIde,
			final String[] holidayList) {
		String slaID = null;
		final String slaMasterSql = ADMIN_SettingsSQLQueryConstants.INSERT_SLA_MASTER_ADMIN_CONSOLE_LOC_ADDN;
		final String maxSlaIDSql = ADMIN_SettingsSQLQueryConstants.SELECT_MAX_SLA_ID;
		final String insertOprTimeDetSql = ADMIN_SettingsSQLQueryConstants.INSERT_OPERATING_TIME_DETAILS;
		int result = 0;
		if (sla == null) {
			// Insert the sla detail
			result = this.jdbcTemplate.update(slaMasterSql, new Object[] {
					cityName, "C", "0", "1", createdBy, createdDate });
			// If inserted in Sla master table, insert op details
			if (result == 1) {
				slaID = this.jdbcTemplate.queryForObject(maxSlaIDSql,
						String.class);
				// Insert the Operation Time Detail
				final String slaIde = slaID;
				this.jdbcTemplate.batchUpdate(insertOprTimeDetSql,
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Map<String, Object> map = operationTimeList
										.get(i);
								ps.setString(1, slaIde);
								ps.setString(2, map.get("DAY").toString()
										.trim());
								ps.setString(3, map.get("START_TIME")
										.toString());
								ps.setString(4, map.get("END_TIME").toString());
								ps.setString(5, map.get("NEXT_WORKING_DAY")
										.toString().trim());
								ps.setString(6, "1");
								ps.setString(7, createdBy);
								ps.setString(8, createdDate);
							}

							public int getBatchSize() {
								return operationTimeList.size();
							}
						});
				sla = String.valueOf(slaID);
			}
		}

		// Insert IC_IHD_SERVICE_WINDOW_DETAILS detail for Location
		this.jdbcTemplate
				.update(
						ADMIN_SettingsSQLQueryConstants.INSERT_SERVICE_WINDOW_DET_FOR_LOCN,
						new Object[] { locnIDe, locnDetIde, functionID, sla,
								"1", createdBy, createdDate });

		// Insert the holidayList for the location
		String insertHolidayQuery = ADMIN_SettingsSQLQueryConstants.INSERT_HOL_EXCLUSION_SLA_MAPPING;

		for (int i = 0; i < holidayList.length; i++) {
			this.jdbcTemplate.update(insertHolidayQuery, sla, locnIDe,
					holidayList[i], "1", createdBy, createdDate);
		}

		// Insert the group location Mapping
		this.jdbcTemplate
				.update(
						ADMIN_SettingsSQLQueryConstants.INSERT_FUNCN_LOCATION_MAPPING,
						new Object[] { functionID, locnIDe, "1", createdBy,
								createdDate });

		return sla;
	}

	public int insertLocDetailAdminConsoleLocAddIT(
			final List<Map<String, Object>> buildingList, final int locnIDe,
			final String functionID, final String createdBy,
			final String createdDate) {
		int maxLocnId = 0;
		if (functionID.equalsIgnoreCase("256")) {
			List<Map<String, Object>> maxLocDeTlist = new ArrayList<Map<String, Object>>();
			maxLocDeTlist = this.jdbcTemplate
					.queryForList(ADMIN_SettingsSQLQueryConstants.SELECT_MAX_LOCNDET_ID);
			final int maxLocnDetIT = Integer.parseInt(maxLocDeTlist.get(0).get(
					"LOC_DET_ID").toString());
			maxLocnId = maxLocnDetIT;

			String insertLocnDetSql = ADMIN_SettingsSQLQueryConstants.INSERT_LOCATION_DET_FOR_IT;
			this.jdbcTemplate.batchUpdate(insertLocnDetSql,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {

							Map<String, Object> map = buildingList.get(i);
							int maxLocationDetID = maxLocnDetIT;

							ps.setInt(1, maxLocationDetID + i);
							ps.setInt(2, locnIDe);
							ps.setString(3, map.get("Building").toString());
							ps.setString(4, map.get("Floor").toString());
							ps.setString(5, "1");
							ps.setString(6, createdBy);
							ps.setString(7, createdDate);
						}

						public int getBatchSize() {
							return buildingList.size();
						}
					});

		}
		return maxLocnId;

	}

	public void insertApproverEmpDet(
			final List<Map<String, Object>> approverList, final int locnIDe,
			final String createdBy, final String createdDate) {
		
		this.jdbcTemplate.update(ADMIN_SettingsSQLQueryConstants.INSERT_USER_ROLE_DETAILS, approverList.get(0).get("EMPLOYEE_ID").toString(),"10",approverList.get(0).get("EMPLOYEE_ID").toString(),"10","1",createdBy,createdDate);
		
		String insertApproverListQuery = ADMIN_SettingsSQLQueryConstants.INSERT_APPROVER_EMP_DET_ADMIN_CONSOLE_FOR_LOCN;
		this.jdbcTemplate.batchUpdate(insertApproverListQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Map<String, Object> map = approverList.get(i);
						ps.setInt(1, Integer.parseInt(map.get("APPROVER_ID")
								.toString()));
						ps.setString(2, map.get("EMPLOYEE_ID").toString());
						ps.setString(3, "1");
						ps.setString(4, createdBy);
						ps.setString(5, createdDate);
						ps.setInt(6, locnIDe);
					}

					public int getBatchSize() {
						return approverList.size();
					}
				});

	}

	public int insertDefaultDeptDefaultGroupForLocation(
			final List<Map<String, Object>> grpMapList,
			final List<Map<String, Object>> grpMapDeptList,
			final String functionID, final String slaId,
			final String locationId, final String createdBy,
			final String createdDate) {
		String defAssignmentGrpQuery = ADMIN_SettingsSQLQueryConstants.INSERT_DEFAULT_ASSIGNMENT_GROUP_DETAIL;
		String deptDefAssignmentGrpQuery = ADMIN_SettingsSQLQueryConstants.INSERT_DEPT_DEFAULT_ASSIGNMENT_GROUP_DETAIL;

		String selectDefAssignmentQuery = ADMIN_SettingsSQLQueryConstants.SELECT_DEFAULT_ASSIGNMENT_GROUP_DETAIL;
		String selectdeptDefAssignmentQuery = ADMIN_SettingsSQLQueryConstants.SELECT_DEPT_DEFAULT_ASSIGNMENT_GROUP_DETAIL;

		int result = 0;
		this.jdbcTemplate.batchUpdate(defAssignmentGrpQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Map<String, Object> map = grpMapList.get(i);
						ps.setString(1, map.get("SUB_CATEGORY_ID").toString());
						ps.setString(2, locationId);
						ps.setString(3, map.get("GROUP_ID").toString());
						ps.setString(4, "1");
						ps.setString(5, createdBy);
						ps.setString(6, createdDate);

					}

					public int getBatchSize() {
						return grpMapList.size();
					}
				});

		this.jdbcTemplate.batchUpdate(deptDefAssignmentGrpQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Map<String, Object> map = grpMapDeptList.get(i);
						ps.setString(1, map.get("DEPT_ID").toString());
						ps.setString(2, map.get("SUB_CATEGORY_ID").toString());
						ps.setString(3, locationId);
						ps.setString(4, map.get("GROUP_ID").toString());
						ps.setString(5, "1");
						ps.setString(6, createdBy);
						ps.setString(7, createdDate);

					}

					public int getBatchSize() {
						return grpMapDeptList.size();
					}
				});

		final int rowCountDefGrp = this.jdbcTemplate.queryForInt(
				selectDefAssignmentQuery, new Object[] { locationId });

		final int rowCountDeptDefGrp = this.jdbcTemplate.queryForInt(
				selectdeptDefAssignmentQuery, new Object[] { locationId });

		if (rowCountDefGrp > 0 && rowCountDeptDefGrp > 0) {
			result = 1;
		}

		return result;
	}

	public List<Map<String, Object>> getApproverEmployeeDetail(
			String statusValue, String approverName, String statusVal,
			String subcategoryID) {
		String sql = ADMIN_SettingsSQLQueryConstants.SELECT_ADMINCONSOLE_APPROVER_EMPLOYEE_DETAILS;
		if (statusValue.equalsIgnoreCase("All")) {
			sql = sql.replace("approverEmp.IS_ACTIVE in (?)",
					"approverEmp.IS_ACTIVE in (0,1)");
		} else if (statusValue.equalsIgnoreCase("Active")) {
			sql = sql.replace("approverEmp.IS_ACTIVE in (?)",
					"approverEmp.IS_ACTIVE in (1)");
		} else if (statusValue.equalsIgnoreCase("Inactive")) {
			sql = sql.replace("approverEmp.IS_ACTIVE in (?)",
					"approverEmp.IS_ACTIVE in (0)");
		}
		List<Map<String, Object>> approverEmpList = this.jdbcTemplate
				.queryForList(sql, new Object[] { approverName, subcategoryID });
		return approverEmpList;
	}

	public List<Map<String, Object>> getOpenTicketsForCategoryAndSubCategory(
			List<HELPDESK_Category> categoryIDList, String catOrSubCat) {
		String sql = null;
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		if (catOrSubCat.equalsIgnoreCase("category")) {
			sql = ADMIN_SettingsSQLQueryConstants.SELECT_OPEN_TICKETS_FOR_CATEGORY;

			for (Iterator<HELPDESK_Category> iterator = categoryIDList
					.iterator(); iterator.hasNext();) {
				HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator
						.next();
				resList = this.jdbcTemplate.queryForList(sql, new Object[] {
						helpdeskCategory.getCATEGORY_ID(),
						helpdeskCategory.getCATEGORY_ID() });
			}
		} else {
			sql = ADMIN_SettingsSQLQueryConstants.SELECT_OPEN_TICKETS_FOR_SUBCATEGORY;
			for (Iterator<HELPDESK_Category> iterator = categoryIDList
					.iterator(); iterator.hasNext();) {
				HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator
						.next();
				resList = this.jdbcTemplate.queryForList(sql, new Object[] {
						helpdeskCategory.getSUBCATEGORY_ID(),
						helpdeskCategory.getSUBCATEGORY_ID() });
			}

		}
		return resList;
	}

	public String updateCategoryAsInactive(
			List<HELPDESK_Category> inactiveCategoryWithNoOpenTickets,
			List<HELPDESK_Category> inactiveSubCategoryWithNoOpenTickets,
			String loginID) {

		List<String> TABLES_TO_INACTIVATE = new ArrayList<String>();
		TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_MASTER");
		TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_APPROVAL_DETAILS");
		//TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS");
		//TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_ODC_MAPPING");
		TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_PRIORITY_DETAILS");
		//TABLES_TO_INACTIVATE.add("IC_IHD_CATEGORY_ROLE_MAPPING");
		TABLES_TO_INACTIVATE.add("IC_IHD_DEFAULT_ASSIGNMENT_DETAILS");
		//TABLES_TO_INACTIVATE.add("IC_IHD_DEPT_DEFAULT_ASSIGNMENT");
		//TABLES_TO_INACTIVATE
			//	.add("IC_IHD_LOC_DETAIL_DEFAULT_ASSIGNMENT_DETAILS");


		for (Iterator<String> iterator = TABLES_TO_INACTIVATE.iterator(); iterator
				.hasNext();) {
			String tableName = (String) iterator.next();
			if (inactiveCategoryWithNoOpenTickets.size() > 0) {

				for (Iterator<HELPDESK_Category> iterator2 = inactiveCategoryWithNoOpenTickets
						.iterator(); iterator2.hasNext();) {
					HELPDESK_Category categoryMap = (HELPDESK_Category) iterator2
							.next();
					String categoryID = categoryMap.getCATEGORY_ID();
					int catStatus = 0;
					if (categoryMap.getCATEGORY_STATUS().equals("Active")) {
						catStatus = 1;
					}
					StringBuffer query = new StringBuffer();
					query.append("UPDATE ");
					query.append(tableName);
					query.append(" SET ");
					query.append("IS_ACTIVE");
					query.append("=");
					query.append("?");
					query.append(",");
					query.append("MODIFIED_BY");
					query.append("=");
					query.append("?");
					query.append(",");
					query.append("MODIFIED_DATE");
					query.append("=");
					query.append("GETDATE()");
					query.append(" WHERE ");
					query.append("CATEGORY_ID");
					query.append("=");
					query.append("?");
					
					final int f_catStatus=catStatus;
					final String f_loginID=loginID;
					final String f_categoryID=categoryID;
					final String finalQuery=query.toString();
					this.jdbcTemplate.update(new PreparedStatementCreator() {
						
						public PreparedStatement createPreparedStatement(java.sql.Connection connection)
								throws SQLException {
							PreparedStatement ps = null;	
							ps = connection.prepareStatement(finalQuery);
							ps.setInt(1, f_catStatus);
							ps.setString(2, f_loginID);
							ps.setString(3, f_categoryID);
							return ps;
						}
					});

					if(catStatus==0){
						this.jdbcTemplate.update(ADMIN_SettingsSQLQueryConstants.IC_IHD_CATEGORY_MASTER_ALL_SUB_CATEGORY,catStatus,loginID,categoryID);
					}

				}
			}

			if (inactiveSubCategoryWithNoOpenTickets.size() > 0) {
				for (Iterator<HELPDESK_Category> iterator2 = inactiveSubCategoryWithNoOpenTickets
						.iterator(); iterator2.hasNext();) {
					HELPDESK_Category categoryMap = (HELPDESK_Category) iterator2
							.next();
					String subCategoryID = categoryMap.getSUBCATEGORY_ID();

					int subcatStatus = 0;

					if (categoryMap.getSUBCATEGORY_STATUS().equals("Active")) {
						subcatStatus = 1;
					}
					
					StringBuffer query = new StringBuffer();
					query.append("UPDATE ");
					query.append(tableName);
					query.append(" SET ");
					query.append("IS_ACTIVE");
					query.append("=");
					query.append("?");
					query.append(",");
					query.append("MODIFIED_BY");
					query.append("=");
					query.append("?");
					query.append(",");
					query.append("MODIFIED_DATE");
					query.append("=");
					query.append("GETDATE()");
					query.append(" WHERE ");
					query.append("CATEGORY_ID");
					query.append("=");
					query.append("?");
					
					final int f_subcatStatus=subcatStatus;
					final String f_loginID=loginID;
					final String f_subCategoryID=subCategoryID;
					final String finalQuery=query.toString();
					
					this.jdbcTemplate.update(new PreparedStatementCreator() {
						
						public PreparedStatement createPreparedStatement(java.sql.Connection connection)
								throws SQLException {
							PreparedStatement ps = null;	
							ps = connection.prepareStatement(finalQuery);
							ps.setInt(1, f_subcatStatus);
							ps.setString(2, f_loginID);
							ps.setString(3, f_subCategoryID);
							return ps;
						}
					});


					int activeSubCategoryCount=this.jdbcTemplate.queryForInt(ADMIN_SettingsSQLQueryConstants.SELECT_ACTIVE_SUBCATEGORY_COUNT,subCategoryID);
					
					if(activeSubCategoryCount==0){
						this.jdbcTemplate.update(ADMIN_SettingsSQLQueryConstants.INACTIVATE_CATEGORY,loginID,subCategoryID);
						
					}
				}
			}

		}

		return "Success";
	}

	public int countOfOpenTickets(String categoryIDe) {
		int countOfTickets = this.jdbcTemplate.queryForInt(
				ADMIN_SettingsSQLQueryConstants.SELECT_COUNT_OF_TICKETS,
				new Object[] { categoryIDe });
		return countOfTickets;
	}

	public int countOfOpenTicketsSubCategory(String subCategoryIDe) {
		int countOfTickets = this.jdbcTemplate.queryForInt(
				ADMIN_SettingsSQLQueryConstants.SELECT_COUNT_OF_TICKETS_SUBCAT,
				new Object[] { subCategoryIDe });
		return countOfTickets;
	}
	
	
	
	public String updateLocation(List<ADMIN_ModifyLocation> locationMapList,
			String loginID,String LocationName) {
	
		String result = "Success";
	
		List<ADMIN_ModifyLocation> oldLocationObj = new ArrayList<ADMIN_ModifyLocation>();
		try{
			for (ADMIN_ModifyLocation helpdeskLocation : locationMapList) {
				oldLocationObj = new ArrayList<ADMIN_ModifyLocation>();
		
				oldLocationObj = this.jdbcTemplate
						.query(
								ADMIN_SettingsSQLQueryConstants.SELECT_LOCATION_OLD_DETAILS,
								new Object[] {
										helpdeskLocation.getLOCATION_ID(),helpdeskLocation.getLOCDETID()},
								new RowMapper<ADMIN_ModifyLocation>() {
									public ADMIN_ModifyLocation mapRow(ResultSet rs, int count)
									throws SQLException {
										ADMIN_ModifyLocation loc = new ADMIN_ModifyLocation();
										loc.setLOCDETID(rs
												.getString("LOCDETID"));
										loc.setCITY(rs
												.getString("CITY"));
										loc.setBUILDING(rs
												.getString("BUILDING"));
										loc.setFLOOR(rs
												.getString("FLOOR"));
										loc.setSTATUS(rs
												.getString("STATUS"));
										
										return loc;
									}

								});

		
								
				int Status = 0;
				if (helpdeskLocation.getSTATUS().equals("Active")) {
					Status = 1;
				}
				
				this.jdbcTemplate
						.update(ADMIN_SettingsSQLQueryConstants.IC_UPADTE_LOACTION_MASTER,
								new Object[] { helpdeskLocation.getCITY(),
										loginID, "%" + LocationName + "%" });
				this.jdbcTemplate
						.update(ADMIN_SettingsSQLQueryConstants.UPADTE_IC_LOCATION_DETAILS,
								new Object[] { helpdeskLocation.getBUILDING(),
										helpdeskLocation.getFLOOR(), Status,
										loginID,
										helpdeskLocation.getLOCATION_ID(),
										helpdeskLocation.getLOCDETID() });
			
		
			/*********************************** Modify Location Audit Detail *********************************************/
			/**
			 * Modify Location : Menu ID:138
			 */
		
	
		
			StringBuffer dataChangeXml = new StringBuffer("");
			if (oldLocationObj != null) {
				ADMIN_ModifyLocation oldLocObj = new ADMIN_ModifyLocation();
				oldLocObj = oldLocationObj.get(0);
				dataChangeXml = new StringBuffer("");
				dataChangeXml = new StringBuffer("<data_changed>");
			
				dataChangeXml.append("<LOCATION>");
				dataChangeXml.append("<ID>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getLOCATION_ID()).append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getLOCATION_ID()).append(
						"</new_value>");
				dataChangeXml.append("</ID>");
				dataChangeXml.append("<NAME>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getCITY())
						.append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getCITY()).append(
						"</new_value>");
				dataChangeXml.append("</NAME>");
				dataChangeXml.append("</LOCATION>");
				dataChangeXml.append("<BUILDING>");
				dataChangeXml.append("<ID>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getLOCDETID()).append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getLOCDETID()).append(
						"</new_value>");
				dataChangeXml.append("</ID>");
				dataChangeXml.append("<NAME>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getBUILDING())
						.append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getBUILDING()).append(
						"</new_value>");
				dataChangeXml.append("</NAME>");
				dataChangeXml.append("</BUILDING>");
				dataChangeXml.append("<FLOOR>");
				dataChangeXml.append("<ID>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getLOCDETID()).append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getLOCDETID()).append(
						"</new_value>");
				dataChangeXml.append("</ID>");
				dataChangeXml.append("<NAME>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getFLOOR())
						.append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getFLOOR()).append(
						"</new_value>");
				dataChangeXml.append("</NAME>");
				dataChangeXml.append("</FLOOR>");
				dataChangeXml.append("<STATUS>");
				dataChangeXml.append("<old_value>").append(
						oldLocObj.getSTATUS())
						.append("</old_value>");
				dataChangeXml.append("<new_value>").append(
						helpdeskLocation.getSTATUS()).append(
						"</new_value>");
				dataChangeXml.append("</STATUS>");
				dataChangeXml.append("</data_changed>");
			}

			this.jdbcTemplate
					.update(
							ADMIN_SettingsSQLQueryConstants.INSERT_AUDIT_DET_CATEGORY,
							new Object[] {
									helpdeskLocation.getLOCATION_ID(),
									"138",
									null,
									null,
									"U",
									dataChangeXml.toString(),
									loginID,
									CustomDateFormatConstants
											.creationDateGMTFormat(),
									loginID });
		} 
		}catch (Exception e) {
		log.error("Error While updating location:" + e);
		e.printStackTrace();

		result = "Error";
	}

		return result;
	}
	

	public String updateCategory(List<HELPDESK_Category> catListActive,
			String loginID) {
		String result = "Success";

		List<HELPDESK_Category> oldCategoryObj = new ArrayList<HELPDESK_Category>();

		try {
			for (Iterator<HELPDESK_Category> iterator = catListActive
					.iterator(); iterator.hasNext();) {

				oldCategoryObj = new ArrayList<HELPDESK_Category>();

				HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator
						.next();

				oldCategoryObj = this.jdbcTemplate
						.query(
								ADMIN_SettingsSQLQueryConstants.SELECT_CATEGORY_OLD_DETAILS,
								new Object[] {
										helpdeskCategory.getCATEGORY_ID(),
										helpdeskCategory.getSUBCATEGORY_ID() },
								new RowMapper<HELPDESK_Category>() {

									public HELPDESK_Category mapRow(
											ResultSet rs, int count)
											throws SQLException {
										HELPDESK_Category cat = new HELPDESK_Category();
										cat.setCATEGORY_ID(rs
												.getString("CATEGORY_ID"));
										cat.setCATEGORY_NAME(rs
												.getString("CATEGORY_NAME"));
										cat.setCATEGORY_STATUS(rs
												.getString("CATEGORY_STATUS"));
										cat.setSUBCATEGORY_ID(rs
												.getString("SUBCATEGORY_ID"));
										cat.setSUBCATEGORY_NAME(rs
												.getString("SUBCATEGORY_NAME"));
										cat
												.setSUBCATEGORY_STATUS(rs
														.getString("SUBCATEGORY_STATUS"));
										cat
												.setIS_CHANGE_REQUEST(rs
														.getString("IS_CHANGE_REQUEST"));
										cat
												.setRECOMMENDED_PRIORITY(rs
														.getString("RECOMMENDED_PRIORITY"));
										cat.setAPPROVER_LEVEL_1(rs
												.getString("APPROVER_LEVEL_1"));
										cat.setAPPROVER_LEVEL_2(rs
												.getString("APPROVER_LEVEL_2"));
										cat.setAPPROVER_LEVEL_3(rs
												.getString("APPROVER_LEVEL_3"));
										cat
												.setAPPROVER_LEVEL_STATUS_1(rs
														.getString("APPROVER_LEVEL_STATUS_1"));
										cat
												.setAPPROVER_LEVEL_STATUS_2(rs
														.getString("APPROVER_LEVEL_STATUS_2"));
										cat
												.setAPPROVER_LEVEL_STATUS_3(rs
														.getString("APPROVER_LEVEL_STATUS_3"));
										cat.setLINK(rs.getString("LINK"));//ADDED BY SALI
										return cat;
									}

								});

				int isChangeReq = 0;
				int priorityID = 1;

				if (helpdeskCategory.getRECOMMENDED_PRIORITY()
						.equalsIgnoreCase("Medium")) {
					priorityID = 2;
				} else if (helpdeskCategory.getRECOMMENDED_PRIORITY()
						.equalsIgnoreCase("High")) {
					priorityID = 3;
				}
				if (helpdeskCategory.getIS_CHANGE_REQUEST().equalsIgnoreCase(
						"Yes")) {
					isChangeReq = 1;
				}

				String updateQueryCategory = ADMIN_SettingsSQLQueryConstants.UPDATE_CATEGORY_MASTER;
				String updateQuerySubCategory = ADMIN_SettingsSQLQueryConstants.UPDATE_SUBCATEGORY_MASTER;
				/**
				 * Update IC_IHD_CATEGORY_APPROVAL_DETAILS and
				 * IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS table on the basis of
				 * SubCategory
				 */

				if (isChangeReq == 0) {
					// IC_IHD_CATEGORY_APPROVAL_DETAILS
					this.jdbcTemplate
							.update(ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_CATEGORY_APPROVAL_DETAILS,loginID,helpdeskCategory.getSUBCATEGORY_ID());

					// IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS
					this.jdbcTemplate
					.update(ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS,loginID,helpdeskCategory.getSUBCATEGORY_ID());
				}

				/**
				 * Update Category Master on the basis of Category Id and Sub
				 * Category Id
				 */
				int catStatus = 0;
				int subcatStatus = 0;
				if (helpdeskCategory.getCATEGORY_STATUS().equals("Active")) {
					catStatus = 1;
				}
				if (helpdeskCategory.getSUBCATEGORY_STATUS().equals("Active")) {
					subcatStatus = 1;
				}
				
		
				
				this.jdbcTemplate
						.update(updateQueryCategory, new Object[] {
								helpdeskCategory.getCATEGORY_NAME(),
								priorityID, catStatus, loginID,
								helpdeskCategory.getCATEGORY_ID() });
				this.jdbcTemplate.update(updateQuerySubCategory,new Object[]{
								helpdeskCategory.getSUBCATEGORY_NAME(),
								isChangeReq,subcatStatus,priorityID,
								helpdeskCategory.getLINK(),helpdeskCategory.getLINK(),//L2: 2751
								loginID,helpdeskCategory.getSUBCATEGORY_ID()});//Added by sali
				/**
				 * call function to update approvers Get the sub category id
				 * 
				 * Get the list of approvers for this sub category ide
				 * 
				 * 
				 * 
				 */
				String subCategoryId = helpdeskCategory.getSUBCATEGORY_ID();
				int countOfApproverOrder = this.jdbcTemplate
						.queryForInt(
								ADMIN_SettingsSQLQueryConstants.SELECT_COUNT_APPROVER_ORDER_FOR_CATEGORY,
								new Object[] { subCategoryId });

				String sql = ADMIN_SettingsSQLQueryConstants.INSERT_CATEGORY_APPROVAL_DETAILS;

				if (countOfApproverOrder == 0) {
					if (helpdeskCategory.getAPPROVER_LEVEL_1().trim().length() > 0) {

						int approverIDe = this.jdbcTemplate
								.queryForInt(
										ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER,
										helpdeskCategory.getAPPROVER_LEVEL_1()
												.toString());
						this.jdbcTemplate
								.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 1, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_1()),
										loginID);
					}
					if (helpdeskCategory.getAPPROVER_LEVEL_2().trim().length() > 0) {

						int approverIDe = this.jdbcTemplate.queryForInt(ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER,helpdeskCategory
								.getAPPROVER_LEVEL_2().toString());
						this.jdbcTemplate
								.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 2, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_2()),
										loginID);
					}

					if (helpdeskCategory.getAPPROVER_LEVEL_3().trim().length() > 0) {
						int approverIDe = this.jdbcTemplate.queryForInt(ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER,helpdeskCategory
								.getAPPROVER_LEVEL_3().toString());
						this.jdbcTemplate
								.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 3, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_3()),
										loginID);
					}

				} else {
					if (countOfApproverOrder >= 1) {
						int countOfApp = this.jdbcTemplate
								.queryForInt(ADMIN_SettingsSQLQueryConstants.COUNT_FOR_APPROVAL_DETAILS,
										helpdeskCategory.getSUBCATEGORY_ID(),"1");
						if (countOfApp >= 1 && helpdeskCategory.getAPPROVER_LEVEL_1().length() > 0) {
							
							int approverIDe = this.jdbcTemplate
									.queryForInt(
											ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
											helpdeskCategory
											.getAPPROVER_LEVEL_1().toString());
							this.jdbcTemplate
									.update(ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_CATEGORY_APPROVAL_DETAILS_STATUS,
											"1",approverIDe,
											getApproverStatus(helpdeskCategory
													.getAPPROVER_LEVEL_STATUS_1()),
											loginID, "1",helpdeskCategory
													.getSUBCATEGORY_ID());
						} else {
							if (helpdeskCategory.getAPPROVER_LEVEL_1().length() > 0) {
								int approverIDe = this.jdbcTemplate
										.queryForInt(
												ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
												helpdeskCategory
												.getAPPROVER_LEVEL_1().toString());
								this.jdbcTemplate.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 1, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_1()),
										loginID);
							}

						}

						countOfApp = this.jdbcTemplate
								.queryForInt(ADMIN_SettingsSQLQueryConstants.COUNT_FOR_APPROVAL_DETAILS,
										helpdeskCategory.getSUBCATEGORY_ID(),"2");
						if (countOfApp >= 1 && helpdeskCategory.getAPPROVER_LEVEL_2().length() > 0) {
							int approverIDe = this.jdbcTemplate
									.queryForInt(
											ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
											helpdeskCategory
											.getAPPROVER_LEVEL_2().toString());
							
							this.jdbcTemplate
							.update(ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_CATEGORY_APPROVAL_DETAILS_STATUS,
									"2",approverIDe,
									getApproverStatus(helpdeskCategory
											.getAPPROVER_LEVEL_STATUS_2()),
									loginID,"2", helpdeskCategory
											.getSUBCATEGORY_ID());
						} else {
							if (helpdeskCategory.getAPPROVER_LEVEL_2().length() > 0) {
								int approverIDe = this.jdbcTemplate
										.queryForInt(
												ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
												helpdeskCategory
												.getAPPROVER_LEVEL_2().toString());
								this.jdbcTemplate.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 2, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_2()),
										loginID);
							}

						}

						countOfApp = this.jdbcTemplate
								.queryForInt(ADMIN_SettingsSQLQueryConstants.COUNT_FOR_APPROVAL_DETAILS,
										helpdeskCategory.getSUBCATEGORY_ID(),"3");
						if (countOfApp >= 1 && helpdeskCategory.getAPPROVER_LEVEL_3().length() > 0) {
							int approverIDe = this.jdbcTemplate
									.queryForInt(
											ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
											helpdeskCategory
											.getAPPROVER_LEVEL_3().toString());
							this.jdbcTemplate
							.update(ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_CATEGORY_APPROVAL_DETAILS_STATUS,
									"3",approverIDe,
									getApproverStatus(helpdeskCategory
											.getAPPROVER_LEVEL_STATUS_3()),
									loginID,"3", helpdeskCategory
											.getSUBCATEGORY_ID());
						} else {
							if (helpdeskCategory.getAPPROVER_LEVEL_3().length() > 0) {
								int approverIDe = this.jdbcTemplate
										.queryForInt(
												ADMIN_SettingsSQLQueryConstants.SELECT_IC_IHD_APPROVER_MASTER_ISACTIVE,
												helpdeskCategory
												.getAPPROVER_LEVEL_3().toString());
								this.jdbcTemplate.update(sql, helpdeskCategory
										.getSUBCATEGORY_ID(), 3, approverIDe,
										getApproverStatus(helpdeskCategory
												.getAPPROVER_LEVEL_STATUS_3()),
										loginID);
							}
						}
					}
				}

				/*********************************** Category Audit Detail *********************************************/
				/**
				 * Modify Category : Menu ID:139
				 */
				StringBuffer dataChangeXml = new StringBuffer("");
				if (oldCategoryObj != null) {
					HELPDESK_Category oldCatObj = new HELPDESK_Category();
					oldCatObj = oldCategoryObj.get(0);
					dataChangeXml = new StringBuffer("");
					dataChangeXml = new StringBuffer("<data_changed>");

					dataChangeXml.append("<CATEGORY>");
					dataChangeXml.append("<ID>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getCATEGORY_ID()).append("</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getCATEGORY_ID()).append(
							"</new_value>");
					dataChangeXml.append("</ID>");
					dataChangeXml.append("<NAME>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getCATEGORY_NAME())
							.append("</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getCATEGORY_NAME()).append(
							"</new_value>");
					dataChangeXml.append("</NAME>");
					dataChangeXml.append("<STATUS>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getCATEGORY_STATUS()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getCATEGORY_STATUS()).append(
							"</new_value>");
					dataChangeXml.append("</STATUS>");
					dataChangeXml.append("</CATEGORY>");
					dataChangeXml.append("<SUB_CATEGORY>");
					dataChangeXml.append("<ID>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getSUBCATEGORY_ID()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getSUBCATEGORY_ID()).append(
							"</new_value>");
					dataChangeXml.append("</ID>");
					dataChangeXml.append("<NAME>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getSUBCATEGORY_NAME()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getSUBCATEGORY_NAME()).append(
							"</new_value>");
					dataChangeXml.append("</NAME>");
					dataChangeXml.append("<STATUS>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getSUBCATEGORY_STATUS()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getSUBCATEGORY_STATUS()).append(
							"</new_value>");
					dataChangeXml.append("</STATUS>");
					dataChangeXml.append("</SUB_CATEGORY>");
					dataChangeXml.append("<APPROVER_LEVEL1>");
					dataChangeXml.append("<NAME>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_1()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_1()).append(
							"</new_value>");
					dataChangeXml.append("</NAME>");
					dataChangeXml.append("<STATUS>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_STATUS_1()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_STATUS_1())
							.append("</new_value>");
					dataChangeXml.append("</STATUS>");
					dataChangeXml.append("</APPROVER_LEVEL1>");
					dataChangeXml.append("<APPROVER_LEVEL2>");
					dataChangeXml.append("<NAME>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_2()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_2()).append(
							"</new_value>");
					dataChangeXml.append("</NAME>");
					dataChangeXml.append("<STATUS>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_STATUS_2()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_STATUS_2())
							.append("</new_value>");
					dataChangeXml.append("</STATUS>");
					dataChangeXml.append("</APPROVER_LEVEL2>");
					dataChangeXml.append("<APPROVER_LEVEL3>");
					dataChangeXml.append("<NAME>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_3()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_3()).append(
							"</new_value>");
					dataChangeXml.append("</NAME>");
					dataChangeXml.append("<STATUS>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getAPPROVER_LEVEL_STATUS_3()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getAPPROVER_LEVEL_STATUS_3())
							.append("</new_value>");
					dataChangeXml.append("</STATUS>");
					dataChangeXml.append("</APPROVER_LEVEL3>");
					dataChangeXml.append("<REC_PRTY>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getRECOMMENDED_PRIORITY()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getRECOMMENDED_PRIORITY()).append(
							"</new_value>");
					dataChangeXml.append("</REC_PRTY>");
					dataChangeXml.append("<IS_CHANGE_REQUEST>");
					dataChangeXml.append("<old_value>").append(
							oldCatObj.getIS_CHANGE_REQUEST()).append(
							"</old_value>");
					dataChangeXml.append("<new_value>").append(
							helpdeskCategory.getIS_CHANGE_REQUEST()).append(
							"</new_value>");
					dataChangeXml.append("</IS_CHANGE_REQUEST>");
					dataChangeXml.append("</data_changed>");
				}

				this.jdbcTemplate
						.update(
								ADMIN_SettingsSQLQueryConstants.INSERT_AUDIT_DET_CATEGORY,
								new Object[] {
										helpdeskCategory.getCATEGORY_ID(),
										"141",
										null,
										null,
										"U",
										dataChangeXml.toString(),
										loginID,
										CustomDateFormatConstants
												.creationDateGMTFormat(),
										loginID });
			}
		} catch (Exception e) {
			log.error("Error While updating category:" + e);

			result = "Error";
		}

		return result;
	}

	public int getApproverStatus(String statusName) {
		if (statusName.equals("1")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	

	public void groupMemberAudit(String groupID, String groupMemID,
			String oldStatus, String newStatus, String loginID) {
		// Auditlog
		StringBuffer dataChange = new StringBuffer();
		dataChange.append("<data_changed><GROUP>");
		dataChange.append("</GROUP><GROUP_MEMBER_DETAILS>");
		dataChange.append("<GROUP_MEMBER_ID>").append("<OLD_VALUE>").append(
				groupMemID).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(groupMemID).append("</NEW_VALUE>").append(
						"</GROUP_MEMBER_ID>");
		dataChange.append("<IS_ACTIVE>").append("<OLD_VALUE>")
				.append(oldStatus).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(newStatus).append("</NEW_VALUE>")
				.append("</IS_ACTIVE>");
		dataChange.append("</GROUP_MEMBER_DETAILS><data_changed>");

		String auditData = dataChange.toString();

		
		this.jdbcTemplate
		.update(
				ADMIN_SettingsSQLQueryConstants.IC_AUDIT_LOG_DETAILS,
				groupID,"135",
				"U", auditData, loginID,
				CustomDateFormatConstants
						.creationDateGMTFormat(),
				loginID,CustomDateFormatConstants
				.creationDateISTFormat_iTrack(),"1");


	}

	// Added for admin screen role manipulation
	public List<Map<String, Object>> getEmployeeRoleDetail(String employeeId) {
		return this.jdbcTemplate
				.queryForList(ADMIN_SettingsSQLQueryConstants.SELECT_ROLE_DETAILS,
						employeeId);
	}
	
	public String updateRoleDetails(final JSONArray jsonArrayObj,final String loginID) throws JSONException{
		
		final List<String> locArr=new ArrayList<String>();
		int[] row = null ;
		if(jsonArrayObj.length()>0){
			 row = jdbcTemplate
			.batchUpdate(
					ADMIN_SettingsSQLQueryConstants.UPDATE_ROLE_MANIPULATION,
					new BatchPreparedStatementSetter() {
	
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							try {
								JSONArray subMemArray = (JSONArray) jsonArrayObj
										.get(i);
								ps.setString(1, subMemArray.get(2)
										.toString());
								ps.setString(2, loginID);
								ps.setString(3, CustomDateFormatConstants.creationDateGMTFormat());
								ps.setString(4, subMemArray.get(0)
										.toString());
								ps.setString(5, subMemArray.get(1)
										.toString());
								if(subMemArray.get(0)
										.toString().equalsIgnoreCase("10")){
									locArr.add(subMemArray.get(3)
											.toString());
								}else{								
									roleMemberAudit(subMemArray.get(0)
											.toString(),subMemArray.get(1)
											.toString(),subMemArray.get(4)
											.toString(),subMemArray.get(2)
											.toString(),loginID);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
	
						}
	
						public int getBatchSize() {
							return jsonArrayObj.length();
						}
			});
			if(locArr.size()>0){
				int[] rowLoc = jdbcTemplate
				.batchUpdate(
						ADMIN_SettingsSQLQueryConstants.UPDATE_ROLE_MANIPULATION_LOC,
						new BatchPreparedStatementSetter() {
		
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								try {
									JSONArray subMemArray = (JSONArray) jsonArrayObj
											.get(i);
									/*if(subMemArray.get(0)
											.toString().equalsIgnoreCase("10")){*/
									   ps.setString(1, subMemArray.get(1)
											.toString());
									   ps.setString(2, subMemArray.get(3)
												.toString());
										ps.setString(3, subMemArray.get(2)
												.toString());
										ps.setString(4, loginID);
										ps.setString(5, CustomDateFormatConstants.creationDateGMTFormat());
										ps.setString(6, subMemArray.get(3)
												.toString());
										ps.setString(7, subMemArray.get(1)
												.toString());
										roleMemberAuditApproval(subMemArray.get(0)
												.toString(),subMemArray.get(1)
												.toString(),subMemArray.get(4)
												.toString(),subMemArray.get(2)
												.toString(),loginID,subMemArray.get(3)
												.toString());
									//}
		
								} catch (JSONException e) {
									e.printStackTrace();
								}
		
							}
		
							public int getBatchSize() {
								return jsonArrayObj.length();
							}
				});
				
				//for getting empId 
				JSONArray subMemArray = (JSONArray) jsonArrayObj
				.get(0);
				jdbcTemplate
				.update(
						"IF EXISTS (SELECT 1 FROM IC_IHD_APPROVER_EMPLOYEE_DETAILS WHERE EMPLOYEE_ID = ? AND IS_ACTIVE=1)"
								+ "UPDATE IC_USER_ROLE_DETAILS SET IS_ACTIVE=?,MODIFIED_BY=?,MODIFIED_DATE=? WHERE EMPLOYEE_ID=? " +
										" AND ROLE_ID=10 ELSE UPDATE IC_USER_ROLE_DETAILS SET IS_ACTIVE=?,MODIFIED_BY=?,MODIFIED_DATE=? WHERE EMPLOYEE_ID=? " +
										" AND ROLE_ID=10",
										subMemArray.get(1)
										.toString(),
						"1",loginID,
						CustomDateFormatConstants.creationDateGMTFormat(),
						subMemArray.get(1)
						.toString(),"0",loginID,
						CustomDateFormatConstants.creationDateGMTFormat(),
						subMemArray.get(1)
						.toString());
				String currStatus=jdbcTemplate.queryForObject("SELECT IS_ACTIVE FROM IC_USER_ROLE_DETAILS " +
						"WHERE EMPLOYEE_ID=? AND ROLE_ID=10",new Object[] { subMemArray.get(1)
						.toString()}, String.class);
				String oldStatus="";
				if(currStatus=="1"){
					oldStatus="0";
				}else{
					oldStatus="1";
				}
				roleMemberAudit("10",subMemArray.get(1)
						.toString(),oldStatus,currStatus,loginID);
			}
		}
		if (row.length > 0)
			return "Success";
		else
			return "NoChange";
	}
	
	public List<Map<String, Object>> getAddRoleDetails(String employeeId) {
		return this.jdbcTemplate
				.queryForList(ADMIN_SettingsSQLQueryConstants.SELECT_ROLE_FOR_ADDITION,
						employeeId);
	}
	
	public List<Map<String, Object>> getAddRoleLocDetails(String employeeId) {
		return this.jdbcTemplate
				.queryForList(ADMIN_SettingsSQLQueryConstants.SELECT_IT_FUNHEAD_ROLE_LOC,
						employeeId);
	}
	
   public String insertRoleDetails(final JSONObject jsonArrayObj,final JSONObject locArrayObj,final String loginID) throws JSONException{
		
	    int row =0;
	    if((jsonArrayObj.has("EmpId")) && (!jsonArrayObj.get("RoleId").toString().
	    		equalsIgnoreCase("--Please Select--"))){
			row = jdbcTemplate.update(
					ADMIN_SettingsSQLQueryConstants.INSERT_USER_ROLE_DETAILS,jsonArrayObj.get("EmpId").toString()
				      , jsonArrayObj.get("RoleId").toString(),jsonArrayObj.get("EmpId").toString()
					      , jsonArrayObj.get("RoleId").toString(),"1", loginID
					      , CustomDateFormatConstants.creationDateGMTFormat());
	    }
		if(locArrayObj.getJSONArray("Location").length()>0){
			row =row+1;
			final int arrLen=locArrayObj.getJSONArray("Location").length();
			int[] rowLoc = jdbcTemplate
			.batchUpdate(
					ADMIN_SettingsSQLQueryConstants.INSERT_IC_IHD_APPROVER_EMPLOYEE_DETAILS,
					new BatchPreparedStatementSetter() {
	
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							try {
									ps.setString(1, "2");
									ps.setString(2, jsonArrayObj.get("EmpId").toString());
									ps.setString(3, "1");
									ps.setString(4, loginID);
									ps.setString(5, CustomDateFormatConstants.creationDateGMTFormat());									
									ps.setString(6, locArrayObj.getJSONArray("Location").get(i).toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
	
						}
						public int getBatchSize() {
							return arrLen;
						}
			});
		}
		
		if(row>0)
			return "Success";
		else
			return "NoChange";
	}
   
   public void roleMemberAudit(String roleID, String roleMemID,
			String oldStatus, String newStatus, String loginID) {
		// Auditlog
		StringBuffer dataChange = new StringBuffer();
		dataChange.append("<data_changed><ROLE>");
		dataChange.append("</ROLE><ROLE_MEMBER_DETAILS>");
		dataChange.append("<ROLE_MEMBER_ID>").append("<OLD_VALUE>").append(
				roleMemID).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(roleMemID).append("</NEW_VALUE>").append(
						"</ROLE_MEMBER_ID>");
		dataChange.append("<IS_ACTIVE>").append("<OLD_VALUE>")
				.append(oldStatus).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(newStatus).append("</NEW_VALUE>")
				.append("</IS_ACTIVE>");
		dataChange.append("</ROLE_MEMBER_DETAILS><data_changed>");

		String auditData = dataChange.toString();
		
		this.jdbcTemplate
		.update(
				ADMIN_SettingsSQLQueryConstants.IC_AUDIT_LOG_DETAILS,
				roleID,"135",
				"U", auditData, loginID,
				CustomDateFormatConstants
						.creationDateGMTFormat(),
				loginID,CustomDateFormatConstants
				.creationDateISTFormat_iTrack(),"1");
	}
   
   public void roleMemberAuditApproval(String roleID, String roleMemID,
			String oldStatus, String newStatus, String loginID,String locId) {
		// Auditlog
		StringBuffer dataChange = new StringBuffer();
		dataChange.append("<data_changed><ROLE>");
		dataChange.append("</ROLE><ROLE_MEMBER_DETAILS>");
		dataChange.append("<ROLE_MEMBER_ID>").append("<OLD_VALUE>").append(
				roleMemID).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(roleMemID).append("</NEW_VALUE>").append(
						"</ROLE_MEMBER_ID>");
		dataChange.append("<ROLE_APPROVER_ID>").append("<OLD_VALUE>").append(
				"2").append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append("2").append("</NEW_VALUE>").append(
						"</ROLE_APPROVER_ID>");
		dataChange.append("<ROLE_LOCATION_ID>").append("<OLD_VALUE>").append(
				locId).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(locId).append("</NEW_VALUE>").append(
						"</ROLE_LOCATION_ID>");
		dataChange.append("<IS_ACTIVE>").append("<OLD_VALUE>")
				.append(oldStatus).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(newStatus).append("</NEW_VALUE>")
				.append("</IS_ACTIVE>");
		dataChange.append("</ROLE_MEMBER_DETAILS><data_changed>");

		String auditData = dataChange.toString();

		
		this.jdbcTemplate
		.update(
				ADMIN_SettingsSQLQueryConstants.IC_AUDIT_LOG_DETAILS,
				roleID,"135",
				"U", auditData, loginID,
				CustomDateFormatConstants
						.creationDateGMTFormat(),
				loginID,CustomDateFormatConstants
				.creationDateISTFormat_iTrack(),"1");
	}
   public String updateGroupRoleDetails(final JSONArray jsonArrayObj,final String loginID) throws JSONException{
		
		int[] row = null ;
		if(jsonArrayObj.length()>0){
			 row = jdbcTemplate
			.batchUpdate(
					ADMIN_SettingsSQLQueryConstants.IC_IHD_UPDATE_MEMBER_ROLE_STATUS,
					new BatchPreparedStatementSetter() {
	
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							try {
								
								JSONArray subMemArray = (JSONArray) jsonArrayObj
										.get(i);
								ps.setString(1, subMemArray.get(0)
										.toString());
								ps.setString(2, subMemArray.get(1)
										.toString());
								ps.setString(3, loginID);
								ps.setString(4, CustomDateFormatConstants.creationDateGMTFormat());
								ps.setString(5, subMemArray.get(2)
										.toString());
								ps.setString(6, subMemArray.get(3)
										.toString());
								
								groupMemberRoleAudit(subMemArray.get(3)
										.toString(),subMemArray.get(2)
										.toString(),subMemArray.get(5)
										.toString(),subMemArray.get(1)
										.toString(),loginID,subMemArray.get(4)
										.toString(),subMemArray.get(0)
										.toString());
	
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						public int getBatchSize() {
							return jsonArrayObj.length();
						}
			});
		}
		if (row.length > 0)
			return "Success";
		else
			return "NoChange";
	}
  
  public void groupMemberRoleAudit(String groupID, String groupMemID,
			String oldStatus, String newStatus, String loginID,String oldRole,String newRole) {
		// Auditlog
		StringBuffer dataChange = new StringBuffer();
		dataChange.append("<data_changed><GROUP_MEMBER_DETAILS><GROUP>");
		dataChange.append("<OLD_VALUE>").append(
				groupID).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(groupID).append("</NEW_VALUE>");
		dataChange.append("</GROUP><GROUP_MEMBER_DETAILS>");
		dataChange.append("<GROUP_MEMBER_ID>").append("<OLD_VALUE>").append(
				groupMemID).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(groupMemID).append("</NEW_VALUE>").append(
						"</GROUP_MEMBER_ID>");
		dataChange.append("<IS_ACTIVE>").append("<OLD_VALUE>")
				.append(oldStatus).append("</OLD_VALUE>").append("<NEW_VALUE>")
				.append(newStatus).append("</NEW_VALUE>")
				.append("</IS_ACTIVE>");
		dataChange.append("<GROUP_ROLE>").append("<OLD_VALUE>")
		.append(oldRole).append("</OLD_VALUE>").append("<NEW_VALUE>")
		.append(newRole).append("</NEW_VALUE>")
		.append("</GROUP_ROLE>");
		dataChange.append("</GROUP_MEMBER_DETAILS><data_changed>");

		String auditData = dataChange.toString();

		this.jdbcTemplate
		.update(
				ADMIN_SettingsSQLQueryConstants.IC_AUDIT_LOG_DETAILS,
				groupID,"135",
				"U", auditData, loginID,
				CustomDateFormatConstants
						.creationDateGMTFormat(),
				loginID,CustomDateFormatConstants
				.creationDateISTFormat_iTrack(),"1");

	}
  
	// Added for Excel upload in admin screen  by anjana
	/*public String updateTable(Vector cellVectorHolder, String loginID,
			ADMIN_ExcelUpload eUpload) throws SQLException {

		int row = 0;
		int i=0;
		int j=0;
		Throwable message = null;
		boolean uFlag=false;
		
		  --------- new query format -----------  
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sbvalues = new StringBuffer();
		sb.append("INSERT INTO ").append(eUpload.getTableName()).append("(");
		try {
		for (i = 0; i < cellVectorHolder.size(); i++) {
			Vector cellStoreVector = (Vector) cellVectorHolder.elementAt(i);

			for (j = 0; j < cellStoreVector.size(); j++) {
				Cell myCell = (Cell) cellStoreVector.elementAt(j);
				String st = myCell.toString();
				st = checkvalue(st, myCell, j);
				if (i == 0) {					
					sb = sb.append(st);
					if (cellStoreVector.size() != j + 1) {
						sb = sb.append(",");

					} else {
						sb = sb.append(" ) values ");
					}
					
				} 
				else {
					uFlag=true;
					if (j == 0) {
						sbvalues.setLength(0);
						sbvalues.append(sb);
						sbvalues = sbvalues.append("(");
					}
					sbvalues.append("'").append(st).append("'");
					if (cellStoreVector.size() != j + 1) {
						sbvalues = sbvalues.append(",");
					} else {
						sbvalues = sbvalues.append(" ) ");
					}
				}

			}
			
			if(uFlag)
			{
			this.jdbcTemplate.execute(sbvalues.toString());
			row++;
			uFlag=false;
			}
			sbvalues.setLength(0);
		}			
		} catch (Exception e) {
			row = 0;
			String value1 = e.toString();
			String value = value1.replace("'", "");
			message = e.getCause();
			String l_description="Error message:"+ value+", at Row:"+i+"in Sheet:"+eUpload.getSheetName();
			try {
				this.jdbcTemplate
						.update(ADMIN_SettingsSQLQueryConstants.INSERT_EXCEPTIONAL_FILEUPLOAD,
								l_description, CustomDateFormatConstants
										.creationDateGMTFormat(), loginID);
			} catch (Exception e1) {
				log.error("Exception occured in updateTable", e1);				
			}
		}
		if (row > 0) {
			return "SUCCESS";
		} else {
			return "FALSE -" + message;
		}
	}

public String checkvalue(String st, Cell myCell, int j) {
	
	String strValue="";
	if(myCell.getCellType()==myCell.CELL_TYPE_NUMERIC)
	{		
		if(DateUtil.isCellDateFormatted(myCell))
		{
			Date date = new Date(st);
		    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        strValue=format.format(date);
				  
		}
		 else
		 {	
			 int n1 = st.indexOf('.');
			 strValue=st.substring(0, n1);	
		   	     
		 }
	}
	else if(myCell.getCellType()==myCell.CELL_TYPE_BLANK)
	{
		strValue="NULL";	
	}
	else
	{
		strValue=st;
	}
	  return strValue;
	}*/
//Autocomeplte

public  List<Map<String, Object>>  getTablelist(String query) {
	String tablename_QUERY = COMMON_CacheSQLQueryConstants.ISL_TABLE_NAMELIST_SQL;
	StringBuffer tablename = new StringBuffer();
	tablename.append(tablename_QUERY);
	tablename.append(" and t.name LIKE  '%" + query.trim()+ "%'");
	return this.jdbcTemplate.queryForList(tablename.toString());
}


public String createUserProfile(ADMIN_CreateUserBO aDMIN_CreateUserBO,
			String loginID) {
		if (null != aDMIN_CreateUserBO.getButtonType()
				&& aDMIN_CreateUserBO.getButtonType().equalsIgnoreCase("Add")) {
			
			String ldapCheckForEmpID=IsUserExist(aDMIN_CreateUserBO.getEmployeeId());
			System.out.println("ldapCheckForEmpID:::"+ldapCheckForEmpID);
			if(null!=ldapCheckForEmpID){

			if (null != aDMIN_CreateUserBO.getDuId()
					&& !aDMIN_CreateUserBO.getDuId().equalsIgnoreCase("")
					&& this.jdbcTemplate
							.queryForInt(
									ADMIN_SettingsSQLQueryConstants.CHECK_DUID_AVAILABILITY,
									aDMIN_CreateUserBO.getDuId()) < 1) {
				return "Invalid DU ID.";
			}

			if (null != aDMIN_CreateUserBO.getReportingManager()
					&& !aDMIN_CreateUserBO.getReportingManager()
							.equalsIgnoreCase("")
					&& this.jdbcTemplate
							.queryForInt(
									ADMIN_SettingsSQLQueryConstants.CHECK_USER_AVAILABILITY,
									aDMIN_CreateUserBO.getReportingManager()) < 1) {
				return "Invalid RO.";
			}

			if (this.jdbcTemplate.queryForInt(
					ADMIN_SettingsSQLQueryConstants.CHECK_USER_AVAILABILITY,
					aDMIN_CreateUserBO.getEmployeeId()) < 1) {

				try {
					this.jdbcTemplate
							.update(ADMIN_SettingsSQLQueryConstants.INSERT_INTO_IC_USER_DETAILS,
									aDMIN_CreateUserBO.getEmployeeId(),
									aDMIN_CreateUserBO.getEmployeeName(),
									aDMIN_CreateUserBO.getEmailAddress(),
									aDMIN_CreateUserBO.getReportingManager(),
									aDMIN_CreateUserBO.getLocationId(),
									aDMIN_CreateUserBO.getTimeZone(),
									aDMIN_CreateUserBO.getDuId(),
									aDMIN_CreateUserBO.getMobilenumber(),
									aDMIN_CreateUserBO.getExtNumber(),
									aDMIN_CreateUserBO.getGender(), "I", "1",
									aDMIN_CreateUserBO.getStatus(), loginID);

					this.jdbcTemplate
							.update(ADMIN_SettingsSQLQueryConstants.IC_IHD_ADD_ROLE_TO_GROUP_MEMBER,
									aDMIN_CreateUserBO.getEmployeeId(), "1",
									"0", "1", loginID,
									CustomDateFormatConstants
											.creationDateGMTFormat(),
									aDMIN_CreateUserBO.getEmployeeId(), "1",
									aDMIN_CreateUserBO.getEmployeeId(), "1",
									"1", loginID, CustomDateFormatConstants
											.creationDateGMTFormat());
				} catch (DataAccessException e) {
					e.printStackTrace();
					return "Updation failed.";
				}

			} else {
				return "User profile already exists.";
			}
		}else{
			return "Employee Id not present in Active Directory..";
		}
		} else if (null != aDMIN_CreateUserBO.getButtonType()
				&& aDMIN_CreateUserBO.getButtonType().equalsIgnoreCase("Edit")) {
			
			int count = this.jdbcTemplate.queryForInt(ADMIN_SettingsSQLQueryConstants.VALIDATE_USER_DETAILS,aDMIN_CreateUserBO.getEmployeeId(),
					aDMIN_CreateUserBO.getEmployeeName(),
					aDMIN_CreateUserBO.getEmailAddress(),
					aDMIN_CreateUserBO.getReportingManager(),
					aDMIN_CreateUserBO.getLocationId(),
					aDMIN_CreateUserBO.getTimeZone(),
					aDMIN_CreateUserBO.getDuId(),
					aDMIN_CreateUserBO.getMobilenumber(),
					aDMIN_CreateUserBO.getExtNumber(),
					aDMIN_CreateUserBO.getGender(),
					aDMIN_CreateUserBO.getStatus());
			
			if(count!=1){
			
			
			 try {
				this.jdbcTemplate.update(
						ADMIN_SettingsSQLQueryConstants.UPDATE_IC_USER_DETAILS,
							aDMIN_CreateUserBO.getEmployeeId(),
						aDMIN_CreateUserBO.getEmployeeName(),
						aDMIN_CreateUserBO.getEmailAddress(),
						aDMIN_CreateUserBO.getReportingManager(),
						aDMIN_CreateUserBO.getLocationId(),
						aDMIN_CreateUserBO.getTimeZone(),
						aDMIN_CreateUserBO.getDuId(),
						aDMIN_CreateUserBO.getMobilenumber(),
						aDMIN_CreateUserBO.getExtNumber(),
						aDMIN_CreateUserBO.getGender(),
						aDMIN_CreateUserBO.getStatus(), loginID);
				return "Success";
			} catch (DataAccessException e) {
				log.error(e);
				return "Updation failed.";
			}
			}else{
			
				return "No Changes Made.";
			}
			
		}
		return "Success";
	}

	public List<Map<String, Object>> getLHProjects() {
		return this.jdbcTemplate
				.queryForList(ADMIN_SettingsSQLQueryConstants.LH_PROJECT_DETAILS);

	}
	
	public String insertLHProjectData(String projectId,String loginId){
		
		if(this.jdbcTemplate.queryForInt(ADMIN_SettingsSQLQueryConstants.CHECK_LHPROJECT_AVAILABILITY, projectId)>=1){
			return "Project ID already mapped.";
		}else{
			try {
				this.jdbcTemplate.update(ADMIN_SettingsSQLQueryConstants.INSERT_INTO_IC_LH_PROJECT_MASTER,projectId,loginId);
			} catch (DataAccessException e) {
				e.printStackTrace();
				return "Project ID not Exist/InActive in Project Master.";
			}
		}
		
		return "Success";
	}
	
	
	public List<Map<String, Object>> getEmployeeProfInfo(String employeeId) {
		return this.jdbcTemplate
				.queryForList(
						ADMIN_SettingsSQLQueryConstants.IC_USER_DETAILS_FOR_EDIT_USER_PROFILE,
						employeeId);
	}

	
	
public String updateProjectDetails(final JSONArray jsonArrayObj,final String loginID){
		
		int[] row = null ;
		if(jsonArrayObj.length()>0){
			 row = jdbcTemplate
			.batchUpdate(
					ADMIN_SettingsSQLQueryConstants.UPDATE_IC_IHD_PROJECT_MASTER,
					new BatchPreparedStatementSetter() {
	
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							try {
								JSONArray proj = (JSONArray) jsonArrayObj
										.get(i);
								ps.setString(1, proj.get(1)
										.toString());
								ps.setString(2, loginID);
								ps.setString(3, proj.get(0)
										.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						public int getBatchSize() {
							return jsonArrayObj.length();
						}
			});
		}
		if (row.length > 0)
			return "Success";
		else
			return "NoChange";
	}


private boolean checkSpecialCharacter(String loginId) {
    String invalidEntries = LoginSQLQueryConstants.INVALID;
    boolean invalidFound = false;
    for (int i = 0; i < invalidEntries.length(); i++) {
        if (loginId.indexOf(invalidEntries.charAt(i)) != -1) {
            invalidFound = true;
            break;
        }
    }
    return invalidFound;

}

@SuppressWarnings("unchecked")
public String IsUserExist(final String empmID) {
    if (checkSpecialCharacter(empmID))
        return null;
    List<String> membersList = new ArrayList<String>();
    try{
    membersList = ldapTemplate.search(DistinguishedName.EMPTY_PATH,
            "(samaccountname=" + empmID + ")", new AttributesMapper() {
                public Object mapFromAttributes(Attributes attrs)
                        throws javax.naming.NamingException {
                    
                    
                    String samaccountname ="";
                    if(attrs.get(EMPLOYEE_ID)!=null)
                    {
                    		samaccountname=attrs.get(EMPLOYEE_ID).get().toString();
                    }
                    else
                    {
                    	samaccountname=attrs.get(SAME_ACCOUNT_NAME).get().toString();
                    }
                    	
                    
                    samaccountname +="-"+attrs.get(SAME_ACCOUNT_NAME).get().toString();
                    return samaccountname;
                }
            });
    if(membersList.size()==0)
    {
           membersList = ldapTemplate.search(DistinguishedName.EMPTY_PATH,
                 "(employeeid=" + empmID + ")", new AttributesMapper() {
                     public Object mapFromAttributes(Attributes attrs)
                             throws javax.naming.NamingException {
                         
                         
                         String samaccountname ="";
                         if(attrs.get(EMPLOYEE_ID)!=null)
                         {
                                     samaccountname=attrs.get(EMPLOYEE_ID).get().toString();
                         }
                         else
                         {
                              samaccountname=attrs.get(SAME_ACCOUNT_NAME).get().toString();
                         }
                              
                         
                         samaccountname +="-"+attrs.get(SAME_ACCOUNT_NAME).get().toString();
                         return samaccountname;
                     }
                 });
    }

    if (membersList.size() > 0) {
        return membersList.get(0);
    } else {
        return null;
    }
    }
    catch(NullPointerException e){
    	return null;
    }
}

/**
 * Validate Employess for providing them roles through Admin console.L2: 2751
 * 
 */
public Map<String, Object> getEmployeeDetailsForAdminConsole(String empId) {

	return this.jdbcTemplate.queryForMap(
			ADMIN_SettingsSQLQueryConstants.IC_EMPLOYEE_DETAILS_FOR_ADMIN_CONSOLE_SQL, empId);

}



}