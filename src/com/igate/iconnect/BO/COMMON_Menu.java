/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */


package com.igate.iconnect.BO;

import java.io.Serializable;

 


public class COMMON_Menu implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8849515351512918746L;
    private String menuId;
    private String menuName;
    private String link;
    private String parentId;
    private String roleId;
    private String childExsists;
    private String query;     
    private String displayname;
    private String columnname;
    private String columnid;
    private String orderno;
    private String tooltip;
    private String haschild;
    private String tittle;
    private String employeeid;
    private String referenceId;
    
    
    public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**** Added For Campus Crawler By (Poovamma)716302 **************************/
    private String structureId;
    private String cubicleCode;

	
	public String getCubicleCode() {
		return cubicleCode;
	}

	public void setCubicleCode(String cubicleCode) {
		this.cubicleCode = cubicleCode;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}
	/****Added For Visual  Added For Campus Crawler By (Poovamma)716302 **************************/
	public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getChildExsists() {
		return childExsists;
	}

	public void setChildExsists(String childExsists) {
		this.childExsists = childExsists;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setHaschild(String haschild) {
		this.haschild = haschild;
	}

	public String getHaschild() {
		return haschild;
	}

	/**
	 * @param tittle the tittle to set
	 */
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * @return the tittle
	 */
	public String getTittle() {
		return tittle;
	}

	/**
	 * @param employeeid the employeeid to set
	 */
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	/**
	 * @return the employeeid
	 */
	public String getEmployeeid() {
		return employeeid;
	}

}


 

/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:702166

Changes Made on:Jun 21, 2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/
