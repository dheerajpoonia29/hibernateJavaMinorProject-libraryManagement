package com.jbdl.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbdl.library.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	@Autowired
	AuthorService service;
	
	@GetMapping
	String test() {
		return service.test();
	}
}
