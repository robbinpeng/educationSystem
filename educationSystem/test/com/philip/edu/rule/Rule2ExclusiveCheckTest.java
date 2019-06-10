package com.philip.edu.rule;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.junit.Test;

import com.philip.edu.basic.Constants;

public class Rule2ExclusiveCheckTest {

	@Test
	public void testGetMessage() {
		String rule = "{\"rules\":[{\"field\":\"XXMC\",\"type\":\"field\"},{\"relateField\":\"XQMC\",\"type\":\"relateForm\",\"relateForm\":\"XQDZ\"}]}";
		JSONObject object = new JSONObject(rule);
		MessageInfo message = null;
		Rule2ExclusiveCheck engine = new Rule2ExclusiveCheck();
		
		//do check process:
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1-1.xls");
			wb = WorkbookFactory.create(in);
			
			message = engine.getMessage(wb, object, Constants.FORM_ID);
			ArrayList al = message.getMessage_info();
			if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS)System.out.println("上传成功！");
			else {
				for(int i=0; i<al.size(); i++){
				System.out.println(al.get(i).toString());
				}
			}

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

}
