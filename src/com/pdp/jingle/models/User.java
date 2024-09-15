package com.pdp.jingle.models;

public class User {
	String id;
	String firstName;
	String lastName;
	String email;
	String password;
	String location;
	String dp;

	public User(String id, String firstName, String lastName, String email, String password, String location,
			String dp) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.location = location;
		this.dp = dp;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getLocation() {
		return location;
	}

	public String getDp() {
		return dp;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

}
