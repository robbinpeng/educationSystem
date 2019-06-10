package com.philip.edu.upload;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.rule.DBHelper;
import com.philip.edu.rule.ExcelHelper;

public class UploadManager {
	
	private Logger logger = Logger.getLogger(UploadManager.class);
	private static FormManager formManager = new FormManager();
	private static ExcelHelper excelHelper = new ExcelHelper();
	
	public boolean uploadData(Workbook wb, int form_id){
		boolean isSuccess = false;
		
		// check format is right:
		int excelColumns = excelHelper.getExcelColumns(wb);
		int lines = excelHelper.getExcelLines(wb);
		boolean format_right = excelHelper.is_format_right(wb,form_id);
		
		if(format_right==false)return false;
		
		// get table_name by form_id:
		Form form = formManager.getFormById(form_id);
		String table_name = form.getPhsic_name();
		
		// cycle Caption to get all the fields, set to new CaptionList.
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		ArrayList captionList = new ArrayList();
		
		ArrayList formFields = formManager.getFormFields(form_id);
		HashMap map = new HashMap();
		for(int j=0; j<formFields.size(); j++){
			FormField field = (FormField)formFields.get(j);
			map.put(field.getBus_name(), field);
		}
		
		for(int i=0; i<excelColumns; i++){
			Cell cell = row.getCell(i);
			String field_name = cell.getStringCellValue().trim();
			FormField captionField = (FormField)map.get(field_name);
			captionList.add(captionField);
		}
		
		//cycle every line:
		//  1、 phrase1: "insert into " + table_name + "(" + every field + ")"
		//	   phrase2: " values( + " + every value + ")"
		//     add phrase to List
		
		ArrayList sqlList = new ArrayList();

		for(int k=1; k<lines; k++){
			StringBuffer sql1 = new StringBuffer("insert into " + table_name + "(");
			StringBuffer sql2 = new StringBuffer(" values(");
			
			row = sheet.getRow(k);
			for(int l=0; l<captionList.size(); l++){
				FormField field1 = (FormField) captionList.get(l);
				String fieldName = field1.getPhysic_name();
				Cell cell = row.getCell(l);	
				
				if(l==captionList.size()-1){
					sql1.append(fieldName + ")");
					sql2.append("'" + cell.getStringCellValue() + "')");
				} else {
					sql1.append(fieldName + ", ");
					sql2.append("'" + cell.getStringCellValue() + "', ");
				}
			}
			
			StringBuffer sql = sql1.append(sql2);
			
			logger.info("插入数据库语句:" + sql);
			sqlList.add(sql.toString());
		}
		
		//  2、execute in database, at last commit;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DBHelper.getConnection();
			
			for(int i=0; i<sqlList.size(); i++){
				String sql = (String)sqlList.get(i);
				
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				
			}
			
			conn.commit();
			isSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("连接数据库错误！");
		}
	
		return isSuccess;
	}

}
