package com.philip.edu.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.philip.edu.basic.FormField;
import com.philip.edu.basic.HibernateUtil;

public class UploadDAO {
	public boolean uploadData(ArrayList list){
		Session session = null;
		boolean b = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			for(int i=0; i<list.size(); i++){
				String sql = (String)list.get(i);
				
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, new Date());
				query.setParameter(1, new Date());
				query.setParameter(2, new Date());
				query.executeUpdate();
			}

			session.getTransaction().commit();
			b=true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return b;
	}

}
