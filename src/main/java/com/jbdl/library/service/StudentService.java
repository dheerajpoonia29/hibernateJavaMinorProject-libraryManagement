package com.jbdl.library.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.jbdl.library.LibraryManagement;
import com.jbdl.library.entity.StudentEntity;
import com.jbdl.library.model.request.StudentRequest;

@Service
public class StudentService {
	
	public String create(StudentRequest detail) {
		System.out.println("detail: "+detail);
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		StudentEntity entity = new StudentEntity(
				detail.getAge(), 
				detail.getName(), 
				detail.getCountry(), 
				detail.getEmail(),
				detail.getPhoneNo(), 
				new Date(),
				null);
		System.out.println("entity: "+entity);
//		entity.setStudentCard(detail.getCard());
		session.save(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Student created";
	}

	public List<StudentEntity> readAll() {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from StudentEntity");
		List<StudentEntity> entities = (List<StudentEntity>) query.list();
		session.flush();
		transaction.commit();
		session.close();
		return entities;
	}

	public StudentEntity readById(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from StudentEntity where id=:id");
		query.setParameter("id", id);
		System.out.println("query: "+query.toString());
		StudentEntity studentEntity = (StudentEntity) query.uniqueResult();
		session.flush();
		transaction.commit();
		session.close();
		return studentEntity;
	}

	public String update(StudentRequest detail, int id) {
		System.out.println(detail.toString());
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		StudentEntity entity = readById(id);
		if(entity==null) {
			return "Student not found";
		}
		entity.setAge(detail.getAge()==0?entity.getAge():detail.getAge());
		entity.setName(detail.getName()==null?entity.getName():detail.getName());
		entity.setCountry(detail.getCountry()==null?entity.getCountry():detail.getCountry());
		entity.setEmail(detail.getEmail()==null?entity.getEmail():detail.getEmail());
		entity.setPhoneNo(detail.getPhoneNo()==0?entity.getPhoneNo():detail.getPhoneNo());
		entity.setUpdatedOn(new Date());
//		entity.setStudentCard(detail.getCard()==null?entity.getStudentCard():detail.getCard());
		System.out.println(entity.toString());
		session.saveOrUpdate(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Student updated";
	}
	
	public String delete(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		StudentEntity entity = readById(id);
		if(entity==null) {
			return "Student not found";
		}
		session.delete(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Student deleted";
	}
}
