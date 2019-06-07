package com.philip.edu.excel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Messagebox;

public class UploadController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	@Listen("onUpload=#uploadButton")
	public void uploadExcel(){
		//1.check it's excel;
		//2.load data from excel;
		//3.save data into database;
	}
	
}
