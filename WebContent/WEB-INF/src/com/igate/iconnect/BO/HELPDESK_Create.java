/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class HELPDESK_Create  implements Serializable{
 
	/**
     * 
     */
    private static final long serialVersionUID = 4468140859472564231L;
    private String orchOdcId;
    
    public String getOrchOdcId() {
		return orchOdcId;
	}
	public void setOrchOdcId(String orchOdcId) {
		this.orchOdcId = orchOdcId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOnbeofEmpId() {
		return onbeofEmpId;
	}
	public void setOnbeofEmpId(String onbeofEmpId) {
		this.onbeofEmpId = onbeofEmpId;
	}
	public String getPhoneoption() {
		return phoneoption;
	}
	public void setPhoneoption(String phoneoption) {
		this.phoneoption = phoneoption;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAltcontactnumber() {
		return altcontactnumber;
	}
	public void setAltcontactnumber(String altcontactnumber) {
		this.altcontactnumber = altcontactnumber;
	}
	public String getEmpProject() {
		return EmpProject;
	}
	public void setEmpProject(String empProject) {
		EmpProject = empProject;
	}
	public String getCCEmailID() {
		return CCEmailID;
	}
	public void setCCEmailID(String cCEmailID) {
		CCEmailID = cCEmailID;
	}
	public String getImpactusercheck() {
		return impactusercheck;
	}
	public void setImpactusercheck(String impactusercheck) {
		this.impactusercheck = impactusercheck;
	}
	public String getImpactusercount() {
		return impactusercount;
	}
	public void setImpactusercount(String impactusercount) {
		this.impactusercount = impactusercount;
	}
    public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getCubicalcode() {
		return cubicalcode;
	}
	public void setCubicalcode(String cubicalcode) {
		this.cubicalcode = cubicalcode;
	}
    public CommonsMultipartFile getProblemfile() {
		return problemfile;
	}
	public void setProblemfile(CommonsMultipartFile problemfile) {
		this.problemfile = problemfile;
	}
	public CommonsMultipartFile getApprovalfile() {
		return approvalfile;
	}
	public void setApprovalfile(CommonsMultipartFile approvalfile) {
		this.approvalfile = approvalfile;
	}
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String function;
	private String category;
	private String subcategory;
    private String priority ;
	private String subject;
	private String description;
	private CommonsMultipartFile problemfile;
    private CommonsMultipartFile approvalfile;
	private String onbeofEmpId;
    private String phoneoption;
    private String phonenumber;
    private String altcontactnumber;
    private String EmpProject;
    public String getEmplevel2Project() {
		return Emplevel2Project;
	}
	public void setEmplevel2Project(String emplevel2Project) {
		Emplevel2Project = emplevel2Project;
	}
	public String getEmplevel3Project() {
		return Emplevel3Project;
	}
	public void setEmplevel3Project(String emplevel3Project) {
		Emplevel3Project = emplevel3Project;
	}

	public String getEmplevelProject() {
		return EmplevelProject;
	}
	public void setEmplevelProject(String emplevelProject) {
		EmplevelProject = emplevelProject;
	}

	private String Emplevel2Project;
    private String Emplevel3Project;
    private String EmplevelProject;
    private String CCEmailID;
    private String impactusercheck;
    private String impactusercount;
	private String locationId;
	private String floor;
	private String cubicalcode;
	public boolean isApprovalFlag() {
		return approvalFlag;
	}
	public void setApprovalFlag(boolean approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	private String source;
	private String createddate;
	private String mailid;
	private String attachmentName;
	private String attachmentPath;
	private String department;
	// These eight variables are required for Web services with itrack 
    private String currentLocation;
    private String requestedBy;
    private String mainRequestId;
    private String resourceId;
    private String type;
    private String locdetailid;
    private String organization;
    private String subRequestId;
    private String desktopname;
    private String requesttypeid;
    private boolean approvalFlag;
    public String getRequesttypeid() {
		return requesttypeid;
	}
	public void setRequesttypeid(String requesttypeid) {
		this.requesttypeid = requesttypeid;
	}

	private String privatecloudjson;
    
    
    public String getPrivatecloudjson() {
		return privatecloudjson;
	}
	public void setPrivatecloudjson(String privatecloudjson) {
		this.privatecloudjson = privatecloudjson;
	}
	public String getDesktopname() {
		return desktopname;
	}
	public void setDesktopname(String desktopname) {
		this.desktopname = desktopname;
	}

	//Added to idenity the mails from HRhelpdesk /HRSS as part of
    // implementation of the tool to track EX employees quereis for HR function
	private String toAddressMailId;
	private String additionalInfo;
	
	//Added by anjana For orchestration
	private String orchestrationInput1;
	
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getToAddressMailId() {
		return toAddressMailId;
	}
	public void setToAddressMailId(String toAddressMailId) {
		this.toAddressMailId = toAddressMailId;
	}
	//End
	public String getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public String getRequestedBy() {
        return requestedBy;
    }
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    public String getMainRequestId() {
        return mainRequestId;
    }
    public void setMainRequestId(String mainRequestId) {
        this.mainRequestId = mainRequestId;
    }
    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLocdetailid() {
        return locdetailid;
    }
    public void setLocdetailid(String locdetailid) {
        this.locdetailid = locdetailid;
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getSubRequestId() {
        return subRequestId;
    }
    public void setSubRequestId(String subRequestId) {
        this.subRequestId = subRequestId;
    }
    public String getOsdetails() {
		return osdetails;
	}
	public void setOsdetails(String osdetails) {
		this.osdetails = osdetails;
	}
	public String getBrowserdetails() {
		return browserdetails;
	}
	public void setBrowserdetails(String browserdetails) {
		this.browserdetails = browserdetails;
	}

	private String osdetails;
	private String browserdetails;
	private String odc;
	private String building;
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getOdc() {
		return odc;
	}
	public void setOdc(String odc) {
		this.odc = odc;
	}

	private String mailTrackerType;

	public String getMailTrackerType() {
		return mailTrackerType;
	}

	public void setMailTrackerType(String mailTrackerType) {
		this.mailTrackerType = mailTrackerType;
	}
	
	private String projectname;

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	//Added to implement the tool to track EX employees quereis for HR function
	private String isEXEmployeeCheck;
	private String isEXEmp_Value;
	
	
	public String getIsEXEmployeeCheck() {
		return isEXEmployeeCheck;
	}
	public void setIsEXEmployeeCheck(String isEXEmployeeCheck) {
		this.isEXEmployeeCheck = isEXEmployeeCheck;
	}
	public String getIsEXEmp_Value() {
		return isEXEmp_Value;
	}
	public void setIsEXEmp_Value(String isEXEmpValue) {
		isEXEmp_Value = isEXEmpValue;
	}

	private String fromAddress;
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	
	//End
	public void setOrchestrationInput1(String orchestrationInput1) {
		this.orchestrationInput1 = orchestrationInput1;
	}
	public String getOrchestrationInput1() {
		return orchestrationInput1;
	}
	//L2 1184 Start
	private String hostName;
	private String isAutomationRequired;
	private String softwareId;
	private String coreLoadId;
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIsAutomationRequired() {
		return isAutomationRequired;
	}
	public void setIsAutomationRequired(String isAutomationRequired) {
		this.isAutomationRequired = isAutomationRequired;
	}
	public String getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}
	public String getCoreLoadId() {
		return coreLoadId;
	}
	public void setCoreLoadId(String coreLoadId) {
		this.coreLoadId = coreLoadId;
	}
	//L2 1184 END
	
}
