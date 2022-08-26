package com.in28minutes.rest.webservices.restwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	
	@GetMapping(value="/v1/getPerson", params="version-1")
	public Person1 getParam1() {
		return new Person1("Anupriya Gola");
	}
	
	@GetMapping(value="/v1/getPerson", params="version-2")
	public Person2 getParam2() {
		return new Person2(new Name("Anupriya","Gola"));
	}
	
	@GetMapping(value="/person/header", headers="X-APIVERSION=1")
	public Person1 getheader1() {
		return new Person1("Anupriya Gola");
	}
	
	@GetMapping(value="/person/header", headers="X-APIVERSION=2")
	public Person2 getheader2() {
		return new Person2(new Name("Anupriya","Gola"));
	}
	
	
	@GetMapping("/v2/getPerson")
	public Person2 getPerson2() {
		return new Person2(new Name("Anupriya","Gola"));
	}
	

}
