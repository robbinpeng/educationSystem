package com.philip.edu.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zel.ImportHandler;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.rule.MessageInfo;
import com.philip.edu.rule.RuleManager;

import org.apache.log4j.Logger;

public class UploadController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(UploadController.class);
	private FormManager formManager = new FormManager();
	
	@Wire
	private Listbox formlist;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Form> forms = formManager.getForms(Constants.USER_ID);
		formlist.setModel(new ListModelList<Form>(forms));
	}
	
	@Listen("onUpload = #formlist")
	public void uploadExcel(Event event){
		Form form = (Form)event.getData();
		
		UploadEvent ue = null;
	    if (event instanceof UploadEvent) {
	        ue = (UploadEvent) event;
	    } else if (event instanceof ForwardEvent) {
	        ue = (UploadEvent) ((ForwardEvent) event).getOrigin();
	    }				
		
		Workbook wb = null;
		ArrayList list = null;		
		
		RuleManager engine = new RuleManager();
		MessageInfo message = null;
		String sMessage = "";
		//1.check it's excel;
		Media media = ue.getMedia();
		if("application/vnd.ms-excel".equals(media.getContentType())){
			//2.load data from excel;
			try {
				wb = WorkbookFactory.create(media.getStreamData());
				list = engine.rulesCheck(form.getId(), wb);
				for(int j=0; j<list.size(); j++){
					message = (MessageInfo)list.get(j);
					if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){}
					else {
						ArrayList al = message.getMessage_info();
						if(al.size()!=0)sMessage += message.getFail_information() + ":\n";
						for(int i=0; i<al.size(); i++){ 
							System.out.println(al.get(i).toString());
							sMessage += al.get(i).toString() + "\n";
						}
						sMessage += "\n";
					}
				}
				//create a window programmatically and use it as a modal dialog.
				HashMap map = new HashMap();
				map.put("message", sMessage);
		        Window window1 = (Window)Executions.createComponents(
		                "/rule_check.zul", null, map);
		       
		        window1.doModal();
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			}
			//3.save data into database;
		} else {
			Messagebox.show("您上传的不是excel表格！");
		}	
	}
	
}
