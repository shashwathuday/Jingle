package com.pdp.jingle.models;

public class Artist {
	String id;
	String firstName;
	String lastName;
	String location;

	public Artist(String id, String firstName, String lastName, String location) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
