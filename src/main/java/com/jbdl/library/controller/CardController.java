package com.jbdl.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbdl.library.entity.CardEntity;
import com.jbdl.library.entity.StudentEntity;
import com.jbdl.library.model.request.CardRequest;
import com.jbdl.library.model.response.CardResponse;
import com.jbdl.library.model.response.StudentResponse;
import com.jbdl.library.service.CardService;
import com.jbdl.library.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {
	@Autowired
	CardService service;
	
	@GetMapping("/test")
	public String test() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody CardRequest req) {
		try {
		return service.create(req);
		} catch(ConstraintViolationException e) {
			return "Not created, card already exist";
		}
	}
	
	@GetMapping("/read")
	public ArrayList<CardResponse> readAll() {
		List<CardEntity> entities = service.readAll();
		ArrayList<CardResponse> response =  new ArrayList<>();
		entities.forEach(cardEntity -> {
			System.out.println("card entity: "+cardEntity);
			System.out.println("card entity student: "+cardEntity.getStudent());
			StudentEntity studentEntity = cardEntity.getStudent();
			StudentResponse studentResponse = new StudentResponse(
					studentEntity.getId(),
					studentEntity.getAge(),
					studentEntity.getName(),
					studentEntity.getCountry(),
					studentEntity.getEmail(),
					studentEntity.getPhoneNo(),
					studentEntity.getCreatedOn(),
					studentEntity.getUpdatedOn());
			response.add(new CardResponse(
					cardEntity.getId(),
					cardEntity.getStatus(),
					cardEntity.getEmail(),
					cardEntity.getValidUpto(),
					cardEntity.getCreatedOn(),
					cardEntity.getUpdatedOn(),
					studentResponse));
		});
		return response;
	}
	
	@GetMapping("/read/{id}")
	public CardResponse readById(@PathVariable int id) {
		CardResponse response;
		try {
			CardEntity cardEntity = service.readById(id);
			StudentEntity studentEntity = cardEntity.getStudent();
			StudentResponse studentResponse = new StudentResponse(
					studentEntity.getId(),
					studentEntity.getAge(),
					studentEntity.getName(),
					studentEntity.getCountry(),
					studentEntity.getEmail(),
					studentEntity.getPhoneNo(),
					studentEntity.getCreatedOn(),
					studentEntity.getUpdatedOn());
			response = new CardResponse(
					cardEntity.getId(),
					cardEntity.getStatus(),
					cardEntity.getEmail(),
					cardEntity.getValidUpto(),
					cardEntity.getCreatedOn(),
					cardEntity.getUpdatedOn(),
					studentResponse);
		} catch (NullPointerException e) {
			System.out.println("Card not found");
			response = new CardResponse();
		}
		return response;
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody CardRequest req) {
		System.out.println("1");
		return service.update(req, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return service.delete(id);
	}
}
