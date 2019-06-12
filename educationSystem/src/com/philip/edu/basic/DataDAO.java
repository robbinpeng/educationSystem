package com.philip.edu.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.philip.edu.rule.DBHelper;


public class DataDAO {
	
	private Logger logger = Logger.getLogger(DataDAO.class);
	
	public ArrayList getTableData(ArrayList fields, String tbl_name){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DataInfo data = null;
		ArrayList result = new ArrayList();
		ArrayList line = new ArrayList();
		StringBuffer sb = new StringBuffer("select id, ");
		
		//create sql:
		for(int i=0; i<fields.size(); i++){
			FormField field = (FormField) fields.get(i);
			//Caption:
			data = new DataInfo();
			data.setId(i+1);
			data.setValue(field.getBus_name());
			line.add(data);
			
			//sql:
			if(i != fields.size()-1){
				sb.append(field.getPhysic_name() + ", ");
			} else {
				sb.append(field.getPhysic_name());
			}
		}
		result.add(line);
		sb.append(" from " + tbl_name + " order by id");
		
		
		try {
			conn = DBHelper.getConnection();
	
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				line = new ArrayList();
					
				for(int i=1; i<=fields.size(); i++){
					data = new DataInfo();
					data.setId(i);
					data.setValue(rs.getString(i+1));
					line.add(data);
				}
				
				result.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("连接数据库错误！");
		}
			
		return result;
	}
}
