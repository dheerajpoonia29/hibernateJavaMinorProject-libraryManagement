package com.jbdl.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbdl.library.entity.BookEntity;
import com.jbdl.library.model.request.BookRequest;
import com.jbdl.library.model.response.AuthorResponse;
import com.jbdl.library.model.response.BookResponse;
import com.jbdl.library.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	BookService service;
	
	@GetMapping("/test")
	public String test() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody BookRequest req) {
		return service.create(req);
	}
	
	@GetMapping("/read")
	public ArrayList<BookResponse> readAll() {
		List<BookEntity> entities = service.readAll();
		ArrayList<BookResponse> response =  new ArrayList<>();
		entities.forEach(entity -> {
			AuthorResponse authorResponse = new AuthorResponse();
			response.add(new BookResponse(
					entity.getId(),
					entity.getName(),
					entity.getTotalPage(),
					entity.getLanguage(),
					entity.getAvailable(),
					entity.getGenre(),
					entity.getIsbnNo(),
					entity.getPublishedDate(),
					authorResponse));
		});
		return response;
	}
	
	@GetMapping("/read/{id}")
	public BookResponse readById(@PathVariable int id) {
		BookResponse response;
		try {
			BookEntity entity = service.readById(id);
			AuthorResponse authorResponse = new AuthorResponse();
			response = new BookResponse(
					entity.getId(),
					entity.getName(),
					entity.getTotalPage(),
					entity.getLanguage(),
					entity.getAvailable(),
					entity.getGenre(),
					entity.getIsbnNo(),
					entity.getPublishedDate(),
					authorResponse);
		} catch (NullPointerException e) {
			System.out.println("Author not found");
			response = new BookResponse();
		}
		return response;
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody BookRequest req) {
		return service.update(req, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return service.delete(id);
	}
}
