package com.philip.edu.rule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Rule2ExclusiveCheck {
	
	private static FormManager manager = new FormManager();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String rule = "{\"rules\":[{\"field\":\"XXMC\",\"type\":\"field\"},{\"relateField\":\"XQMC\",\"type\":\"relateForm\",\"relateForm\":\"XQDZ\"}]}";
		JSONObject object = new JSONObject(rule);
		Rule2ExclusiveCheck engine = new Rule2ExclusiveCheck();
		ArrayList al = null;
		String ruleSQL = null;
		String bus_name = null;
		
		//1. fetch the sql:
		try {
			al = engine.TranslateRuleSimple(object);
			
			ruleSQL = (String) al.get(0);
			bus_name = (String) al.get(1);
		} catch (NotImplementException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getErrorMessage());
			e1.printStackTrace();
		}
		
		//2. check process:
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1-1.xls");
			wb = WorkbookFactory.create(in);
			
			Sheet sheet = wb.getSheetAt(0);
			
			//0. make sure columns are the same:
			int excelColumns=0;
			int tableColumns=0;
			Row row = sheet.getRow(0);
			int i = 1;
			while(i > 0){
				Cell cell = row.getCell(i-1);
				if(cell==null || "".equals(cell.getStringCellValue().trim()))break;
				i++;
			}
			excelColumns = i -1;
			List al1 = manager.getFormFields(Constants.FORM_ID);
			tableColumns = al1.size() - 1;
			if(excelColumns != tableColumns){System.out.println("导入数据表格式不正确！");return;}
			
			//1. make sure the column:
			String columnName = bus_name;
			row = sheet.getRow(0);
			int j = 0;
			for(j = 0; j<excelColumns; j++){
				Cell cell = row.getCell(j);
				if(columnName.trim().equals(cell.getStringCellValue().trim()))break;
			}
			int theColumn = j;
			
			//2. make sure total lines:
			int lines = 0;
			int index = 0;
			while(index+1 > 0){
				Row row1 = sheet.getRow(index);
				if(row1==null)break;
				Cell cell1 = row1.getCell(0);
				if(cell1==null || "".equals(cell1.getStringCellValue().trim()))break;
				index++;
			}
			lines = index;
			
			//3. cycle the column to end:
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList TranslateRuleSimple(JSONObject rule) throws NotImplementException{
		String ruleSQL = new String();
		JSONArray array = (JSONArray) rule.get("rules");
		ArrayList result = new ArrayList();
		
		if(array.length()>2)throw new NotImplementException("Translate many conditions using and has not been implemented!");
		
		//get table:
		JSONObject obj = (JSONObject) array.get(1);
		String table_name = obj.get("relateForm").toString();
		Form form = manager.getFormByName(Constants.USER_ID, table_name);
		String table = form.getPhsic_name();
		//get field:
		String field = obj.get("relateField").toString();
		ruleSQL = "select * from " + table + " where " + field + "=?";
		
		//get check fieldName:
		obj = (JSONObject) array.get(0);
		String field_name = obj.get("field").toString();
		FormField formField = manager.getFieldByPhysicName(Constants.FORM_ID, field_name);
		String bus_name = formField.getBus_name();
		
		result.add(ruleSQL);
		result.add(bus_name);
		
		return result;
	}
	
	private String TranslateRule(JSONObject rule){
		String ruleSQL = new String();
		
		JSONArray array = (JSONArray) rule.get("rules");
		JSONArray tempArray = new JSONArray();
		JSONArray newArray = new JSONArray();
		
		for(int i=0; i<array.length(); i++){
			JSONObject obj = (JSONObject) array.get(i);
			if(Constants.RULE_OP_AND.equals(obj.get("type").toString())){
				
			} else if(Constants.RULE_FORMFIELD.equals(obj.get("type").toString()) || Constants.RULE_RELATE_FORM.equals(obj.get("type").toString())) {
				newArray.put(obj);
			}
		}
		
		return ruleSQL;
	}

}
