package com.philip.edu.basic;

import java.util.ArrayList;

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
}
