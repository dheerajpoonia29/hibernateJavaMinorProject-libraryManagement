package com.jbdl.library.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbdl.library.LibraryManagement;
import com.jbdl.library.entity.CardEntity;
import com.jbdl.library.entity.StudentEntity;
import com.jbdl.library.model.request.CardRequest;

import com.jbdl.library.service.StudentService;

@Service
public class CardService {
	@Autowired
	StudentService studentService;
	
	public String create(CardRequest detail) {
		System.out.println("card create request:"+detail);
		StudentEntity studentEntity = studentService.readById(detail.getStudentId());
		System.out.println("studentEntity:"+studentEntity);
		if(studentEntity==null || studentEntity.getId()==0) {
			System.out.println("Student not found");
			return "Card not created, request student not found";
		} else {
			Session session = LibraryManagement.SESSION_FACTORY.openSession();
			Transaction transaction = session.beginTransaction();
			Date validUpto = new Date();
			validUpto.setMonth((new Date()).getMonth()+1);
			CardEntity entity = new CardEntity(
					detail.getStatus(), 
					studentEntity.getEmail(), 
					validUpto,
					new Date(),
					null,
					studentEntity);
			System.out.println("cardEntity:"+entity);
			session.save(entity);
			session.flush();
			transaction.commit();
			session.close();
			return "Card created";
		}
	}

	public List<CardEntity> readAll() {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from CardEntity");
		List<CardEntity> entities = (List<CardEntity>) query.list();
		session.flush();
		transaction.commit();
		session.close();
		return entities;
	}

	public CardEntity readById(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from CardEntity where id=:id");
		query.setParameter("id", id);
		CardEntity entity = (CardEntity) query.uniqueResult();
		session.flush();
		transaction.commit();
		session.close();
		return entity;
	}

	public String update(CardRequest detail, int id) {
		System.out.println("2: "+detail.toString());
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		CardEntity entity = readById(id);
		if(entity==null) {
			return "Card not found";
		}
		entity.setStatus(detail.getStatus()!=entity.getStatus()?detail.getStatus():entity.getStatus());
		entity.setValidUpto(detail.getValidUpto()==null?entity.getValidUpto():detail.getValidUpto());
		entity.setUpdatedOn(new Date());
		System.out.println(entity.toString());
		session.saveOrUpdate(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Card updated";
	}
	
	public String delete(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		CardEntity entity = readById(id);
		if(entity==null) {
			return "Card not found";
		}
		session.delete(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Card deleted";
	}
}