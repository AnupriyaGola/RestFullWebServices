package com.in28minutes.rest.webservices.restwebservices.users;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity

public class Person {

@Id
@GeneratedValue
private int id;	

@Size(min=2, message="Name should have altleast 2 characters")
private String name;

@Past
private Date birthDtae;

public int getId() {
	return id;
}
public String getName() {
	return name;
}
public Date getBirthDtae() {
	return birthDtae;
}
protected Person() {
}
public void setId(int id) {
	this.id = id;
}
public void setName(String name) {
	this.name = name;
}
public void setBirthDtae(Date birthDtae) {
	this.birthDtae = birthDtae;
}
@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", birthDtae=" + birthDtae + "]";
}
public Person(int id, String name, Date birthDtae) {
	super();
	this.id = id;
	this.name = name;
	this.birthDtae = birthDtae;
}

@OneToMany(mappedBy="user")
private List <Post> post;

public List<Post> getPost() {
	return post;
}
public void setPost(List<Post> post) {
	this.post = post;
}
}

