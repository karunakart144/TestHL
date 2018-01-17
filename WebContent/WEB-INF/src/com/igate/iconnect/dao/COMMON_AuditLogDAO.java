/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.dao;

import java.util.List;

import com.igate.iconnect.BO.COMMON_AuditLog;

public interface COMMON_AuditLogDAO {
    /**
     * Description: To get the history information per ticket.
     * 
     * @param To
     *            pass the ticket no either issue log ticket or helpdesk ticket.
     * @return To return the list of values per ticket.
     */
    public List<COMMON_AuditLog> getAuditLogInfo(String ticketNo, String menuID,
            int userTimeZone);

    /**
     * Description: To get the history detail information per ticket
     * e.g:field_name,old_Val & New_Val.
     * 
     * @param To
     *            pass the history id of the ticket.
     * @return To return the list of values per ticket.
     */
    public List<COMMON_AuditLog> getAuditLogDetailInfo(int historyId);

}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 10,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

