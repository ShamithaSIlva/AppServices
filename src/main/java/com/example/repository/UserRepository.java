package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.entity.User;

public interface UserRepository  extends CrudRepository<User, Long> {
	@Query(value = "SELECT * FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
	  User findByUserName(String username);
}