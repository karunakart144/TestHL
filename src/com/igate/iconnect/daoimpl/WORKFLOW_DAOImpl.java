/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.daoimpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.WORKFLOW_FieldPermission;
import com.igate.iconnect.BO.WORKFLOW_Master;
import com.igate.iconnect.BO.WORKFLOW_RecordPermission;
import com.igate.iconnect.BO.WORKFLOW_Role;
import com.igate.iconnect.BO.WORKFLOW_States;
import com.igate.iconnect.BO.WORKFLOW_UIField;
import com.igate.iconnect.BO.WORKFLOW_ValidTransition;
import com.igate.iconnect.constants.WORKFLOW_SQLQueryConstants;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.StringToDateConverter;

@Transactional(rollbackFor = Exception.class)
public class WORKFLOW_DAOImpl implements WORKFLOW_DAO {

	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(WORKFLOW_DAOImpl.class);

	private static List<WORKFLOW_Master> wfMasterList = new ArrayList<WORKFLOW_Master>();

	private static Map<String, List<WORKFLOW_States>> wfStatesMap = new HashMap<String, List<WORKFLOW_States>>();

	private static Map<String, List<WORKFLOW_Role>> wfRoleMap = new HashMap<String, List<WORKFLOW_Role>>();

	private static Map<String, List<WORKFLOW_ValidTransition>> wfValidTransitionMap = new HashMap<String, List<WORKFLOW_ValidTransition>>();

	private static Map<String, List<WORKFLOW_RecordPermission>> wfRecordPermissionMap = new HashMap<String, List<WORKFLOW_RecordPermission>>();

	private static Map<String, List<WORKFLOW_FieldPermission>> wfFieldPermissionMap = new HashMap<String, List<WORKFLOW_FieldPermission>>();

