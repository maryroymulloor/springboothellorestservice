package com.sample.springboot.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sample.springboot.hello.entities.User;
import com.sample.springboot.hello.exceptions.UserAlreadyExistsException;
import com.sample.springboot.hello.exceptions.UserNotFoundException;
import com.sample.springboot.hello.repositories.UserRepositories;

@Service
public class UserService {

	@Autowired
	private UserRepositories userRepository;
	
	public List<User>getAllUserList(){
		return userRepository.findAll();
	}
	
	public User createUser(User user) throws UserAlreadyExistsException{
		String username = user.getUsername();
		if(userRepository.findByUsername(username)!=null) {
			throw new UserAlreadyExistsException("This user "+username+" already exists in system");
		};
		return userRepository.save(user);
		
	}
	
	public Optional<User> getUserById(Long id) throws UserNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User is not found in repository");
		}
		return user;
	}
	
	public User updateUserById(Long id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		if( userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		};
	}
	
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
