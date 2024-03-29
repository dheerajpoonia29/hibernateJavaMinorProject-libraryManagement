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

import com.jbdl.library.entity.AuthorEntity;
import com.jbdl.library.model.request.AuthorRequest;
import com.jbdl.library.model.response.AuthorResponse;
import com.jbdl.library.service.AuthorService;
import com.jbdl.library.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	@Autowired
	AuthorService service;
	
	@GetMapping("/test")
	public String test() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody AuthorRequest req) {
		return service.create(req);
	}
	
	@GetMapping("/read")
	public ArrayList<AuthorResponse> readAll() {
		List<AuthorEntity> entities = service.readAll();
		ArrayList<AuthorResponse> response =  new ArrayList<>();
		entities.forEach(entity -> {
			response.add(new AuthorResponse(
					entity.getId(),
					entity.getAge(),
					entity.getName(),
					entity.getCountry(),
					entity.getEmail()));
		});
		return response;
	}
	
	@GetMapping("/read/{id}")
	public AuthorResponse readById(@PathVariable int id) {
		AuthorResponse response;
		try {
			AuthorEntity entity = service.readById(id);
			response = new AuthorResponse(
					entity.getId(),
					entity.getAge(),
					entity.getName(),
					entity.getCountry(),
					entity.getEmail());
		} catch (NullPointerException e) {
			System.out.println("Author not found");
			response = new AuthorResponse();
		}
		return response;
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody AuthorRequest req) {
		return service.update(req, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return service.delete(id);
	}
}
