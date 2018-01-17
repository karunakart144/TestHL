package com.igate.iconnect.dao;

import com.igate.iconnect.BO.Approval_DetailsBO;

public interface HELPDESK_EmailApprovalDAO {
	public void validateApprovalDetails(Approval_DetailsBO approvalBO);

	public void validateTCRApprovalDetails(Approval_DetailsBO approvalDetails);

}
