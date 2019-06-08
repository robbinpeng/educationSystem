package com.philip.edu.basic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import jxl.common.Logger;

public class FormDAO {
	//private Logger logger = Logger.getLogger(FormDAO.class);
	
	public ArrayList getForms(int user_id){
		Session session = null;
		ArrayList forms = new ArrayList();
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		forms = (ArrayList) session.createQuery("FROM Form WHERE user_id="+user_id).list();
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return forms;
	}
	
	public ArrayList getFormFields(int form_id){
		Session session = null;
		ArrayList fields = new ArrayList();
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		fields = (ArrayList) session.createQuery("FROM FormField WHERE form_id=" + form_id + " order by sequence").list();
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return fields;
	}
	
	public ArrayList getFormFieldsByFormName(int user_id, String table_name){
		Session session = null;
		ArrayList forms = new ArrayList();
		ArrayList fields = new ArrayList();
		Form form = null;
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		forms = (ArrayList)session.createQuery("FROM Form WHERE user_id=" + user_id + " AND tbl_name='" + table_name + "'").list();
		for(int i=0; i<forms.size(); i++){
			form = (Form)forms.get(i);
			break;
		}
		
		if(form!=null){
			fields = (ArrayList)session.createQuery("FROM FormField WHERE form_id=" + form.getId() + " order by sequence").list();
		}
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return fields;
	}
}