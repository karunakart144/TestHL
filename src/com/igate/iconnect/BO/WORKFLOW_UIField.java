/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package  com.igate.iconnect.BO;

import java.io.Serializable;

public class WORKFLOW_UIField implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -446348996660054537L;
    private String uiFieldName;
    private String uiId;
    public String getUiFieldName() {
        return uiFieldName;
    }
    public void setUiFieldName(String uiFieldName) {
        this.uiFieldName = uiFieldName;
    }
    public String getUiId() {
        return uiId;
    }
    public void setUiId(String uiId) {
        this.uiId = uiId;
    }
    

}


/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:701901
Changes Made on:Jun 15, 2011
End-------Version 1.0-------
		
-----------------------------------------------------------------------------*/