package com.philip.edu.rule;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.philip.edu.basic.HibernateUtil;

public class RuleDAO {
	
	public ArrayList getRules(int form_id){
		Session session = null;
		ArrayList rules = new ArrayList();
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		rules = (ArrayList) session.createQuery("FROM Rule WHERE form_id="+form_id).list();
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return rules;
	}
}
