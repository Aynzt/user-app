package com.example.userapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.userapp.service.UserService;
import com.example.userapp.util.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	private UserDTO dto;

	@BeforeEach
	void setup() {				
		dto = new UserDTO();
		dto.setAddress("address");
		dto.setAge(20);
		dto.setFirstname("test");
		dto.setLastname("test");
		dto.setLogin("test-login");
		dto.setPwd("test-pwd");
		dto.setResident(false);
	}
	
	@Test
	void whenRegistering_mustReturn200() throws Exception {			
		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isCreated());		
	}
	
	@Test
	void whenGettingInfo_mustReturn200() throws Exception {		
		mockMvc.perform(get("/users/{id}", 1L)
				.content("application/json"))
		.andExpect(status().isOk());		
	}
	
}
