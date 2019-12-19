package com.sample.springboot.hello.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.sample.springboot.hello.entities.User;
import com.sample.springboot.hello.exceptions.UserAlreadyExistsException;
import com.sample.springboot.hello.exceptions.UserNotFoundException;
import com.sample.springboot.hello.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService; 
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUserList();
	} 
	
	@PostMapping("/createUser") 
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
		try {   
			userService.createUser(user);
			HttpHeaders httpheader = new HttpHeaders();
			httpheader.setLocation(builder.path("users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(httpheader, HttpStatus.CREATED);
		}
		catch(UserAlreadyExistsException ex) {
			throw new ResponseStatusException (HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping("/getUserById/{id}")
	public Optional<User> getUserById(@PathVariable ("id") Long id){
		try {
			return userService.getUserById(id);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
		}
		
	}
	
	@PostMapping("/updateUserById/{id}")
	public User updateUserById(@PathVariable ("id") Long id, @RequestBody User user) {
		return userService.updateUserById(id, user);
	}
	
	@DeleteMapping("/deleteUserById/{id}")
	public void deleteUserById(@PathVariable ("id") Long id) {
		userService.deleteUserById(id);
	}
	
	@GetMapping("/hello")
	public String getHelloWorld(){
		return "Hello World";
	}
	
	@GetMapping("/getUserByUsername/{username}")
	public User getUserByUsername(@PathVariable ("username") String username) {
		return userService.findUserByUsername(username);
	}
}
