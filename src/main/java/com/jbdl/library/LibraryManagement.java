package com.jbdl.library;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class LibraryManagement {
	private static final Configuration CONNECTION = new Configuration();
	public static final SessionFactory SESSION_FACTORY = CONNECTION.configure(new File("src/main/java/resources/hibernate.cfg.xml")).buildSessionFactory();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(LibraryManagement.class, args);
	}
}
