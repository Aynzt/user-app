package com.example.userapp.service;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.util.dto.UserDTO;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {
	
	private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	public Long register(UserDTO userDTO) {		
		// checking if user exists
		if (userRepo.findByLogin(userDTO.getLogin()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
		// creating user	
		User user = userRepo.save(convertToEntity(userDTO));
		
		return user.getId();
	}
	
	public UserDTO getInfo(Long id) {
		return convertToDTO(
				userRepo.findById(id)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
	
	private UserDTO convertToDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setAddress(user.getAddress());
		dto.setAge(user.getAge());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		dto.setLogin(user.getLogin());
		dto.setPwd(user.getPwd());
		dto.setResident(user.isResident());
		return dto;
	}
	
	private User convertToEntity(UserDTO userDTO) {
		User user = new User();
		user.setAddress(userDTO.getAddress());
		user.setAge(userDTO.getAge());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setLogin(userDTO.getLogin());
		user.setPwd(BCrypt.with(new SecureRandom()).hashToString(10, userDTO.getPwd().toCharArray()));
		user.setResident(userDTO.isResident());
		return user;
	}
}
