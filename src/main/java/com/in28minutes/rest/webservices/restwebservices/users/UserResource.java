package com.in28minutes.rest.webservices.restwebservices.users;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDao userService;
	
	@GetMapping("/users")
	public List<Person> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<Person> getOneUser(@PathVariable int id) {
		Person user = userService.getSingleUser(id);
		if(user == null) {
			throw new UserNotFoundException("id is :"+id);
		}
		
		EntityModel<Person> model = EntityModel.of(user);
		WebMvcLinkBuilder LinkToUsers= linkTo(methodOn(this.getClass()).getUsers());
		model.add(LinkToUsers.withRel("all-users"));
		return model;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody Person user) {
		Person savedUser = userService.createUser(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
	     return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public Person deleteUser(@PathVariable int id) {
		Person deletedUser = userService.deleteUser(id);
		if(deletedUser == null) {
			throw new UserNotFoundException("id is :"+id);
		}
		return deletedUser;
	}

}
