package com.igate.iconnect.BO;

public class WORKSPACE_PLANNING_UpdateBO {
	
	 private String TABLE_NAME;
	 private String COLUMN_NAME;
	 private String COLUMN_VALUE;
	 private String WHERE_CONDITION;
	 
	/**
	 * @return the wHERE_CONDITION
	 */
	public String getWHERE_CONDITION() {
		return WHERE_CONDITION;
	}

	/**
	 * @param wHERECONDITION the wHERE_CONDITION to set
	 */
	public void setWHERE_CONDITION(String wHERECONDITION) {
		WHERE_CONDITION = wHERECONDITION;
	}

	/**
	 * @return the tABLE_NAME
	 */
	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	/**
	 * @param tABLENAME the tABLE_NAME to set
	 */
	public void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}

	/**
	 * @return the cOLUMN_NAME
	 */
	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}

	/**
	 * @param cOLUMNNAME the cOLUMN_NAME to set
	 */
	public void setCOLUMN_NAME(String cOLUMNNAME) {
		COLUMN_NAME = cOLUMNNAME;
	}

	/**
	 * @return the cOLUMN_VALUE
	 */
	public String getCOLUMN_VALUE() {
		return COLUMN_VALUE;
	}

	/**
	 * @param cOLUMNVALUE the cOLUMN_VALUE to set
	 */
	public void setCOLUMN_VALUE(String cOLUMNVALUE) {
		COLUMN_VALUE = cOLUMNVALUE;
	}
	 
	 

}
