/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2585328081184961183L;
    private String loginId;
    private String userDomain;
    private boolean authenticationRequired;
    private String userName;
    private String emailAddress;
    private String grade;
    private String reportingManagerName;
    private String reportingMangerId;
    private String locationCity;
    private String locationArea;
    private String locationShortName;
    private String userRole;
    private String userRoleId;
    private List<WORKFLOW_Role> userRoleList;
    private String password;
    private String organization;
    private String mobile;
    private String extension;
    private String timeZoneId;
    private String samaccountname;
    private String approvalExceptionFlag;
    private String workspacePlanning_Grade;
    private String workSpacePlanning_Grade_Desc;
    private String workSpacePlanning_Grade_Level;
    private int  workSpacePlanning_Access;

    public int getWorkSpacePlanning_Access() {
		return workSpacePlanning_Access;
	}

	public void setWorkSpacePlanning_Access(int workSpacePlanningAccess) {
		workSpacePlanning_Access = workSpacePlanningAccess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWorkSpacePlanning_Grade_Desc() {
		return workSpacePlanning_Grade_Desc;
	}

	public void setWorkSpacePlanning_Grade_Desc(String workSpacePlanningGradeDesc) {
		workSpacePlanning_Grade_Desc = workSpacePlanningGradeDesc;
	}

	public String getWorkSpacePlanning_Grade_Level() {
		return workSpacePlanning_Grade_Level;
	}

	public void setWorkSpacePlanning_Grade_Level(String workSpacePlanningGradeLevel) {
		workSpacePlanning_Grade_Level = workSpacePlanningGradeLevel;
	}

	public String getWorkspacePlanning_Grade() {
		return workspacePlanning_Grade;
	}

	public void setWorkspacePlanning_Grade(String workspacePlanningGrade) {
		workspacePlanning_Grade = workspacePlanningGrade;
	}

	public String getApprovalExceptionFlag() {
		return approvalExceptionFlag;
	}

	public void setApprovalExceptionFlag(String approvalExceptionFlag) {
		this.approvalExceptionFlag = approvalExceptionFlag;
	}

	public String getSamaccountname() {
		return samaccountname;
	}

	public void setSamaccountname(String samaccountname) {
		this.samaccountname = samaccountname;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<WORKFLOW_Role> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<WORKFLOW_Role> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getReportingManagerName() {
        return reportingManagerName;
    }

    public void setReportingManagerName(String reportingManagerName) {
        this.reportingManagerName = reportingManagerName;
    }

    public String getReportingMangerId() {
        return reportingMangerId;
    }

    public void setReportingMangerId(String reportingMangerId) {
        this.reportingMangerId = reportingMangerId;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getLocationShortName() {
        return locationShortName;
    }

    public void setLocationShortName(String locationShortName) {
        this.locationShortName = locationShortName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 15, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
