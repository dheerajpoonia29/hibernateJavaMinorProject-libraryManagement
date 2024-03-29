package com.jbdl.library.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.jbdl.library.LibraryManagement;
import com.jbdl.library.entity.AuthorEntity;
import com.jbdl.library.model.request.AuthorRequest;

@Service
public class AuthorService {
	
	public String create(AuthorRequest detail) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		AuthorEntity entity = new AuthorEntity(
				0,
				detail.getAge(), 
				detail.getName(), 
				detail.getEmail(),
				detail.getCountry());
		session.save(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Author created";
	}

	public List<AuthorEntity> readAll() {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from AuthorEntity");
		List<AuthorEntity> entities = (List<AuthorEntity>) query.list();
		session.flush();
		transaction.commit();
		session.close();
		return entities;
	}

	public AuthorEntity readById(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from AuthorEntity where id=:id");
		query.setParameter("id", id);
		AuthorEntity entity = (AuthorEntity) query.uniqueResult();
		session.flush();
		transaction.commit();
		session.close();
		return entity;
	}

	public String update(AuthorRequest detail, int id) {
		System.out.println(detail.toString());
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		AuthorEntity entity = readById(id);
		if(entity==null) {
			return "Author not found";
		}
		entity.setAge(detail.getAge()==0?entity.getAge():detail.getAge());
		entity.setName(detail.getName()==null?entity.getName():detail.getName());
		entity.setCountry(detail.getCountry()==null?entity.getCountry():detail.getCountry());
		entity.setEmail(detail.getEmail()==null?entity.getEmail():detail.getEmail());
		System.out.println(entity.toString());
		session.saveOrUpdate(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Author updated";
	}
	
	public String delete(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		AuthorEntity entity = readById(id);
		if(entity==null) {
			return "Author not found";
		}
		session.delete(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Author deleted";
	}
}