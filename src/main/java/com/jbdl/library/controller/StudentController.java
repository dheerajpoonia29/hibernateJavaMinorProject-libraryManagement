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

import com.jbdl.library.entity.StudentEntity;
import com.jbdl.library.model.request.StudentRequest;
import com.jbdl.library.model.response.StudentResponse;
import com.jbdl.library.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService service;
	
	@GetMapping("/test")
	public String test() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody StudentRequest req) {
		return service.create(req);
	}
	
	@GetMapping("/read")
	public ArrayList<StudentResponse> readAll() {
		List<StudentEntity> entities = service.readAll();
		ArrayList<StudentResponse> response =  new ArrayList<>();
		entities.forEach(entity -> {
			response.add(new StudentResponse(
					entity.getId(),
					entity.getAge(),
					entity.getName(),
					entity.getCountry(),
					entity.getEmail(),
					entity.getPhoneNo(),
					entity.getCreatedOn(),
					entity.getUpdatedOn()));
		});
		return response;
	}
	
	@GetMapping("/read/{id}")
	public StudentResponse readById(@PathVariable int id) {
		System.out.println("read student id:"+id);
		StudentResponse response;
		try {
			StudentEntity entity = service.readById(id);
			response = new StudentResponse(
					entity.getId(),
					entity.getAge(),
					entity.getName(),
					entity.getCountry(),
					entity.getEmail(),
					entity.getPhoneNo(),
					entity.getUpdatedOn(),
					entity.getCreatedOn());
		} catch (NullPointerException e) {
			response = new StudentResponse();
		}
		System.out.println("response: "+response);
		return response;
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody StudentRequest req) {
		return service.update(req, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return service.delete(id);
	}
}
