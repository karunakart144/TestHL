/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

//Dhiren:Added Serializable
public class HELPDESK_AssetTab implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6263171175128851303L;
	private String ASSET_TYPE;
    private String MAKE;
    private String MODEL;
    private String ASSET_PARENT_SERIAL_NO;
    private String ASSET_CHILD_SERIAL_NO;
    private String COMPONENT;
    private String SUB_COMPONENT;
    private String SUPPORTING_VENDOR;
    private String OS_VERSION;
    private String CLASSIFICATION;
    private String ALLOCATION_DATE;
    private String WARRENTY_START_DATE;
    private String WARRENTY_END_DATE;
    private String ASSET_DETAIL_ID;
    private String TICKET_ID;
    private String WORKFLOW_STATE;
    private boolean PERMISSION;

    public boolean isPERMISSION() {
        return PERMISSION;
    }

    public void setPERMISSION(boolean pERMISSION) {
        PERMISSION = pERMISSION;
    }

    public String getWORKFLOW_STATE() {
        return WORKFLOW_STATE;
    }

    public void setWORKFLOW_STATE(String wORKFLOWSTATE) {
        WORKFLOW_STATE = wORKFLOWSTATE;
    }

    public String getTICKET_ID() {
        return TICKET_ID;
    }

    public void setTICKET_ID(String tICKETID) {
        TICKET_ID = tICKETID;
    }

    public String getASSET_DETAIL_ID() {
        return ASSET_DETAIL_ID;
    }

    public void setASSET_DETAIL_ID(String aSSETDETAILID) {
        ASSET_DETAIL_ID = aSSETDETAILID;
    }

    public String getCLASSIFICATION() {
        return CLASSIFICATION;
    }

    public void setCLASSIFICATION(String cLASSIFICATION) {
        CLASSIFICATION = cLASSIFICATION;
    }

    public String getALLOCATION_DATE() {
        return ALLOCATION_DATE;
    }

    public void setALLOCATION_DATE(String aLLOCATIONDATE) {
        ALLOCATION_DATE = aLLOCATIONDATE;
    }

    public String getWARRENTY_START_DATE() {
        return WARRENTY_START_DATE;
    }

    public void setWARRENTY_START_DATE(String wARRENTYSTARTDATE) {
        WARRENTY_START_DATE = wARRENTYSTARTDATE;
    }

    public String getWARRENTY_END_DATE() {
        return WARRENTY_END_DATE;
    }

    public void setWARRENTY_END_DATE(String wARRENTYENDDATE) {
        WARRENTY_END_DATE = wARRENTYENDDATE;
    }

    public String getASSET_TYPE() {
        return ASSET_TYPE;
    }

    public void setASSET_TYPE(String aSSETTYPE) {
        ASSET_TYPE = aSSETTYPE;
    }

    public String getMAKE() {
        return MAKE;
    }

    public void setMAKE(String mAKE) {
        MAKE = mAKE;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String mODEL) {
        MODEL = mODEL;
    }

    public String getASSET_PARENT_SERIAL_NO() {
        return ASSET_PARENT_SERIAL_NO;
    }

    public void setASSET_PARENT_SERIAL_NO(String aSSETPARENTSERIALNO) {
        ASSET_PARENT_SERIAL_NO = aSSETPARENTSERIALNO;
    }

    public String getASSET_CHILD_SERIAL_NO() {
        return ASSET_CHILD_SERIAL_NO;
    }

    public void setASSET_CHILD_SERIAL_NO(String aSSETCHILDSERIALNO) {
        ASSET_CHILD_SERIAL_NO = aSSETCHILDSERIALNO;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String cOMPONENT) {
        COMPONENT = cOMPONENT;
    }

    public String getSUB_COMPONENT() {
        return SUB_COMPONENT;
    }

    public void setSUB_COMPONENT(String sUBCOMPONENT) {
        SUB_COMPONENT = sUBCOMPONENT;
    }

    public String getSUPPORTING_VENDOR() {
        return SUPPORTING_VENDOR;
    }

    public void setSUPPORTING_VENDOR(String sUPPORTINGVENDOR) {
        SUPPORTING_VENDOR = sUPPORTINGVENDOR;
    }

    public String getOS_VERSION() {
        return OS_VERSION;
    }

    public void setOS_VERSION(String oSVERSION) {
        OS_VERSION = oSVERSION;
    }

}
