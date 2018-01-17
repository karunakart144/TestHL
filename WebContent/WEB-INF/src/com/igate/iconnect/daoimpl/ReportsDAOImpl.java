package com.igate.iconnect.daoimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.igate.iconnect.BO.Reports;
import com.igate.iconnect.constants.ReportsConstants;
import com.igate.iconnect.dao.ReportsDAO;

public class ReportsDAOImpl implements ReportsDAO{

		private static Logger log = Logger.getLogger(ReportsDAOImpl.class);
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void init(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
		
		public String generateTicketReport(Reports GenerateReportBean,
				HttpServletRequest request, HttpServletResponse response){
				
			try {	

				String functionId = GenerateReportBean.getFunction();
				String categoryId = GenerateReportBean.getCategory();
				String statusId = GenerateReportBean.getStatus();
				String startDate = GenerateReportBean.getStartDate();
				String endDate = GenerateReportBean.getEndDate();
				String onBasisOf = GenerateReportBean.getOnBasisOf();
				
				List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
				
				System.out.println("***********Start of LH On Demand Report********************");

				data=this.jdbcTemplate.queryForList(ReportsConstants.IC_ON_DEMAND_REPORT_QUERY,startDate,endDate,functionId,categoryId,statusId,onBasisOf);
				
				if(data.size()>0){
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet excelSheet =  (HSSFSheet) workbook.createSheet("Report");
					setExcelHeader(workbook, excelSheet, data);
					setExcelRows(workbook, excelSheet, data);
					response.setContentType(".xls");
					response.setHeader("Content-Disposition", "attachment; filename=\"On_Demand_Report.xls\"");
					workbook.write(response.getOutputStream());
					response.getOutputStream().flush();
					
				System.out.println("************End of LH On Demand Report********************");
				}
			} catch (Exception e) {
				log.error("Exception Occured while generating the Report" + e);
			}
			return "No Records found !!";
		}
		
		public void setExcelHeader(Workbook workbook, HSSFSheet excelSheet,
				List<Map<String, Object>> reportData) {
			try {
				Row excelHeader = excelSheet.createRow(0);
				CellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(CellStyle.BORDER_MEDIUM);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft(CellStyle.BORDER_MEDIUM);
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderRight(CellStyle.BORDER_MEDIUM);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(CellStyle.BORDER_MEDIUM);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style.setWrapText(true);
				style.setAlignment(CellStyle.ALIGN_CENTER);
		
				Font font = workbook.createFont();
				font.setFontName("Calibri");
				font.setFontHeightInPoints((short) 10);
				font.setColor(HSSFColor.WHITE.index);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style.setFont(font);
				Set<Entry<String, Object>> entrySet = reportData.get(0)
						.entrySet();
				Iterator<Entry<String, Object>> mapData = entrySet.iterator();
				int i = 0;
				while (mapData.hasNext()) {
					Entry<String, Object> element = mapData.next();
					excelHeader.createCell(i).setCellValue(element.getKey());
					excelHeader.getCell(i).setCellStyle(style);
					workbook.getSheetAt(0).autoSizeColumn(i);
					i++;
				}
			} catch (Exception e) {
				log.error("Exception Occured while ExcelHeader in the Report" + e);
			}
		
		}
		
		public void setExcelRows(Workbook workbook, HSSFSheet excelSheet,
				List<Map<String, Object>> reportData) {
			try {
				CellStyle style = workbook.createCellStyle();
				style.setBorderBottom(CellStyle.BORDER_THIN);
				style.setBorderLeft(CellStyle.BORDER_THIN);
				style.setBorderRight(CellStyle.BORDER_THIN);
				style.setBorderTop(CellStyle.BORDER_THIN);
				Font font = workbook.createFont();
				font.setFontName("Calibri");
				font.setFontHeightInPoints((short) 10);
				style.setFont(font);
				int record = 1;
				for (int j = 0; j < reportData.size(); j++) {
					Row excelRow = excelSheet.createRow(record++);
					Set<Entry<String, Object>> entrySet = reportData.get(j)
							.entrySet();
					Iterator<Entry<String, Object>> mapData = entrySet.iterator();
					int i = 0;
					while (mapData.hasNext()) {
						Entry<String, Object> element = mapData.next();
						excelRow.createCell(i).setCellValue(
								element.getValue().toString());
						excelRow.getCell(i).setCellStyle(style);
						i++;
					}
		
				}
			} catch (Exception e) {
				log.error("Exception Occured while ExcelRows in the Report" + e);
			}
		}
}