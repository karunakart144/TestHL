package com.igate.iconnect.BO;

import java.io.Serializable;

public class NewRequestBO implements Serializable  {
	private String NEW_SPACE_REQUIREMENT_FLAG;
	
	 private String NEW_SPACE_OR_EXPANSION_FLAG;
	 private String ODC_FLAG;
	 private String PROJECT_NAME;
	 private String STATUS_NAME;
	 private String TOTAL_SEATS_PROVIDED;
	 private String CREATED_DATE;
	 private String DEPT_HEAD_COMMENTS;
	 private String REQUESTED_QTR_MONTH;
	 private String Date;
	 private String COMMENT_FLAG;
	 private String REQUIREMENT_ID;
	 private String REQUIREMENT_NAME;
	 private String MAIN_REQ_ID;
	 private String MainReqId;
	 private String REQUESTED_COUNT_OF_SEATS;
	 private String DELIVERY_HEAD_COMMENTS;
	 private String STATUS_FLAG;
	 private String CUSTOMER_FOR_OPPORTUNITY;
	 private String SUB_TOTAL_SEATS_REQUESTED;
	 private String IS_EDITABLE;
	 private String REQUESTER_ID;
	 private String REQUESTER_NAME;
	 private String PENDING_WITH;
	 
	 public String getPENDING_WITH() {
		return PENDING_WITH;
	}
	public void setPENDING_WITH(String pENDINGWITH) {
		PENDING_WITH = pENDINGWITH;
	}
	/**
	 * @return the rEQUESTER_ID
	 */
	public String getREQUESTER_ID() {
		return REQUESTER_ID;
	}
	/**
	 * @param rEQUESTERID the rEQUESTER_ID to set
	 */
	public void setREQUESTER_ID(String rEQUESTERID) {
		REQUESTER_ID = rEQUESTERID;
	}


	 
	/**
	 * @return the rEQUESTER_NAME
	 */
	public String getREQUESTER_NAME() {
		return REQUESTER_NAME;
	}
	/**
	 * @param rEQUESTERNAME the rEQUESTER_NAME to set
	 */
	public void setREQUESTER_NAME(String rEQUESTERNAME) {
		REQUESTER_NAME = rEQUESTERNAME;
	}
	/**
	 * @return the iS_EDITABLE
	 */
	public String getIS_EDITABLE() {
		return IS_EDITABLE;
	}
	/**
	 * @param iSEDITABLE the iS_EDITABLE to set
	 */
	public void setIS_EDITABLE(String iSEDITABLE) {
		IS_EDITABLE = iSEDITABLE;
	}
	public String getSUB_TOTAL_SEATS_REQUESTED() {
		return SUB_TOTAL_SEATS_REQUESTED;
	}
	public void setSUB_TOTAL_SEATS_REQUESTED(String sUBTOTALSEATSREQUESTED) {
		SUB_TOTAL_SEATS_REQUESTED = sUBTOTALSEATSREQUESTED;
	}
	public String getCUSTOMER_FOR_OPPORTUNITY() {
		return CUSTOMER_FOR_OPPORTUNITY;
	}
	public void setCUSTOMER_FOR_OPPORTUNITY(String cUSTOMERFOROPPORTUNITY) {
		CUSTOMER_FOR_OPPORTUNITY = cUSTOMERFOROPPORTUNITY;
	}
	public String getSTATUS_FLAG() {
		return STATUS_FLAG;
	}
	public void setSTATUS_FLAG(String sTATUSFLAG) {
		STATUS_FLAG = sTATUSFLAG;
	}
	public String getDELIVERY_HEAD_COMMENTS() {
		return DELIVERY_HEAD_COMMENTS;
	}
	public void setDELIVERY_HEAD_COMMENTS(String dELIVERYHEADCOMMENTS) {
		DELIVERY_HEAD_COMMENTS = dELIVERYHEADCOMMENTS;
	}
	public String getREQUESTED_COUNT_OF_SEATS() {
		return REQUESTED_COUNT_OF_SEATS;
	}
	public void setREQUESTED_COUNT_OF_SEATS(String rEQUESTEDCOUNTOFSEATS) {
		REQUESTED_COUNT_OF_SEATS = rEQUESTEDCOUNTOFSEATS;
	}
	public String getMainReqId() {
		return MainReqId;
	}
	public void setMainReqId(String mainReqId) {
		MainReqId = mainReqId;
	}
	public String getMAIN_REQ_ID() {
		return MAIN_REQ_ID;
	}
	public void setMAIN_REQ_ID(String mAINREQID) {
		MAIN_REQ_ID = mAINREQID;
	}
	public String getCOMMENT_FLAG() {
		return COMMENT_FLAG;
	}
	public void setCOMMENT_FLAG(String cOMMENTFLAG) {
		COMMENT_FLAG = cOMMENTFLAG;
	}
	public String getREQUIREMENT_ID() {
		return REQUIREMENT_ID;
	}
	public void setREQUIREMENT_ID(String rEQUIREMENTID) {
		REQUIREMENT_ID = rEQUIREMENTID;
	}
	public String getREQUIREMENT_NAME() {
		return REQUIREMENT_NAME;
	}
	public void setREQUIREMENT_NAME(String rEQUIREMENTNAME) {
		REQUIREMENT_NAME = rEQUIREMENTNAME;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getNEW_SPACE_REQUIREMENT_FLAG() {
		return NEW_SPACE_REQUIREMENT_FLAG;
	}
	public void setNEW_SPACE_REQUIREMENT_FLAG(String nEWSPACEREQUIREMENTFLAG) {
		NEW_SPACE_REQUIREMENT_FLAG = nEWSPACEREQUIREMENTFLAG;
	}
	public String getNEW_SPACE_OR_EXPANSION_FLAG() {
		return NEW_SPACE_OR_EXPANSION_FLAG;
	}
	public void setNEW_SPACE_OR_EXPANSION_FLAG(String nEWSPACEOREXPANSIONFLAG) {
		NEW_SPACE_OR_EXPANSION_FLAG = nEWSPACEOREXPANSIONFLAG;
	}
	public String getODC_FLAG() {
		return ODC_FLAG;
	}
	public void setODC_FLAG(String oDCFLAG) {
		ODC_FLAG = oDCFLAG;
	}
	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}
	public void setPROJECT_NAME(String pROJECTNAME) {
		PROJECT_NAME = pROJECTNAME;
	}
	public String getSTATUS_NAME() {
		return STATUS_NAME;
	}
	public void setSTATUS_NAME(String sTATUSNAME) {
		STATUS_NAME = sTATUSNAME;
	}
	public String getTOTAL_SEATS_PROVIDED() {
		return TOTAL_SEATS_PROVIDED;
	}
	public void setTOTAL_SEATS_PROVIDED(String tOTALSEATSPROVIDED) {
		TOTAL_SEATS_PROVIDED = tOTALSEATSPROVIDED;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATEDDATE) {
		CREATED_DATE = cREATEDDATE;
	}
	public String getDEPT_HEAD_COMMENTS() {
		return DEPT_HEAD_COMMENTS;
	}
	public void setDEPT_HEAD_COMMENTS(String dEPTHEADCOMMENTS) {
		DEPT_HEAD_COMMENTS = dEPTHEADCOMMENTS;
	}
	public String getREQUESTED_QTR_MONTH() {
		return REQUESTED_QTR_MONTH;
	}
	public void setREQUESTED_QTR_MONTH(String rEQUESTEDQTRMONTH) {
		REQUESTED_QTR_MONTH = rEQUESTEDQTRMONTH;
	}
	public String getCOUNTRY_ID() {
		return COUNTRY_ID;
	}
	public void setCOUNTRY_ID(String cOUNTRYID) {
		COUNTRY_ID = cOUNTRYID;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getCITY_ID() {
		return CITY_ID;
	}
	public void setCITY_ID(String cITYID) {
		CITY_ID = cITYID;
	}
	public String getAREA() {
		return AREA;
	}
	public void setAREA(String aREA) {
		AREA = aREA;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*private String SBU_NAME;
	private String SBU_ID;
	private String SBU_HEAD;*/
	
	
	
	private String UNIT_CODE;
	private String REQUESTED_QUARTER;
	private String STATE_ID;
	private String TOTAL_SEATS_REQUESTED;
	private String DATE_OF_REQUISITION;
	private String REQUESTOR_COMMENTS;
	private String DEPT_HEAD_ID;
	private String DELIVERY_HEAD_ID;
	private String CREATED_BY;
	private String NEW_SPACE_REQ_FLAG;
	private String COUNTRY;
	private String COUNTRY_ID;
	private String CITY;
	private String CITY_ID;
	private String AREA;
	
	
	
	
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	private String SUB_BUSINESS_UNIT_ID;
	private String OPPORTUNITY_IDORPROJ;
	private String CUSTOMER_OPP_NAME;
	public String getCUSTOMER_OPP_NAME() {
		return CUSTOMER_OPP_NAME;
	}

	public void setCUSTOMER_OPP_NAME(String cUSTOMEROPPNAME) {
		CUSTOMER_OPP_NAME = cUSTOMEROPPNAME;
	}

	public String getSUB_BUSINESS_UNIT_ID() {
		return SUB_BUSINESS_UNIT_ID;
	}
	
		public void setSUB_BUSINESS_UNIT_ID(String sUBBUSINESSUNITID) {
		SUB_BUSINESS_UNIT_ID = sUBBUSINESSUNITID;
	}
	public String getOPPORTUNITY_IDORPROJ() {
		return OPPORTUNITY_IDORPROJ;
	}
	public void setOPPORTUNITY_IDORPROJ(String oPPORTUNITYIDORPROJ) {
		OPPORTUNITY_IDORPROJ = oPPORTUNITYIDORPROJ;
	}
	
		//Added by shruti
	private String LOCATION_NAME;
	private String BUILDING_NAME;
	private String FLOOR_NAME;
	private String WING_NAME;
	private int ALREDY_SOFT_ALL; 
	private int SOFT_ALLOCATION;
	private String REMARKS;
	
	public String getLOCATION_NAME() {
		return LOCATION_NAME;
	}
	public void setLOCATION_NAME(String lOCATIONNAME) {
		LOCATION_NAME = lOCATIONNAME;
	}
	public String getBUILDING_NAME() {
		return BUILDING_NAME;
	}
	public void setBUILDING_NAME(String bUILDINGNAME) {
		BUILDING_NAME = bUILDINGNAME;
	}
	public String getFLOOR_NAME() {
		return FLOOR_NAME;
	}
	public void setFLOOR_NAME(String fLOORNAME) {
		FLOOR_NAME = fLOORNAME;
	}
	public String getWING_NAME() {
		return WING_NAME;
	}
	public void setWING_NAME(String wINGNAME) {
		WING_NAME = wINGNAME;
	}
	public int getSOFT_ALLOCATION() {
		return SOFT_ALLOCATION;
	}
	public void setSOFT_ALLOCATION(int sOFTALLOCATION) {
		SOFT_ALLOCATION = sOFTALLOCATION;
	}
	public int getALREDY_SOFT_ALL() {
		return ALREDY_SOFT_ALL;
	}
	public void setALREDY_SOFT_ALL(int aLREDYSOFTALL) {
		ALREDY_SOFT_ALL = aLREDYSOFTALL;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	
	private String jsonSpecialReq;
	public String getJsonSpecialReq() {
		return jsonSpecialReq;
	}
	public void setJsonSpecialReq(String jsonSpecialReq) {
		this.jsonSpecialReq = jsonSpecialReq;
	}
	private String BU;
	public String getBU() {
		return BU;
	}
	public void setBU(String bU) {
		BU = bU;
	}
	
	public String getNEW_SPACE_REQ_FLAG() {
		return NEW_SPACE_REQ_FLAG;
	}
	public void setNEW_SPACE_REQ_FLAG(String nEWSPACEREQFLAG) {
		NEW_SPACE_REQ_FLAG = nEWSPACEREQFLAG;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATEDBY) {
		CREATED_BY = cREATEDBY;
	}
	public String getDELIVERY_HEAD_ID() {
		return DELIVERY_HEAD_ID;
	}
	public void setDELIVERY_HEAD_ID(String dELIVERYHEADID) {
		DELIVERY_HEAD_ID = dELIVERYHEADID;
	}
	public String getDEPT_HEAD_ID() {
		return DEPT_HEAD_ID;
	}
	public void setDEPT_HEAD_ID(String dEPTHEADID) {
		DEPT_HEAD_ID = dEPTHEADID;
	}
	public String getREQUESTOR_COMMENTS() {
		return REQUESTOR_COMMENTS;
	}
	public void setREQUESTOR_COMMENTS(String rEQUESTORCOMMENTS) {
		REQUESTOR_COMMENTS = rEQUESTORCOMMENTS;
	}
	public String getDATE_OF_REQUISITION() {
		return DATE_OF_REQUISITION;
	}
	public void setDATE_OF_REQUISITION(String dATEOFREQUISITION) {
		DATE_OF_REQUISITION = dATEOFREQUISITION;
	}
	public String getTOTAL_SEATS_REQUESTED() {
		return TOTAL_SEATS_REQUESTED;
	}
	public void setTOTAL_SEATS_REQUESTED(String tOTALSEATSREQUESTED) {
		TOTAL_SEATS_REQUESTED = tOTALSEATSREQUESTED;
	}
	public String getSTATE_ID() {
		return STATE_ID;
	}
	public void setSTATE_ID(String sTATEID) {
		STATE_ID = sTATEID;
	}
	public String getREQUESTED_QUARTER() {
		return REQUESTED_QUARTER;
	}
	public void setREQUESTED_QUARTER(String rEQUESTEDQUARTER) {
		REQUESTED_QUARTER = rEQUESTEDQUARTER;
	}
	public String getREQUESTED_MONTH() {
		return REQUESTED_MONTH;
	}
	public void setREQUESTED_MONTH(String rEQUESTEDMONTH) {
		REQUESTED_MONTH = rEQUESTEDMONTH;
	}
	public String getREQUESTED_YEAR() {
		return REQUESTED_YEAR;
	}
	public void setREQUESTED_YEAR(String rEQUESTEDYEAR) {
		REQUESTED_YEAR = rEQUESTEDYEAR;
	}
	private String REQUESTED_MONTH;
	private String REQUESTED_YEAR;
	public String getUNIT_CODE() {
		return UNIT_CODE;
	}
	public void setUNIT_CODE(String uNITCODE) {
		UNIT_CODE = uNITCODE;
	}
	public String getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(String dEPTID) {
		DEPT_ID = dEPTID;
	}
	public String getDEPT_HEAD() {
		return DEPT_HEAD;
	}
	public void setDEPT_HEAD(String dEPTHEAD) {
		DEPT_HEAD = dEPTHEAD;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String DEPT_ID;
	private String DEPT_HEAD;
	
	public String getBUSINESS_UNIT() {
		return BUSINESS_UNIT;
	}
	public void setBUSINESS_UNIT(String bUSINESSUNIT) {
		BUSINESS_UNIT = bUSINESSUNIT;
	}
	public String getQUARTER_MONTH() {
		return QUARTER_MONTH;
	}
	public void setQUARTER_MONTH(String qUARTERMONTH) {
		QUARTER_MONTH = qUARTERMONTH;
	}
	private String SBU_ID;
	private String L6_SBU_NAME;
	
	private String OPPORTUNITY_ID;
	private String BUSINESS_UNIT;
	private String QUARTER_MONTH;
	private int MAIN_REQUEST_ID;
	
	private String STATUS;
	private String SBU_NAME;
	
	private String areaId;
	private String cityId;
	private String totalSeats;
	
	private String newCustFlag;
	private String newSpaceExpnFlag;
	private String jsonLocations;
	private String selected_quarter;
	private String MAIN_REQUEST_ID_FOR_LIST;
	
	
	public String getMAIN_REQUEST_ID_FOR_LIST() {
		return MAIN_REQUEST_ID_FOR_LIST;
	}
	public void setMAIN_REQUEST_ID_FOR_LIST(String mAINREQUESTIDFORLIST) {
		MAIN_REQUEST_ID_FOR_LIST = mAINREQUESTIDFORLIST;
	}
	public String getSelected_quarter() {
		return selected_quarter;
	}
	public void setSelected_quarter(String selectedQuarter) {
		selected_quarter = selectedQuarter;
	}
		public int getMAIN_REQUEST_ID() {
		return MAIN_REQUEST_ID;
	}
	public void setMAIN_REQUEST_ID(int mAINREQUESTID) {
		MAIN_REQUEST_ID = mAINREQUESTID;
	}
	public String getJsonLocations() {
		return jsonLocations;
	}
	public void setJsonLocations(String jsonLocations) {
		this.jsonLocations = jsonLocations;
	}
	public String getNewCustFlag() {
		return newCustFlag;
	}
	public void setNewCustFlag(String newCustFlag) {
		this.newCustFlag = newCustFlag;
	}
	public String getNewSpaceExpnFlag() {
		return newSpaceExpnFlag;
	}
	public void setNewSpaceExpnFlag(String newSpaceExpnFlag) {
		this.newSpaceExpnFlag = newSpaceExpnFlag;
	}
	public String getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}
	private String ReqComments;
	private int odcFlag;
	
	
	
	public int getOdcFlag() {
		return odcFlag;
	}
	public void setOdcFlag(int odcFlag) {
		this.odcFlag = odcFlag;
	}
	public String getReqComments() {
		return ReqComments;
	}
	public void setReqComments(String reqComments) {
		ReqComments = reqComments;
	}
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	public String getOPPORTUNITY_ID() {
		return OPPORTUNITY_ID;
	}
	public void setOPPORTUNITY_ID(String oPPORTUNITYID) {
		OPPORTUNITY_ID = oPPORTUNITYID;
	}
	private String CUSTOMER_ID;
	private String CUSTOMER_NAME;
	
	private String PROJECT_ID;
	private String NAME;
	
	
	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setSBU_ID(String sBU_ID) {
		SBU_ID = sBU_ID;
	}
	public String getSBU_ID() {
		return SBU_ID;
	}
	public void setL6_SBU_NAME(String l6_SBU_NAME) {
		L6_SBU_NAME = l6_SBU_NAME;
	}
	public String getL6_SBU_NAME() {
		return L6_SBU_NAME;
	}
	public void setPROJECT_ID(String pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}
	public String getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNAME() {
		return NAME;
	}
	public void setSBU_NAME(String sBU_NAME) {
		SBU_NAME = sBU_NAME;
	}
	public String getSBU_NAME() {
		return SBU_NAME;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getSTATUS() {
		return STATUS;
	}
	//Added by preeti for space planning team comments
	private String SPACE_PLANNING_TEAM_COMMENTS;


	public String getSPACE_PLANNING_TEAM_COMMENTS() {
		return SPACE_PLANNING_TEAM_COMMENTS;
	}
	public void setSPACE_PLANNING_TEAM_COMMENTS(String sPACEPLANNINGTEAMCOMMENTS) {
		SPACE_PLANNING_TEAM_COMMENTS = sPACEPLANNINGTEAMCOMMENTS;
	}
	//end Preeti
	

}
