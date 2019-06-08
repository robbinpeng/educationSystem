package com.philip.edu.basic;

import java.util.ArrayList;
import java.util.List;

import jxl.common.Logger;

public class FormManager {
	//private Logger logger = Logger.getLogger(FormManager.class);
	
	private FormDAO dao = new FormDAO();
	
	public ArrayList getForms(int user_id){
		return dao.getForms(user_id);
	}
	
	public ArrayList getFormFields(int form_id){
		return dao.getFormFields(form_id);
	}
	
	public ArrayList getFormFieldsByFormName(int user_id, String table_name){
		return dao.getFormFieldsByFormName(user_id, table_name);
	}
}