package com.igate.iconnect.BO;

public class EditRequest {

	private String NEW_SPACE_REQUIREMENT_FLAG;
	private String NEW_SPACE_OR_EXPANSION_FLAG;
	private String ODC_FLAG;
	private String SBU_NAME;
	private String OPPORTUNITY_ID;
	private String CUSTOMER_NAME;
	private String REQUESTED_QUARTER;
	private String REQUESTED_YEAR;
	private String REQUESTED_MONTH;
	private String TOTAL_SEATS_REQUESTED;
	private String TOTAL_SEATS_PROVIDED;
	private String STATE_ID;
	private String jsonLocations;
	private String STATUS;
	private String REQUIREMENT_NAME;
	private String REQUIREMENT_ID;
	private String REQUESTED_COUNT_OF_SEATS;
	private String MAIN_REQUEST_ID;
	private String mainReqId;
	private String SUB_REQUEST_ID;
	private String EXCEPTION_COUNT;
	private String AVAILABLE_COUNT;
	private String ALLOCATABLE_SEATS;
	private String DEACTIVATE_SOFT_ALLOC_SEATS;
	
	
	public String getDEACTIVATE_SOFT_ALLOC_SEATS() {
		return DEACTIVATE_SOFT_ALLOC_SEATS;
	}
	public void setDEACTIVATE_SOFT_ALLOC_SEATS(String dEACTIVATESOFTALLOCSEATS) {
		DEACTIVATE_SOFT_ALLOC_SEATS = dEACTIVATESOFTALLOCSEATS;
	}
	public String getALLOCATABLE_SEATS() {
		return ALLOCATABLE_SEATS;
	}
	public void setALLOCATABLE_SEATS(String aLLOCATABLESEATS) {
		ALLOCATABLE_SEATS = aLLOCATABLESEATS;
	}
	public String getAVAILABLE_COUNT() {
		return AVAILABLE_COUNT;
	}
	public void setAVAILABLE_COUNT(String aVAILABLECOUNT) {
		AVAILABLE_COUNT = aVAILABLECOUNT;
	}
	public String getEXCEPTION_COUNT() {
		return EXCEPTION_COUNT;
	}
	public void setEXCEPTION_COUNT(String eXCEPTIONCOUNT) {
		EXCEPTION_COUNT = eXCEPTIONCOUNT;
	}
	public String getSUB_REQUEST_ID() {
		return SUB_REQUEST_ID;
	}
	public void setSUB_REQUEST_ID(String sUBREQUESTID) {
		SUB_REQUEST_ID = sUBREQUESTID;
	}
	public String getMainReqId() {
		return mainReqId;
	}
	public void setMainReqId(String mainReqId) {
		this.mainReqId = mainReqId;
	}
	private String SUB_BUSINESS_UNIT_ID;
	private String OPPORTUNITY_IDORPROJ;
	
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

	public String getMAIN_REQUEST_ID() {
		return MAIN_REQUEST_ID;
	}
	
	public void setMAIN_REQUEST_ID(String mAINREQUESTID) {
		MAIN_REQUEST_ID = mAINREQUESTID;
	}
	
