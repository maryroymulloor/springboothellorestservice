package com.sample.springboot.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springboot.hello.entities.User;

@Repository
public interface UserRepositories extends JpaRepository <User, Long>{

	public User findByUsername(String userName);
}
