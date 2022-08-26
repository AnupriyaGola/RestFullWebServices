package com.in28minutes.rest.webservices.restwebservices.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class UserDao {

	public static List<Person> users = new ArrayList();
	static int counterId = 3;
	
	static {
		users.add(new Person(1,"pranav",new Date()));
		users.add(new Person(2,"anu",new Date()));
		users.add(new Person(3,"vikki",new Date()));
	}
	
	public List<Person> getUsers() {
		return users;
	}
	
	public Person getSingleUser(int id) {
		for(Person user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public Person createUser(Person user) {
		if(user.getId() == 0) {
			user.setId(++counterId);
		}
		users.add(user);
		return user;
	}
	
	public Person deleteUser(int id) {
		Iterator<Person> iterator =  users.iterator();
		while(iterator.hasNext()) {
			Person user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
}
