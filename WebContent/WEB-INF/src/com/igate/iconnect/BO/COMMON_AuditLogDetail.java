/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

 

public class COMMON_AuditLogDetail  implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2133287578243381763L;
    public List<String> getField() {
		return field;
	}
	public void setField(List<String> field) {
		this.field = field;
	}
	public List<String> getOldValue() {
		return oldValue;
	}
	public void setOldValue(List<String> oldValue) {
		this.oldValue = oldValue;
	}
	public List<String> getNewValue() {
		return newValue;
	}
	public void setNewValue(List<String> newValue) {
		this.newValue = newValue;
	}
	private List<String> field=new ArrayList<String>();
    private List<String> oldValue=new ArrayList<String>();
    private List<String> newValue=new ArrayList<String>();
    private String errormessage;
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
  

}
/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:712836

Changes Made on:JUNE 10,2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/


 