	public String getREQUESTED_COUNT_OF_SEATS() {
		return REQUESTED_COUNT_OF_SEATS;
	}
	public void setREQUESTED_COUNT_OF_SEATS(String rEQUESTEDCOUNTOFSEATS) {
		REQUESTED_COUNT_OF_SEATS = rEQUESTEDCOUNTOFSEATS;
	}
	public String getREQUIREMENT_NAME() {
		return REQUIREMENT_NAME;
	}
	public void setREQUIREMENT_NAME(String rEQUIREMENTNAME) {
		REQUIREMENT_NAME = rEQUIREMENTNAME;
	}
	public String getREQUIREMENT_ID() {
		return REQUIREMENT_ID;
	}
	public void setREQUIREMENT_ID(String rEQUIREMENTID) {
		REQUIREMENT_ID = rEQUIREMENTID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getJsonSpecialReq() {
		return jsonSpecialReq;
	}
	public void setJsonSpecialReq(String jsonSpecialReq) {
		this.jsonSpecialReq = jsonSpecialReq;
	}
	public String getBU() {
		return BU;
	}
	public void setBU(String bU) {
		BU = bU;
	}
	public String getDATE_OF_REQUISITION() {
		return DATE_OF_REQUISITION;
	}
	public void setDATE_OF_REQUISITION(String dATEOFREQUISITION) {
		DATE_OF_REQUISITION = dATEOFREQUISITION;
	}
	public String getDEPT_HEAD_ID() {
		return DEPT_HEAD_ID;
	}
	public void setDEPT_HEAD_ID(String dEPTHEADID) {
		DEPT_HEAD_ID = dEPTHEADID;
	}
	public String getDELIVERY_HEAD_ID() {
		return DELIVERY_HEAD_ID;
	}
	public void setDELIVERY_HEAD_ID(String dELIVERYHEADID) {
		DELIVERY_HEAD_ID = dELIVERYHEADID;
	}
	public String getNEW_SPACE_REQ_FLAG() {
		return NEW_SPACE_REQ_FLAG;
	}
	public void setNEW_SPACE_REQ_FLAG(String nEWSPACEREQFLAG) {
		NEW_SPACE_REQ_FLAG = nEWSPACEREQFLAG;
	}
	public String getSelected_quarter() {
		return selected_quarter;
	}
	public void setSelected_quarter(String selectedQuarter) {
		selected_quarter = selectedQuarter;
	}
	private String jsonSpecialReq;
	private String BU;
	
	private String DATE_OF_REQUISITION;
	private String DEPT_HEAD_ID;
	private String DELIVERY_HEAD_ID;
	private String NEW_SPACE_REQ_FLAG;
	
	private String selected_quarter;
	
	public String getJsonLocations() {
		return jsonLocations;
	}
	public void setJsonLocations(String jsonLocations) {
		this.jsonLocations = jsonLocations;
	}
	private String MAIN_REQ_ID;
	private String COMMENT_FLAG;
	private String DEPT_HEAD_COMMENTS;
	private String DELIVERY_HEAD_COMMENTS;
	private String STATUS_FLAG;
	public String getSTATUS_FLAG() {
		return STATUS_FLAG;
	}
	public void setSTATUS_FLAG(String sTATUSFLAG) {
		STATUS_FLAG = sTATUSFLAG;
	}
	public String getCOMMENT_FLAG() {
		return COMMENT_FLAG;
	}
	public void setCOMMENT_FLAG(String cOMMENTFLAG) {
		COMMENT_FLAG = cOMMENTFLAG;
	}
	public String getDEPT_HEAD_COMMENTS() {
		return DEPT_HEAD_COMMENTS;
	}
	public void setDEPT_HEAD_COMMENTS(String dEPTHEADCOMMENTS) {
		DEPT_HEAD_COMMENTS = dEPTHEADCOMMENTS;
	}
	public String getDELIVERY_HEAD_COMMENTS() {
		return DELIVERY_HEAD_COMMENTS;
	}
	public void setDELIVERY_HEAD_COMMENTS(String dELIVERYHEADCOMMENTS) {
		DELIVERY_HEAD_COMMENTS = dELIVERYHEADCOMMENTS;
	}
	public String getMAIN_REQ_ID() {
		return MAIN_REQ_ID;
	}
	public void setMAIN_REQ_ID(String mAINREQID) {
		MAIN_REQ_ID = mAINREQID;
	}
	private int Status;
	
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getSTATE_ID() {
		return STATE_ID;
	}
	public void setSTATE_ID(String sTATEID) {
		STATE_ID = sTATEID;
	}
	public String getTOTAL_SEATS_PROVIDED() {
		return TOTAL_SEATS_PROVIDED;
	}
	public void setTOTAL_SEATS_PROVIDED(String tOTALSEATSPROVIDED) {
		TOTAL_SEATS_PROVIDED = tOTALSEATSPROVIDED;
	}
	private String Date;
	private String CREATED_BY;
	private String CREATED_DATE;
	private String REQUESTOR_COMMENTS;
	private String PROJECT_ID;
	
	public String getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setPROJECT_ID(String pROJECTID) {
		PROJECT_ID = pROJECTID;
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
	public String getSBU_NAME() {
		return SBU_NAME;
	}
	public void setSBU_NAME(String sBUNAME) {
		SBU_NAME = sBUNAME;
	}
	public String getOPPORTUNITY_ID() {
		return OPPORTUNITY_ID;
	}
	public void setOPPORTUNITY_ID(String oPPORTUNITYID) {
		OPPORTUNITY_ID = oPPORTUNITYID;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMERNAME) {
		CUSTOMER_NAME = cUSTOMERNAME;
	}
	public String getREQUESTED_QUARTER() {
		return REQUESTED_QUARTER;
	}
	public void setREQUESTED_QUARTER(String rEQUESTEDQUARTER) {
		REQUESTED_QUARTER = rEQUESTEDQUARTER;
	}
	public String getREQUESTED_YEAR() {
		return REQUESTED_YEAR;
	}
	public void setREQUESTED_YEAR(String rEQUESTEDYEAR) {
		REQUESTED_YEAR = rEQUESTEDYEAR;
	}
	public String getREQUESTED_MONTH() {
		return REQUESTED_MONTH;
	}
	public void setREQUESTED_MONTH(String rEQUESTEDMONTH) {
		REQUESTED_MONTH = rEQUESTEDMONTH;
	}
	public String getTOTAL_SEATS_REQUESTED() {
		return TOTAL_SEATS_REQUESTED;
	}
	public void setTOTAL_SEATS_REQUESTED(String tOTALSEATSREQUESTED) {
		TOTAL_SEATS_REQUESTED = tOTALSEATSREQUESTED;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
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
	public String getREQUESTOR_COMMENTS() {
		return REQUESTOR_COMMENTS;
	}
	public void setREQUESTOR_COMMENTS(String rEQUESTORCOMMENTS) {
		REQUESTOR_COMMENTS = rEQUESTORCOMMENTS;
	}
	
	private String SBU_ID;

	public String getSBU_ID() {
		return SBU_ID;
	}
	public void setSBU_ID(String sBUID) {
		SBU_ID = sBUID;
	}
	//Added by shruti
	private String START_COUNT_PAGE;
	private String LOCATION_NAME;
	private String BUILDING_NAME;
	private String FLOOR_NAME;
	private String WING_NAME;
	private String ALREDY_SOFT_ALL; 
	private int SOFT_ALLOCATION;
	private String REMARKS;
	private int AREA_ID;
	private int BUILDING_ID;
	private int CITY_ID;
	private int FLOOR_ID;
	private int WING_ID;
	
	
	public int getAREA_ID() {
		return AREA_ID;
	}
	public void setAREA_ID(int aREAID) {
		AREA_ID = aREAID;
	}
	public int getBUILDING_ID() {
		return BUILDING_ID;
	}
	public void setBUILDING_ID(int bUILDINGID) {
		BUILDING_ID = bUILDINGID;
	}
	public int getCITY_ID() {
		return CITY_ID;
	}
	public void setCITY_ID(int cITYID) {
		CITY_ID = cITYID;
	}
	public int getFLOOR_ID() {
		return FLOOR_ID;
	}
	public void setFLOOR_ID(int fLOORID) {
		FLOOR_ID = fLOORID;
	}
	public int getWING_ID() {
		return WING_ID;
	}
	public void setWING_ID(int wINGID) {
		WING_ID = wINGID;
	}
	public String getSTART_COUNT_PAGE() {
		return START_COUNT_PAGE;
	}
	public void setSTART_COUNT_PAGE(String sTARTCOUNTPAGE) {
		START_COUNT_PAGE = sTARTCOUNTPAGE;
	}
	
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
	public String getALREDY_SOFT_ALL() {
		return ALREDY_SOFT_ALL;
	}
	public void setALREDY_SOFT_ALL(String aLREDYSOFTALL) {
		ALREDY_SOFT_ALL = aLREDYSOFTALL;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	
}
