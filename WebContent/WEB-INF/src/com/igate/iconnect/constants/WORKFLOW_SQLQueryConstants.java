/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.constants;

public class WORKFLOW_SQLQueryConstants {

    public static final String SELECT_WFID_NAME_FROM_IC_WORKFLOW_MASTER = "SELECT WORKFLOW_ID, NAME,DESCRIPTION FROM IC_WORKFLOW_MASTER ";

    public static final String SELECT_STATE_ID_NAME_FROM_IC_WORKFLOW_STATE_MASTER = "SELECT STATE_ID, NAME FROM IC_WORKFLOW_STATE_MASTER WHERE WORKFLOW_ID  = ?";

    public static final String SELECT_ROLE_ID_NAME_FROM_IC_WORKFLOW_ROLE_MAPPING = "SELECT WRM.ROLE_ID, RM.NAME FROM IC_WORKFLOW_ROLE_MAPPING WRM, IC_ROLE_MASTER RM WHERE RM.ROLE_ID = WRM.ROLE_ID AND WORKFLOW_ID = ?";

    public static final String SELECT_TRANISTION_ID_FROMSTATE_TOSTATE = "SELECT TD.TRANSITION_ID,TD.NAME, SM_FROMSTATE.NAME,SM_TOSTATE.NAME FROM IC_WORKFLOW_TRANSITION_MASTER TD,  IC_WORKFLOW_STATE_MASTER SM_FROMSTATE,IC_WORKFLOW_STATE_MASTER SM_TOSTATE "
            + " WHERE TD.FROM_STATE = SM_FROMSTATE.STATE_ID "
            + " AND TD.TO_STATE = SM_TOSTATE.STATE_ID AND TD.WORKFLOW_ID = ?  ORDER BY SM_TOSTATE.NAME";

    public static final String SELECT_ROLENAME_CUSTOMFIELD_FROM_IC_WORKFLOW_TRANSITION_ROLE_MAPPING = "SELECT RMASTER.NAME,TR.CUSTOM_FIELDS FROM "
            + " IC_WORKFLOW_TRANSITION_ROLE_MAPPING TR,  "
            + " IC_ROLE_MASTER RMASTER , "
            + " IC_WORKFLOW_ROLE_MAPPING WRM "
            + " WHERE TR.ROLE_ID = RMASTER.ROLE_ID AND WRM.WORKFLOW_ID = ? AND WRM.ROLE_ID = RMASTER.ROLE_ID AND TR.TRANSITION_ID = ? ";

    public static final String SELECT_ROLENAME_CUSTOMFIELD_PERMISSION_FROM_IC_WORKFLOW_RECORD_PERMISSION = "SELECT SM.NAME, RMASTER.NAME,RP.CUSTOM_FIELDS, RP.PERMISSION FROM IC_WORKFLOW_RECORD_PERMISSION RP, IC_WORKFLOW_STATE_MASTER SM, IC_WORKFLOW_ROLE_MAPPING RM,IC_ROLE_MASTER RMASTER  "
            + " WHERE RP.STATE_ID = SM.STATE_ID "
            + " AND RP.ROLE_ID = RMASTER.ROLE_ID AND RM.ROLE_ID = RMASTER.ROLE_ID AND RP.WORKFLOW_ID = ?";

    public static final String dfdf = "SELECT DISTINCT SM.NAME, RMASTER.NAME  FROM IC_WORKFLOW_FIELD_PERMISSION FP, IC_WORKFLOW_STATE_MASTER SM, IC_WORKFLOW_ROLE_MAPPING RM,IC_ROLE_MASTER RMASTER  "
            + " WHERE FP.STATE_ID = SM.STATE_ID"
            + " AND FP.ROLE_ID = RMASTER.ROLE_ID AND RM.ROLE_ID = RMASTER.ROLE_ID AND FP.WORKFLOW_ID = ?";

    public static final String custom = "SELECT DISTINCT FP.UI_FIELDS, FP.PERMISSION,MFD.DB_FIELDS FROM IC_WORKFLOW_FIELD_PERMISSION FP, IC_WORKFLOW_STATE_MASTER SM, IC_WORKFLOW_ROLE_MAPPING RM,IC_MENU_FIELD_DETAILS MFD "
            + " WHERE FP.STATE_ID = (SELECT STATE_ID FROM IC_WORKFLOW_STATE_MASTER WHERE NAME = ? and workflow_id = ?)"
            + " AND FP.ROLE_ID = (SELECT ROLE_ID FROM IC_ROLE_MASTER WHERE NAME = ?) AND MFD.FIELD_ID = FP.UI_FIELDS AND FP.WORKFLOW_ID = ?";

    public static final String SELECT_WF_DEFAULT_STATE_ROLE_MAPPING = "select ISR.FUNCTION_ID ,ISR.ROLE_ID, ISR.STATE_ID, IRM.NAME AS ROLE_NAME, IC.NAME AS FUNCTION_NAME , IST.NAME AS STATE_NAME,STATE_ORDER from IC_WORKFLOW_DEFAULT_STATE_ROLE_MAPPING ISR,IC_IHD_CATEGORY_MASTER IC,IC_WORKFLOW_STATE_MASTER IST, "
    						+" IC_ROLE_MASTER IRM WHERE ISR.FUNCTION_ID=IC.CATEGORY_ID AND ISR.ROLE_ID=IRM.ROLE_ID AND ISR.STATE_ID=IST.STATE_ID ORDER BY FUNCTION_ID,STATE_ORDER";
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jun 9, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
