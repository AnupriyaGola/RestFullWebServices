package com.in28minutes.rest.webservices.restwebservices.users;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("jpa/users")
	public List<Person> getUsers() {
		return repository.findAll();
	}
	
	@GetMapping("jpa/users/{id}")
	public EntityModel<Person> getOneUser(@PathVariable int id) {
		Optional<Person> person = repository.findById(id);
		if(!person.isPresent()) {
			throw new UserNotFoundException("id is :"+id);
		}
		
		EntityModel<Person> model = EntityModel.of(person.get());
		WebMvcLinkBuilder LinkToUsers= linkTo(methodOn(this.getClass()).getUsers());
		model.add(LinkToUsers.withRel("all-users"));
		return model;
	}
	
	@PostMapping("jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody Person user) {
		Person savedUser = repository.save(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
	     return ResponseEntity.created(location).build();
	}
	
	@PostMapping("jpa/users/{id}/posts")
	public ResponseEntity<Object> creatPost(@PathVariable int id, @RequestBody Post post) {
		Optional<Person> optionalUser = repository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("id is :"+id);
		}
		Person user = optionalUser.get();
		post.setUser(user);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
	     return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		repository.deleteById(id);
	}
	
	@GetMapping("jpa/users/{id}/posts")
	public List<Post> getPosts(@PathVariable int id) {
		 Optional<Person> user = repository.findById(id);
		 if(!user.isPresent()) {
			 throw new UserNotFoundException("id is :"+id);
		 }
		 
		 return user.get().getPost();
	}

}
