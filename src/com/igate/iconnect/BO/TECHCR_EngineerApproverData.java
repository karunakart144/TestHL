/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

public class TECHCR_EngineerApproverData implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7016259426416489107L;
    private String empId;
    private String empName;
    private String empIdAndName;
    private String groupId;
    private String workLoad;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkLoad() {
        return workLoad;
    }

    public void setWorkLoad(String workLoad) {
        this.workLoad = workLoad;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpIdAndName() {
        return empIdAndName;
    }

    public void setEmpIdAndName(String empIdAndName) {
        this.empIdAndName = empIdAndName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
