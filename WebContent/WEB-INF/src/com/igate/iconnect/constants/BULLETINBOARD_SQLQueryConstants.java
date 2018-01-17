package com.igate.iconnect.constants;

public class BULLETINBOARD_SQLQueryConstants {
	public static final String IC_BULLETIN_CREATE_MESSAGE = "insert into IC_IHD_BULLETIN_BOARD(BULLETIN_DESCRIPTION,BULLETIN_HEADER,MASTER_TICKET_ID,IS_ACTIVE,CREATED_BY,CREATED_DATE)values(?,?,?,?,?,?)";
	public static final String IC_BULLETIN_REMOVE_MESSAGE = "update IC_IHD_BULLETIN_BOARD set IS_ACTIVE=0 where BULLETIN_ID=?";
	public static final String IC_MASTER_REMOVE_MESSAGE = "update IC_IHD_BULLETIN_BOARD set IS_ACTIVE=0 where MASTER_TICKET_ID=?";
}
