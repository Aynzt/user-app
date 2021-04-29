package com.example.userapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByLogin(String login);
	
}
