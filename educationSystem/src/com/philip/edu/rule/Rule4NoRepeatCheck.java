package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Rule4NoRepeatCheck {

	private Logger logger = Logger.getLogger(Rule4NoRepeatCheck.class);
	private static ExcelHelper helper = new ExcelHelper();
	private static FormManager manager = new FormManager();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject object = new JSONObject();
	}

	public MessageInfo getMessage(Workbook wb, JSONObject object){
		int[] columns = new int[10];
		logger.info("rule 4: begin to check");
		
		MessageInfo message = new MessageInfo();
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		ArrayList messageList = new ArrayList();
		
		int excelColumns = helper.getExcelColumns(wb);
		int lines = helper.getExcelLines(wb);
		
		String keyInfo = "(";
		
		ArrayList al = translateRules(object);
		for(int i=0; i<al.size(); i++){
			FormField field = (FormField)al.get(i);
			keyInfo += field.getBus_name() + ",";
			int index = helper.getColumn2Check(wb, field.getBus_name(), excelColumns);
			columns[i] = index;
		}
		keyInfo += ")";
		
		Sheet sheet = wb.getSheetAt(0);
		for(int j=1; j<lines; j++){
			String key = null;

			Row row = sheet.getRow(j);
			String result = "";
			
			for(int k=0; k<columns.length; k++){
				Cell cell = row.getCell(columns[k]);
				if(cell!=null) result += cell.getStringCellValue();
			}
			
			for(int l=j+1; l<lines; l++){
				Row row1 = sheet.getRow(l);
				String compare = "";
				
				for(int m=0; m<columns.length; m++){
					Cell cell1 = row1.getCell(columns[m]);
					if(cell1!=null) compare += cell1.getStringCellValue();
				}
				
				if(result.equals(compare)){
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (j+1) + "行的主键" + keyInfo + "与第" + (l+1) +"行记录重复！");
					break;
				}
			}
		}
		
		message.setMessage_info(messageList);;
		
		return message;
	}
	
	private ArrayList translateRules(JSONObject object){
		logger.info("begin to translate the rule");
		ArrayList al = new ArrayList();
		JSONArray array = (JSONArray) object.get("rules");
		
		for(int i=0; i<array.length(); i++){
			JSONObject ob = array.getJSONObject(i);
			if("field".equals(ob.get("type").toString())){
				//al.add(ob.get("field").toString());
				String field_name = ob.get("field").toString();
				FormField field = manager.getFieldByPhysicName(Constants.FORM_ID, field_name);
				al.add(field);
			}
		}
		
		return al;
	}
}
