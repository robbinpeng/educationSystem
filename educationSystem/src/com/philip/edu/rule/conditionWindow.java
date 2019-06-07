package com.philip.edu.rule;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

public class conditionWindow extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	@Listen("onClick=#frontConButton")
	public void addFrontCondition(){
		Div myDiv = new Div();
		Combobox combox = new Combobox();
		combox.appendItem("ѧУ");
		Image image = new Image("/test");
		ZScript script = new ZScript("java","delItem()");
		image.addEventHandler("onClick", new EventHandler(script));
	}
}
