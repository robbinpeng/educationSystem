package com.philip.edu.upload;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import com.philip.edu.basic.Constants;

public class UploadManagerTest {

	@Test
	public void testUploadData() {
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1-11.xls");
			wb = WorkbookFactory.create(in);
			
			// check the excel is right:
			UploadManager manager = new UploadManager();
			
			boolean isSuccess = manager.uploadData(wb, Constants.FORM_ID);
			if(isSuccess)System.out.println("�ɹ��ϴ����ݣ�");
			else System.out.println("�ϴ�����ʧ�ܡ�");
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
