package com.jbdl.library.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.jbdl.library.LibraryManagement;
import com.jbdl.library.entity.AuthorEntity;
import com.jbdl.library.entity.BookEntity;
import com.jbdl.library.model.request.BookRequest;

@Service
public class BookService {
	
	public String create(BookRequest detail) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		AuthorEntity authorEntity = new AuthorEntity();
		BookEntity entity = new BookEntity(
				0,
				detail.getName(),
				detail.getTotalPage(),
				detail.getLanguage(),
				detail.getAvailable(),
				detail.getGenre(),
				detail.getIsbnNo(),
				detail.getPublishedDate(),
				authorEntity);
		session.save(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Book created";
	}

	public List<BookEntity> readAll() {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from BookEntity");
		List<BookEntity> entities = (List<BookEntity>) query.list();
		session.flush();
		transaction.commit();
		session.close();
		return entities;
	}

	public BookEntity readById(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from BookEntity where id=:id");
		query.setParameter("id", id);
		BookEntity entity = (BookEntity) query.uniqueResult();
		session.flush();
		transaction.commit();
		session.close();
		return entity;
	}

	public String update(BookRequest detail, int id) {
		System.out.println(detail.toString());
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		BookEntity entity = readById(id);
		if(entity==null) {
			return "Book not found";
		}
//		entity.setAge(detail.getAge()==0?entity.getAge():detail.getAge());
		
		System.out.println(entity.toString());
		session.saveOrUpdate(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Book updated";
	}
	
	public String delete(int id) {
		Session session = LibraryManagement.SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		BookEntity entity = readById(id);
		if(entity==null) {
			return "Book not found";
		}
		session.delete(entity);
		session.flush();
		transaction.commit();
		session.close();
		return "Book deleted";
	}
}