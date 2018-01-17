package com.igate.iconnect.BO;

import java.io.Serializable;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ADMIN_ExcelUpload  implements Serializable{
	
	private static final long serialVersionUID = 3434728958817144909L;
	private CommonsMultipartFile excelUpload;
	
	private String tableName;
	private String sheetName;
	private String Upload_Path;
	private String Upload_Path_Name;
	
	public void setExcelUpload(CommonsMultipartFile excelUpload) {
		this.excelUpload = excelUpload;
	}

	public CommonsMultipartFile getExcelUpload() {
		return excelUpload;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setUpload_Path(String upload_Path) {
		Upload_Path = upload_Path;
	}

	public String getUpload_Path() {
		return Upload_Path;
	}

	public void setUpload_Path_Name(String upload_Path_Name) {
		Upload_Path_Name = upload_Path_Name;
	}

	public String getUpload_Path_Name() {
		return Upload_Path_Name;
	}

}
