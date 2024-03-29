package com.jbdl.library.model.request;

public class AuthorRequest {
	int age;
	String name;
	String country;
	String email;
	
	public AuthorRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AuthorRequest(int age, String name, String country, String email) {
		super();
		this.age = age;
		this.name = name;
		this.country = country;
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "AuthorRequest [age=" + age + ", name=" + name + ", country=" + country + ", email=" + email + "]";
	}
	
}
