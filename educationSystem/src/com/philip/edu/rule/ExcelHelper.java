package com.philip.edu.rule;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class ExcelHelper {
	private Logger logger = Logger.getLogger(ExcelHelper.class);
	
	private static FormManager manager = new FormManager();
	
	public boolean is_format_right(Workbook wb, int form_id){
		logger.info("begin to check whether excel format is right.");
		
		boolean is_right = true;
		
		Sheet sheet = wb.getSheetAt(0);
		
		//0. make sure columns are the same:
		int excelColumns=0;
		int tableColumns=0;
		
		excelColumns = getExcelColumns(wb);
		List al1 = manager.getFormFields(form_id);
		tableColumns = al1.size() - 1;
		if(excelColumns != tableColumns){logger.info("----------ERROR:导入数据表格式不正确！---------");is_right=false;}
		
		return is_right;
	}
	
	public int getExcelColumns(Workbook wb){
		logger.info("to count the excel columns");
		
		int excelColumns=0;
		
		Sheet sheet = wb.getSheetAt(0);	
		Row row = sheet.getRow(0);
		int i = 1;
		while(i > 0){
			Cell cell = row.getCell(i-1);
			if(cell==null || "".equals(cell.getStringCellValue().trim()))break;
			i++;
		}
		excelColumns = i -1;
		
		logger.info("the excel has " + excelColumns + " columns!");
		
		return excelColumns;
	}
	
	public int getExcelLines(Workbook wb){
		logger.info("to count the excel lines");
		int lines = 0;		
		int index = 0;
		
		Sheet sheet = wb.getSheetAt(0);
		while(index+1 > 0){
			Row row1 = sheet.getRow(index);
			if(row1==null)break;
			Cell cell1 = row1.getCell(0);
			if(cell1==null || "".equals(cell1.getStringCellValue().trim()))break;
			index++;
		}
		lines = index;
		
		logger.info("the excel has " + lines + " lines");
		
		return lines;
		
	}
	
	public int getColumn2Check(Workbook wb, String bus_name, int columnTotal){
		logger.info("to decide which column to check");
		int column = 0;
		
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		
		for(int i=0; i<columnTotal; i++){
			Cell cell = row.getCell(i);
			if(cell!=null && bus_name.trim().equals(cell.getStringCellValue().trim())){
				column = i;
				break;
			}
		}
		
		return column;
	}
}
