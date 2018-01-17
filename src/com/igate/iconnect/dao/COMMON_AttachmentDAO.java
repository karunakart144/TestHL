/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.dao;

import java.util.HashMap;
import java.util.List;

import com.igate.iconnect.BO.HELPDESK_Attachment;
import com.igate.iconnect.BO.HELPDESK_Emergency_Attachment;
import com.igate.iconnect.BO.TECHCR_Attachment;

public interface COMMON_AttachmentDAO {

    /**
     * Description: To get the attachment path per ticket.
     * 
     * @param To
     *            pass the ticket no of the helpdesk ticket.
     * @return To return the list of values per ticket.
     */
    public List<HELPDESK_Attachment> getAttachmentList(String ticketNo);
    public List<HELPDESK_Attachment> getAttachmentMasterList(String ticketNo);

    /**
     * Description: To get the attachment path per ticket.
     * 
     * @param To
     *            pass the ticket no of the helpdesk ticket.
     * @return To return the list of values per ticket.
     */
    public List<TECHCR_Attachment> getAttachmentListForTCR(String issueNo);

    public String updateAttachmentFortechCRTicket(
            final TECHCR_Attachment techCRBean,
            HashMap<String, Object> auditlogdetails, final String loginID);
    //Added for Emergency L1
    public void batchInsert_Emergency(final List<HELPDESK_Emergency_Attachment> attachmentList, final String verifiedby);
    
    public void batchUpdateWithoutAttachmentList(final List<HELPDESK_Emergency_Attachment> attachmentList, final String verifiedby);
}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 13,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

