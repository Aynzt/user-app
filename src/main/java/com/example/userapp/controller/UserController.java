package com.example.userapp.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.userapp.service.UserService;
import com.example.userapp.util.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<Void> register(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder) {
		
		Long id = userService.register(userDTO);
		
		UriComponents uriComponents = uriBuilder.path("/users/{id}").buildAndExpand(id);
		
		return ResponseEntity.created(uriComponents.toUri()).build();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getInfo(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getInfo(id));
	}
	
}
