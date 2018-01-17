/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

public class COMMON_ExecuteProcedure {
	public String executeiconnectProc(String procName, String[] argName,
			int[] argType, String[] inputValues, DataSource ds) { // setSql(SPROC_NAME);
		iConnectStoredProcedure sproc = new iConnectStoredProcedure(ds,
				procName, argName, argType);
		Map results = sproc.execute(argName, inputValues);
		return (String) results.get("json");
	}
	private class iConnectStoredProcedureTest extends StoredProcedure {

		public iConnectStoredProcedureTest(DataSource ds, String procName,
				String argName[], int argType[]) {
			setDataSource(ds);
			setSql(procName);
			for (int i = 0; i < argName.length; i++) {
				declareParameter(new SqlParameter(argName[i], argType[i]));
			}
			compile();
		}

		public Map execute(String[] names, String[] values) {
			Map<String,String> inputs = new HashMap<String, String>();
			for (int i = 0; i < names.length; i++) {
				inputs.put(names[i], values[i]);
			}
			return execute(inputs);
		}
	}
	public List executeiconnectProcColourCode(String procName, String[] argName,
			int[] argType, String[] inputValues, DataSource ds) { // setSql(SPROC_NAME);
		iConnectStoredProcedureTest sproc = new iConnectStoredProcedureTest(ds,
				procName, argName, argType);
		Map results = sproc.execute(argName, inputValues);
		return  (List) results.get("#result-set-1");
	}
	private class iConnectStoredProcedure extends StoredProcedure {

		public iConnectStoredProcedure(DataSource ds, String procName,
				String argName[], int argType[]) {
			setDataSource(ds);
			setSql(procName);
			for (int i = 0; i < argName.length; i++) {
				declareParameter(new SqlParameter(argName[i], argType[i]));
			}
			declareParameter(new SqlOutParameter("json", Types.INTEGER, null,
					new ReturnClob()));
			compile();
		}

		public Map execute(String[] names, String[] values) {
			Map<String,String> inputs = new HashMap<String, String>();
			// inputs.put("project_id", ("1001"));
			for (int i = 0; i < names.length; i++) {
				inputs.put(names[i], values[i]);
			}
			return execute(inputs);
		}
		
	}
	private class ReturnClob implements SqlReturnType {
		public Object getTypeValue(CallableStatement cs, int i, int sqlType,
				String typeName) throws SQLException {
			Clob c = cs.getClob(i);

			// log.debug(lobHandler.getClobAsString(cs.getResultS et(), i));
			String clbString = "";
			clbString = ClobToString(c);
			return clbString;
		}
	}
	private static String ClobToString(Clob cl) throws SQLException {
		if (cl == null)
			return "";

		if (cl != null) {
			BufferedReader reader = new BufferedReader(cl.getCharacterStream());
            StringBuilder clobValue = new StringBuilder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					clobValue.append(line);
					clobValue.append('\n');
				}
				return clobValue.toString();
			} catch (IOException e) {
				throw new SQLException("Unable to read CLOB: " + e.getMessage());
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ignore) {
					}
				}
			}
		} else {
			return null;
		}
	}

	
	public void executeBatchUpdation(String query, ArrayList arglist,int ArgTypes[],  DataSource ds)
			 {
		executeBatch(query, ArgTypes, arglist, ds);
	}

	private void executeBatch(String query, int[] argTypes, ArrayList argsList,
			DataSource dtsrc) {
		if (argsList != null && argsList.size() > 0) {
			BatchSqlUpdate updBatch = new BatchSqlUpdate(dtsrc, query);
            for (int argType : argTypes) updBatch.declareParameter(new SqlParameter(argType));
			updBatch.compile();
			Object[] genArgs = new Object[argTypes.length];
            for (Object anArgsList : argsList) {
                genArgs = (Object[]) anArgsList;
                updBatch.update(genArgs);

            }
			if (updBatch.getQueueCount() > 0) {
				int resultIns[] = updBatch.flush();
			}
		}
	}

	public void executeBatchUpdationForCustomList(List<Map<String, Object>> listofdata,  DataSource ds)
	{
        for (Map<String, Object> data : listofdata) {

            executeBatch(data.get("QUERY").toString(), (int[]) data.get("ARGTYPE"), (ArrayList) data.get("ARGLIST"), ds);
        }
			
	}
	/**********Auto Assignment************/
	public String executeProcedureForScoreUpdation(String procName, String[] argName,
			int[] argType, String[] inputValues, DataSource ds){
		iconnectScoreCalculation scoreProc=new iconnectScoreCalculation(ds,argName,argType,procName);
		Map results=scoreProc.execute(argName,inputValues);		
		return (String) results.get("scoreOutput");
		
	}
	private class iconnectScoreCalculation extends StoredProcedure{
		
		public iconnectScoreCalculation(DataSource ds,String[] argName,int[] argType,String procName){
			setDataSource(ds);
			setSql(procName);
			for(int i=0;i<argName.length;i++){
				declareParameter(new SqlParameter(argName[i],argType[i]));				
			}			
			declareParameter(new SqlOutParameter("scoreOutput", Types.INTEGER, null,new ReturnClob()));
			compile();
		}
		public Map execute(String[] argNames,String[] inputValues){
			Map<String,String> inputs = new HashMap<String, String>();
			for(int i=0;i< argNames.length;i++){
				inputs.put(argNames[i],inputValues[i]);
			}
			return execute(inputs);
		}
		
	}
	/**********END Auto Assignment************/
}




/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:714599
Changes Made on:Jun 15, 2011
End-------Version 1.0-------
            
-----------------------------------------------------------------------------*/