	private static List<Map<String, Object>> wfFunctionDefStateRoleMapping = new ArrayList<Map<String, Object>>();

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		prepareDistinctWorkFlow(jdbcTemplate);
		prepareDistinctStatesForEachWorkFlow(jdbcTemplate);
		prepareDistinctRolesForEachWorkFlow(jdbcTemplate);
		prepareValidTransitionForEachWorkFlow(jdbcTemplate);
		prepareRoleOrFieldAllowedForValidTransition(jdbcTemplate);
		prepareRecordPermissionForEachWorkFlow(jdbcTemplate);
		prepareFieldPermissionForEachWorkFlow(jdbcTemplate);
		List<String> gotData = getNextValidTransition("72", "Some State1",
				"Administrator");
		log.info("GotData:" + gotData.toString());
		Map<String, String> gotData1 = getIndividualRecordPermission("73",
				"Open", "IT Engineer");
		for (Map.Entry<String, String> entry : gotData1.entrySet()) {
			log.info("Key:" + entry.getKey());
			log.info("Value:" + entry.getValue());
		}
		Map<String, List<String>> map = getAllPermissionForFields("73",
				"Help Desk Executive", "Open");
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			log.info("Permission---------" + entry.getKey());
			log.info("Fields-------------" + entry.getValue().toString());

		}

		prepareFunctionDefaultStateRoleMappingMaster();

	}

	private void prepareFieldPermissionForEachWorkFlow(
			JdbcTemplate jdbcTemplate2) {
		String prepareFieldPermissionForEachWorkFlowQuery = WORKFLOW_SQLQueryConstants.dfdf;
		for (WORKFLOW_Master obj : wfMasterList) {
			final long wfId = obj.getWorkFlowId();
			List<WORKFLOW_FieldPermission> wfFieldPermissionList = this.jdbcTemplate
					.query(prepareFieldPermissionForEachWorkFlowQuery,
							new Object[] { obj.getWorkFlowId() },
							new RowMapper<WORKFLOW_FieldPermission>() {

								public WORKFLOW_FieldPermission mapRow(ResultSet rs,
										int count) throws SQLException {
									WORKFLOW_FieldPermission obj = new WORKFLOW_FieldPermission();
									obj.setCurrentState(rs.getString(1));
									obj.setRoleName(rs.getString(2));
									// obj.setPermission(rs.getString(3));
									return obj;
								}

							});

			wfFieldPermissionMap.put(String.valueOf(wfId),
					wfFieldPermissionList);

		}

		for (Map.Entry<String, List<WORKFLOW_FieldPermission>> stringListEntry : wfFieldPermissionMap
				.entrySet()) {
			List<WORKFLOW_FieldPermission> fpList = new ArrayList<WORKFLOW_FieldPermission>();
			//Map.Entry<String, List<WORKFLOW_FieldPermission>> entry = stringListEntry;
			final String workFlowId = stringListEntry.getKey();
			List<WORKFLOW_FieldPermission> fieldPermissionList = stringListEntry.getValue();
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				final String stateName = fieldPermission.getCurrentState();
				final String roleName = fieldPermission.getRoleName();
				final Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();
				String permissionValue = "";
				List<String> viewCustomFieldsList = this.jdbcTemplate.query(
						WORKFLOW_SQLQueryConstants.custom, new Object[] { stateName,
								workFlowId, roleName, workFlowId },
						new RowMapper<String>() {

							public String mapRow(ResultSet rs, int count)
									throws SQLException {
								String permissionVal = rs.getString(2);
								if (permissionMap.containsKey(permissionVal)) {
									List<String> permissionList = permissionMap
											.get(permissionVal);
									permissionList.add(rs.getString(3));
									permissionMap.put(permissionVal,
											permissionList);
								} else {
									List<String> permissionList = new ArrayList<String>();
									permissionList.add(rs.getString(3));
									permissionMap.put(permissionVal,
											permissionList);
								}
								return rs.getString(1);
							}

						});
				fieldPermission
						.setCustomFieldsWithPermissionList(permissionMap);
				fpList.add(fieldPermission);

			}
			wfFieldPermissionMap.put(workFlowId, fpList);

		}
		log.info("wfFieldPermissionMap--"
				+ wfFieldPermissionMap.toString());
		for (Map.Entry<String, List<WORKFLOW_FieldPermission>> entry1 : wfFieldPermissionMap
				.entrySet()) {
			List<WORKFLOW_FieldPermission> fieldPermissionList = entry1.getValue();
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				log.info("fieldPermission.getRoleName()-"
						+ fieldPermission.getRoleName());
				Map<String, List<String>> gotValues = fieldPermission
						.getCustomFieldsWithPermissionList();
				for (Map.Entry<String, List<String>> entry2 : gotValues
						.entrySet()) {
					log.info("Permission:" + entry2.getKey()
							+ " Fields:" + entry2.getValue());

				}
			}
		}
		log.info("wfFieldPermissionMap Size:"
				+ wfValidTransitionMap.size());

	}

	private void prepareRecordPermissionForEachWorkFlow(
			JdbcTemplate jdbcTemplate2) {
		String prepareRecordPermissionForEachWorkFlowQuery = WORKFLOW_SQLQueryConstants.SELECT_ROLENAME_CUSTOMFIELD_PERMISSION_FROM_IC_WORKFLOW_RECORD_PERMISSION;
		for (WORKFLOW_Master obj : wfMasterList) {
			final long wfId = obj.getWorkFlowId();
			List<WORKFLOW_RecordPermission> wfValidTransitionList = this.jdbcTemplate
					.query(prepareRecordPermissionForEachWorkFlowQuery,
							new Object[] { obj.getWorkFlowId() },
							new RowMapper<WORKFLOW_RecordPermission>() {

								public WORKFLOW_RecordPermission mapRow(ResultSet rs,
										int count) throws SQLException {
									WORKFLOW_RecordPermission obj = new WORKFLOW_RecordPermission();
									obj.setCurrentState(rs.getString(1));
									obj.setRoleName(rs.getString(2));
									obj.setReservedForName(rs.getString(3));
									obj.setPermission(rs.getString(4));
									return obj;
								}

							});
			wfRecordPermissionMap.put(String.valueOf(wfId),
					wfValidTransitionList);

		}
		log.info("wfRecordPermissionMap Size:"
				+ wfValidTransitionMap.size());

	}

	private void prepareRoleOrFieldAllowedForValidTransition(
			JdbcTemplate jdbcTemplate2) {
		String perpareRoleOrFieldAllowedForValidTransitionQuery = WORKFLOW_SQLQueryConstants.SELECT_ROLENAME_CUSTOMFIELD_FROM_IC_WORKFLOW_TRANSITION_ROLE_MAPPING;
		for (WORKFLOW_Master obj : wfMasterList) {
			List<WORKFLOW_ValidTransition> validTransitionList = wfValidTransitionMap
					.get(String.valueOf(obj.getWorkFlowId()));
			log.info("validTransitionList Size:"
					+ validTransitionList.size());
			List<WORKFLOW_ValidTransition> updatedList = new ArrayList<WORKFLOW_ValidTransition>();
			for (WORKFLOW_ValidTransition validTransition : validTransitionList) {
				final List<String> roleMapped = new ArrayList<String>();
				final List<String> customFields = new ArrayList<String>();
				this.jdbcTemplate.query(
						perpareRoleOrFieldAllowedForValidTransitionQuery,
						new Object[] { obj.getWorkFlowId(),
								validTransition.getTransitionId() },
						new RowMapper<Object>() {

							public Object mapRow(ResultSet rs, int count)
									throws SQLException {
								roleMapped.add(rs.getString(1));
								if (rs.getString(2) != null)
									customFields.add(rs.getString(2));
								return "";
							}

						});
				validTransition.setRoleMapped(roleMapped);
				validTransition.setCustomFields(customFields);
				updatedList.add(validTransition);

			}
			wfValidTransitionMap.put(String.valueOf(obj.getWorkFlowId()),
					updatedList);
			// TODO:Remove me
			for (WORKFLOW_ValidTransition validTransition : updatedList) {
				log.info("Valid Transition Id:"
						+ validTransition.getTransitionId());
				log.info("Role Mapped:"
						+ validTransition.getRoleMapped());
				log.info("Custom Field Mapped:"
						+ validTransition.getCustomFields());

			}
		}

	}

	private void prepareValidTransitionForEachWorkFlow(
			JdbcTemplate jdbcTemplate2) {
		String prepareValidTransitionForEachWorkFlowQuery = WORKFLOW_SQLQueryConstants.SELECT_TRANISTION_ID_FROMSTATE_TOSTATE;
		for (WORKFLOW_Master obj : wfMasterList) {
			final long wfId = obj.getWorkFlowId();
			List<WORKFLOW_ValidTransition> wfValidTransitionList = this.jdbcTemplate
					.query(prepareValidTransitionForEachWorkFlowQuery,
							new Object[] { obj.getWorkFlowId() },
							new RowMapper<WORKFLOW_ValidTransition>() {

								public WORKFLOW_ValidTransition mapRow(ResultSet rs,
										int count) throws SQLException {
									WORKFLOW_ValidTransition obj = new WORKFLOW_ValidTransition();
									obj.setTransitionId(rs.getLong(1));
									obj.setTransitionName(rs.getString(2));
									obj.setCurrentState(rs.getString(3));
									obj.setNextState(rs.getString(4));
									obj.setWorkFlowId(String.valueOf(wfId));
									return obj;
								}

							});
			wfValidTransitionMap.put(String.valueOf(wfId),
					wfValidTransitionList);

		}
		log.info("wfValidTransitionMap Size:"
				+ wfValidTransitionMap.size());

	}

	private void prepareDistinctRolesForEachWorkFlow(JdbcTemplate jdbcTemplate2) {
		String prepareDistinctRolesForEachWorkFlowQuery = WORKFLOW_SQLQueryConstants.SELECT_ROLE_ID_NAME_FROM_IC_WORKFLOW_ROLE_MAPPING;
		for (WORKFLOW_Master obj : wfMasterList) {
			final long wfId = obj.getWorkFlowId();
			List<WORKFLOW_Role> wfRoleList = this.jdbcTemplate.query(
					prepareDistinctRolesForEachWorkFlowQuery,
					new Object[] { obj.getWorkFlowId() },
					new RowMapper<WORKFLOW_Role>() {

						public WORKFLOW_Role mapRow(ResultSet rs, int count)
								throws SQLException {
							WORKFLOW_Role obj = new WORKFLOW_Role();
							obj.setRoleId(rs.getLong(1));
							obj.setRoleName(rs.getString(2));
							obj.setWorkFlowId(String.valueOf(wfId));
							return obj;
						}

					});
			wfRoleMap.put(String.valueOf(wfId), wfRoleList);

		}
		log.info("wfRoleMap Size:" + wfRoleMap.size());

	}

	private void prepareDistinctStatesForEachWorkFlow(JdbcTemplate jdbcTemplate2) {
		String prepareDistinctStatesForEachWorkFlowQuery = WORKFLOW_SQLQueryConstants.SELECT_STATE_ID_NAME_FROM_IC_WORKFLOW_STATE_MASTER;
		for (WORKFLOW_Master obj : wfMasterList) {
			final long wfId = obj.getWorkFlowId();
			List<WORKFLOW_States> wfStatesList = this.jdbcTemplate.query(
					prepareDistinctStatesForEachWorkFlowQuery,
					new Object[] { wfId }, new RowMapper<WORKFLOW_States>() {

						public WORKFLOW_States mapRow(ResultSet rs, int count)
								throws SQLException {
							WORKFLOW_States obj = new WORKFLOW_States();
							obj.setStateId(rs.getLong(1));
							obj.setState(rs.getString(2));
							obj.setWorkFlowId(wfId);
							return obj;
						}

					});
			wfStatesMap.put(String.valueOf(wfId), wfStatesList);

		}
		log.info("wfStatesMap Size:" + wfStatesMap.size());

	}

	private void prepareDistinctWorkFlow(JdbcTemplate jdbcTemplate2) {
		String prepareDistinctWorkFlowQuery = WORKFLOW_SQLQueryConstants.SELECT_WFID_NAME_FROM_IC_WORKFLOW_MASTER;
		wfMasterList = this.jdbcTemplate.query(prepareDistinctWorkFlowQuery,
				new RowMapper<WORKFLOW_Master>() {

					public WORKFLOW_Master mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_Master obj = new WORKFLOW_Master();
						obj.setWorkFlowId(rs.getLong(1));
						obj.setWorkFlowName(rs.getString(2));
						obj.setWorkFlowDesc(rs.getString(3));
						return obj;
					}

				});
		log.info("wfMasterList Size is:" + wfMasterList.size());
	}

	public String getWorkflowID(String workflowname) {

		String workflowid = "";
		for (WORKFLOW_Master obj : wfMasterList) {
			if (obj.getWorkFlowName().equalsIgnoreCase(workflowname)) {
				workflowid = obj.getWorkFlowId().toString();
			}
		}
		return workflowid;
	}

	public List<WORKFLOW_States> getAllStates(String workFlowId) {
		if (wfStatesMap.containsKey(workFlowId)) {
			return wfStatesMap.get(workFlowId);
		} else {
			return new ArrayList<WORKFLOW_States>();
		}
	}

	public String getStateId(String stateName, String workFlowId) {
		String toReturn = "";
		if (wfStatesMap.containsKey(workFlowId)) {
			List<WORKFLOW_States> wfStatesList = wfStatesMap.get(workFlowId);
			for (WORKFLOW_States obj : wfStatesList) {
				if (obj.getState().equalsIgnoreCase(stateName))
					toReturn = String.valueOf(obj.getStateId());

			}
		}
		return toReturn;
	}

	public String getStateName(String stateId, String workFlowId) {
		String toReturn = "";
		long state_id = Long.parseLong(stateId);
		if (wfStatesMap.containsKey(workFlowId)) {
			List<WORKFLOW_States> wfStatesList = wfStatesMap.get(workFlowId);
			for (WORKFLOW_States obj : wfStatesList) {
				if (obj.getStateId() == state_id)
					toReturn = String.valueOf(obj.getState());

			}
		}
		return toReturn;
	}

	public List<String> getNextValidTransition(String workFlowId,
			String currentState, String roleName) {
		List<String> nextValidStatesList = new ArrayList<String>();
		if (wfValidTransitionMap.containsKey(workFlowId)) {
			List<WORKFLOW_ValidTransition> validTrasitionList = wfValidTransitionMap
					.get(workFlowId);
			log.info("validTrasitionList Size:"
					+ validTrasitionList.size());
			for (WORKFLOW_ValidTransition validTransition : validTrasitionList) {
				log.info("validTransition.getCurrentState():"
						+ validTransition.getCurrentState());
				log.info("validTransition.getRoleMapped():"
						+ validTransition.getRoleMapped().toString());
				if ((validTransition.getCurrentState()
						.equalsIgnoreCase(currentState))
						&& (validTransition.getRoleMapped().contains(roleName))) {
					nextValidStatesList.add(validTransition.getNextState());
				}

			}
		}

		return nextValidStatesList;
	}

	public Map<String, String> getIndividualRecordPermission(String workFlowId,
			String currentState, String roleName) {
		Map<String, String> permissionMap = new HashMap<String, String>();
		if (wfRecordPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_RecordPermission> recordPermissionList = wfRecordPermissionMap
					.get(workFlowId);
			for (WORKFLOW_RecordPermission recordPermission : recordPermissionList) {
				log.info("recordPermission.getCurrentState():"
						+ recordPermission.getCurrentState());
				log.info("recordPermission.getRoleName():"
						+ recordPermission.getRoleName());
				if (recordPermission.getCurrentState().equalsIgnoreCase(
						currentState)
						&& recordPermission.getRoleName().equalsIgnoreCase(
								roleName)) {
					String customField = recordPermission.getReservedForName();
					String permission = recordPermission.getPermission();
					log.info("customField:" + customField);
					log.info("permission:" + permission);
					if ((customField != null)
							&& (customField.trim().length() > 0)
							&& (!customField.equalsIgnoreCase("None"))) {
						permissionMap.put(customField, permission);
					} else {
						permissionMap.put("NoCustomField", permission);
					}
				}

			}
		}
		return permissionMap;
	}

	public Map<String, List<String>> getAllStatesRecordPermission(
			String workFlowId, String roleName) {
		Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();
		if (wfRecordPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_RecordPermission> recordPermissionList = wfRecordPermissionMap
					.get(workFlowId);
			for (WORKFLOW_RecordPermission recordPermission : recordPermissionList) {
				if (recordPermission.getRoleName().equalsIgnoreCase(roleName)) {
					String customField = recordPermission.getReservedForName();

					if (permissionMap.containsKey(customField)) {
						if ((customField != null)
								&& (customField.trim().length() > 0)
								&& (!customField.equalsIgnoreCase("None"))) {
							List<String> stateList = permissionMap
									.get(customField);
							stateList.add(recordPermission.getCurrentState());
							permissionMap.put(customField, stateList);

						} else {
							List<String> stateList = permissionMap.get("None");
							stateList.add(recordPermission.getCurrentState());
							permissionMap.put("None", stateList);
						}
					} else {
						if ((customField != null)
								&& (customField.trim().length() > 0)
								&& (!customField.equalsIgnoreCase("None"))) {
							List<String> stateList = new ArrayList<String>();
							stateList.add(recordPermission.getCurrentState());
							permissionMap.put(customField, stateList);

						} else {
							List<String> stateList = new ArrayList<String>();
							stateList.add(recordPermission.getCurrentState());
							permissionMap.put("None", stateList);
						}
					}
				}
			}
		}
		return permissionMap;
	}

	public Map<String, List<String>> getAllPermissionForFields(
			String workFlowId, String roleName, String currentState) {
		if (wfFieldPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_FieldPermission> fieldPermissionList = wfFieldPermissionMap
					.get(workFlowId);
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				if (fieldPermission.getCurrentState().equalsIgnoreCase(
						currentState)
						&& fieldPermission.getRoleName().equalsIgnoreCase(
								roleName)) {
					return fieldPermission.getCustomFieldsWithPermissionList();
				}

			}
		}
		return Collections.emptyMap();

	}

	// public Map<String, List<String>> getAllPermissionsForField(
	// String workFlowId, String roleName, String currentState) {
	// if (wfFieldPermissionMap.containsKey(workFlowId)) {
	// List<FieldPermission> fieldPermissionList = wfFieldPermissionMap
	// .get(workFlowId);
	// for (Iterator<FieldPermission> iterator = fieldPermissionList
	// .iterator(); iterator.hasNext();) {
	// FieldPermission fieldPermission = (FieldPermission) iterator
	// .next();
	// if (fieldPermission.getCurrentState().equalsIgnoreCase(
	// currentState)
	// && fieldPermission.getRoleName().equalsIgnoreCase(
	// roleName)) {
	// return fieldPermission.getCustomFieldsWithPermissionList();
	// }
	//
	// }
	// }
	// return Collections.EMPTY_MAP;
	//
	// }

	public List<String> getViewOnlyPermissionForField(String workFlowId,
			String roleName, String currentState) {
		if (wfFieldPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_FieldPermission> fieldPermissionList = wfFieldPermissionMap
					.get(workFlowId);
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				if (fieldPermission.getCurrentState().equalsIgnoreCase(
						currentState)
						&& fieldPermission.getRoleName().equalsIgnoreCase(
								roleName)) {
					Map<String, List<String>> fpMap = fieldPermission
							.getCustomFieldsWithPermissionList();
					if (fpMap.containsKey("V"))
						return fpMap.get("V");
				}

			}
		}
		return Collections.EMPTY_LIST;

	}

	public List<String> getEditOnlyPermissionForField(String workFlowId,
			String roleName, String currentState) {
		if (wfFieldPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_FieldPermission> fieldPermissionList = wfFieldPermissionMap
					.get(workFlowId);
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				if (fieldPermission.getCurrentState().equalsIgnoreCase(
						currentState)
						&& fieldPermission.getRoleName().equalsIgnoreCase(
								roleName)) {
					Map<String, List<String>> fpMap = fieldPermission
							.getCustomFieldsWithPermissionList();
					if (fpMap.containsKey("E"))
						return fpMap.get("E");
				}

			}
		}
		return Collections.EMPTY_LIST;

	}

	public List<String> getNoneOnlyPermissionForField(String workFlowId,
			String roleName, String currentState) {
		if (wfFieldPermissionMap.containsKey(workFlowId)) {
			List<WORKFLOW_FieldPermission> fieldPermissionList = wfFieldPermissionMap
					.get(workFlowId);
			for (WORKFLOW_FieldPermission fieldPermission : fieldPermissionList) {
				if (fieldPermission.getCurrentState().equalsIgnoreCase(
						currentState)
						&& fieldPermission.getRoleName().equalsIgnoreCase(
								roleName)) {
					Map<String, List<String>> fpMap = fieldPermission
							.getCustomFieldsWithPermissionList();
					if (fpMap.containsKey("X"))
						return fpMap.get("X");
				}

			}
		}
		return Collections.EMPTY_LIST;

	}

	public List<String> getTablesFromDB() throws SQLException {
		DatabaseMetaData dbmd = this.jdbcTemplate.getDataSource()
				.getConnection().getMetaData();
		String[] types = { "TABLE" };
		ResultSet resultSet = dbmd.getTables(null, null, "%", types);
		List<String> tablesList = new ArrayList<String>();
		while (resultSet.next()) {
			String tableName = resultSet.getString(3);
			if (!tableName.contains("WORKFLOW"))
				tablesList.add(tableName);
			/*
			 * String tableCatalog = resultSet.getString(1); String tableSchema
			 * = resultSet.getString(2);
			 */
		}
		return tablesList;
	}

	public String insertWorkFlowMaster(final String name,
			final String description) throws SQLException, ParseException {
		final java.sql.Date creationDate = StringToDateConverter
				.getGMTDateForStorage();
		final String insertMasterWorkFlowQuery = "INSERT INTO IC_WORKFLOW_MASTER (NAME,DESCRIPTION,CREATED_DATE) VALUES (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						insertMasterWorkFlowQuery,
						new String[] { "WORKFLOW_ID" });
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setDate(3, creationDate);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().toString();
	}

	public List<WORKFLOW_States> getWorkFlowStates(String workFlowId) {
		String getWorkFlowStatesQuery = "SELECT STATE_ID,NAME from IC_WORKFLOW_STATE_MASTER where WORKFLOW_ID = "
				+ workFlowId;
		return this.jdbcTemplate.query(getWorkFlowStatesQuery,
				new RowMapper<WORKFLOW_States>() {

					public WORKFLOW_States mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_States obj = new WORKFLOW_States();
						obj.setState(rs.getString("NAME"));
						obj.setStateId(rs.getLong("STATE_ID"));
						return obj;
					}

				});
	}

	public List<WORKFLOW_FieldPermission> getFieldPermissionForWorkFlow(String workFlowId) {
		String getFieldPermissionForWorkFlowQuery = "  SELECT DISTINCT WFF.RECORD_ID FIELDRECORDID, "
				+ " FD.UI_FIELD CUSTOMFIELDS,   "
				+ " RMASTER.NAME ROLE,   "
				+ " WFF.PERMISSION PERMISSION, "
				+ " WFS.NAME STATE  "
				+ " FROM IC_WORKFLOW_FIELD_PERMISSION WFF, "
				+ " IC_WORKFLOW_ROLE_MAPPING ROLE,   "
				+ " IC_WORKFLOW_STATE_MASTER WFS ,  "
				+ " IC_ROLE_MASTER RMASTER, "
				+ " IC_MENU_FIELD_DETAILS FD  "
				+ " WHERE WFF.WORKFLOW_ID = WFS.WORKFLOW_ID "
				+ " AND WFF.STATE_ID = WFS.STATE_ID   "
				+ " AND WFF.ROLE_ID = RMASTER.ROLE_ID   "
				+ " AND RMASTER.ROLE_ID = ROLE.ROLE_ID  "
				+ " AND FD.FIELD_ID = WFF.UI_FIELDS "
				+ " AND WFF.WORKFLOW_ID =?";
		return this.jdbcTemplate.query(getFieldPermissionForWorkFlowQuery,new Object[] {workFlowId},
				new RowMapper<WORKFLOW_FieldPermission>() {

					public WORKFLOW_FieldPermission mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_FieldPermission obj = new WORKFLOW_FieldPermission();
						obj.setCurrentState(rs.getString("STATE"));
						obj.setPermission(rs.getString("PERMISSION"));
						obj.setReservedForName(rs.getString("CUSTOMFIELDS"));
						obj.setRoleName(rs.getString("ROLE"));
						obj.setFieldPermissionId(rs.getString("FIELDRECORDID"));
						return obj;
					}

				});
	}

	public List<WORKFLOW_RecordPermission> getRecordPermissionForWorkFlow(
			String workFlowId) {
		String getRecordPermissionForWorkFlowQuery = "SELECT DISTINCT WFR.RECORD_ID RECORDID, "
				+ " WFR.CUSTOM_FIELDS FIELDS,  "
				+ " RMASTER.NAME ROLE,  "
				+ " WFR.PERMISSION PERMISSION, "
				+ " WFS.NAME STATE   "
				+ " FROM    IC_WORKFLOW_RECORD_PERMISSION WFR, "
				+ " IC_WORKFLOW_ROLE_MAPPING ROLE,  "
				+ " IC_WORKFLOW_STATE_MASTER WFS , "
				+ " IC_ROLE_MASTER RMASTER "
				+ " WHERE   WFR.WORKFLOW_ID = WFS.WORKFLOW_ID "
				+ " AND WFR.STATE_ID = WFS.STATE_ID  "
				+ " AND WFR.ROLE_ID = ROLE.ROLE_ID  "
				+ " AND RMASTER.ROLE_ID = ROLE.ROLE_ID "
				+ " AND WFR.WORKFLOW_ID =? ";
		return this.jdbcTemplate.query(getRecordPermissionForWorkFlowQuery,new Object[] {workFlowId},
				new RowMapper<WORKFLOW_RecordPermission>() {

					public WORKFLOW_RecordPermission mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_RecordPermission obj = new WORKFLOW_RecordPermission();
						obj.setCurrentState(rs.getString("STATE"));
						obj.setPermission(rs.getString("PERMISSION"));
						obj.setRoleName(rs.getString("ROLE"));
						obj.setReservedForName(rs.getString("FIELDS"));
						obj.setRecordPermissionId(rs.getLong("RECORDID"));
						return obj;
					}

				});
	}

	public List<WORKFLOW_UIField> getUIField() {
		String getUIFieldQuery = "select FD.FIELD_ID, MM.NAME+'-'+FD.UI_FIELD from IC_MENU_FIELD_DETAILS FD, IC_MENU_MASTER MM "
				+ " where FD.MENU_ID = MM.MENU_ID";
		return this.jdbcTemplate.query(getUIFieldQuery,
				new RowMapper<WORKFLOW_UIField>() {

					public WORKFLOW_UIField mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_UIField obj = new WORKFLOW_UIField();
						obj.setUiId(rs.getString(1));
						obj.setUiFieldName(rs.getString(2));
						return obj;
					}

				});
	}

	public List<WORKFLOW_Role> getRolesMappedForWorkFlow(String workFlowId) {
		String getRolesForWorkFlowQuery = "SELECT WRM.ROLE_ID, RMASTER.NAME from IC_WORKFLOW_ROLE_MAPPING WRM,IC_ROLE_MASTER RMASTER where RMASTER.ROLE_ID = WRM.ROLE_ID AND WORKFLOW_ID = ?";
		return this.jdbcTemplate.query(getRolesForWorkFlowQuery,new Object[] {workFlowId},
				new RowMapper<WORKFLOW_Role>() {

					public WORKFLOW_Role mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_Role obj = new WORKFLOW_Role();
						obj.setRoleId(rs.getLong(1));
						obj.setRoleName(rs.getString(2));
						return obj;
					}

				});

	}

	public List<WORKFLOW_Role> getDistinctRoles() {
		String getDistinctRolesQuery = "SELECT ROLE_ID,NAME FROM IC_ROLE_MASTER";
		return this.jdbcTemplate.query(getDistinctRolesQuery,
				new RowMapper<WORKFLOW_Role>() {

					public WORKFLOW_Role mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_Role obj = new WORKFLOW_Role();
						obj.setRoleId(rs.getLong(1));
						obj.setRoleName(rs.getString(2));
						return obj;
					}

				});
	}

	public List<WORKFLOW_ValidTransition> getValidTransitionForWorkFlow(String workFlowId) {
		String getValidTransitionForWorkFlowQuery = "SELECT WFT.TRANSITION_ID TRANSITIONID, WFFROMSTATE.NAME FROMSTATE, WFTOSTATE.NAME TOSTATE, WFT.NAME TRANSITIONNAME"
				+ " FROM IC_WORKFLOW_TRANSITION_MASTER WFT, IC_WORKFLOW_STATE_MASTER WFFROMSTATE, IC_WORKFLOW_STATE_MASTER WFTOSTATE "
				+ " WHERE WFT.WORKFLOW_ID = WFFROMSTATE.WORKFLOW_ID "
				+ " AND WFT.WORKFLOW_ID = WFTOSTATE.WORKFLOW_ID "
				+ " AND WFT.FROM_STATE = WFFROMSTATE.STATE_ID "
				+ " AND WFT.TO_STATE = WFTOSTATE.STATE_ID "
				+ " AND WFT.WORKFLOW_ID = ?";
		List<WORKFLOW_ValidTransition> validTransitionList = this.jdbcTemplate.query(
				getValidTransitionForWorkFlowQuery,new Object[] {workFlowId},
				new RowMapper<WORKFLOW_ValidTransition>() {

					public WORKFLOW_ValidTransition mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_ValidTransition obj = new WORKFLOW_ValidTransition();
						obj.setTransitionId(rs.getLong("TRANSITIONID"));
						obj.setCurrentState(rs.getString("FROMSTATE"));
						obj.setNextState(rs.getString("TOSTATE"));
						obj.setTransitionName(rs.getString("TRANSITIONNAME"));
						return obj;
					}

				});

		List<WORKFLOW_ValidTransition> toReturnList = new ArrayList<WORKFLOW_ValidTransition>();
		for (WORKFLOW_ValidTransition aValidTransitionList : validTransitionList) {
			String roleAndCustomFieldMappingQuery = "SELECT RMASTER.NAME ROLENAME, TRM.CUSTOM_FIELDS CUSTOMFIELDS "
					+ " FROM IC_WORKFLOW_TRANSITION_ROLE_MAPPING TRM, "
					+ " IC_WORKFLOW_ROLE_MAPPING RM ,IC_ROLE_MASTER RMASTER "
					+ " WHERE RM.ROLE_ID = TRM.ROLE_ID  "
					+ " AND RMASTER.ROLE_ID = RM.ROLE_ID "
					+ " AND TRM.ROLE_ID = RM.ROLE_ID "
					+ " AND TRM.TRANSITION_ID = ";
			final WORKFLOW_ValidTransition validTransition = aValidTransitionList;
			final List<String> roleMappedList = new ArrayList<String>();
			final List<String> customFieldsList = new ArrayList<String>();
			roleAndCustomFieldMappingQuery = roleAndCustomFieldMappingQuery
					+ validTransition.getTransitionId();
			log.info(roleAndCustomFieldMappingQuery);
			this.jdbcTemplate.query(roleAndCustomFieldMappingQuery,
					new RowMapper<List<String>>() {

						public List<String> mapRow(ResultSet rs, int count)
								throws SQLException {
							roleMappedList.add(rs.getString("ROLENAME"));
							customFieldsList.add(rs.getString("CUSTOMFIELDS"));
							return null;
						}

					});
			log.info("roleMappedList:" + roleMappedList);
			validTransition.setRoleMapped(roleMappedList);
			validTransition.setCustomFields(customFieldsList);
			toReturnList.add(validTransition);
		}
		return toReturnList;
	}

	public List<WORKFLOW_Master> getExistingWorkFlow() throws SQLException {

		String getExistingWorkFlowQuery = "SELECT WORKFLOW_ID,NAME,DESCRIPTION FROM IC_WORKFLOW_MASTER ";
		return this.jdbcTemplate.query(getExistingWorkFlowQuery,
				new RowMapper<WORKFLOW_Master>() {

					public WORKFLOW_Master mapRow(ResultSet rs, int count)
							throws SQLException {
						WORKFLOW_Master obj = new WORKFLOW_Master();
						obj.setWorkFlowId(rs.getLong("WORKFLOW_ID"));
						obj.setWorkFlowName(rs.getString("NAME"));
						obj.setWorkFlowDesc(rs.getString("DESCRIPTION"));
						return obj;
					}

				});
	}

	// TODO:Use if Exists Query(Note it is specific to SQL server)
	public String insertWorkFlowState(final String stateName,
			final String workFlowId) throws SQLException, ParseException {
		String checkStateExistsQuery = "SELECT COUNT(*) FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID = ? AND NAME = ?";
		int countofRecords = this.jdbcTemplate
				.queryForInt(checkStateExistsQuery,workFlowId,stateName);
		log.info("countofRecords" + countofRecords);
		if (countofRecords == 0) {
			java.sql.Date creationDate = StringToDateConverter
					.getGMTDateForStorage();
			final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
					creationDate.getTime());
			final String insertWorkFlowStateQuery = "INSERT INTO IC_WORKFLOW_STATE_MASTER (NAME, WORKFLOW_ID,CREATED_DATE) VALUES (?,?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							insertWorkFlowStateQuery,
							new String[] { "STATE_ID" });
					ps.setString(1, stateName);
					ps.setLong(2, Long.parseLong(workFlowId));
					ps.setTimestamp(3, latestCurDate);
					return ps;
				}
			}, keyHolder);

			return keyHolder.getKey().toString();

		} else {
			return stateName + " already Exists !";
		}
	}

	public String insertWorkFlowTransition(final String transitionName,
			final String fromState, final String toState,
			final String workFlowId) throws SQLException, ParseException {

		String checkTransitionExistsQuery = "SELECT COUNT(*) FROM IC_WORKFLOW_TRANSITION_MASTER WHERE WORKFLOW_ID = ? AND TO_STATE = (SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID = ? AND NAME = ?) AND FROM_STATE = (SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID = ? AND NAME = ?)";
		int countofRecords = this.jdbcTemplate
				.queryForInt(checkTransitionExistsQuery,workFlowId,workFlowId,toState,workFlowId,fromState);
		log.info("Count of Records:" + countofRecords);
		if (countofRecords == 0) {
			final String insertWorkFlowTransitionQuery = "INSERT INTO IC_WORKFLOW_TRANSITION_MASTER (WORKFLOW_ID, FROM_STATE, TO_STATE, NAME,CREATED_DATE) VALUES (?,(SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID = ? AND NAME = ?),(SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID =? AND NAME = ?),?,?)";
			log.info("insertWorkFlowTransitionQuery:"
					+ insertWorkFlowTransitionQuery);
			java.sql.Date creationDate = StringToDateConverter
					.getGMTDateForStorage();
			final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
					creationDate.getTime());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							insertWorkFlowTransitionQuery,
							new String[] { "TRANSITION_ID" });
					ps.setLong(1, Long.parseLong(workFlowId));
					ps.setLong(2, Long.parseLong(workFlowId));
					 ps.setLong(3, Long.parseLong(fromState));
					 ps.setLong(4, Long.parseLong(workFlowId));
					ps.setLong(5, Long.parseLong(toState));
					ps.setString(6, transitionName);
					ps.setTimestamp(7, latestCurDate);
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().toString();
		} else {
			return "Transition already Exists !";
		}
	}

	public String insertWorkFlowRole(final String roleName,
			final String workFlowId) throws SQLException, ParseException {
		String checkRoleExistsQuery = "SELECT COUNT(*) FROM IC_WORKFLOW_ROLE_MAPPING WHERE WORKFLOW_ID =? AND ROLE_ID = ?";
		int countofRecords = this.jdbcTemplate
				.queryForInt(checkRoleExistsQuery,workFlowId,roleName);
		if (countofRecords == 0) {
			final String insertWorkFlowRoleQuery = "INSERT INTO IC_WORKFLOW_ROLE_MAPPING (WORKFLOW_ID, ROLE_ID,CREATED_DATE) VALUES (?,?,?)";
			java.sql.Date creationDate = StringToDateConverter
					.getGMTDateForStorage();
			final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
					creationDate.getTime());
			this.jdbcTemplate.update(insertWorkFlowRoleQuery, workFlowId,
					roleName, latestCurDate);
			return "Success";
		} else {
			return "Role already Exists !";
		}
	}

	public String insertRoleForTransition(final String workFlowId,
			final String transitionId, final String roleName,
			String customFields) throws SQLException, ParseException {
		if (customFields == null) {
			customFields = "";
		}
		final String customFieldsToPass = customFields;
		final List<String> roleMappedList = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(roleName, ",");
		while (tokenizer.hasMoreElements()) {
			roleMappedList.add(tokenizer.nextToken());

		}
		java.sql.Date creationDate = StringToDateConverter
				.getGMTDateForStorage();
		final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
				creationDate.getTime());
		final String insertRoleForTransitionQuery = "INSERT INTO IC_WORKFLOW_TRANSITION_ROLE_MAPPING (TRANSITION_ID,ROLE_ID,CUSTOM_FIELDS,CREATED_DATE)"
				+ " values (?,(SELECT ROLE_ID FROM IC_ROLE_MASTER WHERE NAME = ?),?,?)";
		jdbcTemplate.batchUpdate(insertRoleForTransitionQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						String obj = roleMappedList.get(i);
						ps.setString(1, transitionId);
						ps.setString(2, obj);
						ps.setString(3, customFieldsToPass);
						ps.setTimestamp(4, latestCurDate);
					}

					public int getBatchSize() {
						return roleMappedList.size();
					}
				});
		/*
		 * this.jdbcTemplate.update(insertRoleForTransitionQuery, new Object[] {
		 * transitionId, roleName, customFieldsToPass, latestCurDate });
		 */
		return "Success";
	}

	public String insertRecordPermission(final String workFlowId,
			final String roleId, final String state, String customFields,
			final String permission) throws SQLException, ParseException {
		if (customFields == null) {
			customFields = "";
		}
		final String customFieldsToPass = customFields;
		final String insertRecordPermissionQuery = "INSERT INTO IC_WORKFLOW_RECORD_PERMISSION (WORKFLOW_ID,STATE_ID,ROLE_ID,CUSTOM_FIELDS,PERMISSION,CREATED_DATE)"
				+ " values (?,(SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID = ? AND NAME =?),?,?,?,?)";
		java.sql.Date creationDate = StringToDateConverter
				.getGMTDateForStorage();
		final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
				creationDate.getTime());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						insertRecordPermissionQuery,
						new String[] { "RECORD_ID" });
				ps.setLong(1, Long.parseLong(workFlowId));
				ps.setLong(2, Long.parseLong(workFlowId));
				ps.setLong(3, Long.parseLong(state));
				ps.setLong(4, Long.parseLong(roleId));
				ps.setString(5, customFieldsToPass);
				ps.setString(6, permission);
				ps.setTimestamp(7, latestCurDate);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().toString();
	}

	public String insertFieldPermission(final String workFlowId,
			final String roleId, final String state, String customFields,
			final String permission) throws SQLException, ParseException {
		if (customFields == null) {
			customFields = "";
		}
		final List<WORKFLOW_FieldPermission> fieldPermissionList = new ArrayList<WORKFLOW_FieldPermission>();
		StringTokenizer tokenizer = new StringTokenizer(customFields, ",");
		while (tokenizer.hasMoreElements()) {
			WORKFLOW_FieldPermission obj = new WORKFLOW_FieldPermission();
			obj.setCurrentState(state);
			obj.setPermission(permission);
			obj.setReservedForName(tokenizer.nextToken());
			obj.setRoleName(roleId);
			fieldPermissionList.add(obj);
		}
		final String insertFieldPermissionQuery = "INSERT INTO IC_WORKFLOW_FIELD_PERMISSION (WORKFLOW_ID,STATE_ID,ROLE_ID,UI_FIELDS,PERMISSION,CREATED_DATE)"
				+ " values (?,(SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID =? AND NAME = ?),?,?,?,?)";
		java.sql.Date creationDate = StringToDateConverter
				.getGMTDateForStorage();
		final java.sql.Timestamp latestCurDate = new java.sql.Timestamp(
				creationDate.getTime());
		jdbcTemplate.batchUpdate(insertFieldPermissionQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						WORKFLOW_FieldPermission obj = fieldPermissionList.get(i);
						ps.setString(1, workFlowId);
						ps.setString(2, workFlowId);
						ps.setLong(3, Long.parseLong(state));
						ps.setLong(4, Long.parseLong(roleId));
						ps.setString(5, obj.getReservedForName());
						ps.setString(6, obj.getPermission());
						ps.setTimestamp(7, latestCurDate);
					}

					public int getBatchSize() {
						return fieldPermissionList.size();
					}
				});
		return "Success !";
	}

	public synchronized boolean resetPrepareDistinctWorkFlow() {
		wfMasterList.clear();
		log.info("IN");
		//try {
			prepareDistinctWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetPrepareDistinctStatesForEachWorkFlow() {
		wfStatesMap.clear();
		//try {
			prepareDistinctStatesForEachWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetPrepareDistinctRolesForEachWorkFlow() {
		wfRoleMap.clear();
		//try {
			prepareDistinctRolesForEachWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetPrepareValidTransitionForEachWorkFlow() {
		wfValidTransitionMap.clear();
		//try {
			prepareValidTransitionForEachWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetprepareRoleOrFieldAllowedForValidTransition() {
		//try {
			prepareRoleOrFieldAllowedForValidTransition(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetPrepareRecordPermissionForEachWorkFlow() {
		wfRecordPermissionMap.clear();
		//try {
			prepareRecordPermissionForEachWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetPrepareFieldPermissionForEachWorkFlow() {
		wfFieldPermissionMap.clear();
		//try {
			prepareFieldPermissionForEachWorkFlow(jdbcTemplate);
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	public void prepareFunctionDefaultStateRoleMappingMaster() {

		wfFunctionDefStateRoleMapping = this.jdbcTemplate
				.queryForList(WORKFLOW_SQLQueryConstants.SELECT_WF_DEFAULT_STATE_ROLE_MAPPING);

	}
	//Nisha --changed for HR Helpdesk start
	public List<String> getStatesForSelectedFunctionOnRoleWise(String categoryId, String subCategoryID, String functionName, int roleID){
		
		List<String> availableStates = new ArrayList<String>();
		
		
		if (null != subCategoryID && !subCategoryID.equalsIgnoreCase("")) {
			for(Map<String,Object> map : wfFunctionDefStateRoleMapping){
				String FUNCTION_ID = (String)map.get("FUNCTION_ID").toString(); 
				int ROLE_ID =  (Integer)map.get("ROLE_ID");
				if(FUNCTION_ID.equalsIgnoreCase(subCategoryID) && ROLE_ID == roleID){
					availableStates.add(map.get("STATE_NAME").toString());
				}
			}
			
			if(availableStates.size()==0){
				for(Map<String,Object> map : wfFunctionDefStateRoleMapping){
					String FUNCTION_ID = (String)map.get("FUNCTION_ID").toString(); 
					int ROLE_ID =  (Integer)map.get("ROLE_ID");
					if(FUNCTION_ID.equalsIgnoreCase(categoryId) && ROLE_ID == roleID){
						availableStates.add(map.get("STATE_NAME").toString());
					}
				}
			}
			if(availableStates.size()==0){
				for(Map<String,Object> map : wfFunctionDefStateRoleMapping){
					String FUNCTION_NAME = (String)map.get("FUNCTION_NAME").toString(); 
					int ROLE_ID =  (Integer)map.get("ROLE_ID");
					if(FUNCTION_NAME.equalsIgnoreCase(functionName)&& ROLE_ID == roleID){
						availableStates.add(map.get("STATE_NAME").toString());
					}
				}
			}
			

		}else if(null != categoryId && !categoryId.equalsIgnoreCase("")){
			for(Map<String,Object> map : wfFunctionDefStateRoleMapping){
				String FUNCTION_ID = (String)map.get("FUNCTION_ID").toString(); 
				int ROLE_ID =  (Integer)map.get("ROLE_ID");
				if(FUNCTION_ID.equalsIgnoreCase(categoryId) && ROLE_ID == roleID){
					availableStates.add(map.get("STATE_NAME").toString());
				}
			}
		}
		if(availableStates.size()==0){
		for(Map<String,Object> map : wfFunctionDefStateRoleMapping){
			String FUNCTION_NAME = (String)map.get("FUNCTION_NAME").toString(); 
			int ROLE_ID =  (Integer)map.get("ROLE_ID");
			if(FUNCTION_NAME.equalsIgnoreCase(functionName)&& ROLE_ID == roleID){
				availableStates.add(map.get("STATE_NAME").toString());
			}
		}
		}
		
		return availableStates;
	}
	public synchronized boolean resetPrepareFunctionDefaultStateRoleMapping() {
		wfFunctionDefStateRoleMapping.clear();
		//try {
		prepareFunctionDefaultStateRoleMappingMaster();
		/*} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		return true;
	}
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jun 9, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
