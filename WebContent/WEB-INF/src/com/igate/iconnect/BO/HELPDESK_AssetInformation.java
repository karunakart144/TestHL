/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

//Dhiren:Added Serializable
public class HELPDESK_AssetInformation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7405554648307546495L;
	private String ASSET_DET_ID;
    private String COMPANY_NAME;
    private String SERIAL_NUMBER;
    private String ASSET_ID;
    private String PSFT_ASSET_CODE;
    private String COUNTRY_NAME;
    private String LOCATION_NAME;
    private String DEPARTMENT;
    private String SUB_LOCATION;
    private String ASSET_TYPE;
    private String CLASSIFICATION;
    private String HOST_NAME;
    private String STATUS;
    private String ASSET_RETURN_DATE;
    private String MAKE;
    private String MODEL;
    private String PRIMARY_ADMIN;
    private String MANAGER;
    private String SECONDARY_ADMIN;
    private String ALLOCATION_DATE;
    private String IP_ADDRESS;
    private String CUBICLE_CODE;
    private String ASSIGNED_TO;
    private String PROJECT_NAME;
    private String PROJECT_MANAGER;
    private String CRITICALITY;
    private String IS_SECURITY_CONFIRMED;
    private String PRIORTIZATION;
    private String LAPTOP_GATEPASS_NO;
    private String ENGINEER_REMARKS;
    private String PROC_TYPE;
    private String PURCHASE_ORDER;
    private String PO_DATE;
    private String CURRENCY;
    private String APPRX_COST;
    private String IS_BONDED;
    private String STPI_BOND;
    private String STPI_BOND_DATE;
    private String STP_BOND_EXP_DATE;
    private String OWNER_TYPE;
    private String OWNED_BY;
    private String DISP_TYPE;
    private String ADMIN_REMARKS;
    private String SUPP_TYPE;
    private String VENDOR;
    private String SUPPLY_VENDOR;
    private String INSTALLATION_DATE;
    private String WARRANTY_START_DATE;
    private String WARRANTY_END_DATE;
    private String SUPPORT_VENDOR;
    private String SUPP_REFERENCE;
    private String SUPPORT_Start_date;
    private String SUPPORT_END_DATE;
    private String SUPPORT_DESC;
    private String OWNER;
    private String FUNCTION_NAME;
    private String CREATED_BY;
    private String CREATED_DATE;
    private String MODIFIED_BY;
    private String MODIFIED_DATE;
    private String ASSET_COMP_ID;
    private String PARENT_SERIAL_NUMBER;
    private String COMPONENT;


    public String getASSET_COMP_ID() {
        return ASSET_COMP_ID;
    }

    public void setASSET_COMP_ID(String aSSETCOMPID) {
        ASSET_COMP_ID = aSSETCOMPID;
    }

    public String getPARENT_SERIAL_NUMBER() {
        return PARENT_SERIAL_NUMBER;
    }

    public void setPARENT_SERIAL_NUMBER(String pARENTSERIALNUMBER) {
        PARENT_SERIAL_NUMBER = pARENTSERIALNUMBER;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String cOMPONENT) {
        COMPONENT = cOMPONENT;
    }

    public String getASSET_DET_ID() {
        return ASSET_DET_ID;
    }

    public void setASSET_DET_ID(String aSSETDETID) {
        ASSET_DET_ID = aSSETDETID;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String cOMPANYNAME) {
        COMPANY_NAME = cOMPANYNAME;
    }

    public String getSERIAL_NUMBER() {
        return SERIAL_NUMBER;
    }

    public void setSERIAL_NUMBER(String sERIALNUMBER) {
        SERIAL_NUMBER = sERIALNUMBER;
    }

    public String getASSET_ID() {
        return ASSET_ID;
    }

    public void setASSET_ID(String aSSETID) {
        ASSET_ID = aSSETID;
    }

    public String getPSFT_ASSET_CODE() {
        return PSFT_ASSET_CODE;
    }

    public void setPSFT_ASSET_CODE(String pSFTASSETCODE) {
        PSFT_ASSET_CODE = pSFTASSETCODE;
    }

    public String getCOUNTRY_NAME() {
        return COUNTRY_NAME;
    }

    public void setCOUNTRY_NAME(String cOUNTRYNAME) {
        COUNTRY_NAME = cOUNTRYNAME;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String lOCATIONNAME) {
        LOCATION_NAME = lOCATIONNAME;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String dEPARTMENT) {
        DEPARTMENT = dEPARTMENT;
    }

    public String getSUB_LOCATION() {
        return SUB_LOCATION;
    }

    public void setSUB_LOCATION(String sUBLOCATION) {
        SUB_LOCATION = sUBLOCATION;
    }

    public String getASSET_TYPE() {
        return ASSET_TYPE;
    }

    public void setASSET_TYPE(String aSSETTYPE) {
        ASSET_TYPE = aSSETTYPE;
    }

    public String getCLASSIFICATION() {
        return CLASSIFICATION;
    }

    public void setCLASSIFICATION(String cLASSIFICATION) {
        CLASSIFICATION = cLASSIFICATION;
    }

    public String getHOST_NAME() {
        return HOST_NAME;
    }

    public void setHOST_NAME(String hOSTNAME) {
        HOST_NAME = hOSTNAME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    public String getASSET_RETURN_DATE() {
        return ASSET_RETURN_DATE;
    }

    public void setASSET_RETURN_DATE(String aSSETRETURNDATE) {
        ASSET_RETURN_DATE = aSSETRETURNDATE;
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

    public String getPRIMARY_ADMIN() {
        return PRIMARY_ADMIN;
    }

    public void setPRIMARY_ADMIN(String pRIMARYADMIN) {
        PRIMARY_ADMIN = pRIMARYADMIN;
    }

    public String getMANAGER() {
        return MANAGER;
    }

    public void setMANAGER(String mANAGER) {
        MANAGER = mANAGER;
    }

    public String getSECONDARY_ADMIN() {
        return SECONDARY_ADMIN;
    }

    public void setSECONDARY_ADMIN(String sECONDARYADMIN) {
        SECONDARY_ADMIN = sECONDARYADMIN;
    }

    public String getALLOCATION_DATE() {
        return ALLOCATION_DATE;
    }

    public void setALLOCATION_DATE(String aLLOCATIONDATE) {
        ALLOCATION_DATE = aLLOCATIONDATE;
    }

    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public void setIP_ADDRESS(String iPADDRESS) {
        IP_ADDRESS = iPADDRESS;
    }

    public String getCUBICLE_CODE() {
        return CUBICLE_CODE;
    }

    public void setCUBICLE_CODE(String cUBICLECODE) {
        CUBICLE_CODE = cUBICLECODE;
    }

    public String getASSIGNED_TO() {
        return ASSIGNED_TO;
    }

    public void setASSIGNED_TO(String aSSIGNEDTO) {
        ASSIGNED_TO = aSSIGNEDTO;
    }

    public String getPROJECT_NAME() {
        return PROJECT_NAME;
    }

    public void setPROJECT_NAME(String pROJECTNAME) {
        PROJECT_NAME = pROJECTNAME;
    }

    public String getPROJECT_MANAGER() {
        return PROJECT_MANAGER;
    }

    public void setPROJECT_MANAGER(String pROJECTMANAGER) {
        PROJECT_MANAGER = pROJECTMANAGER;
    }

    public String getCRITICALITY() {
        return CRITICALITY;
    }

    public void setCRITICALITY(String cRITICALITY) {
        CRITICALITY = cRITICALITY;
    }

    public String getIS_SECURITY_CONFIRMED() {
        return IS_SECURITY_CONFIRMED;
    }

    public void setIS_SECURITY_CONFIRMED(String iSSECURITYCONFIRMED) {
        IS_SECURITY_CONFIRMED = iSSECURITYCONFIRMED;
    }

    public String getPRIORTIZATION() {
        return PRIORTIZATION;
    }

    public void setPRIORTIZATION(String pRIORTIZATION) {
        PRIORTIZATION = pRIORTIZATION;
    }

    public String getLAPTOP_GATEPASS_NO() {
        return LAPTOP_GATEPASS_NO;
    }

    public void setLAPTOP_GATEPASS_NO(String lAPTOPGATEPASSNO) {
        LAPTOP_GATEPASS_NO = lAPTOPGATEPASSNO;
    }

    public String getENGINEER_REMARKS() {
        return ENGINEER_REMARKS;
    }

    public void setENGINEER_REMARKS(String eNGINEERREMARKS) {
        ENGINEER_REMARKS = eNGINEERREMARKS;
    }

    public String getPROC_TYPE() {
        return PROC_TYPE;
    }

    public void setPROC_TYPE(String pROCTYPE) {
        PROC_TYPE = pROCTYPE;
    }

    public String getPURCHASE_ORDER() {
        return PURCHASE_ORDER;
    }

    public void setPURCHASE_ORDER(String pURCHASEORDER) {
        PURCHASE_ORDER = pURCHASEORDER;
    }

    public String getPO_DATE() {
        return PO_DATE;
    }

    public void setPO_DATE(String pODATE) {
        PO_DATE = pODATE;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String cURRENCY) {
        CURRENCY = cURRENCY;
    }

    public String getAPPRX_COST() {
        return APPRX_COST;
    }

    public void setAPPRX_COST(String aPPRXCOST) {
        APPRX_COST = aPPRXCOST;
    }

    public String getIS_BONDED() {
        return IS_BONDED;
    }

    public void setIS_BONDED(String iSBONDED) {
        IS_BONDED = iSBONDED;
    }

    public String getSTPI_BOND() {
        return STPI_BOND;
    }

    public void setSTPI_BOND(String sTPIBOND) {
        STPI_BOND = sTPIBOND;
    }

    public String getSTPI_BOND_DATE() {
        return STPI_BOND_DATE;
    }

    public void setSTPI_BOND_DATE(String sTPIBONDDATE) {
        STPI_BOND_DATE = sTPIBONDDATE;
    }

    public String getSTP_BOND_EXP_DATE() {
        return STP_BOND_EXP_DATE;
    }

    public void setSTP_BOND_EXP_DATE(String sTPBONDEXPDATE) {
        STP_BOND_EXP_DATE = sTPBONDEXPDATE;
    }

    public String getOWNER_TYPE() {
        return OWNER_TYPE;
    }

    public void setOWNER_TYPE(String oWNERTYPE) {
        OWNER_TYPE = oWNERTYPE;
    }

    public String getOWNED_BY() {
        return OWNED_BY;
    }

    public void setOWNED_BY(String oWNEDBY) {
        OWNED_BY = oWNEDBY;
    }

    public String getDISP_TYPE() {
        return DISP_TYPE;
    }

    public void setDISP_TYPE(String dISPTYPE) {
        DISP_TYPE = dISPTYPE;
    }

    public String getADMIN_REMARKS() {
        return ADMIN_REMARKS;
    }

    public void setADMIN_REMARKS(String aDMINREMARKS) {
        ADMIN_REMARKS = aDMINREMARKS;
    }

    public String getSUPP_TYPE() {
        return SUPP_TYPE;
    }

    public void setSUPP_TYPE(String sUPPTYPE) {
        SUPP_TYPE = sUPPTYPE;
    }

    public String getVENDOR() {
        return VENDOR;
    }

    public void setVENDOR(String vENDOR) {
        VENDOR = vENDOR;
    }

    public String getSUPPLY_VENDOR() {
        return SUPPLY_VENDOR;
    }

    public void setSUPPLY_VENDOR(String sUPPLYVENDOR) {
        SUPPLY_VENDOR = sUPPLYVENDOR;
    }

    public String getINSTALLATION_DATE() {
        return INSTALLATION_DATE;
    }

    public void setINSTALLATION_DATE(String iNSTALLATIONDATE) {
        INSTALLATION_DATE = iNSTALLATIONDATE;
    }

    public String getWARRANTY_START_DATE() {
        return WARRANTY_START_DATE;
    }

    public void setWARRANTY_START_DATE(String wARRANTYSTARTDATE) {
        WARRANTY_START_DATE = wARRANTYSTARTDATE;
    }

    public String getWARRANTY_END_DATE() {
        return WARRANTY_END_DATE;
    }

    public void setWARRANTY_END_DATE(String wARRANTYENDDATE) {
        WARRANTY_END_DATE = wARRANTYENDDATE;
    }

    public String getSUPPORT_VENDOR() {
        return SUPPORT_VENDOR;
    }

    public void setSUPPORT_VENDOR(String sUPPORTVENDOR) {
        SUPPORT_VENDOR = sUPPORTVENDOR;
    }

    public String getSUPP_REFERENCE() {
        return SUPP_REFERENCE;
    }

    public void setSUPP_REFERENCE(String sUPPREFERENCE) {
        SUPP_REFERENCE = sUPPREFERENCE;
    }

    public String getSUPPORT_Start_date() {
        return SUPPORT_Start_date;
    }

    public void setSUPPORT_Start_date(String sUPPORTStartDate) {
        SUPPORT_Start_date = sUPPORTStartDate;
    }

    public String getSUPPORT_END_DATE() {
        return SUPPORT_END_DATE;
    }

    public void setSUPPORT_END_DATE(String sUPPORTENDDATE) {
        SUPPORT_END_DATE = sUPPORTENDDATE;
    }

    public String getSUPPORT_DESC() {
        return SUPPORT_DESC;
    }

    public void setSUPPORT_DESC(String sUPPORTDESC) {
        SUPPORT_DESC = sUPPORTDESC;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String oWNER) {
        OWNER = oWNER;
    }

    public String getFUNCTION_NAME() {
        return FUNCTION_NAME;
    }

    public void setFUNCTION_NAME(String fUNCTIONNAME) {
        FUNCTION_NAME = fUNCTIONNAME;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String cREATEDBY) {
        CREATED_BY = cREATEDBY;
    }

    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(String cREATEDDATE) {
        CREATED_DATE = cREATEDDATE;
    }

    public String getMODIFIED_BY() {
        return MODIFIED_BY;
    }

    public void setMODIFIED_BY(String mODIFIEDBY) {
        MODIFIED_BY = mODIFIEDBY;
    }

    public String getMODIFIED_DATE() {
        return MODIFIED_DATE;
    }

    public void setMODIFIED_DATE(String mODIFIEDDATE) {
        MODIFIED_DATE = mODIFIEDDATE;
    }

}
