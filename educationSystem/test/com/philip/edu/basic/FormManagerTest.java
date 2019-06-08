package com.philip.edu.basic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FormManagerTest {
	private FormManager manager = new FormManager();

	@Test
	public void testGetForms() {
		ArrayList forms = manager.getForms(Constants.USER_ID);
		
		assertNotEquals(forms.size(), 0);		
		//print some:
		for(int i=0; i<forms.size(); i++){
			Form form = (Form)forms.get(i);
			System.out.println("form_id:" + form.getId());
			System.out.println("form name:" + form.getTbl_name());
			System.out.println("form bussiness name:" + form.getBus_name());
			
		}	
	}
	
	@Test
	public void testGetFields() {
		ArrayList fields = manager.getFormFields(Constants.FORM_ID);
		
		assertNotEquals(fields.size(), 0);
		//print some:
		for(int i=0; i<fields.size(); i++){
			FormField field = (FormField)fields.get(i);
			System.out.println("sequence: " + field.getSequence());
			System.out.println("field name: " + field.getBus_name());
			System.out.println("field physic name: " + field.getPhysic_name());
		}
	}

	@Test
	public void testGetFormFieldsByTableName(){
		ArrayList fields = manager.getFormFieldsByFormName(Constants.USER_ID, Constants.FORM_NAME);
		
		assertNotEquals(fields.size(),0);
		//print some:
				for(int i=0; i<fields.size(); i++){
					FormField field = (FormField)fields.get(i);
					System.out.println("sequence: " + field.getSequence());
					System.out.println("field name: " + field.getBus_name());
					System.out.println("field physic name: " + field.getPhysic_name());
				}
	}
}