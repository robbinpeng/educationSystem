package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import com.philip.edu.basic.Rule;

public class RuleManager {
	private Logger logger = Logger.getLogger(RuleManager.class);
	
	private static RuleDAO dao = new RuleDAO(); 
	
	public MessageInfo rulesCheck(int form_id, Workbook wb){
		MessageInfo message = null;
		
		ArrayList rules = getRules(form_id);
		
		for(int i=0; i<rules.size(); i++){
			Rule rule = (Rule)rules.get(i);
			int type = rule.getRule_class();
			String rule_content = rule.getRule_content();
			JSONObject object = new JSONObject(rule_content);
			
			switch(type){
				case 1:
					//
					break;
				case 2:
					//Exclusive Check:
					Rule2ExclusiveCheck engine2 = new Rule2ExclusiveCheck();
					message = engine2.getMessage(wb, object, form_id);
					break;
				case 3:
					//
					break;
				case 4:
					//Only Check:
					Rule4NoRepeatCheck engine4 = new Rule4NoRepeatCheck();
					message = engine4.getMessage(wb, object);
					break;
				case 5:
					//
					break;
				case 6:
					//
					break;
				default:
					break;
			}
		}
		
		return message;
	}
	
	public ArrayList getRules(int form_id){
		return dao.getRules(form_id);
	}
}
